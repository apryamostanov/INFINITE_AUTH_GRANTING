package com.a9ae0b01f0ffc.infinite_auth_granting.authentication_modules

import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_5_context
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_resource_set
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Accessor
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Authentication

import java.nio.charset.StandardCharsets

import static base.T_common_base_3_utils.nvl
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.*

System.out.println(this.getClass().getSimpleName())


T_auth_grant_base_5_context i_context = binding.getVariable("i_context") as T_auth_grant_base_5_context
Authentication io_authentication = binding.getVariable("io_authentication") as Authentication
Map<String, String> o_key_field_map = binding.getVariable("o_key_field_map") as Map<String, String>
Map<String, String> o_functional_field_map = binding.getVariable("o_functional_field_map") as Map<String, String>

if (is_null(io_authentication.publicDataFieldSet)) {
    io_authentication.failure()
    return
}

if (io_authentication.publicDataFieldSet.get("appName") == null ||
        io_authentication.publicDataFieldSet.get("platform") == null ||
        io_authentication.publicDataFieldSet.get("appVersion") == null ||
        io_authentication.publicDataFieldSet.get("fiid") == null ||
        io_authentication.publicDataFieldSet.get("productGroup") == null ||
        io_authentication.publicDataFieldSet.get("apiVersionName") == null ||
        io_authentication.publicDataFieldSet.get("endpointName") == null) {
    io_authentication.failure()
    return
}

T_resource_set<Accessor> l_accessor_set_to_match = i_context.hal_request(i_context.app_conf().infiniteAuthConfigurationBaseUrl + i_context.app_conf().matchAccessors
        + "?appName=" + URLEncoder.encode(io_authentication.publicDataFieldSet.get("appName"), StandardCharsets.UTF_8.name())
        + "&platform=" + URLEncoder.encode(io_authentication.publicDataFieldSet.get("platform"), StandardCharsets.UTF_8.name())
        + "&appVersion=" + URLEncoder.encode(io_authentication.publicDataFieldSet.get("appVersion"), StandardCharsets.UTF_8.name())
        + "&fiid=" + URLEncoder.encode(io_authentication.publicDataFieldSet.get("fiid"), StandardCharsets.UTF_8.name())
        + "&product=" + URLEncoder.encode(nvl(io_authentication.publicDataFieldSet.get("product"), GC_ANY) as String, StandardCharsets.UTF_8.name())
        + "&productGroup=" + URLEncoder.encode(io_authentication.publicDataFieldSet.get("productGroup"), StandardCharsets.UTF_8.name())
        + "&apiVersionName=" + URLEncoder.encode(io_authentication.publicDataFieldSet.get("apiVersionName"), StandardCharsets.UTF_8.name())
        + "&endpointName=" + URLEncoder.encode(io_authentication.publicDataFieldSet.get("endpointName"), StandardCharsets.UTF_8.name())
        , GC_TRAVERSE_YES) as T_resource_set

if (l_accessor_set_to_match.isEmpty()) {
    io_authentication.failure()
    return
}

if (l_accessor_set_to_match.first().isForbidden == 1) {
    io_authentication.failure()
    return
}
o_key_field_map.put("accessorName", l_accessor_set_to_match.first().accessorName)
io_authentication.success()