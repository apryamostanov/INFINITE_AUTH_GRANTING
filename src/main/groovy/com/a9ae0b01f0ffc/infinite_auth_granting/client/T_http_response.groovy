package com.a9ae0b01f0ffc.infinite_auth_granting.client

import groovy.transform.ToString
import okhttp3.Response

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

@ToString(includeNames = true, includeFields = true, includeSuper = false)
class T_http_response {

    Object p_slurped_response_json
    Response p_okhttp_response
    String p_response_string
    Object p_resource_object
    Set<String> p_link_set = new HashSet<String>()

}
