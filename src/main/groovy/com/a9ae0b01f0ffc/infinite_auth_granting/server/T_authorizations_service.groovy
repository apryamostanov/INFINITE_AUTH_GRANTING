package com.a9ae0b01f0ffc.infinite_auth_granting.server

import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_5_context
import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_hal_resource
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_resource_set
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Accessor
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Authorization
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Scope
import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.time.TimeCategory
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired

import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import java.nio.charset.StandardCharsets

import static base.T_common_base_1_const.GC_NULL_OBJ_REF
import static base.T_common_base_3_utils.*
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.*

@Path("/authorizations")
class T_authorizations_service {

    @JsonIgnore
    @Autowired
    T_auth_grant_base_5_context p_context

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response post_list(String i_json_string) {
        Object l_parsed_json_array = new JsonSlurper().parseText(i_json_string)
        Response l_granting_response
        Set<Authorization> l_authorization_set = new HashSet<Authorization>()
        l_parsed_json_array.each { l_json_array_element ->
            Authorization l_authorization = T_auth_grant_base_5_context.get_app_context().p_object_mapper.readValue(JsonOutput.toJson(l_json_array_element), Class.forName(GC_DOMAIN_MODEL_CLASS_PREFIX + l_json_array_element.resourceName)) as Authorization
            validate_authorization(l_authorization)
            l_authorization_set.add(l_authorization)
        }
        l_granting_response = Response.ok().entity(l_authorization_set).build()
        return l_granting_response
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
            )
            l_granting_response = Response.ok().entity(l_final_authorization_set).build()
        }
        return l_granting_response
    }

    Set<T_hal_resource> find_authorizations(
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
        )
        Scope l_target_scope = find_scope(i_scope_name, l_matched_accessor_set)
        return find_authorizations(l_target_scope, l_matched_accessor_set, i_authorization_type)
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
    ) {
        T_resource_set<Accessor> l_accessor_set_to_match = T_auth_grant_base_6_util.hal_request(p_context.app_conf().infiniteAuthConfigurationBaseUrl + p_context.app_conf().matchAccessors
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

    Scope find_scope(String i_scope_name, List<Accessor> i_match_accessor_list) {
        for (Accessor l_matched_accessor in i_match_accessor_list) {
            T_resource_set<Scope> l_matched_accessor_scopes = T_auth_grant_base_6_util.hal_request(p_context.app_conf().infiniteAuthConfigurationBaseUrl + p_context.app_conf().infiniteAuthConfigurationRelativeUrlsScopesSearchFindByScopeNameAndAccessor + "?scopeName=" + URLEncoder.encode(i_scope_name, StandardCharsets.UTF_8.name()) + "&accessor=" + URLEncoder.encode(l_matched_accessor.resourceSelfUrl, StandardCharsets.UTF_8.name()), GC_TRAVERSE_NO) as T_resource_set
            if (not(l_matched_accessor_scopes.resourceSet.isEmpty())) {
                return l_matched_accessor_scopes.resourceSet.first()
            }
        }
        return GC_NULL_OBJ_REF as Scope
    }

    Set<Authorization> find_authorizations(Scope i_scope, List<Accessor> i_match_accessor_list, String i_authorization_type) {
        T_auth_grant_base_5_context.get_app_context().p_resources_by_reference_url.clear()
        T_auth_grant_base_5_context.get_app_context().p_resources_by_self_url.clear()
        if (is_not_null(i_scope)) {
            for (Accessor l_matched_accessor in i_match_accessor_list) {
                T_resource_set<Authorization> l_matched_accessor_authorizations = T_auth_grant_base_6_util.hal_request(p_context.app_conf().infiniteAuthConfigurationBaseUrl + p_context.app_conf().infiniteAuthConfigurationRelativeUrlsAuthorizationsSearchFindByScopeAndAuthorizationTypeAndAccessor + "?scope=" + URLEncoder.encode(i_scope.getResourceSelfUrl(), StandardCharsets.UTF_8.name()) + "&accessor=" + URLEncoder.encode(l_matched_accessor.resourceSelfUrl, StandardCharsets.UTF_8.name()) + "&authorizationType=" + URLEncoder.encode(nvl(i_authorization_type, GC_AUTHORIZATION_TYPE_ACCESS) as String, StandardCharsets.UTF_8.name()), GC_TRAVERSE_YES) as T_resource_set
                if (not(l_matched_accessor_authorizations.resourceSet.isEmpty())) {
                    return l_matched_accessor_authorizations.resourceSet
                }
            }
            T_resource_set<Authorization> l_matched_accessor_authorizations = T_auth_grant_base_6_util.hal_request(p_context.app_conf().infiniteAuthConfigurationBaseUrl + p_context.app_conf().infiniteAuthConfigurationRelativeUrlsAuthorizationsSearchFindByScopeAndAuthorizationTypeAndDefaultAccessor + "?scope=" + URLEncoder.encode(i_scope.getResourceSelfUrl(), StandardCharsets.UTF_8.name()) + "&authorizationType=" + URLEncoder.encode(nvl(i_authorization_type, GC_AUTHORIZATION_TYPE_ACCESS) as String, StandardCharsets.UTF_8.name()), GC_TRAVERSE_YES) as T_resource_set
            if (not(l_matched_accessor_authorizations.resourceSet.isEmpty())) {
                return l_matched_accessor_authorizations.resourceSet
            }
            return new HashSet<Authorization>()
        } else return new HashSet<Authorization>()
    }

    Boolean match_with_conf_coll(T_resource_set i_user_resource, T_resource_set i_conf_resource) {
        if (getClass() != i_conf_resource.class) return false
        T_resource_set<T_hal_resource> l_conf_resource = (T_resource_set) i_conf_resource
        if (l_conf_resource.resourceSet.size() != i_user_resource.resourceSet.size()) return false
        if (l_conf_resource.resourceSet.size() == i_user_resource.resourceSet.size() && i_user_resource.resourceSet.size() == GC_ZERO) return true
        i_user_resource.resourceSet = i_user_resource.resourceSet.sort { it.getSortKeyValue() }
        l_conf_resource.resourceSet = l_conf_resource.resourceSet.sort { it.getSortKeyValue() }
        for (Integer l_resource_index = GC_ZERO; l_resource_index < l_conf_resource.resourceSet.size(); l_resource_index ++) {
            if (l_resource_index >= i_user_resource.resourceSet.size()) return false
            if (not(match_with_conf(l_conf_resource.resourceSet[l_resource_index], i_user_resource.resourceSet[l_resource_index] as T_hal_resource))) {
                return false
            }
        }
        return true
    }

    Boolean match_with_conf(T_hal_resource i_user_resource, T_hal_resource i_conf_resource) {
        if (getClass() != i_conf_resource.class) {
            return false
        }
        T_hal_resource l_config_resource = getClass().cast(i_conf_resource) as T_hal_resource
        for (l_prop_name in i_user_resource.properties.keySet()) {
            System.out.println("Property: " + l_prop_name)
            if (not(i_conf_resource.p_ignored_property_names.contains(l_prop_name))) {
                if (i_user_resource.properties.get(l_prop_name) instanceof T_hal_resource) {
                    System.out.println((T_hal_resource) i_user_resource.properties.get(l_prop_name))
                    System.out.println(i_conf_resource.properties.get(l_prop_name))
                    if (i_user_resource.properties.get(l_prop_name) instanceof T_resource_set) {
                        if (!match_with_conf_coll(i_user_resource.properties.get(l_prop_name) as T_resource_set, i_conf_resource.properties.get(l_prop_name) as T_resource_set)) return GC_FALSE
                    } else {
                        if (!match_with_conf(((T_hal_resource) i_user_resource.properties.get(l_prop_name)), i_conf_resource.properties.get(l_prop_name) as T_hal_resource)) return GC_FALSE
                    }
                } else {
                    System.out.println(i_user_resource.properties.get(l_prop_name))
                    System.out.println(i_conf_resource.properties.get(l_prop_name))
                    if (i_user_resource.properties.get(l_prop_name) != i_conf_resource.properties.get(l_prop_name)) return GC_FALSE
                }
            } else {
                System.out.println("Ignoring property: " + l_prop_name)
            }
        }
        if (i_user_resource instanceof Authorization) {
            i_user_resource.authorizationStatus = GC_STATUS_SUCCESSFUL
            set_validity(i_user_resource)
            set_jwt(i_user_resource)
        }
        return GC_TRUE
    }

    void validate_authorization(Authorization i_authorization) {
        Set<Authorization> l_config_authorization_set = find_authorizations(
                  i_authorization.scope?.scopeName
                , i_authorization.authorizationType
                , i_authorization.accessor?.appName
                , i_authorization.accessor?.platform
                , i_authorization.accessor?.appVersion
                , i_authorization.accessor?.fiid
                , i_authorization.accessor?.product
                , i_authorization.accessor?.productGroup
                , i_authorization.accessor?.apiVersionName
                , i_authorization.accessor?.endpointName
        ) as Set<Authorization>
        for (Authorization l_config_authorization in l_config_authorization_set) {
            System.out.println("Start validation!!!")
            if (match_with_conf(i_authorization, l_config_authorization)) {
                System.out.println("SUCCESS!!!!!!!!")
            }
        }
        if (i_authorization.authorizationStatus == GC_STATUS_NEW) {
            i_authorization.authorizationStatus = GC_STATUS_FAILED
        }
    }



    void set_validity(Authorization i_authorization) {
        i_authorization.creationDate = new Date()
        use(TimeCategory) {
            i_authorization.expiryDate = i_authorization.creationDate + i_authorization.durationSeconds.seconds
        }
    }


    void set_jwt(Authorization i_authorization) {
        String l_payload = T_auth_grant_base_6_util.get_app_context().p_object_mapper.writeValueAsString(this)
        i_authorization.jwt = Jwts.builder().setPayload(l_payload)
                .signWith(SignatureAlgorithm.HS512, p_context.p_jwt_manager.get_jwt_key())
                .compact()
    }

}
