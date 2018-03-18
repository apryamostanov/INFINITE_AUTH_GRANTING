package com.a9ae0b01f0ffc.infinite_auth.validation

import com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_base_5_context
import com.a9ae0b01f0ffc.infinite_auth.granting.Authorization
import com.a9ae0b01f0ffc.infinite_auth.server.ApiException
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.json.JSONObject
import org.json.XML
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.ws.rs.*
import javax.ws.rs.core.*

import static base.T_common_base_1_const.GC_EMPTY_SIZE
import static base.T_common_base_3_utils.is_null
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
        if (validate_token(i_uri_info.getQueryParameters(), "POST", i_json_string, i_jwt, p_app_context, i_uri_info.getPath()) == GC_JWT_VALIDITY_EXPIRED) {
            throw new ApiException(Response.Status.UNAUTHORIZED.getStatusCode(), 401, "Expired jwt",
                    "Expired jwt", i_uri_info.getRequestUri().toString())
        } else if (validate_token(i_uri_info.getQueryParameters(), "POST", i_json_string, i_jwt, p_app_context, i_uri_info.getPath()) == GC_JWT_VALIDITY_INVALID) {
            System.out.println(8)
            throw new ApiException(Response.Status.FORBIDDEN.getStatusCode(), 403, "Invalid jwt",
                    "Invalid jwt", i_uri_info.getRequestUri().toString())
        } else {
            l_granting_response = Response.noContent().build()
        }
        l_granting_response.close()
        return l_granting_response
    }

    @GET
    Response get(@Context UriInfo i_uri_info, @HeaderParam("Authorization") String i_jwt) {
        Response l_granting_response
        if (validate_token(i_uri_info.getQueryParameters(), "GET", i_json_string, i_jwt, p_app_context, i_uri_info.getPath()) == GC_JWT_VALIDITY_EXPIRED) {
            throw new ApiException(Response.Status.UNAUTHORIZED.getStatusCode(), 401, "Expired jwt",
                    "Expired jwt", i_uri_info.getRequestUri().toString())
        } else if (validate_token(i_uri_info.getQueryParameters(), "POST", i_json_string, i_jwt, p_app_context, i_uri_info.getPath()) == GC_JWT_VALIDITY_INVALID) {
            System.out.println(8)
            throw new ApiException(Response.Status.FORBIDDEN.getStatusCode(), 403, "Invalid jwt",
                    "Invalid jwt", i_uri_info.getRequestUri().toString())
        } else {
            l_granting_response = Response.noContent().build()
        }
        l_granting_response.close()
        return l_granting_response
    }

    Integer validate_token(MultivaluedMap<String, String> i_query_parameters, String i_method, String i_body, String i_jwt, T_auth_grant_base_5_context i_context, String i_url_path) {
        System.out.println(i_url_path)
        if (is_null(i_jwt)) {
            System.out.println(1)
            return GC_JWT_VALIDITY_INVALID
        }
        if (Authorization.is_invalid_access_jwt(i_jwt, i_context)) {
            System.out.println(2)
            return GC_JWT_VALIDITY_INVALID
        }
        Authorization l_authorization = Authorization.access_jwt2authorization(i_jwt, i_context)
        if (l_authorization.expiryDate.before(new Date())) {
            System.out.println(3)
            return GC_JWT_VALIDITY_EXPIRED
        }
        if (p_app_context.p_revocation_repository.findByAuthorizationId(l_authorization.authorizationId).size() > GC_EMPTY_SIZE) {
            System.out.println(4)
            return GC_JWT_VALIDITY_INVALID
        }
        if (p_app_context.p_usage_repository.findByAuthorizationId(l_authorization.authorizationId).size() > (nvl(l_authorization.maxUsageCount, GC_ZERO) as Integer)) {
            System.out.println(5)
            return GC_JWT_VALIDITY_INVALID
        }
        Boolean l_is_matched_resource_grant = GC_FALSE
        for (l_grant in l_authorization.scope.grantSet) {
            if (is_not_null(l_grant.urlMask)) {
                if (l_grant.urlMask.matches(i_url_path)) {
                    l_is_matched_resource_grant = GC_TRUE
                }
            } else {
                if (l_grant.restResourceName == i_url_path) {
                    l_is_matched_resource_grant = GC_TRUE
                }
            }
            if (l_is_matched_resource_grant && l_grant.method == i_method) {
                Binding l_binding = new Binding()
                l_binding.setVariable("i_query_parameters", i_query_parameters)
                l_binding.setVariable("i_method", i_method)
                l_binding.setVariable("i_body", i_body)
                l_binding.setVariable("i_jwt", i_jwt)
                l_binding.setVariable("i_context", i_context)
                l_binding.setVariable("i_url_path", i_url_path)
                l_binding.setVariable("i_authorization", l_authorization)
                if (i_context.get_validation_runner().run(l_grant.validationModuleName + i_context.app_conf().validationModulesExtension, l_binding)) {
                    Usage l_usage = new Usage(authorizationId: l_authorization.authorizationId, usageDate: new Date(), restResourceName: l_grant.restResourceName)
                    p_app_context.p_usage_repository.save([l_usage])
                    return GC_JWT_VALIDITY_OK
                } else {
                    System.out.println(6)
                    return GC_JWT_VALIDITY_INVALID
                }
            }
            l_is_matched_resource_grant = GC_FALSE
        }
        System.out.println(7)
        return GC_JWT_VALIDITY_INVALID
    }

}