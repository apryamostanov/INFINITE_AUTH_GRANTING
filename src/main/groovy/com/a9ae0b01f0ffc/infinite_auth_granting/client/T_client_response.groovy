package com.a9ae0b01f0ffc.infinite_auth_granting.client

import groovy.transform.ToString
import okhttp3.Response
import org.json.JSONObject

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

@ToString(includeNames = true, includeFields = true, includeSuper = false)
class T_client_response {

    Object p_slurped_response_json
    Response p_okhttp_response
    String p_response_string
    Object p_resource_object
    Set<String> p_link_set = new HashSet<String>()
    JSONObject p_json_object

}
