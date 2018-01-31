package com.a9ae0b01f0ffc.infinite_auth_granting.authentication_modules

import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_5_context
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_resource_set
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Accessor
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Authentication

import java.nio.charset.StandardCharsets

import static base.T_common_base_3_utils.nvl
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.GC_ANY
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.GC_TRAVERSE_YES
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.getGC_AUTHORIZATION_ERROR_CODE_14

System.out.println(this.getClass().getSimpleName())


T_auth_grant_base_5_context i_context = binding.getVariable("i_context") as T_auth_grant_base_5_context
Authentication io_authentication = binding.getVariable("io_authentication") as Authentication
Map<String, String> l_publicDataFieldSet = i_context.resource_set2map(io_authentication.publicDataFieldSet)

T_resource_set<Accessor> l_accessor_set_to_match = i_context.hal_request(i_context.app_conf().infiniteAuthConfigurationBaseUrl + i_context.app_conf().matchAccessors
        + "?appName=" + URLEncoder.encode(l_publicDataFieldSet.get("appName"), StandardCharsets.UTF_8.name())
        + "&platform=" + URLEncoder.encode(l_publicDataFieldSet.get("platform"), StandardCharsets.UTF_8.name())
        + "&appVersion=" + URLEncoder.encode(l_publicDataFieldSet.get("appVersion"), StandardCharsets.UTF_8.name())
        + "&fiid=" + URLEncoder.encode(l_publicDataFieldSet.get("fiid"), StandardCharsets.UTF_8.name())
        + "&product=" + URLEncoder.encode(nvl(l_publicDataFieldSet.get("product"), GC_ANY) as String, StandardCharsets.UTF_8.name())
        + "&productGroup=" + URLEncoder.encode(l_publicDataFieldSet.get("productGroup"), StandardCharsets.UTF_8.name())
        + "&apiVersionName=" + URLEncoder.encode(l_publicDataFieldSet.get("apiVersionName"), StandardCharsets.UTF_8.name())
        + "&endpointName=" + URLEncoder.encode(l_publicDataFieldSet.get("endpointName"), StandardCharsets.UTF_8.name())
        , GC_TRAVERSE_YES) as T_resource_set

if (l_accessor_set_to_match.resourceSet.isEmpty()) {
    io_authentication.failure(GC_AUTHORIZATION_ERROR_CODE_14)
    return
}

if (l_accessor_set_to_match.resourceSet.first().isForbidden == 1) {
    io_authentication.failure(GC_AUTHORIZATION_ERROR_CODE_14)
    return
}