package com.a9ae0b01f0ffc.infinite_auth.granting

import com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_base_5_context
import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.AuthenticationType
import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.AuthorizationType
import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.IdentityType
import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.ScopeType
import com.a9ae0b01f0ffc.infinite_auth.server.ApiResponseMessage
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.time.TimeCategory
import io.jsonwebtoken.Jwt
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import java.security.Key

import static base.T_common_base_1_const.*
import static base.T_common_base_3_utils.*
import static com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_base_4_const.*
import static com.a9ae0b01f0ffc.infinite_auth.validation.Validation.remove_jwt_bearer

@Path("/{resource: authorizations|Authorizations}")
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
class Authorization {
    String authorizationName
    Long authorizationId

    Identity identity

    Scope scope

    Integer durationSeconds

    @JsonProperty("usage_limit")
    Integer maxUsageCount

    @JsonProperty("functional_data")
    HashMap<String, String> functionalFieldMap

    Authorization prerequisiteAuthorization
    @JsonIgnore
    Authorization refreshAuthorization
    @JsonFormat(timezone = "UTC")
    Date creationDate
    @JsonFormat(timezone = "UTC")
    Date expiryDate
    @JsonProperty("status")
    String authorizationStatus = GC_STATUS_NEW
    @JsonProperty("mdwl_error_number")
    String errorCode
    @JsonProperty("mdwl_error_text")
    String errorText
    @JsonProperty("token")
    String jwt
    String authorizationType

    @Autowired
    @JsonIgnore
    T_auth_grant_base_5_context p_app_context


    void success(T_auth_grant_base_5_context i_context) {
        this.authorizationStatus = GC_STATUS_SUCCESSFUL
        this.authorizationId = Long.parseLong(new Date().format("yyMMddHHmmssSSS"))
        if (is_null(this.jwt)) {
            set_validity()
            set_jwt(i_context)
        }
    }

    void failure(String i_error_code) {
        this.authorizationStatus = GC_STATUS_FAILED
        this.errorCode = i_error_code
        this.functionalFieldMap = GC_NULL_OBJ_REF as HashMap<String, String>
        this.scope.keyFieldMap = GC_NULL_OBJ_REF as HashMap<String, String>
        this.identity.authenticationSet.forEach { l_authentication ->
            l_authentication.keyFieldMap = GC_NULL_OBJ_REF as HashMap<String, String>
            l_authentication.functionalFieldMap = GC_NULL_OBJ_REF as HashMap<String, String>
        }
        this.authorizationId = Long.parseLong(new Date().format("yyMMddHHmmssSSS"))
    }

    static Boolean is_invalid_access_jwt(String i_jwt_string, T_auth_grant_base_5_context i_context) {
        try {
            return (access_jwt2authorization(i_jwt_string, i_context).authorizationStatus != GC_STATUS_SUCCESSFUL)
        } catch (Exception ignored) {
            return GC_TRUE
        }
    }

    static Boolean is_invalid_refresh_jwt(String i_jwt_string, T_auth_grant_base_5_context i_context) {
        try {
            return (refresh_jwt2authorization(i_jwt_string, i_context).authorizationStatus != GC_STATUS_SUCCESSFUL)
        } catch (Exception ignored) {
            return GC_TRUE
        }
    }

