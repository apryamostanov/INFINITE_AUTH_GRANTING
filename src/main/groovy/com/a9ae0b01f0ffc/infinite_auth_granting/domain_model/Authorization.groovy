package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_configuration.base.T_auth_base_5_context
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
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import java.nio.charset.StandardCharsets

import static base.T_common_base_1_const.GC_NULL_OBJ_REF
import static base.T_common_base_3_utils.*
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.*

@Path("/authorizations")
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
class Authorization extends T_hal_resource {
    String authorizationName
    Accessor accessor


    T_resource_set<Authentication> authenticationSet

    Scope scope

    Integer durationSeconds

    Integer maxUsageCount

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

    @JsonIgnore
    String[] p_ignored_property_names = ["resourceSelfUrl", "p_app_context", "creationDate", "expiryDate"]

    @Override
    Boolean match_with_conf(T_hal_resource i_conf_resource, T_auth_grant_base_5_context i_context) {
        Boolean l_is_successful = super.match_with_conf(i_conf_resource, i_context)
        if (l_is_successful) {
            authorizationStatus = GC_STATUS_SUCCESSFUL
            set_validity(i_context)
            set_jwt(i_context)
        }
        return l_is_successful
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
        )
        for (Authorization l_config_authorization in l_config_authorization_set) {
            System.out.println("Start validation!!!")
            if (match_with_conf(l_config_authorization, i_context)) {
                System.out.println("SUCCESS!!!!!!!!")
                break
            }
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
        String l_payload = i_context.p_object_mapper.writeValueAsString(this)
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
            Authorization l_authorization = p_app_context.p_object_mapper.readValue(JsonOutput.toJson(l_json_array_element), Class.forName(GC_DOMAIN_MODEL_CLASS_PREFIX + l_json_array_element.resourceName)) as Authorization
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
        return l_accessor_set_to_match.resourceSet
    }

    Scope find_scope(String i_scope_name, List<Accessor> i_match_accessor_list, T_auth_grant_base_5_context i_context) {
        for (Accessor l_matched_accessor in i_match_accessor_list) {
            T_resource_set<Scope> l_matched_accessor_scopes = i_context.hal_request(i_context.app_conf().infiniteAuthConfigurationBaseUrl + i_context.app_conf().infiniteAuthConfigurationRelativeUrlsScopesSearchFindByScopeNameAndAccessor + "?scopeName=" + URLEncoder.encode(i_scope_name, StandardCharsets.UTF_8.name()) + "&accessor=" + URLEncoder.encode(l_matched_accessor.resourceSelfUrl, StandardCharsets.UTF_8.name()), GC_TRAVERSE_NO) as T_resource_set
            if (not(l_matched_accessor_scopes.resourceSet.isEmpty())) {
                return l_matched_accessor_scopes.resourceSet.first()
            }
        }
        return GC_NULL_OBJ_REF as Scope
    }

    Set<Authorization> find_authorizations(Scope i_scope, List<Accessor> i_match_accessor_list, String i_authorization_type, T_auth_grant_base_5_context i_context) {
        i_context.p_resources_by_reference_url.clear()
        i_context.p_resources_by_self_url.clear()
        if (is_not_null(i_scope)) {
            for (Accessor l_matched_accessor in i_match_accessor_list) {
                T_resource_set<Authorization> l_matched_accessor_authorizations = i_context.hal_request(i_context.app_conf().infiniteAuthConfigurationBaseUrl + i_context.app_conf().infiniteAuthConfigurationRelativeUrlsAuthorizationsSearchFindByScopeAndAuthorizationTypeAndAccessor + "?scope=" + URLEncoder.encode(i_scope.getResourceSelfUrl(), StandardCharsets.UTF_8.name()) + "&accessor=" + URLEncoder.encode(l_matched_accessor.resourceSelfUrl, StandardCharsets.UTF_8.name()) + "&authorizationType=" + URLEncoder.encode(nvl(i_authorization_type, GC_AUTHORIZATION_TYPE_ACCESS) as String, StandardCharsets.UTF_8.name()), GC_TRAVERSE_YES) as T_resource_set
                if (not(l_matched_accessor_authorizations.resourceSet.isEmpty())) {
                    return l_matched_accessor_authorizations.resourceSet
                }
            }
            T_resource_set<Authorization> l_matched_accessor_authorizations = i_context.hal_request(i_context.app_conf().infiniteAuthConfigurationBaseUrl + i_context.app_conf().infiniteAuthConfigurationRelativeUrlsAuthorizationsSearchFindByScopeAndAuthorizationTypeAndDefaultAccessor + "?scope=" + URLEncoder.encode(i_scope.getResourceSelfUrl(), StandardCharsets.UTF_8.name()) + "&authorizationType=" + URLEncoder.encode(nvl(i_authorization_type, GC_AUTHORIZATION_TYPE_ACCESS) as String, StandardCharsets.UTF_8.name()), GC_TRAVERSE_YES) as T_resource_set
            if (not(l_matched_accessor_authorizations.resourceSet.isEmpty())) {
                return l_matched_accessor_authorizations.resourceSet
            }
            return new HashSet<Authorization>()
        } else return new HashSet<Authorization>()
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
        Scope l_target_scope = find_scope(i_scope_name, l_matched_accessor_set, i_context)
        return find_authorizations(l_target_scope, l_matched_accessor_set, i_authorization_type, i_context)
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

    @JsonIgnore
    String getSortKeyValue() {
        return authorizationName
    }

}