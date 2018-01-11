package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const
import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_5_context
import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_conf
import com.a9ae0b01f0ffc.infinite_auth_granting.server.ApiResponseMessage
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.xml.bind.annotation.XmlTransient
import java.nio.charset.StandardCharsets

@Path("/tokens")
@Component
class Token {

    String authorizationName
    String scopeName
    List<Identity> identityList
    Map<String, List<String>> dataFieldListMap
    String prerequisiteToken
    Date creationDate
    Date expiryDate
    Integer usageLimit
    List<Grant> resourceGrantList
    String authorizationStatus
    String errorCode
    Token refreshToken
    String jwt

    @Autowired
    @JsonIgnore
    T_auth_grant_conf p_app_conf


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
        Request l_request = new Request.Builder().url(p_app_conf.infiniteAuthConfigurationBaseUrl + p_app_conf.infiniteAuthConfigurationRelativeUrlsScopesSearchFindByScopeNameUsingScopeName + URLEncoder.encode(i_scope_name, StandardCharsets.UTF_8.name())).build()
        OkHttpClient l_ok_http_client = T_auth_grant_base_5_context.get_app_context().p_ok_http_client
        Response l_configuration_api_response
        try {
            l_configuration_api_response = l_ok_http_client.newCall(l_request).execute()
            if (!l_configuration_api_response.isSuccessful()) {
                return javax.ws.rs.core.Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "configuration error")).build()
            } else {
                JsonSlurper l_configuration_api_response_json_slurper = new JsonSlurper()
                Object l_slurped_conf_api_response_json = l_configuration_api_response_json_slurper.parseText(l_configuration_api_response.body().string())
                scopeName = l_slurped_conf_api_response_json._embedded.scopes[0].scopeName
                return javax.ws.rs.core.Response.ok().entity(this).build()
            }
            Headers responseHeaders = l_configuration_api_response.headers()
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i))
            }
        }
        finally {
            if (l_configuration_api_response != null) l_configuration_api_response.close()
        }
    }

}