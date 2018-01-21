package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_5_context
import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_hal_resource
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_resource_set
import com.a9ae0b01f0ffc.infinite_auth_granting.server.ApiResponseMessage
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.time.TimeCategory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import java.nio.charset.StandardCharsets

import static base.T_common_base_3_utils.is_null
import static base.T_common_base_3_utils.nvl
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
    T_auth_grant_base_5_context p_context

    void validate_authorization() {
        Set<T_hal_resource> l_config_authorization_set = find_authorization(
                scope?.scopeName
                , authorizationType
                , accessor?.appName
                , accessor?.platform
                , accessor?.appVersion
                , accessor?.user
                , accessor?.fiid
                , accessor?.product
                , accessor?.productGroup
                , accessor?.apiVersion?.versionName
        )
        for (Authorization l_config_authorization in l_config_authorization_set) {
            if (match_with_conf(l_config_authorization)) {
                authorizationStatus = GC_STATUS_SUCCESSFUL
                set_validity()
                break
            }
            authorizationStatus = GC_STATUS_FAILED
        }
    }

    void set_validity() {
        creationDate = new Date()
        use(TimeCategory) {
            expiryDate = creationDate + durationSeconds.seconds
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response post_list(String i_json_string) {
        Object l_parsed_json_array = new JsonSlurper().parseText(i_json_string)
        Response l_granting_response
        Set<Authorization> l_authorization_set = new HashSet<Authorization>()
        l_parsed_json_array.each { l_json_array_element ->
            Authorization l_authorization = T_auth_grant_base_5_context.get_app_context().p_object_mapper.readValue(JsonOutput.toJson(l_json_array_element), Class.forName(GC_DOMAIN_MODEL_CLASS_PREFIX + l_json_array_element.resourceName)) as Authorization
            l_authorization.p_context = p_context
            l_authorization.validate_authorization()
            l_authorization_set.add(l_authorization)
        }
        l_granting_response = Response.ok().entity(l_authorization_set).build()
        return l_granting_response
    }

    Set<T_hal_resource> find_authorization(
            String i_scope_name
            , String i_authorization_type
            , String i_AccessorAppName
            , String i_AccessorPlatform
            , String i_AccessorAppVersion
            , String i_AccessorUser
            , String i_AccessorFiid
            , String i_AccessorProduct
            , String i_AccessorProductGroup
            , String i_AccessorApiVersionName
    ) {
        System.out.println(
                "i_authorization_type: " + i_authorization_type + "\n" +
                        "i_AccessorAppName: " + i_AccessorAppName + "\n" +
                        "i_AccessorPlatform: " + i_AccessorPlatform + "\n" +
                        "i_AccessorAppVersion: " + i_AccessorAppVersion + "\n" +
                        "i_AccessorUser: " + i_AccessorUser + "\n" +
                        "i_AccessorFiid: " + i_AccessorFiid + "\n" +
                        "i_AccessorProduct: " + i_AccessorProduct + "\n" +
                        "i_AccessorProductGroup: " + i_AccessorProductGroup + "\n" +
                        "i_AccessorApiVersionName: " + i_AccessorApiVersionName + "\n"
        )
        String l_AccessorApiVersionName = nvl(i_AccessorApiVersionName, GC_ANY)
        T_auth_grant_base_5_context.get_app_context().p_resources_by_reference_url.clear()
        T_auth_grant_base_5_context.get_app_context().p_resources_by_self_url.clear()
        T_resource_set l_version_set = T_auth_grant_base_6_util.hal_request(p_context.app_conf().infiniteAuthConfigurationBaseUrl + p_context.app_conf().infiniteAuthConfigurationRelativeUrlsVersionsSearchFindByVersionName + URLEncoder.encode(l_AccessorApiVersionName, StandardCharsets.UTF_8.name()), GC_TRAVERSE_NO) as T_resource_set
        Set<T_hal_resource> l_final_authorization_set = new HashSet<T_hal_resource>()
        Set<T_hal_resource> l_final_authorization_set_all_accessors = new HashSet<T_hal_resource>()
        Set<T_hal_resource> l_final_authorization_set_specific_accessor = new HashSet<T_hal_resource>()
        for (l_version in l_version_set.resourceSet) {
            T_resource_set l_accessor_set = T_auth_grant_base_6_util.hal_request(p_context.app_conf().infiniteAuthConfigurationBaseUrl + p_context.app_conf().infiniteAuthConfigurationRelativeUrlsAccessors, GC_TRAVERSE_YES) as T_resource_set
            List<Accessor> l_matched_accessor_set = new ArrayList<Accessor>()
            for (Accessor l_accessor in l_accessor_set?.getResourceSet()) {
                if ([nvl(i_AccessorAppName, GC_ANY), GC_ANY].contains(l_accessor.appName)
                        && [nvl(i_AccessorPlatform, GC_ANY), GC_ANY].contains(l_accessor.platform)
                        && [nvl(i_AccessorAppVersion, GC_ANY), GC_ANY].contains(l_accessor.appVersion)
                        && [nvl(i_AccessorUser, GC_ANY), GC_ANY].contains(l_accessor.user)
                        && [nvl(i_AccessorFiid, GC_ANY), GC_ANY].contains(l_accessor.fiid)
                        && [nvl(i_AccessorProduct, GC_ANY), GC_ANY].contains(l_accessor.product)
                        && [nvl(i_AccessorProductGroup, GC_ANY), GC_ANY].contains(l_accessor.productGroup)
                        && [nvl(i_AccessorApiVersionName, GC_ANY), GC_ANY].contains(((Version) l_accessor.apiVersion)?.versionName)
                ) {
                    l_matched_accessor_set.add(l_accessor)
                }
            }
            l_matched_accessor_set = l_matched_accessor_set.sort { it.lookupPriority }
            l_matched_accessor_set = l_matched_accessor_set.reverse()
            Set<Scope> l_scope_set = new HashSet<Scope>()
            for (l_matched_accessor in l_matched_accessor_set) {
                //specific accessor can have the authorization but no specific scope - only more broader scope
                System.out.println("Matched accessor: " + l_matched_accessor.accessorName)
                T_resource_set l_scope_resource_set = T_auth_grant_base_6_util.hal_request(p_context.app_conf().infiniteAuthConfigurationBaseUrl + p_context.app_conf().infiniteAuthConfigurationRelativeUrlsScopesSearchFindByScopeNameAndAccessor + "?scopeName=" + URLEncoder.encode(i_scope_name, StandardCharsets.UTF_8.name()) + "&accessor=" + URLEncoder.encode(l_matched_accessor.resourceSelfUrl, StandardCharsets.UTF_8.name()), GC_TRAVERSE_NO) as T_resource_set
                l_scope_set.addAll(l_scope_resource_set.getResourceSet() as Collection<? extends Scope>)
            }
            T_auth_grant_base_5_context.get_app_context().p_resources_by_reference_url.clear()
            T_auth_grant_base_5_context.get_app_context().p_resources_by_self_url.clear()
            for (l_scope in l_scope_set) {
                T_resource_set l_authorization_set = T_auth_grant_base_6_util.hal_request(p_context.app_conf().infiniteAuthConfigurationBaseUrl + p_context.app_conf().infiniteAuthConfigurationRelativeUrlsAuthorizationsSearchFindByScopeAndAuthorizationType + "?scope=" + URLEncoder.encode(l_scope.getResourceSelfUrl(), StandardCharsets.UTF_8.name()) + "&authorizationType=" + URLEncoder.encode(i_authorization_type, StandardCharsets.UTF_8.name()), GC_TRAVERSE_YES) as T_resource_set
                //todo: add accessor to search and search for all accessors; refactor this method - separate initial accessor matching, scope searching and authorization searching.
                //make it more transparent
                for (Authorization l_authorization in l_authorization_set.getResourceSet()) {
                    l_authorization.set_validity()
                    if (l_authorization.accessor?.resourceSelfUrl == l_matched_accessor.resourceSelfUrl) {
                        l_final_authorization_set_specific_accessor.add(l_authorization)
                    } else if (is_null(l_authorization.accessor)) {
                        l_final_authorization_set_all_accessors.add(l_authorization)
                    }
                }
            }
        }
        if (!l_final_authorization_set_specific_accessor.isEmpty()) {
            l_final_authorization_set = l_final_authorization_set_specific_accessor
        } else {
            l_final_authorization_set = l_final_authorization_set_all_accessors
        }
        T_auth_grant_base_5_context.get_app_context().p_resources_by_reference_url.clear()
        T_auth_grant_base_5_context.get_app_context().p_resources_by_self_url.clear()
        return l_final_authorization_set
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
            , @QueryParam("accessorUser") String i_AccessorUser
            , @QueryParam("accessorFiid") String i_AccessorFiid
            , @QueryParam("accessorProduct") String i_AccessorProduct
            , @QueryParam("accessorProductGroup") String i_AccessorProductGroup
            , @QueryParam("accessorApiVersionName") String i_AccessorApiVersionName
    ) {
        Response l_granting_response = Response.ok().entity(new HashSet<Authorization>()).build()
        if (is_null(i_scope_name)) {
            l_granting_response = Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Mandatory parameter 'scopeName' is missing")).build()
        } else if (is_null(i_authorization_type)) {
            l_granting_response = Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Mandatory parameter 'authorizationType' is missing")).build()
        } else if (is_null(i_AccessorProductGroup)) {
            l_granting_response = Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Mandatory parameter 'accessorProductGroup' is missing")).build()
        } else if (is_null(i_AccessorApiVersionName)) {
            l_granting_response = Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Mandatory parameter 'accessorApiVersionName' is missing")).build()
        } else {
            Set<T_hal_resource> l_final_authorization_set = find_authorization(
                    i_scope_name
                    , i_authorization_type
                    , i_AccessorAppName
                    , i_AccessorPlatform
                    , i_AccessorAppVersion
                    , i_AccessorUser
                    , i_AccessorFiid
                    , i_AccessorProduct
                    , i_AccessorProductGroup
                    , i_AccessorApiVersionName
            )
            l_granting_response = Response.ok().entity(l_final_authorization_set).build()
        }
        return l_granting_response
    }

}