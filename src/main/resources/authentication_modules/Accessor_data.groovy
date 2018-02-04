package authentication_modules

import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_resource_set
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Accessor
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Authentication

import java.nio.charset.StandardCharsets

import static base.T_common_base_3_utils.is_null
import static base.T_common_base_3_utils.nvl
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.getGC_ANY
import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.getGC_TRAVERSE_YES

System.out.println(this.getClass().getSimpleName())

Authentication io_user_authentication = binding.getVariable("io_user_authentication") as Authentication

if (is_null(io_user_authentication.publicDataFieldSet)) {
    io_user_authentication.failure()
    return
}

if (io_user_authentication.publicDataFieldSet.get("appName") == null ||
        io_user_authentication.publicDataFieldSet.get("platform") == null ||
        io_user_authentication.publicDataFieldSet.get("appVersion") == null ||
        io_user_authentication.publicDataFieldSet.get("fiid") == null ||
        io_user_authentication.publicDataFieldSet.get("productGroup") == null ||
        io_user_authentication.publicDataFieldSet.get("apiVersionName") == null ||
        io_user_authentication.publicDataFieldSet.get("endpointName") == null ||
        io_user_authentication.publicDataFieldSet.get("manufacturer") == null ||
        io_user_authentication.publicDataFieldSet.get("model") == null ||
        io_user_authentication.publicDataFieldSet.get("osVersion") == null) {
    io_user_authentication.failure()
    return
}

T_resource_set<Accessor> l_accessor_set_to_match = io_user_authentication.p_context.hal_request(io_user_authentication.p_context.app_conf().infiniteAuthConfigurationBaseUrl + io_user_authentication.p_context.app_conf().matchAccessors
        + "?appName=" + URLEncoder.encode(io_user_authentication.publicDataFieldSet.get("appName"), StandardCharsets.UTF_8.name())
        + "&platform=" + URLEncoder.encode(io_user_authentication.publicDataFieldSet.get("platform"), StandardCharsets.UTF_8.name())
        + "&appVersion=" + URLEncoder.encode(io_user_authentication.publicDataFieldSet.get("appVersion"), StandardCharsets.UTF_8.name())
        + "&fiid=" + URLEncoder.encode(io_user_authentication.publicDataFieldSet.get("fiid"), StandardCharsets.UTF_8.name())
        + "&product=" + URLEncoder.encode(nvl(io_user_authentication.publicDataFieldSet.get("product"), GC_ANY) as String, StandardCharsets.UTF_8.name())
        + "&productGroup=" + URLEncoder.encode(io_user_authentication.publicDataFieldSet.get("productGroup"), StandardCharsets.UTF_8.name())
        + "&apiVersionName=" + URLEncoder.encode(io_user_authentication.publicDataFieldSet.get("apiVersionName"), StandardCharsets.UTF_8.name())
        + "&endpointName=" + URLEncoder.encode(io_user_authentication.publicDataFieldSet.get("endpointName"), StandardCharsets.UTF_8.name())
        , GC_TRAVERSE_YES) as T_resource_set

if (l_accessor_set_to_match.isEmpty()) {
    io_user_authentication.failure()
    return
}

if (l_accessor_set_to_match.first().isForbidden == 1) {
    io_user_authentication.failure()
    return
}
io_user_authentication.keyFieldMap = new HashMap<String, String>()
io_user_authentication.functionalFieldMap = new HashMap<String, String>()
io_user_authentication.keyFieldMap.put("accessorName", l_accessor_set_to_match.first().accessorName)
io_user_authentication.functionalFieldMap.put("fiid", l_accessor_set_to_match.first().fiid)
io_user_authentication.success()