    void common_authorization_granting(AuthorizationType i_conf_authorization, T_auth_grant_base_5_context i_context) {
        Boolean l_is_authentication_needed
        Authorization l_user_authorization
        if (is_not_null(this.jwt)) {
            if (is_invalid_access_jwt(this.jwt, i_context)) {
                failure(GC_AUTHORIZATION_ERROR_CODE_01_INVALID_JWT)
                return
            }
            l_user_authorization = access_jwt2authorization(this.jwt, i_context)
            if (is_null(l_user_authorization.expiryDate)) {
                failure(GC_AUTHORIZATION_ERROR_CODE_02_EMPTY_EXPIRY)
                return
            }
            if (l_user_authorization.expiryDate.before(new Date())) {
                failure(GC_AUTHORIZATION_ERROR_CODE_03_EXPIRED)
                return
            }
            l_is_authentication_needed = GC_FALSE
        } else {
            l_user_authorization = this
            l_is_authentication_needed = GC_TRUE
        }
        if (l_user_authorization.authorizationName != i_conf_authorization.authorizationName) {
            failure(GC_AUTHORIZATION_ERROR_CODE_04_WRONG_NAME)
            return
        }
        if (is_not_null(i_conf_authorization.prerequisiteAuthorizationSet)) {
            if (not(i_conf_authorization.prerequisiteAuthorizationSet?.isEmpty())) {
                if (is_null(l_user_authorization.prerequisiteAuthorization)) {
                    failure(GC_AUTHORIZATION_ERROR_CODE_05_NO_PREREQUISITES)
                    return
                }
                Authorization l_prerequisite_user_auth = l_user_authorization.prerequisiteAuthorization
                if (l_prerequisite_user_auth.jwt != null) {
                    l_prerequisite_user_auth = access_jwt2authorization(l_prerequisite_user_auth.jwt, i_context)
                }
                if (i_context.p_revocation_repository.findByAuthorizationId(l_prerequisite_user_auth.authorizationId).size() > GC_EMPTY_SIZE) {
                    failure(GC_AUTHORIZATION_ERROR_CODE_MDWL9403A_REVOKED_PREREQUISITE)
                    return
                }
                if (is_not_null(l_prerequisite_user_auth.maxUsageCount)) {
                    if (i_context.p_usage_repository.findByAuthorizationId(l_prerequisite_user_auth.authorizationId).size() > l_prerequisite_user_auth.maxUsageCount) {
                        failure(GC_AUTHORIZATION_ERROR_CODE_MDWL9403B_EXCEEDED_PREREQUISITE)
                        return
                    }
                }
                for (AuthorizationType l_prerequisite_conf_authorization in i_conf_authorization.prerequisiteAuthorizationSet) {
                    l_prerequisite_user_auth.common_authorization_granting(l_prerequisite_conf_authorization, i_context)
                }
                if (l_prerequisite_user_auth.authorizationStatus != GC_STATUS_SUCCESSFUL) {
                    failure(GC_AUTHORIZATION_ERROR_CODE_MDWL9403_FAILED_PREREQUISITE)
                    return
                }
                if (l_prerequisite_user_auth.expiryDate.before(new Date())) {
                    failure(GC_AUTHORIZATION_ERROR_CODE_MDWL9401_EXPIRED_PREREQUISITE)
                    return
                }
                if (not(merge_field_maps(l_prerequisite_user_auth.scope?.keyFieldMap as HashMap<String, String>, l_prerequisite_user_auth.functionalFieldMap as HashMap<String, String>))) {
                    failure(GC_AUTHORIZATION_ERROR_CODE_18_DATA_CONSISTENCY)
                    return
                }
            }
        }
        if (is_null(l_user_authorization.identity)) {
            failure(GC_AUTHORIZATION_ERROR_CODE_12_NO_IDENTITY)
            return
        }
        if (is_null(l_user_authorization.identity.authenticationSet)) {
            failure(GC_AUTHORIZATION_ERROR_CODE_13_NO_AUTHENTICATIONS)
            return
        }
        if (is_null(l_user_authorization.identity.authenticationSet)) {
            failure(GC_AUTHORIZATION_ERROR_CODE_11_EMPTY_AUTHENTICATIONS)
            return
        }
        if (l_user_authorization.identity.authenticationSet.isEmpty()) {
            failure(GC_AUTHORIZATION_ERROR_CODE_14_EMPTY_AUTHENTICATIONS)
            return
        }
        List<AuthenticationType> l_sorted_conf_authentication_list = new LinkedList<AuthenticationType>()
        i_conf_authorization.identitySet.each { l_conf_identity ->
            if (l_conf_identity.identityName == l_user_authorization.identity.identityName) {
                l_sorted_conf_authentication_list = l_conf_identity.authenticationSet.sort { it -> it.authenticationName }
            }
        } as List<AuthenticationType>
        List l_sorted_user_authentication_list = l_user_authorization.identity.authenticationSet.sort { it -> it.authenticationName }
        if (l_user_authorization.identity.authenticationSet.size() != l_sorted_conf_authentication_list.size()) {
            failure(GC_AUTHORIZATION_ERROR_CODE_15_WRONG_AUTHENTICATIONS_NUMBER)
            return
        }
        Integer l_authentication_index = GC_ZERO
        if (l_is_authentication_needed) {
            for (Authentication l_user_authentication in l_sorted_user_authentication_list) {
                l_user_authentication.common_authentication_validation(l_sorted_conf_authentication_list[l_authentication_index], i_context, this)
                if (l_user_authentication.authenticationStatus == GC_STATUS_FAILED) {
                    this.errorText = l_sorted_conf_authentication_list[l_authentication_index].authenticationName
                    failure(GC_AUTHORIZATION_ERROR_CODE_MDWL8001_FAILED_AUTHENTICATION)
                    return
                }
                if (not(merge_field_maps(l_user_authentication.keyFieldMap, l_user_authentication.functionalFieldMap))) {
                    failure(GC_AUTHORIZATION_ERROR_CODE_18_DATA_CONSISTENCY)
                    return
                }
                l_authentication_index++
            }
        }
        l_user_authorization.durationSeconds = i_conf_authorization.durationSeconds
        l_user_authorization.maxUsageCount = i_conf_authorization.maxUsageCount
        Map<String, String> l_key_field_map = l_user_authorization.scope.keyFieldMap
        l_user_authorization.scope = i_conf_authorization.scopeSet.first().to_user_scope()
        l_user_authorization.scope.keyFieldMap = l_key_field_map
        l_user_authorization.refreshAuthorization = GC_NULL_OBJ_REF as Authorization
        l_user_authorization.authorizationType = "Access"
        l_user_authorization.success(i_context)
        if (i_conf_authorization.isRefreshAllowed) {
            l_user_authorization.refreshAuthorization = new Authorization()
            l_user_authorization.refreshAuthorization.authorizationName = nvl(i_conf_authorization.refreshAuthorizationName, this.authorizationName)
            l_user_authorization.refreshAuthorization.authorizationType = "Refresh"
            l_user_authorization.refreshAuthorization.identity = this.identity
            l_user_authorization.refreshAuthorization.scope = this.scope
            l_user_authorization.refreshAuthorization.durationSeconds = i_conf_authorization.refreshDurationSeconds
            l_user_authorization.refreshAuthorization.maxUsageCount = i_conf_authorization.refreshMaxUsageCount
            l_user_authorization.refreshAuthorization.scope?.keyFieldMap = l_user_authorization.scope?.keyFieldMap
            l_user_authorization.refreshAuthorization.functionalFieldMap = l_user_authorization.functionalFieldMap
            //l_user_authorization.refreshAuthorization.prerequisiteAuthorization = l_user_authorization.prerequisiteAuthorization
            l_user_authorization.refreshAuthorization.success(i_context)
        }
    }

