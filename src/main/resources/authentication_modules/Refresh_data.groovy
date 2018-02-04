package authentication_modules

import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Authentication
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Authorization

import static base.T_common_base_3_utils.is_null

System.out.println(this.getClass().getSimpleName())

Authentication io_user_authentication = binding.getVariable("io_user_authentication") as Authentication

if (is_null(io_user_authentication.publicDataFieldSet)) {
    io_user_authentication.failure()
    return
}

if (is_null(io_user_authentication.privateDataFieldSet)) {
    io_user_authentication.failure()
    return
}

if (io_user_authentication.publicDataFieldSet.get("proxyNumber") == null ||
        io_user_authentication.publicDataFieldSet.get("oldAccessToken") == null ||
        io_user_authentication.privateDataFieldSet.get("refreshToken") == null) {
    io_user_authentication.failure()
    return
}

if (io_user_authentication.p_parent_authorization.is_invalid_refresh_jwt(io_user_authentication.privateDataFieldSet.get("refreshToken"), io_user_authentication.p_context)) {
    io_user_authentication.failure()
    return
}

Authorization l_refresh_authorization = io_user_authentication.p_parent_authorization.refresh_jwt2authorization(io_user_authentication.privateDataFieldSet.get("refreshToken"), io_user_authentication.p_context)

if (l_refresh_authorization.expiryDate.before(new Date())) {
    io_user_authentication.failure()
    return
}

if (io_user_authentication.p_parent_authorization.is_invalid_access_jwt(io_user_authentication.publicDataFieldSet.get("oldAccessToken"), io_user_authentication.p_context)) {
    io_user_authentication.failure()
    return
}

Authorization l_old_access_authorization = io_user_authentication.p_parent_authorization.access_jwt2authorization(io_user_authentication.publicDataFieldSet.get("oldAccessToken"), io_user_authentication.p_context)

if (l_old_access_authorization.scope != l_refresh_authorization.scope) {
    io_user_authentication.failure()
    return
}

if (!l_refresh_authorization.merge_field_maps(l_old_access_authorization.keyFieldMap, l_old_access_authorization.functionalFieldMap)) {
    io_user_authentication.failure()
    return
}

if (!io_user_authentication.keyFieldMap.containsKey("proxyNumber")) {
    io_user_authentication.failure()
    return
}

if (io_user_authentication.keyFieldMap.get("proxyNumber") != io_user_authentication.publicDataFieldSet.get("proxyNumber")) {
    io_user_authentication.failure()
    return
}

io_user_authentication.keyFieldMap = l_refresh_authorization.keyFieldMap
io_user_authentication.functionalFieldMap = l_refresh_authorization.functionalFieldMap

io_user_authentication.success()