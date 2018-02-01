package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_5_context
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_hal_resource
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_resource_set
import com.a9ae0b01f0ffc.infinite_auth_granting.server.ApiResponseMessage
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
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
import java.nio.charset.StandardCharsets

import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.*

@Path("/authorizations")
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
class Authorization extends T_hal_resource {
    String authorizationName
    Accessor accessor

    Identity identity

    Scope scope

    Integer durationSeconds

    Integer maxUsageCount

    Map<String, String> keyFieldSet

    Map<String, String> functionalFieldSet

    T_resource_set<Authorization> prerequisiteAuthorizationSet
    Authorization refreshAuthorization
    @JsonFormat(timezone = "UTC")
    Date creationDate
    Date expiryDate
    String authorizationStatus = GC_STATUS_NEW
    String errorCode
    String jwt
    String authorizationType

    @Autowired
    @JsonIgnore
    T_auth_grant_base_5_context p_app_context


    void success(T_auth_grant_base_5_context i_context) {
        this.authorizationStatus = GC_STATUS_SUCCESSFUL
        if (is_null(this.jwt)) {
            set_validity(i_context)
            set_jwt(i_context)
        }
    }

    void failure(Integer i_error_code) {
        this.authorizationStatus = GC_STATUS_FAILED
        this.errorCode = i_error_code
    }

    Boolean is_invalid_jwt(String i_jwt_string, T_auth_grant_base_5_context i_context) {
        try {
            Jwt l_jwt = Jwts.parser().setSigningKey(p_app_context.p_jwt_manager.get_jwt_key()).parse(i_jwt_string)
            Authorization l_authorization = i_context.p_object_mapper.readValue(l_jwt.getBody() as String, Authorization.class)
        } catch (Exception ignored) {
            return GC_FALSE
        }
        return GC_TRUE
    }

