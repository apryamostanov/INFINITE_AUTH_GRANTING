package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const
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

@Path("/tokens")
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
class Token  extends T_hal_resource{
    String tokenName
    T_hal_resource accessor


    T_hal_resource identity

    T_hal_resource scope

    Integer durationSeconds

    Integer maxUsageCount

    T_hal_resource prerequisiteTokenSet
    T_hal_resource refreshToken
    Date creationDate
    Date expiryDate
    String tokenStatus
    String errorCode
    String jwt

    @Autowired
    @JsonIgnore
    T_auth_grant_base_5_context p_context


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void post_list(String i_json_string) {
        Object l_parsed_json_array = new JsonSlurper().parseText(i_json_string)
        l_parsed_json_array.each { l_json_array_element ->
            ObjectMapper l_object_mapper = new ObjectMapper()
            Token l_token = (Token) l_object_mapper.readValue(new JsonBuilder(l_json_array_element).toPrettyString(), this.class)
            System.out.println("Received authorization " + l_token.getAuthorizationName())
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search/findByScopeName")
    Response find_by_scope_name(@QueryParam("scopeName") String i_scope_name, @Context UriInfo i_uri_info) {
        Response l_granting_response = Response.ok().entity(new HashSet<Token>()).build()
        if (is_null(i_scope_name)) {
            l_granting_response = Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Mandatory parameter 'scopeName' is missing")).build()
        } else {
            T_resource_set l_scope_set = T_auth_grant_base_6_util.hal_request(p_context.app_conf().infiniteAuthConfigurationBaseUrl + p_context.app_conf().infiniteAuthConfigurationRelativeUrlsScopesSearchFindByScopeName + URLEncoder.encode(i_scope_name, StandardCharsets.UTF_8.name()), T_auth_grant_base_4_const.GC_TRAVERSE_NO) as T_resource_set
            Set<T_hal_resource> l_final_token_set = new HashSet<T_hal_resource>()
            T_auth_grant_base_5_context.get_app_context().p_resources_by_reference_url.clear()
            T_auth_grant_base_5_context.get_app_context().p_resources_by_self_url.clear()
            for (l_scope in l_scope_set.getResourceSet()) {
                T_resource_set l_token_set = T_auth_grant_base_6_util.hal_request(p_context.app_conf().infiniteAuthConfigurationBaseUrl + p_context.app_conf().infiniteAuthConfigurationRelativeUrlsTokensSearchFindByScope + URLEncoder.encode(l_scope.getResourceSelfUrl(), StandardCharsets.UTF_8.name()), T_auth_grant_base_4_const.GC_TRAVERSE_YES) as T_resource_set
                l_final_token_set.addAll(l_token_set.getResourceSet())
            }
            l_granting_response = Response.ok().entity(l_final_token_set).build()
        }
        T_auth_grant_base_5_context.get_app_context().p_resources_by_reference_url.clear()
        T_auth_grant_base_5_context.get_app_context().p_resources_by_self_url.clear()
        return l_granting_response
    }

}