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


    static Object hal_request_old(String i_url, Boolean i_is_traverse = GC_TRAVERSE_NO) {
        if (get_app_context().p_resources_by_reference_url.containsKey(i_url)) {
            if (is_not_null(get_app_context().p_resources_by_reference_url.get(i_url))) {
                System.out.println("From cache: " + i_url)
                T_hal_resource l_referenced_resource = new T_hal_resource()
                l_referenced_resource.setResourceAccessUrl(i_url)
                l_referenced_resource.setSelfUrl(get_app_context().p_resources_by_reference_url.get(i_url)?.getSelfUrl())
                l_referenced_resource.setIsReferencing(GC_IS_REFERENCE_YES)
                return l_referenced_resource
            } else {
                return GC_NULL_OBJ_REF
            }
        }
        System.out.println(i_url)
        T_client_response l_client_response = okhttp_request(i_url, javax.ws.rs.core.Response.Status.NOT_FOUND.getStatusCode())
        String l_resource_name
        T_hal_resource l_hal_resource
        if (is_not_null(l_client_response.p_slurped_response_json)) {
            if (is_not_null(l_client_response.p_slurped_response_json?._embedded)) {
                l_hal_resource = new T_resource_set()
                for (l_embedded_resource in l_client_response.p_slurped_response_json?._embedded?.values()?.first()) {
                    l_resource_name = l_embedded_resource.resourceName
                    T_hal_resource l_item = get_app_context().p_object_mapper.readValue(JsonOutput.toJson(l_embedded_resource), Class.forName(GC_DOMAIN_MODEL_CLASS_PREFIX + l_resource_name)) as T_hal_resource
                    if (["https://localhost:8080/api/tokens/3", "https://localhost:8080/api/tokens/7/refreshToken"].contains(i_url)) {
                        System.out.println("q")
                    }///////////////\/\/\/\/
                    if (get_app_context().p_resources_by_reference_url.containsKey(l_embedded_resource._links?.self)) {
                        if (is_not_null(get_app_context().p_resources_by_reference_url.get(l_embedded_resource._links?.self))) {
                            System.out.println("From cache: " + i_url)
                            T_hal_resource l_referenced_resource = new T_hal_resource()
                            l_referenced_resource.setResourceAccessUrl(i_url)
                            l_referenced_resource.setSelfUrl(get_app_context().p_resources_by_reference_url.get(i_url)?.getSelfUrl())
                            l_referenced_resource.setIsReferencing(GC_IS_REFERENCE_YES)
                            get_app_context().p_resources_by_reference_url.put(i_url, l_referenced_resource)
                            l_hal_resource.resourceSet.add(l_referenced_resource)
                        } else {
                            get_app_context().p_resources_by_reference_url.put(i_url, GC_NULL_OBJ_REF as T_hal_resource)
                            l_hal_resource.resourceSet.add(GC_NULL_OBJ_REF)
                        }
                    } else {////////////////\/\/\/\
                        l_hal_resource.resourceSet.add(l_item)
                        for (l_key in ((Map) l_embedded_resource._links)?.keySet()) {
                            if (l_item.properties.containsKey(l_key)) {
                                if (i_is_traverse) {
                                    l_item."${l_key}" = hal_request(l_embedded_resource._links?.get(l_key)?.href, GC_TRAVERSE_YES)
                                }
                            }
                            if (l_key == GC_LINK_NAME_SELF) {
                                l_item.setSelfUrl(l_embedded_resource._links?.get(l_key)?.href)
                                //l_item.setResourceRelativeUrl(i_url)
                            }
                        }
                    }
                }
            } else {
                Object l_resource = l_client_response.p_slurped_response_json
                l_resource_name = l_resource.resourceName
                l_hal_resource = get_app_context().p_object_mapper.readValue(JsonOutput.toJson(l_resource), Class.forName(GC_DOMAIN_MODEL_CLASS_PREFIX + l_resource_name)) as T_hal_resource
                for (l_key in l_resource._links?.keySet()) {
                    if (l_hal_resource.properties.containsKey(l_key)) {
                        if (i_is_traverse) {
                            l_hal_resource."${l_key}" = hal_request(l_resource._links?.get(l_key)?.href, GC_TRAVERSE_YES)
                        }
                    }
                    if (l_key == GC_LINK_NAME_SELF) {
                        l_hal_resource.setSelfUrl(l_resource._links?.get(l_key)?.href)
                    }
                }
                if (get_app_context().p_resources_by_reference_url.containsKey(l_hal_resource.getSelfUrl())) {
                    if (is_not_null(get_app_context().p_resources_by_reference_url.get(l_hal_resource.getSelfUrl()))) {
                        System.out.println("From cache: " + i_url)
                        T_hal_resource l_referenced_resource = new T_hal_resource()
                        l_referenced_resource.setResourceAccessUrl(i_url)
                        l_referenced_resource.setSelfUrl(get_app_context().p_resources_by_reference_url.get(i_url)?.getSelfUrl())
                        l_referenced_resource.setIsReferencing(GC_IS_REFERENCE_YES)
                        get_app_context().p_resources_by_reference_url.put(i_url, l_referenced_resource)
                        return l_referenced_resource
                    } else {
                        get_app_context().p_resources_by_reference_url.put(i_url, GC_NULL_OBJ_REF as T_hal_resource)
                        return GC_NULL_OBJ_REF
                    }
                }
            }
            l_hal_resource.setResourceAccessUrl(i_url)
            if (is_not_null(l_hal_resource.getResourceAccessUrl())) {
                get_app_context().p_resources_by_reference_url.put(l_hal_resource.getResourceAccessUrl(), l_hal_resource)
            }
            if (is_not_null(l_hal_resource.getSelfUrl())) {
                get_app_context().p_resources_by_reference_url.put(l_hal_resource.getSelfUrl(), l_hal_resource)
            }
            return l_hal_resource
        } else {
            get_app_context().p_resources_by_reference_url.put(i_url, GC_NULL_OBJ_REF as T_hal_resource)
            return GC_NULL_OBJ_REF
        }
    }

    static Object hal_request(String i_resource_reference_url, Boolean i_is_traverse = GC_TRAVERSE_NO) {
        T_hal_resource l_hal_resource_result
        if (get_app_context().p_resources_by_reference_url.containsKey(i_resource_reference_url)) {
            l_hal_resource_result = get_from_cache(i_resource_reference_url)
        } else {
            T_client_response l_client_response = okhttp_request(i_resource_reference_url, javax.ws.rs.core.Response.Status.NOT_FOUND.getStatusCode())
            if (is_not_null(l_client_response.p_slurped_response_json)) {
                if (is_not_null(l_client_response.p_slurped_response_json?._embedded)) {
                    l_hal_resource_result = new T_resource_set()
                    l_hal_resource_result.setResourceSelfUrl(l_client_response.p_slurped_response_json?._links?.self?.href)
                    for (l_embedded_resource in l_client_response.p_slurped_response_json?._embedded?.values()?.first()) {
                        l_hal_resource_result.resourceSet.add(slurped_json2resource(l_embedded_resource, i_is_traverse))
                    }
                } else {
                    l_hal_resource_result = slurped_json2resource(l_client_response.p_slurped_response_json, i_is_traverse)
                }
            } else {
                l_hal_resource_result = GC_NULL_OBJ_REF as T_hal_resource
            }
        }
        l_hal_resource_result?.setResourceReferenceUrl(i_resource_reference_url)
        get_app_context().p_resources_by_reference_url.put(i_resource_reference_url, l_hal_resource_result)
        return l_hal_resource_result
    }

    static T_hal_resource get_from_cache(String i_resource_reference_url) {
        T_hal_resource l_hal_resource_result
        T_hal_resource l_referenced_resource = get_app_context().p_resources_by_reference_url.get(i_resource_reference_url)
        if (is_not_null(l_referenced_resource)) {
            T_hal_resource l_resource_reference = new T_hal_resource()
            l_resource_reference.setResourceReferenceUrl(i_resource_reference_url)
            l_resource_reference.setResourceSelfUrl(l_referenced_resource.getResourceSelfUrl())
            l_resource_reference.setResourceName(l_referenced_resource.getResourceName())
            l_resource_reference.setIsReference(GC_IS_REFERENCE_YES)
            l_hal_resource_result = l_resource_reference
        } else {
            l_hal_resource_result = GC_NULL_OBJ_REF as T_hal_resource
        }
        return l_hal_resource_result
    }

    static T_hal_resource slurped_json2resource(Object i_slurped_json, Boolean i_is_traverse) {
        T_hal_resource l_hal_resource = get_app_context().p_object_mapper.readValue(JsonOutput.toJson(i_slurped_json), Class.forName(GC_DOMAIN_MODEL_CLASS_PREFIX + i_slurped_json.resourceName)) as T_hal_resource
        if (i_is_traverse) {
            for (l_key in i_slurped_json._links?.keySet()) {
                if (l_hal_resource.properties.containsKey(l_key)) {
                    l_hal_resource."${l_key}" = hal_request(i_slurped_json._links?.get(l_key)?.href, GC_TRAVERSE_YES)
                }
            }
        }
        l_hal_resource.setResourceSelfUrl(i_slurped_json._links?.self?.href)
        return l_hal_resource
    }


}