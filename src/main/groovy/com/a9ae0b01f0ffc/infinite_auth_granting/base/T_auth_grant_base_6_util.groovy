package com.a9ae0b01f0ffc.infinite_auth_granting.base

import com.a9ae0b01f0ffc.infinite_auth_granting.client.ReferencingResource
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_client_response
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


    static Object hal_request(String i_url, Boolean i_is_traverse = GC_TRAVERSE_NO) {
        if (get_app_context().p_resources_by_url.containsKey(i_url)) {
            if (is_not_null(get_app_context().p_resources_by_url.get(i_url))) {
                System.out.println("From cache: " + i_url)
                ReferencingResource l_referenced_resource = new ReferencingResource()
                l_referenced_resource.setResourceAccessUrl(i_url)
                l_referenced_resource.setResourceAbsoluteUrl(get_app_context().p_resources_by_url.get(i_url)?.getResourceAbsoluteUrl())
                l_referenced_resource.setIsReferencing(GC_IS_REFERENCED_YES)
                return l_referenced_resource
            } else {
                return GC_NULL_OBJ_REF
            }
        }
        System.out.println(i_url)
        T_client_response l_client_response = okhttp_request(i_url, javax.ws.rs.core.Response.Status.NOT_FOUND.getStatusCode())
        String l_resource_name
        ReferencingResource l_hal_resource
        if (is_not_null(l_client_response.p_slurped_response_json)) {
            if (is_not_null(l_client_response.p_slurped_response_json?._embedded)) {
                l_hal_resource = new T_resource_set()
                for (l_embedded_resource in l_client_response.p_slurped_response_json?._embedded?.values()?.first()) {
                    l_resource_name = l_embedded_resource.resourceName
                    ReferencingResource l_item = get_app_context().p_object_mapper.readValue(JsonOutput.toJson(l_embedded_resource), Class.forName(GC_DOMAIN_MODEL_CLASS_PREFIX + l_resource_name)) as ReferencingResource
                    if (["https://localhost:8080/api/tokens/3", "https://localhost:8080/api/tokens/7/refreshToken"].contains(i_url)) {
                        System.out.println("q")
                    }///////////////\/\/\/\/
                    if (get_app_context().p_resources_by_url.containsKey(l_embedded_resource._links?.self)) {
                        if (is_not_null(get_app_context().p_resources_by_url.get(l_embedded_resource._links?.self))) {
                            System.out.println("From cache: " + i_url)
                            ReferencingResource l_referenced_resource = new ReferencingResource()
                            l_referenced_resource.setResourceAccessUrl(i_url)
                            l_referenced_resource.setResourceAbsoluteUrl(get_app_context().p_resources_by_url.get(i_url)?.getResourceAbsoluteUrl())
                            l_referenced_resource.setIsReferencing(GC_IS_REFERENCED_YES)
                            get_app_context().p_resources_by_url.put(i_url, l_referenced_resource)
                            l_hal_resource.resourceSet.add(l_referenced_resource)
                        } else {
                            get_app_context().p_resources_by_url.put(i_url, GC_NULL_OBJ_REF as ReferencingResource)
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
                                l_item.setResourceAbsoluteUrl(l_embedded_resource._links?.get(l_key)?.href)
                                //l_item.setResourceRelativeUrl(i_url)
                            }
                        }
                    }
                }
            } else {
                Object l_resource = l_client_response.p_slurped_response_json
                l_resource_name = l_resource.resourceName
                l_hal_resource = get_app_context().p_object_mapper.readValue(JsonOutput.toJson(l_resource), Class.forName(GC_DOMAIN_MODEL_CLASS_PREFIX + l_resource_name)) as ReferencingResource
                for (l_key in l_resource._links?.keySet()) {
                    if (l_hal_resource.properties.containsKey(l_key)) {
                        if (i_is_traverse) {
                            l_hal_resource."${l_key}" = hal_request(l_resource._links?.get(l_key)?.href, GC_TRAVERSE_YES)
                        }
                    }
                    if (l_key == GC_LINK_NAME_SELF) {
                        l_hal_resource.setResourceAbsoluteUrl(l_resource._links?.get(l_key)?.href)
                    }
                }
                if (get_app_context().p_resources_by_url.containsKey(l_hal_resource.getResourceAbsoluteUrl())) {
                    if (is_not_null(get_app_context().p_resources_by_url.get(l_hal_resource.getResourceAbsoluteUrl()))) {
                        System.out.println("From cache: " + i_url)
                        ReferencingResource l_referenced_resource = new ReferencingResource()
                        l_referenced_resource.setResourceAccessUrl(i_url)
                        l_referenced_resource.setResourceAbsoluteUrl(get_app_context().p_resources_by_url.get(i_url)?.getResourceAbsoluteUrl())
                        l_referenced_resource.setIsReferencing(GC_IS_REFERENCED_YES)
                        get_app_context().p_resources_by_url.put(i_url, l_referenced_resource)
                        return l_referenced_resource
                    } else {
                        get_app_context().p_resources_by_url.put(i_url, GC_NULL_OBJ_REF as ReferencingResource)
                        return GC_NULL_OBJ_REF
                    }
                }
            }
            l_hal_resource.setResourceAccessUrl(i_url)
            if (is_not_null(l_hal_resource.getResourceAccessUrl())) {
                get_app_context().p_resources_by_url.put(l_hal_resource.getResourceAccessUrl(), l_hal_resource)
            }
            if (is_not_null(l_hal_resource.getResourceAbsoluteUrl())) {
                get_app_context().p_resources_by_url.put(l_hal_resource.getResourceAbsoluteUrl(), l_hal_resource)
            }
            return l_hal_resource
        } else {
            get_app_context().p_resources_by_url.put(i_url, GC_NULL_OBJ_REF as ReferencingResource)
            return GC_NULL_OBJ_REF
        }
    }

}