    Boolean merge_field_maps(HashMap<String, String> i_key_field_map, HashMap<String, String> i_functional_field_map) {
        if (functionalFieldMap == null) functionalFieldMap = new HashMap<String, String>()
        if (scope?.keyFieldMap == null) scope?.keyFieldMap = new HashMap<String, String>()
        HashMap l_local_key_field_map = new HashMap()
        l_local_key_field_map.putAll(scope?.keyFieldMap)
        HashMap l_local_functional_field_map = new HashMap()
        l_local_functional_field_map.putAll(functionalFieldMap)
        for (String l_key in i_key_field_map.keySet()) {
            if (l_local_key_field_map.containsKey(l_key)) {
                if (is_not_null(i_key_field_map.get(l_key)) && is_not_null(l_local_key_field_map.get(l_key)) && l_local_key_field_map.get(l_key) != i_key_field_map.get(l_key)) return false
                else l_local_key_field_map.put(l_key, i_key_field_map.get(l_key))
            } else l_local_key_field_map.put(l_key, i_key_field_map.get(l_key))
        }
        for (String l_key in i_functional_field_map.keySet()) {
            if (l_local_functional_field_map.containsKey(l_key)) {
                if (is_not_null(i_functional_field_map.get(l_key)) && is_not_null(l_local_functional_field_map.get(l_key)) && l_local_functional_field_map.get(l_key) != i_functional_field_map.get(l_key)) return false
                else l_local_functional_field_map.put(l_key, i_functional_field_map.get(l_key))
            } else l_local_functional_field_map.put(l_key, i_functional_field_map.get(l_key))
        }
        scope?.keyFieldMap = l_local_key_field_map
        functionalFieldMap = l_local_functional_field_map
        return true
    }

    static Authorization access_jwt2authorization(String i_jwt_string, T_auth_grant_base_5_context i_context) {
        Jwt l_jwt = Jwts.parser().setSigningKey(i_context.p_jwt_manager.get_jwt_access_key()).parse(remove_jwt_bearer(i_jwt_string))
        Authorization l_authorization = i_context.p_object_mapper.readValue(i_context.unzip(((Map) l_jwt.getBody()).get("token") as String), Authorization.class)
        l_authorization.jwt = i_jwt_string
        return l_authorization
    }

    static Authorization refresh_jwt2authorization(String i_jwt_string, T_auth_grant_base_5_context i_context) {
        Jwt l_jwt = Jwts.parser().setSigningKey(i_context.p_jwt_manager.get_jwt_refresh_key()).parse(remove_jwt_bearer(i_jwt_string))
        Authorization l_authorization = i_context.p_object_mapper.readValue(i_context.unzip(((Map) l_jwt.getBody()).get("token") as String), Authorization.class)
        return l_authorization
    }

