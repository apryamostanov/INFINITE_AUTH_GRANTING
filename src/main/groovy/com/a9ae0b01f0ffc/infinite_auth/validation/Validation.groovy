package com.a9ae0b01f0ffc.infinite_auth.validation

import com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_base_5_context
import com.a9ae0b01f0ffc.infinite_auth.granting.Authorization
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo
import static com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_base_4_const.*

@Path("/{resource: (?!(authorizations|Authorizations)).*}")
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
class Validation {

    @Autowired
    @JsonIgnore
    T_auth_grant_base_5_context p_app_context

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response post(@Context UriInfo i_uri_info, @HeaderParam("Authorization") String i_jwt, String i_json_string) {
        Response l_granting_response
        if (validate_token(i_uri_info.getQueryParameters(), "POST", i_json_string, i_jwt, p_app_context) == GC_JWT_VALIDITY_EXPIRED) {
            l_granting_response = Response.status(Response.Status.UNAUTHORIZED).build()
        } else if (validate_token(i_uri_info.getQueryParameters(), "POST", i_json_string, i_jwt, p_app_context) == GC_JWT_VALIDITY_INVALID) {
            l_granting_response = Response.status(Response.Status.FORBIDDEN).build()
        } else {
            l_granting_response = Response.ok().build()
        }
        l_granting_response.close()
        return l_granting_response
    }

    @GET
    Response get(@Context UriInfo i_uri_info, @HeaderParam("Authorization") String i_jwt) {
        Response l_granting_response
        Integer l_token_status = validate_token(i_uri_info.getQueryParameters(), "GET", GC_EMPTY_STRING, i_jwt, p_app_context)
        if (l_token_status == GC_JWT_VALIDITY_EXPIRED) {
            l_granting_response = Response.status(Response.Status.UNAUTHORIZED).build()
        } else if (l_token_status == GC_JWT_VALIDITY_INVALID) {
            l_granting_response = Response.status(Response.Status.FORBIDDEN).build()
        } else {
            l_granting_response = Response.noContent().build()
        }
        l_granting_response.close()
        return l_granting_response
    }


    static Integer validate_token(MultivaluedMap<String, String> i_query_parameters, String i_method, String i_body, String i_jwt, T_auth_grant_base_5_context i_context) {
        if (is_null(i_jwt)) {
            return GC_JWT_VALIDITY_INVALID
        }
        if (Authorization.is_invalid_access_jwt(i_jwt, i_context)) {
            return GC_JWT_VALIDITY_INVALID
        }
        Authorization l_authorization = Authorization.access_jwt2authorization(i_jwt, i_context)
        if (l_authorization.expiryDate.before(new Date())) {
            return GC_JWT_VALIDITY_EXPIRED
        }
        return GC_JWT_VALIDITY_OK
    }

}