    void common_authorization_granting(Authorization i_conf_authorization, T_auth_grant_base_5_context i_context) {
        Boolean l_is_authentication_needed
        Authorization l_user_authorization
        if (is_not_null(this.jwt)) {
            if (is_invalid_jwt(this.jwt, i_context)) {
                failure(GC_AUTHORIZATION_ERROR_CODE_01_INVALID_JWT)
                return
            }
            l_user_authorization = jwt2authorization(this.jwt, i_context)
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
                if (is_null(l_user_authorization.prerequisiteAuthorizationSet)) {
                    failure(GC_AUTHORIZATION_ERROR_CODE_05_NO_PREREQUISITES)
                    return
                }
                if (l_user_authorization.prerequisiteAuthorizationSet.isEmpty()) {
                    failure(GC_AUTHORIZATION_ERROR_CODE_06_EMPTY_PREREQUISITES)
                    return
                }
                if (l_user_authorization.prerequisiteAuthorizationSet.size() != GC_ONE_ONLY) {
                    failure(GC_AUTHORIZATION_ERROR_CODE_07_MORE_THEN_ONE_PREREQUISITE)
                    return
                }
                Authorization l_prerequisite_user_auth = l_user_authorization.prerequisiteAuthorizationSet.first()
                for (Authorization l_prerequisite_conf_authorization in i_conf_authorization.prerequisiteAuthorizationSet) {
                    l_prerequisite_user_auth.common_authorization_granting(l_prerequisite_conf_authorization, i_context)
                }
                if (l_prerequisite_user_auth.authorizationStatus != GC_STATUS_SUCCESSFUL) {
                    failure(GC_AUTHORIZATION_ERROR_CODE_08_FAILED_PREREQUISITE)
                    return
                }
            }
        }
        if (is_not_null(i_conf_authorization.refreshAuthorization)) {
            if (is_null(l_user_authorization.refreshAuthorization)) {
                failure(GC_AUTHORIZATION_ERROR_CODE_09_UNEXPECTED_REFRESH)
                return
            }
            Authorization l_refresh_user_authorization = l_user_authorization.refreshAuthorization
            l_refresh_user_authorization.common_authorization_granting(l_refresh_user_authorization, i_context)
            if (l_refresh_user_authorization.authorizationStatus != GC_STATUS_SUCCESSFUL) {
                failure(GC_AUTHORIZATION_ERROR_CODE_10_FAILED_REFRESH)
                return
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
        if (l_user_authorization.identity.authenticationSet.size() != i_conf_authorization.identity.authenticationSet.size()) {
            failure(GC_AUTHORIZATION_ERROR_CODE_15_WRONG_AUTHENTICATIONS_NUMBER)
            return
        }
        List l_sorted_conf_authentication_list = i_conf_authorization.identity.authenticationSet.sort { it -> it.authenticationName }
        List l_sorted_user_authentication_list = l_user_authorization.identity.authenticationSet.sort { it -> it.authenticationName }
        Integer l_authentication_index = GC_ZERO
        if (l_is_authentication_needed) {
            for (Authentication l_user_authentication in l_sorted_user_authentication_list) {
                l_user_authentication.common_authentication_validation(l_sorted_conf_authentication_list[l_authentication_index], i_context)
                if (l_user_authentication.authenticationStatus == GC_STATUS_FAILED) {
                    failure(GC_AUTHORIZATION_ERROR_CODE_16_FAILED_AUTHENTICATION)
                    return
                }
                l_authentication_index++
            }
        }
        success(i_context)
        l_user_authorization.scope = i_conf_authorization.scope
    }

    Authorization jwt2authorization(String i_jwt_string, T_auth_grant_base_5_context i_context) {
        Jwt l_jwt = Jwts.parser().setSigningKey(i_context.p_jwt_manager.get_jwt_key()).parse(i_jwt_string)
        Authorization l_authorization = i_context.p_object_mapper.readValue(i_context.unzip(l_jwt.getBody() as String), Authorization.class)
        return l_authorization
    }

    void validate_authorization(T_auth_grant_base_5_context i_context) {
        Set<Authorization> l_config_authorization_set = find_authorizations(
                scope?.scopeName
                , authorizationType
                , accessor?.appName
                , accessor?.platform
                , accessor?.appVersion
                , accessor?.fiid
                , accessor?.product
                , accessor?.productGroup
                , accessor?.apiVersionName
                , accessor?.endpointName
                , i_context
                , identity?.identityName
        )
        if (l_config_authorization_set.size() != GC_ONE_ONLY) {
            failure(GC_AUTHORIZATION_ERROR_CODE_17)
            return
        }
        Authorization l_config_authorization = l_config_authorization_set.first()
        System.out.println("Start validation!!!")
        common_authorization_granting(l_config_authorization, i_context)
        if (this.getAuthorizationStatus() == GC_STATUS_SUCCESSFUL) {
            System.out.println("SUCCESS!!!!!!!!")
        }
        if (authorizationStatus == GC_STATUS_NEW) {
            authorizationStatus = GC_STATUS_FAILED
        }
    }

    void set_validity(T_auth_grant_base_5_context i_context) {
        creationDate = new Date()
        use(TimeCategory) {
            expiryDate = creationDate + durationSeconds.seconds
        }
    }


    void set_jwt(T_auth_grant_base_5_context i_context) {
        String l_payload = i_context.zip(i_context.p_object_mapper.writeValueAsString(this))
        jwt = Jwts.builder().setPayload(l_payload)
                .signWith(SignatureAlgorithm.HS512, i_context.p_jwt_manager.get_jwt_key())
                .compact()
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response post_list(String i_json_string) {
        Object l_parsed_json_array = new JsonSlurper().parseText(i_json_string)
        Response l_granting_response
        Set<Authorization> l_authorization_set = new HashSet<Authorization>()
        l_parsed_json_array.each { l_json_array_element ->
            Authorization l_authorization = p_app_context.p_object_mapper.readValue(JsonOutput.toJson(l_json_array_element), Authorization.class) as Authorization
            l_authorization.validate_authorization(p_app_context)
            l_authorization_set.add(l_authorization)
        }
        l_granting_response = Response.ok().entity(l_authorization_set).build()
        return l_granting_response
    }

    LinkedList<Accessor> find_accessors(
            String i_AccessorAppName
            , String i_AccessorPlatform
            , String i_AccessorAppVersion
            , String i_AccessorFiid
            , String i_AccessorProduct
            , String i_AccessorProductGroup
            , String i_AccessorApiVersionName
            , String i_AccessorEndpointName
            , T_auth_grant_base_5_context i_context
    ) {
        T_resource_set<Accessor> l_accessor_set_to_match = i_context.hal_request(i_context.app_conf().infiniteAuthConfigurationBaseUrl + i_context.app_conf().matchAccessors
                + "?appName=" + URLEncoder.encode(i_AccessorAppName, StandardCharsets.UTF_8.name())
                + "&platform=" + URLEncoder.encode(i_AccessorPlatform, StandardCharsets.UTF_8.name())
                + "&appVersion=" + URLEncoder.encode(i_AccessorAppVersion, StandardCharsets.UTF_8.name())
                + "&fiid=" + URLEncoder.encode(i_AccessorFiid, StandardCharsets.UTF_8.name())
                + "&product=" + URLEncoder.encode(nvl(i_AccessorProduct, GC_ANY) as String, StandardCharsets.UTF_8.name())
                + "&productGroup=" + URLEncoder.encode(i_AccessorProductGroup, StandardCharsets.UTF_8.name())
                + "&apiVersionName=" + URLEncoder.encode(i_AccessorApiVersionName, StandardCharsets.UTF_8.name())
                + "&endpointName=" + URLEncoder.encode(i_AccessorEndpointName, StandardCharsets.UTF_8.name())
                , GC_TRAVERSE_YES) as T_resource_set
        return l_accessor_set_to_match
    }

    Set<Authorization> find_authorizations(
            String i_scope_name
            , String i_authorization_type
            , String i_AccessorAppName
            , String i_AccessorPlatform
            , String i_AccessorAppVersion
            , String i_AccessorFiid
            , String i_AccessorProduct
            , String i_AccessorProductGroup
            , String i_AccessorApiVersionName
            , String i_AccessorEndpointName
            , T_auth_grant_base_5_context i_context
            , String i_identity_name = GC_EMPTY_STRING
    ) {
        LinkedList<Accessor> l_matched_accessor_set = find_accessors(
                i_AccessorAppName
                , i_AccessorPlatform
                , i_AccessorAppVersion
                , i_AccessorFiid
                , i_AccessorProduct
                , i_AccessorProductGroup
                , i_AccessorApiVersionName
                , i_AccessorEndpointName
                , i_context
        )
        i_context.p_resources_by_reference_url.clear()
        i_context.p_resources_by_self_url.clear()
        for (Accessor l_matched_accessor in l_matched_accessor_set) {
            T_resource_set<Authorization> l_matched_accessor_authorizations = i_context.hal_request(i_context.app_conf().infiniteAuthConfigurationBaseUrl + i_context.app_conf().matchAuthorizations
                    + "?scopeName=" + URLEncoder.encode(i_scope_name, StandardCharsets.UTF_8.name())
                    + (is_null(l_matched_accessor.accessorName) ? GC_EMPTY_STRING : ("&accessorName=" + URLEncoder.encode(l_matched_accessor.accessorName, StandardCharsets.UTF_8.name())))
                    + (is_null(i_identity_name) ? GC_EMPTY_STRING : ("&identityName=" + URLEncoder.encode(i_identity_name, StandardCharsets.UTF_8.name())))
                    + (is_null(i_authorization_type) ? GC_EMPTY_STRING : ("&authorizationType=" + URLEncoder.encode(i_authorization_type, StandardCharsets.UTF_8.name())))
                    , GC_TRAVERSE_YES) as T_resource_set
            if (not(l_matched_accessor_authorizations.isEmpty())) {
                return l_matched_accessor_authorizations
            }
        }
        T_resource_set<Authorization> l_matched_accessor_authorizations = i_context.hal_request(i_context.app_conf().infiniteAuthConfigurationBaseUrl + i_context.app_conf().matchAuthorizations
                + "?scopeName=" + URLEncoder.encode(i_scope_name, StandardCharsets.UTF_8.name())
                + (is_null(i_identity_name) ? GC_EMPTY_STRING : ("&identityName=" + URLEncoder.encode(i_identity_name, StandardCharsets.UTF_8.name())))
                + (is_null(i_authorization_type) ? GC_EMPTY_STRING : ("&authorizationType=" + URLEncoder.encode(i_authorization_type, StandardCharsets.UTF_8.name())))
                , GC_TRAVERSE_YES) as T_resource_set
        if (not(l_matched_accessor_authorizations.isEmpty())) {
            return l_matched_accessor_authorizations
        }
        return new HashSet<Authorization>()
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search/findByScopeName")
    Response find_by_scope_name(
            @QueryParam("scopeName") String i_scope_name
            , @QueryParam("authorizationType") String i_authorization_type
            , @QueryParam("accessorAppName") String i_AccessorAppName
            , @QueryParam("accessorPlatform") String i_AccessorPlatform
            , @QueryParam("accessorAppVersion") String i_AccessorAppVersion
            , @QueryParam("accessorFiid") String i_AccessorFiid
            , @QueryParam("accessorProduct") String i_AccessorProduct
            , @QueryParam("accessorProductGroup") String i_AccessorProductGroup
            , @QueryParam("accessorApiVersionName") String i_AccessorApiVersionName
            , @QueryParam("accessorEndpointName") String i_AccessorEndPointName
    ) {
        Response l_granting_response = Response.ok().entity(new HashSet<Authorization>()).build()
        if (is_null(i_scope_name)) {
            l_granting_response = Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Mandatory parameter 'scopeName' is missing")).build()
        } else if (is_null(i_AccessorAppName)) {
            l_granting_response = Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Mandatory parameter 'accessorAppName' is missing")).build()
        } else if (is_null(i_AccessorPlatform)) {
            l_granting_response = Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Mandatory parameter 'accessorPlatform' is missing")).build()
        } else if (is_null(i_AccessorAppVersion)) {
            l_granting_response = Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Mandatory parameter 'accessorAppVersion' is missing")).build()
        } else if (is_null(i_AccessorFiid)) {
            l_granting_response = Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Mandatory parameter 'accessorFiid' is missing")).build()
        } else if (is_null(i_AccessorProductGroup)) {
            l_granting_response = Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Mandatory parameter 'accessorProductGroup' is missing")).build()
        } else if (is_null(i_AccessorApiVersionName)) {
            l_granting_response = Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Mandatory parameter 'accessorApiVersionName' is missing")).build()
        } else if (is_null(i_AccessorEndPointName)) {
            l_granting_response = Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Mandatory parameter 'accessorEndpointName' is missing")).build()
        } else {
            Set<T_hal_resource> l_final_authorization_set = find_authorizations(
                    i_scope_name
                    , i_authorization_type
                    , i_AccessorAppName
                    , i_AccessorPlatform
                    , i_AccessorAppVersion
                    , i_AccessorFiid
                    , i_AccessorProduct
                    , i_AccessorProductGroup
                    , i_AccessorApiVersionName
                    , i_AccessorEndPointName
                    , p_app_context
            )
            l_granting_response = Response.ok().entity(l_final_authorization_set).build()
        }
        return l_granting_response
    }

}