    void validate_authorization(T_auth_grant_base_5_context i_context) {
        functionalFieldMap = new HashMap<>()
        scope?.keyFieldMap = new HashMap<>()
        jwt = GC_EMPTY_STRING
        Authorization l_lookup_accessor_authorization = this
        //if it is Anonymous authorization - force the Accessor_data authentication preliminary
        Authentication l_accessor_authentication = identity?.authenticationSet?.find {
            it.authenticationName == "Accessor_data"
        }
        if (is_null(l_accessor_authentication)) {
            Integer l_number_of_step_ups = GC_ZERO
            while (is_not_null(l_lookup_accessor_authorization) && is_null(l_accessor_authentication) && l_number_of_step_ups < i_context.p_app_conf.max_number_of_step_ups) {
                if (is_not_null(l_lookup_accessor_authorization.jwt)) {
                    if (!is_invalid_access_jwt(l_lookup_accessor_authorization.jwt, i_context)) {
                        Authorization l_prerequisite_authorization_unwrapped = access_jwt2authorization(l_lookup_accessor_authorization.jwt, i_context)
                        l_accessor_authentication = l_prerequisite_authorization_unwrapped.identity?.authenticationSet?.find {
                            it.authenticationName == "Accessor_data"
                        }
                        l_lookup_accessor_authorization = l_prerequisite_authorization_unwrapped.prerequisiteAuthorization
                    }
                } else {
                    l_accessor_authentication = l_lookup_accessor_authorization.identity?.authenticationSet?.find {
                        it.authenticationName == "Accessor_data"
                    }
                    l_lookup_accessor_authorization = l_lookup_accessor_authorization.prerequisiteAuthorization
                }
                l_number_of_step_ups ++
            }
        }
        if (is_null(l_accessor_authentication)) {
            failure(GC_AUTHORIZATION_ERROR_CODE_20_MISSING_ACCESSOR_DATA)
            return
        }
        Object[] l_auth_and_scopes = i_context.p_authorization_type_repository.match_authorizations(
                scope?.scopeName
                , identity?.identityName
                , l_accessor_authentication?.authenticationData?.publicDataFieldSet?.get("accessor_name") as String
                , l_accessor_authentication?.authenticationData?.publicDataFieldSet?.get("platform") as String
                , l_accessor_authentication?.authenticationData?.publicDataFieldSet?.get("app_version") as String
                , l_accessor_authentication?.authenticationData?.publicDataFieldSet?.get("FIID") as String
                , l_accessor_authentication?.authenticationData?.publicDataFieldSet?.get("product") as String
                , l_accessor_authentication?.authenticationData?.publicDataFieldSet?.get("product_group") as String
                , l_accessor_authentication?.authenticationData?.publicDataFieldSet?.get("api_major_version") as String
                , i_context.p_app_conf.granting_endpoint_name

                , l_accessor_authentication?.authenticationData?.publicDataFieldSet?.get("accessor_name") as String
                , l_accessor_authentication?.authenticationData?.publicDataFieldSet?.get("platform") as String
                , l_accessor_authentication?.authenticationData?.publicDataFieldSet?.get("app_version") as String
                , l_accessor_authentication?.authenticationData?.publicDataFieldSet?.get("FIID") as String
                , l_accessor_authentication?.authenticationData?.publicDataFieldSet?.get("product") as String
                , l_accessor_authentication?.authenticationData?.publicDataFieldSet?.get("product_group") as String
                , l_accessor_authentication?.authenticationData?.publicDataFieldSet?.get("api_major_version") as String
                , i_context.p_app_conf.granting_endpoint_name
        )[GC_FIRST_INDEX]
        if (is_null(l_auth_and_scopes) || l_auth_and_scopes?.size() == GC_EMPTY_SIZE) {
            failure(GC_AUTHORIZATION_ERROR_CODE_17)
            return
        }
        AuthorizationType l_config_authorization = l_auth_and_scopes[GC_FIRST_INDEX] as AuthorizationType
        l_config_authorization.scopeSet = l_auth_and_scopes[GC_SECOND_INDEX] as Set<ScopeType>
        l_config_authorization.identitySet = l_auth_and_scopes[2] as Set<IdentityType>
        System.out.println("Start validation!!!")
        common_authorization_granting(l_config_authorization, i_context)
        if (this.getAuthorizationStatus() == GC_STATUS_SUCCESSFUL) {
            System.out.println("SUCCESS!!!!!!!!")
        }
        if (authorizationStatus == GC_STATUS_NEW) {
            authorizationStatus = GC_STATUS_FAILED
        }
    }

