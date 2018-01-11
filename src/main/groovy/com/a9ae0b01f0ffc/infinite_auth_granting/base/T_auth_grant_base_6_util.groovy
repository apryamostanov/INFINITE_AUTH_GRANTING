package com.a9ae0b01f0ffc.infinite_auth_granting.base

import base.T_logging_base_5_context
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Accessor
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Authentication
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Authorization
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Identity
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Version
import com.a9ae0b01f0ffc.infinite_auth_granting.server.ApiResponseMessage
import com.a9ae0b01f0ffc.infinite_auth_granting.server.E_api_exception
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import other.E_application_exception

import java.nio.charset.StandardCharsets

@Component
class T_auth_grant_base_6_util extends T_logging_base_5_context {

    static auth_conf_request(String i_url) {
        Request l_request = new Request.Builder().url(i_url).build()
        OkHttpClient l_ok_http_client = T_auth_grant_base_5_context.get_app_context().p_ok_http_client
        Response l_response
        try {
            l_response = l_ok_http_client.newCall(l_request).execute()
            String l_response_body = l_response.body().string()
            if (!l_response.isSuccessful()) {
                throw new E_api_exception(l_response.code(), "Configuration API error")
            } else {
                JsonSlurper l_configuration_api_response_json_slurper = new JsonSlurper()
                Object l_slurped_conf_api_response_json = l_configuration_api_response_json_slurper.parseText(l_response_body)
                return [l_slurped_conf_api_response_json, l_response, l_response_body]
            }
        }
        finally {
            l_response?.close()
        }
    }

    static void process_parent_versions(String i_initial_parent_version_url, Version i_initial_version) {
        String l_parent_version_url = i_initial_parent_version_url
        Version l_version = i_initial_version
        ObjectMapper l_object_mapper = new ObjectMapper()
        while (GC_TRUE) {
            try {
                def (Object l_parent_version_json, Response l_parent_version_response, String l_parent_version_response_body) = auth_conf_request(l_parent_version_url)
                Version l_parent_version = l_object_mapper.readValue(l_parent_version_response_body, Version.class)
                l_version.setParentVersion(l_parent_version)
                l_version = l_parent_version
                l_parent_version_url = l_parent_version_json?._links?.parentVersion?.href
            } catch (E_api_exception e_api_exception) {
                if (e_api_exception.get_code() == HttpURLConnection.HTTP_NOT_FOUND) {
                    break
                } else {
                    throw e_api_exception
                }
            }
        }
    }

    static void process_parent_accessors(String i_initial_parent_accessor_url, Accessor i_initial_accessor) {
        String l_parent_accessor_url = i_initial_parent_accessor_url
        Accessor l_accessor = i_initial_accessor
        ObjectMapper l_object_mapper = new ObjectMapper()
        while (GC_TRUE) {
            try {
                def (Object l_parent_accessor_json, Response l_parent_accessor_response, String l_parent_accessor_response_body) = auth_conf_request(l_parent_accessor_url)
                Accessor l_parent_accessor = l_object_mapper.readValue(l_parent_accessor_response_body, Accessor.class)
                l_accessor.setParentAccessor(l_parent_accessor)
                l_accessor = l_parent_accessor
                l_parent_accessor_url = l_parent_accessor_json?._links?.parentAccessor?.href
            } catch (E_api_exception e_api_exception) {
                if (e_api_exception.get_code() == HttpURLConnection.HTTP_NOT_FOUND) {
                    break
                } else {
                    throw e_api_exception
                }
            }
        }
    }

    static void process_prerequisite_authorizations(String i_initial_prerequisite_authorization_url, Authorization i_initial_authorization) {
        String l_prerequisite_authorization_url = i_initial_prerequisite_authorization_url
        Authorization l_authorization = i_initial_authorization
        ObjectMapper l_object_mapper = new ObjectMapper()
        while (GC_TRUE) {
            try {
                def (Object l_prerequisite_authorization_json, Response l_prerequisite_authorization_response, String l_prerequisite_authorization_response_body) = auth_conf_request(l_prerequisite_authorization_url)
                Authorization l_prerequisite_authorization = l_object_mapper.readValue(l_prerequisite_authorization_response_body, Authorization.class)
                l_authorization.setPrerequisiteAuthorization(l_prerequisite_authorization)
                l_authorization = l_prerequisite_authorization
                l_prerequisite_authorization_url = l_prerequisite_authorization_json?._links?.prerequisiteAuthorization?.href
                def (Object l_identity_set_json, Response l_identity_set_response, String l_identity_set_response_body) = auth_conf_request(l_prerequisite_authorization_json?._links?.identitySet?.href)
                for (l_identity_json in l_identity_set_json?._embedded?.identities) {
                    Identity l_identity = l_object_mapper.readValue(JsonOutput.toJson(l_identity_json), Identity.class)
                    l_authorization.getIdentitySet().add(l_identity)
                    def (Object l_authentication_set_json, Response l_authentication_set_response, String l_authentication_set_response_body) = auth_conf_request(l_identity_json?._links?.authenticationSet?.href)
                    for (l_authentication_json in l_authentication_set_json?._embedded?.authentications) {
                        Authentication l_authentication = l_object_mapper.readValue(JsonOutput.toJson(l_authentication_json), Authentication.class)
                        l_identity.getAuthenticationSet().add(l_authentication)
                    }
                }
            } catch (E_api_exception e_api_exception) {
                if (e_api_exception.get_code() == HttpURLConnection.HTTP_NOT_FOUND) {
                    break
                } else {
                    throw e_api_exception
                }
            }
        }
    }

}