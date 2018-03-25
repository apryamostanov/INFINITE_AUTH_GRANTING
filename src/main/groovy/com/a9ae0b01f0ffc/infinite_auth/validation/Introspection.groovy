package com.a9ae0b01f0ffc.infinite_auth.validation

import com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_base_5_context
import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.AuthenticationType
import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.AuthorizationType
import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.IdentityType
import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.ScopeType
import com.a9ae0b01f0ffc.infinite_auth.granting.Authentication
import com.a9ae0b01f0ffc.infinite_auth.granting.Authorization
import com.a9ae0b01f0ffc.infinite_auth.granting.Identity
import com.a9ae0b01f0ffc.infinite_auth.granting.Scope
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
import static com.a9ae0b01f0ffc.infinite_auth.granting.Authorization.access_jwt2authorization
import static com.a9ae0b01f0ffc.infinite_auth.validation.Validation.remove_jwt_bearer

@Path("/{resource: introspection}")
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
class Introspection {

    @Autowired
    @JsonIgnore
    T_auth_grant_base_5_context p_app_context

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
                l_authorization_set.add(access_jwt2authorization(l_authorization.jwt, p_app_context))
            }
        } else {
            Authorization l_authorization = p_app_context.p_object_mapper.readValue(JsonOutput.toJson(l_parsed_json), Authorization.class) as Authorization
            l_authorization_set.add(access_jwt2authorization(l_authorization.jwt, p_app_context))
        }
        l_granting_response = Response.ok().entity(p_app_context.p_object_mapper.writeValueAsString(l_authorization_set)).build()
        return l_granting_response
    }

}