    void set_validity() {
        creationDate = new Date()
        use(TimeCategory) {
            expiryDate = creationDate + durationSeconds.seconds
        }
    }

    void set_jwt(T_auth_grant_base_5_context i_context) {
        String l_payload = i_context.zip(i_context.p_object_mapper.writeValueAsString(this))
        Key l_key = GC_NULL_OBJ_REF as Key
        if (this.authorizationType == "Access") {
            l_key = i_context.p_jwt_manager.get_jwt_access_key()
        } else if (this.authorizationType == "Refresh") {
            l_key = i_context.p_jwt_manager.get_jwt_refresh_key()
        }
        jwt = Jwts.builder().addClaims(["token": l_payload]).signWith(SignatureAlgorithm.HS512, l_key).compact()
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response post_list(String i_json_string) {
        Object l_parsed_json = new JsonSlurper().parseText(i_json_string)
        Response l_granting_response
        Set<Authorization> l_authorization_set = new HashSet<Authorization>()
        if (!(l_parsed_json instanceof Map)) {
            l_parsed_json.each { l_json_array_element ->
                Authorization l_authorization = p_app_context.p_object_mapper.readValue(JsonOutput.toJson(l_json_array_element), Authorization.class) as Authorization
                l_authorization.validate_authorization(p_app_context)
                l_authorization_set.add(l_authorization)
                if (is_not_null(l_authorization.refreshAuthorization)) {
                    l_authorization_set.add(l_authorization.refreshAuthorization)
                }
            }
        } else {
            Authorization l_authorization = p_app_context.p_object_mapper.readValue(JsonOutput.toJson(l_parsed_json), Authorization.class) as Authorization
            l_authorization.validate_authorization(p_app_context)
            l_authorization_set.add(l_authorization)
            if (is_not_null(l_authorization.refreshAuthorization)) {
                l_authorization_set.add(l_authorization.refreshAuthorization)
            }
        }
        l_granting_response = Response.ok().entity(p_app_context.p_object_mapper.writeValueAsString(l_authorization_set)).build()
        return l_granting_response
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search/findByScopeName")
    Response get(
            @QueryParam("scopeName") String i_scope_name
            , @QueryParam("accessorAppName") String i_AccessorAppName
            , @QueryParam("accessorPlatform") String i_AccessorPlatform
            , @QueryParam("accessorAppVersion") String i_AccessorAppVersion
            , @QueryParam("accessorFiid") String i_AccessorFiid
            , @QueryParam("accessorProduct") String i_AccessorProduct
            , @QueryParam("accessorProductGroup") String i_AccessorProductGroup
            , @QueryParam("accessorApiVersionName") String i_AccessorApiVersionName
            , @QueryParam("identityName") String i_identityName
    ) {
        Response l_granting_response
        if (is_null(i_scope_name)) {
            l_granting_response = Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Mandatory parameter 'scopeName' is missing")).build()
        } else {
            Object[] l_auth_and_scopes = p_app_context.p_authorization_type_repository.match_authorizations(
                    i_scope_name
                    , i_identityName
                    , i_AccessorAppName
                    , i_AccessorPlatform
                    , i_AccessorAppVersion
                    , i_AccessorFiid
                    , i_AccessorProduct
                    , i_AccessorProductGroup
                    , i_AccessorApiVersionName
                    , p_app_context.p_app_conf.granting_endpoint_name

                    , i_AccessorAppName
                    , i_AccessorPlatform
                    , i_AccessorAppVersion
                    , i_AccessorFiid
                    , i_AccessorProduct
                    , i_AccessorProductGroup
                    , i_AccessorApiVersionName
                    , p_app_context.p_app_conf.granting_endpoint_name
            )[GC_FIRST_INDEX]
            Set<Authorization> l_user_authorizations
            if (is_not_null(l_auth_and_scopes) && l_auth_and_scopes?.size() != GC_EMPTY_SIZE) {
                AuthorizationType l_authorization_type = l_auth_and_scopes[GC_FIRST_INDEX] as AuthorizationType
                l_user_authorizations = l_authorization_type.to_user_authorizations(i_scope_name, i_identityName)
                l_authorization_type.scopeSet = l_auth_and_scopes[GC_SECOND_INDEX] as Set<ScopeType>
                l_authorization_type.identitySet = l_auth_and_scopes[2] as Set<IdentityType>
            } else {
                l_user_authorizations = new HashSet<Authorization>()
            }
            l_granting_response = Response.ok().entity(p_app_context.p_object_mapper.writeValueAsString(l_user_authorizations)).build()
        }
        return l_granting_response
    }

}