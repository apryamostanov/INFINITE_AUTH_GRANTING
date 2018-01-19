package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_5_context
import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_hal_resource
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_resource_set
import com.a9ae0b01f0ffc.infinite_auth_granting.server.ApiResponseMessage
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo
import java.nio.charset.StandardCharsets

import static base.T_common_base_3_utils.is_null
import static base.T_common_base_3_utils.nvl
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.*

@Path("/authorizations")
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
class Authorization extends T_hal_resource {
    String authorizationName
    T_hal_resource accessor


    T_hal_resource identity

    T_hal_resource scope

    Integer durationSeconds

    Integer maxUsageCount

    T_hal_resource prerequisiteAuthorizationSet
    T_hal_resource refreshAuthorization
    Date creationDate
    Date expiryDate
    String authorizationStatus = "new"
    String errorCode
    String jwt
    String authorizationType
    @Autowired
    @JsonIgnore
    T_auth_grant_base_5_context p_context


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void post_list(String i_json_string) {
        Object l_parsed_json_array = new JsonSlurper().parseText(i_json_string)
        l_parsed_json_array.each { l_json_array_element ->
            ObjectMapper l_object_mapper = new ObjectMapper()
            Authorization l_token = (Authorization) l_object_mapper.readValue(new JsonBuilder(l_json_array_element).toPrettyString(), this.class)
            System.out.println("Received authorization " + l_token.getAuthorizationName())
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search/findByScopeName")
    Response find_by_scope_name(
            @QueryParam("scopeName") String i_scope_name
            , @QueryParam("nestMode") String i_nest_mode
            , @QueryParam("accessorAppName") String i_AccessorAppName
            , @QueryParam("accessorPlatform") String i_AccessorPlatform
            , @QueryParam("accessorAppVersion") String i_AccessorAppVersion
            , @QueryParam("accessorUser") String i_AccessorUser
            , @QueryParam("accessorFiid") String i_AccessorFiid
            , @QueryParam("accessorProduct") String i_AccessorProduct
            , @QueryParam("accessorProductGroup") String i_AccessorProductGroup
            , @QueryParam("accessorApiVersionName") String i_AccessorApiVersionName
            , @Context UriInfo i_uri_info
    ) {
        Response l_granting_response = Response.ok().entity(new HashSet<Authorization>()).build()
        if (is_null(i_scope_name)) {
            l_granting_response = Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Mandatory parameter 'scopeName' is missing")).build()
        } else {
            String l_AccessorApiVersionName = nvl(i_AccessorApiVersionName, "Any")
            T_auth_grant_base_5_context.get_app_context().p_resources_by_reference_url.clear()
            T_auth_grant_base_5_context.get_app_context().p_resources_by_self_url.clear()
            T_resource_set l_version_set = T_auth_grant_base_6_util.hal_request(p_context.app_conf().infiniteAuthConfigurationBaseUrl + p_context.app_conf().infiniteAuthConfigurationRelativeUrlsVersionsSearchFindByVersionName + URLEncoder.encode(l_AccessorApiVersionName, StandardCharsets.UTF_8.name()), GC_TRAVERSE_NO, GC_NEST_MODE_REFERENCE) as T_resource_set
            Set<T_hal_resource> l_final_authorization_set = new HashSet<T_hal_resource>()
            Set<T_hal_resource> l_final_authorization_set_specific_accessor = new HashSet<T_hal_resource>()
            for (l_version in l_version_set.resourceSet) {
                T_resource_set l_accessor_set = T_auth_grant_base_6_util.hal_request(p_context.app_conf().infiniteAuthConfigurationBaseUrl + p_context.app_conf().infiniteAuthConfigurationRelativeUrlsAccessors, GC_TRAVERSE_YES, GC_NEST_MODE_REFERENCE) as T_resource_set
                List<Accessor> l_matched_accessor_set = new ArrayList<Accessor>()
                for (Accessor l_accessor in l_accessor_set?.getResourceSet()) {
                    if ([nvl(i_AccessorAppName, "Any"), "Any"].contains(l_accessor.appName)
                            && [nvl(i_AccessorPlatform, "Any"), "Any"].contains(l_accessor.platform)
                            && [nvl(i_AccessorAppVersion, "Any"), "Any"].contains(l_accessor.appVersion)
                            && [nvl(i_AccessorUser, "Any"), "Any"].contains(l_accessor.user)
                            && [nvl(i_AccessorFiid, "Any"), "Any"].contains(l_accessor.fiid)
                            && [nvl(i_AccessorProduct, "Any"), "Any"].contains(l_accessor.product)
                            && [nvl(i_AccessorProductGroup, "Any"), "Any"].contains(l_accessor.productGroup)
                            && [nvl(i_AccessorApiVersionName, "Any"), "Any"].contains(((Version) l_accessor.apiVersion)?.versionName)
                    ) {
                        l_matched_accessor_set.add(l_accessor)
                    }
                }
                l_matched_accessor_set.sort { it.lookupPriority }
                l_matched_accessor_set.reverse()
                Set<Scope> l_scope_set = new HashSet<Scope>()
                for (l_matched_accessor in l_matched_accessor_set) {
                    T_resource_set l_scope_resource_set = T_auth_grant_base_6_util.hal_request(p_context.app_conf().infiniteAuthConfigurationBaseUrl + p_context.app_conf().infiniteAuthConfigurationRelativeUrlsScopesSearchFindByScopeNameAndAccessor + "?scopeName=" + URLEncoder.encode(i_scope_name, StandardCharsets.UTF_8.name()) + "&accessor=" + URLEncoder.encode(l_matched_accessor.resourceSelfUrl, StandardCharsets.UTF_8.name()), GC_TRAVERSE_NO, GC_NEST_MODE_REFERENCE) as T_resource_set
                    l_scope_set.addAll(l_scope_resource_set.getResourceSet() as Collection<? extends Scope>)
                    T_auth_grant_base_5_context.get_app_context().p_resources_by_reference_url.clear()
                    T_auth_grant_base_5_context.get_app_context().p_resources_by_self_url.clear()
                    for (l_scope in l_scope_set) {
                        T_resource_set l_authorization_set = T_auth_grant_base_6_util.hal_request(p_context.app_conf().infiniteAuthConfigurationBaseUrl + p_context.app_conf().infiniteAuthConfigurationRelativeUrlsAuthorizationsSearchFindByScope + URLEncoder.encode(l_scope.getResourceSelfUrl(), StandardCharsets.UTF_8.name()), GC_TRAVERSE_YES, i_nest_mode) as T_resource_set
                        for (Authorization l_authorization in l_authorization_set.getResourceSet()) {
                            if (l_authorization.accessor?.resourceSelfUrl == l_matched_accessor.resourceSelfUrl) {
                                l_final_authorization_set_specific_accessor.add(l_authorization)
                            } else if (is_null(l_authorization.accessor)) {
                                l_final_authorization_set.add(l_authorization)
                            }//TODO: does not work well with reference nest mode
                        }
                    }
                }
            }
            if (!l_final_authorization_set_specific_accessor.isEmpty()) {
                l_granting_response = Response.ok().entity(l_final_authorization_set_specific_accessor).build()
            } else {
                l_granting_response = Response.ok().entity(l_final_authorization_set).build()
            }
        }
        T_auth_grant_base_5_context.get_app_context().p_resources_by_reference_url.clear()
        T_auth_grant_base_5_context.get_app_context().p_resources_by_self_url.clear()
        return l_granting_response
    }

}