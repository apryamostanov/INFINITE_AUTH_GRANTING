package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper

import static base.T_common_base_1_const.GC_EMPTY_STRING
import static base.T_common_base_1_const.GC_NULL_OBJ_REF
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util.okhttp_request
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util.process_parent_versions

@JsonIgnoreProperties(ignoreUnknown = true)
class Version {

    String versionName = GC_EMPTY_STRING
    Version parentVersion = GC_NULL_OBJ_REF as Version

    static Version create_from_configuration(String i_version_url) {
        ObjectMapper l_object_mapper = new ObjectMapper()
        def (Object l_version_json, Object l_version_response, String l_version_response_body) = okhttp_request(i_version_url)
        Version l_version = l_object_mapper.readValue(l_version_response_body, Version.class)
        process_parent_versions(l_version_json?._links?.parentVersion?.href, l_version)
        return l_version
    }

}