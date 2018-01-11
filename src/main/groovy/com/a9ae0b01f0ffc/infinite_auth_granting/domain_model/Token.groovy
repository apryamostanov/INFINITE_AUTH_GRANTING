package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_5_context
import com.a9ae0b01f0ffc.infinite_auth_granting.server.ApiResponseMessage
import com.a9ae0b01f0ffc.infinite_auth_granting.server.E_api_exception
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import okhttp3.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import java.nio.charset.StandardCharsets

import static base.T_common_base_3_utils.is_null
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util.auth_conf_request
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util.process_parent_accessors
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util.process_parent_versions
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util.process_prerequisite_authorizations

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
    javax.ws.rs.core.Response find_by_scope_name(@QueryParam("scopeName") String i_scope_name) {
        javax.ws.rs.core.Response l_granting_response = javax.ws.rs.core.Response.ok().entity(new HashSet<Token>()).build()
        ObjectMapper l_object_mapper = new ObjectMapper()
        if (is_null(i_scope_name)) {
            l_granting_response = javax.ws.rs.core.Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Mandatory parameter 'scopeName' is missing")).build()
        } else {
            def (Object l_scopes_json, Response l_scope_response, String l_scope_body) = auth_conf_request(p_context.app_conf().infiniteAuthConfigurationBaseUrl + p_context.app_conf().infiniteAuthConfigurationRelativeUrlsScopesSearchFindByScopeName + URLEncoder.encode(i_scope_name, StandardCharsets.UTF_8.name()))
            for (l_scope_json in l_scopes_json?._embedded?.scopes) {
                Set<Token> l_token_set = new HashSet<Token>()
                def (Object l_accessor_json, Response l_accessor_response, String l_accessor_response_body) = auth_conf_request(l_scope_json?._links?.accessor?.href)
                Accessor l_accessor = l_object_mapper.readValue(l_accessor_response_body, Accessor.class)
                def (Object l_version_json, Response l_version_response, String l_version_response_body) = auth_conf_request(l_accessor_json?._links?.apiVersion?.href)
                Version l_version = l_object_mapper.readValue(l_version_response_body, Version.class)
                l_accessor.setApiVersion(l_version)
                process_parent_versions(l_version_json?._links?.parentVersion?.href, l_version)
                try {
                    def (Object l_endpoint_json, Response l_endpoint_response, String l_endpoint_response_body) = auth_conf_request(l_accessor_json?._links?.endpoint?.href)
                    Endpoint l_endpoint = l_object_mapper.readValue(l_endpoint_response_body, Endpoint.class)
                    l_accessor.setEndpoint(l_endpoint)
                    def (Object l_endpoint_version_json, Response l_endpoint_version_response, String l_endpoint_version_response_body) = auth_conf_request(l_endpoint_json?._links?.apiVersion?.href)
                    Version l_endpoint_version = l_object_mapper.readValue(l_endpoint_version_response_body, Version.class)
                    l_endpoint.setApiVersion(l_endpoint_version)
                    process_parent_versions(l_endpoint_version_json?._links?.parentVersion?.href, l_endpoint_version)
                } catch (E_api_exception e_api_exception) {
                    if (e_api_exception.get_code() != HttpURLConnection.HTTP_NOT_FOUND) {
                        throw e_api_exception
                    }
                }
                process_parent_accessors(l_accessor_json?._links?.parentAccessor?.href, l_accessor)
                def (Object l_authorization_set_json, Response l_authorization_set_response, String l_authorization_set_response_body) = auth_conf_request(l_scope_json?._links?.authorizationSet?.href)
                Scope l_scope = new Scope(scopeName: i_scope_name, accessor: l_accessor)
                for (l_authorization_json in l_authorization_set_json?._embedded?.authorizations) {
                    Authorization l_authorization = l_object_mapper.readValue(JsonOutput.toJson(l_authorization_json), Authorization.class)
                    process_prerequisite_authorizations(l_authorization_json?._links?.prerequisiteAuthorization?.href, l_authorization)
                    l_scope.getAuthorizationSet().add(l_authorization)
                    def (Object l_identity_set_json, Response l_identity_set_response, String l_identity_set_response_body) = auth_conf_request(l_authorization_json?._links?.identitySet?.href)
                    for (l_identity_json in l_identity_set_json?._embedded?.identities) {
                        Identity l_identity = l_object_mapper.readValue(JsonOutput.toJson(l_identity_json), Identity.class)
                        l_authorization.getIdentitySet().add(l_identity)
                        def (Object l_authentication_set_json, Response l_authentication_set_response, String l_authentication_set_response_body) = auth_conf_request(l_identity_json?._links?.authenticationSet?.href)
                        for (l_authentication_json in l_authentication_set_json?._embedded?.authentications) {
                            Authentication l_authentication = l_object_mapper.readValue(JsonOutput.toJson(l_authentication_json), Authentication.class)
                            l_identity.getAuthenticationSet().add(l_authentication)
                        }
                    }
                }
                Token l_token = new Token(scope: l_scope)
                l_token_set.add(l_token)
                //String l_scope_self_href = l_scope?._links?.
                l_granting_response = javax.ws.rs.core.Response.ok().entity(l_token_set).build()
            }
        }
        return l_granting_response
    }

}