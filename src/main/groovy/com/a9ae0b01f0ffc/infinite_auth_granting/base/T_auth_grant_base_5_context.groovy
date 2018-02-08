package com.a9ae0b01f0ffc.infinite_auth_granting.base

import com.a9ae0b01f0ffc.infinite_auth_granting.client.*
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_accessor_type_repository
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_authentication_type_repository
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_authorization_type_repository
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_grant_type_repository
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_identity_type_repository
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_scope_type_repository
import com.a9ae0b01f0ffc.infinite_auth_granting.server.E_api_exception
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.net.ssl.HostnameVerifier
import java.nio.charset.StandardCharsets
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

@Component
class T_auth_grant_base_5_context extends T_auth_grant_base_4_const {


    GroovyScriptEngine p_authentication_runner
    GroovyScriptEngine p_authentication_config_holder
    OkHttpClient p_ok_http_client = new OkHttpClient.Builder().hostnameVerifier(get_unsecure_host_name_verifier()).build()

    @JsonIgnore
    @Autowired
    ObjectMapper p_object_mapper

    @Autowired
    T_jwt_manager p_jwt_manager
    @Autowired
    @JsonIgnore
    T_auth_grant_conf p_app_conf

    @Autowired
    @JsonIgnore
    I_accessor_type_repository p_accessor_type_repository
    @Autowired
    @JsonIgnore
    I_authentication_type_repository p_authentication_repository
    @Autowired
    @JsonIgnore
    I_identity_type_repository p_identity_repository
    @Autowired
    @JsonIgnore
    I_scope_type_repository p_scope_repository
    @Autowired
    @JsonIgnore
    I_grant_type_repository p_grant_repository
    @Autowired
    @JsonIgnore
    I_authorization_type_repository p_authorization_type_repository

    static HostnameVerifier get_unsecure_host_name_verifier() {
        return new T_host_name_verifier()
    }

    GroovyScriptEngine get_authentication_runner() {
        if (is_null(p_authentication_runner)) {
            p_authentication_runner = new GroovyScriptEngine(app_conf().authenticationModulesPath)
        }
        return p_authentication_runner
    }

    GroovyScriptEngine get_authentication_config_holder() {
        if (is_null(p_authentication_runner)) {
            p_authentication_config_holder = new GroovyScriptEngine(app_conf().authenticationConfigPath)
        }
        return p_authentication_config_holder
    }

    T_auth_grant_conf app_conf() {
        return p_app_conf
    }


    T_client_response okhttp_get_request(String i_url, Integer[] i_ignore_error_codes = GC_SKIPPED_ARGS as Integer[]) {
        Request l_request
        l_request = new Request.Builder().url(i_url).build()
        OkHttpClient l_ok_http_client = p_ok_http_client
        Response l_response
        try {
            l_response = l_ok_http_client.newCall(l_request).execute()
            String l_response_body = l_response.body().string()
            if (!l_response.isSuccessful()) {
                if (method_arguments_present(i_ignore_error_codes)) {
                    if (i_ignore_error_codes.contains(l_response.code())) {
                        Object l_slurped_conf_api_response_json = GC_NULL_OBJ_REF
                        JSONObject l_json_object = GC_NULL_OBJ_REF as JSONObject
                        return new T_client_response(p_slurped_response_json: l_slurped_conf_api_response_json, p_okhttp_response: l_response, p_response_string: l_response_body, p_json_object: l_json_object)
                    }
                }
                throw new E_api_exception(l_response.code(), "Configuration API error")
            } else {
                JsonSlurper l_configuration_api_response_json_slurper = new JsonSlurper()
                Object l_slurped_conf_api_response_json = l_configuration_api_response_json_slurper.parseText(l_response_body)
                return new T_client_response(p_slurped_response_json: l_slurped_conf_api_response_json, p_okhttp_response: l_response, p_response_string: l_response_body)
            }
        }
        finally {
            l_response?.close()
        }
    }

