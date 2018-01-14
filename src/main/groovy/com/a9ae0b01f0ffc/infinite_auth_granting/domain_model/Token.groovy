package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_5_context
import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util
import com.a9ae0b01f0ffc.infinite_auth_granting.server.ApiResponseMessage
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import java.nio.charset.StandardCharsets

import static base.T_common_base_3_utils.is_null

@Path("/tokens")
@Component
class Token {

    Scope scope
    Map<String, List<String>> dataFieldListMap
    String prerequisiteToken
    Date creationDate
    Date expiryDate
    Integer usageLimit
    List<Grant> resourceGrantList
    String tokenStatus
    String errorCode
    Token refreshToken
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
    Response find_by_scope_name(@QueryParam("scopeName") String i_scope_name) {
        Response l_granting_response = Response.ok().entity(new HashSet<Token>()).build()
        ObjectMapper l_object_mapper = new ObjectMapper()
        if (is_null(i_scope_name)) {
            l_granting_response = Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Mandatory parameter 'scopeName' is missing")).build()
        } else {
            Set<Scope> l_scope_set = T_auth_grant_base_6_util.hal_request(p_context.app_conf().infiniteAuthConfigurationBaseUrl + p_context.app_conf().infiniteAuthConfigurationRelativeUrlsScopesSearchFindByScopeName + URLEncoder.encode(i_scope_name, StandardCharsets.UTF_8.name())) as Set<Scope>
            for (l_scope in l_scope_set) {
                Token l_token = new Token(scope: l_scope)
                Set<Token> l_token_set = new HashSet<Token>()
                l_token_set.add(l_token)
                l_granting_response = Response.ok().entity(l_token_set).build()
            }
        }
        return l_granting_response
    }

}