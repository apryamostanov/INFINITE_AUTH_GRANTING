package com.a9ae0b01f0ffc.infinite_auth_granting.base

import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_client_response
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_hal_resource
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_resource_set
import com.a9ae0b01f0ffc.infinite_auth_granting.server.E_api_exception
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject

class T_auth_grant_base_6_util extends T_auth_grant_base_5_context {

    static T_client_response okhttp_request(String i_url, Integer... i_ignore_error_codes = GC_SKIPPED_ARGS as Integer[]) {
        Request l_request = new Request.Builder().url(i_url).build()
        OkHttpClient l_ok_http_client = get_app_context().p_ok_http_client
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

    static Object hal_request(String i_resource_reference_url, Boolean i_is_traverse = GC_TRAVERSE_NO, String i_nest_mode = GC_NEST_MODE_REFERENCE) {
        T_hal_resource l_hal_resource_result
        if (get_app_context().p_resources_by_reference_url.containsKey(i_resource_reference_url)) {
            l_hal_resource_result = get_from_reference_cache(i_resource_reference_url, i_nest_mode)
        } else {
            T_client_response l_client_response = okhttp_request(i_resource_reference_url, javax.ws.rs.core.Response.Status.NOT_FOUND.getStatusCode())
            if (is_not_null(l_client_response.p_slurped_response_json)) {
                if (is_not_null(l_client_response.p_slurped_response_json?._embedded)) {
                    l_hal_resource_result = new T_resource_set()
                    l_hal_resource_result.setResourceSelfUrl(l_client_response.p_slurped_response_json?._links?.self?.href)
                    for (l_embedded_resource in l_client_response.p_slurped_response_json?._embedded?.values()?.first()) {
                        l_hal_resource_result.resourceSet.add(slurped_json2resource(l_embedded_resource, i_is_traverse, i_resource_reference_url, i_nest_mode))
                    }
                } else {
                    l_hal_resource_result = slurped_json2resource(l_client_response.p_slurped_response_json, i_is_traverse, i_resource_reference_url, i_nest_mode)
                }
            } else {
                l_hal_resource_result = GC_NULL_OBJ_REF as T_hal_resource
            }
        }
        l_hal_resource_result?.setResourceReferenceUrl(i_resource_reference_url)
        get_app_context().p_resources_by_reference_url.put(i_resource_reference_url, l_hal_resource_result)
        return l_hal_resource_result
    }

    static T_hal_resource get_from_reference_cache(String i_resource_reference_url, String i_nest_mode = GC_NEST_MODE_REFERENCE) {
        T_hal_resource l_hal_resource_result
        T_hal_resource l_referenced_resource = get_app_context().p_resources_by_reference_url.get(i_resource_reference_url)
        if (is_not_null(l_referenced_resource)) {
            if (nvl(i_nest_mode, GC_NEST_MODE_REFERENCE) == GC_NEST_MODE_REFERENCE) {
                T_hal_resource l_resource_reference = new T_hal_resource()
                l_resource_reference.setResourceReferenceUrl(i_resource_reference_url)
                l_resource_reference.setResourceSelfUrl(l_referenced_resource.getResourceSelfUrl())
                l_resource_reference.setResourceName(l_referenced_resource.getResourceName())
                l_resource_reference.setIsReference(GC_IS_REFERENCE_YES)
                l_hal_resource_result = l_resource_reference
            } else {
                l_hal_resource_result = get_app_context().p_resources_by_reference_url.get(i_resource_reference_url)
            }
        } else {
            l_hal_resource_result = GC_NULL_OBJ_REF as T_hal_resource
        }
        return l_hal_resource_result
    }

    static T_hal_resource slurped_json2resource(Object i_slurped_json, Boolean i_is_traverse, String i_resource_reference_url, String i_nest_mode = GC_NEST_MODE_REFERENCE) {
        T_hal_resource l_hal_resource = get_app_context().p_object_mapper.readValue(JsonOutput.toJson(i_slurped_json), Class.forName(GC_DOMAIN_MODEL_CLASS_PREFIX + i_slurped_json.resourceName)) as T_hal_resource
        if (i_is_traverse) {
            for (l_key in i_slurped_json._links?.keySet()) {
                if (l_hal_resource.properties.containsKey(l_key)) {
                    l_hal_resource."${l_key}" = hal_request(i_slurped_json._links?.get(l_key)?.href, GC_TRAVERSE_YES, i_nest_mode)
                }
            }
        }
        l_hal_resource.setResourceSelfUrl(i_slurped_json._links?.self?.href)
        l_hal_resource.setResourceReferenceUrl(i_resource_reference_url)
        if (get_app_context().p_resources_by_self_url.containsKey(l_hal_resource.getResourceSelfUrl())) {
            if (nvl(i_nest_mode, GC_NEST_MODE_REFERENCE) == GC_NEST_MODE_REFERENCE) {
                T_hal_resource l_self_cache_resource = get_app_context().p_resources_by_self_url.get(l_hal_resource.getResourceSelfUrl())
                l_hal_resource = new T_hal_resource()
                l_hal_resource.setResourceSelfUrl(l_self_cache_resource.getResourceSelfUrl())
                l_hal_resource.setResourceName(l_self_cache_resource.getResourceName())
                l_hal_resource.setIsReference(GC_IS_REFERENCE_YES)
            } else {
                l_hal_resource = get_app_context().p_resources_by_self_url.get(l_hal_resource.getResourceSelfUrl())
            }
        } else {
            get_app_context().p_resources_by_self_url.put(l_hal_resource.getResourceSelfUrl(), l_hal_resource)
        }
        return l_hal_resource
    }


}