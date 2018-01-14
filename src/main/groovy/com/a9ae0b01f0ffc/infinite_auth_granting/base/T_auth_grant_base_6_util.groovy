package com.a9ae0b01f0ffc.infinite_auth_granting.base

import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_http_response
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.*
import com.a9ae0b01f0ffc.infinite_auth_granting.server.E_api_exception
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.stereotype.Component
import other.E_application_exception

@Component
class T_auth_grant_base_6_util extends T_auth_grant_base_5_context {

    static T_http_response okhttp_request(String i_url, String... i_ignore_error_codes = GC_SKIPPED_ARGS) {
        Request l_request = new Request.Builder().url(i_url).build()
        OkHttpClient l_ok_http_client = get_app_context().p_ok_http_client
        Response l_response
        try {
            l_response = l_ok_http_client.newCall(l_request).execute()
            String l_response_body = l_response.body().string()
            if (!l_response.isSuccessful()) {
                if (method_arguments_present(i_ignore_error_codes)) {
                    if (i_ignore_error_codes.contains(l_response.code())) {
                        JsonSlurper l_configuration_api_response_json_slurper = new JsonSlurper()
                        Object l_slurped_conf_api_response_json = l_configuration_api_response_json_slurper.parseText(l_response_body)
                        return new T_http_response(p_slurped_response_json: l_slurped_conf_api_response_json, p_okhttp_response: l_response, p_response_string: l_response_body)
                    }
                }
                throw new E_api_exception(l_response.code(), "Configuration API error")
            } else {
                JsonSlurper l_configuration_api_response_json_slurper = new JsonSlurper()
                Object l_slurped_conf_api_response_json = l_configuration_api_response_json_slurper.parseText(l_response_body)
                return new T_http_response(p_slurped_response_json: l_slurped_conf_api_response_json, p_okhttp_response: l_response, p_response_string: l_response_body)
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
                def (Object l_parent_version_json, Response l_parent_version_response, String l_parent_version_response_body) = okhttp_request(l_parent_version_url)
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
                def (Object l_parent_accessor_json, Response l_parent_accessor_response, String l_parent_accessor_response_body) = okhttp_request(l_parent_accessor_url)
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
                def (Object l_prerequisite_authorization_json, Response l_prerequisite_authorization_response, String l_prerequisite_authorization_response_body) = okhttp_request(l_prerequisite_authorization_url)
                Authorization l_prerequisite_authorization = l_object_mapper.readValue(l_prerequisite_authorization_response_body, Authorization.class)
                l_authorization.setPrerequisiteAuthorization(l_prerequisite_authorization)
                l_authorization = l_prerequisite_authorization
                l_prerequisite_authorization_url = l_prerequisite_authorization_json?._links?.prerequisiteAuthorization?.href
                def (Object l_identity_set_json, Response l_identity_set_response, String l_identity_set_response_body) = okhttp_request(l_prerequisite_authorization_json?._links?.identitySet?.href)
                for (l_identity_json in l_identity_set_json?._embedded?.identities) {
                    Identity l_identity = l_object_mapper.readValue(JsonOutput.toJson(l_identity_json), Identity.class)
                    l_authorization.getIdentitySet().add(l_identity)
                    def (Object l_authentication_set_json, Response l_authentication_set_response, String l_authentication_set_response_body) = okhttp_request(l_identity_json?._links?.authenticationSet?.href)
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
/*
    static String translate_url_resource_into_resource_name(String i_url_resource_name) {
        if (i_url_resource_name == "accessors") {
            return "Accessor"
        } else if (i_url_resource_name == "authentications") {
            return "Authentication"
        } else if (i_url_resource_name == "authorizations") {
            return "Authorization"
        } else if (i_url_resource_name == "dataFields") {
            return "DataField"
        } else if (i_url_resource_name == "endpoints") {
            return "Endpoint"
        } else if (i_url_resource_name == "grants") {
            return "Grant"
        } else if (i_url_resource_name == "identities") {
            return "Identity"
        } else if (i_url_resource_name == "methods") {
            return "Method"
        } else if (i_url_resource_name == "resources") {
            return "Resource"
        } else if (i_url_resource_name == "rules") {
            return "Rule"
        } else if (i_url_resource_name == "scopes") {
            return "Scope"
        } else if (i_url_resource_name == "urls") {
            return "Url"
        } else if (i_url_resource_name == "versions") {
            return "Version"
        } else {
            throw new E_application_exception(s.Unsupported_URL_Resource_name_Z1, i_url_resource_name)
        }
    }

    static Object auth_conf_request(String i_href) {
        String l_url_resource_name
        String l_resource_name
        T_http_response l_http_response = okhttp_request(i_href)
        String l_resource_body = l_http_response.p_response_string
        if (is_not_null(l_http_response.p_slurped_response_json?._embedded)) {
            l_url_resource_name = l_http_response.p_slurped_response_json?._embedded?.keySet()?.first()
            l_resource_body = JsonOutput.toJson()
        } else if (i_href.contains(GC_SEARCH_URL_LITERAL)) {
            l_url_resource_name = i_href.substring(GC_FIRST_CHAR, i_href.indexOf(GC_XML_SLASH))
        } else {
            l_url_resource_name = i_href.substring(i_href.lastIndexOf(GC_XML_SLASH) + GC_ONE_CHAR)
        }
        l_resource_name = translate_url_resource_into_resource_name(l_url_resource_name)
        ObjectMapper l_object_mapper = new ObjectMapper()
        Object l_resource_object = l_object_mapper.readValue(l_http_response.p_response_string, Class.forName(GC_DOMAIN_MODEL_CLASS_PREFIX + l_resource_name))
        return l_resource_object
    }*/

}