    Object hal_request(String i_resource_reference_url, Boolean i_is_traverse = GC_TRAVERSE_NO) {
        System.out.println(i_resource_reference_url)
        I_hal_resource l_hal_resource_result
        if (p_resources_by_reference_url.containsKey(i_resource_reference_url)) {
            l_hal_resource_result = get_from_reference_cache(i_resource_reference_url)
        } else {
            T_client_response l_client_response = okhttp_get_request(i_resource_reference_url, [javax.ws.rs.core.Response.Status.NOT_FOUND.getStatusCode()] as Integer[])
            if (is_not_null(l_client_response.p_slurped_response_json)) {
                if (is_not_null(l_client_response.p_slurped_response_json?._embedded)) {
                    l_hal_resource_result = new T_resource_set()
                    l_hal_resource_result.setResourceSelfUrl(l_client_response.p_slurped_response_json?._links?.self?.href)
                    for (l_embedded_resource in l_client_response.p_slurped_response_json?._embedded?.values()?.first()) {
                        l_hal_resource_result.add(slurped_json2resource(l_embedded_resource, i_is_traverse, i_resource_reference_url))
                    }
                } else {
                    l_hal_resource_result = slurped_json2resource(l_client_response.p_slurped_response_json, i_is_traverse, i_resource_reference_url)
                }
            } else {
                l_hal_resource_result = GC_NULL_OBJ_REF as T_hal_resource
            }
        }
        p_resources_by_reference_url.put(i_resource_reference_url, l_hal_resource_result)
        return l_hal_resource_result
    }


    I_hal_resource get_from_reference_cache(String i_resource_reference_url) {
        I_hal_resource l_hal_resource_result
        I_hal_resource l_referenced_resource = p_resources_by_reference_url.get(i_resource_reference_url)
        if (is_not_null(l_referenced_resource)) {
            l_hal_resource_result = p_resources_by_reference_url.get(i_resource_reference_url)
        } else {
            l_hal_resource_result = GC_NULL_OBJ_REF as T_hal_resource
        }
        return l_hal_resource_result
    }

    I_hal_resource slurped_json2resource(Object i_slurped_json, Boolean i_is_traverse, String i_resource_reference_url) {
        I_hal_resource l_hal_resource = new ObjectMapper().readValue(JsonOutput.toJson(i_slurped_json), Class.forName(GC_DOMAIN_MODEL_CLASS_PREFIX + i_slurped_json.resourceName)) as T_hal_resource
        if (i_is_traverse) {
            for (l_key in i_slurped_json._links?.keySet()) {
                if (l_hal_resource.properties.containsKey(l_key)) {
                    l_hal_resource."${l_key}" = hal_request(i_slurped_json._links?.get(l_key)?.href, GC_TRAVERSE_YES)
                }
            }
        }
        l_hal_resource.setResourceSelfUrl(i_slurped_json._links?.self?.href)
        if (p_resources_by_self_url.containsKey(l_hal_resource.getResourceSelfUrl())) {
            l_hal_resource = p_resources_by_self_url.get(l_hal_resource.getResourceSelfUrl())
        } else {
            p_resources_by_self_url.put(l_hal_resource.getResourceSelfUrl(), l_hal_resource)
        }
        return l_hal_resource
    }

    static def zip(String s) {
        def targetStream = new ByteArrayOutputStream()
        def zipStream = new GZIPOutputStream(targetStream)
        zipStream.write(s.getBytes(StandardCharsets.UTF_8.name()))
        zipStream.close()
        def zippedBytes = targetStream.toByteArray()
        targetStream.close()
        return zippedBytes.encodeBase64()
    }

    static def unzip(String compressed) {
        def inflaterStream = new GZIPInputStream(new ByteArrayInputStream(compressed.decodeBase64()))
        def uncompressedStr = inflaterStream.getText(StandardCharsets.UTF_8.name())
        return uncompressedStr
    }

}
