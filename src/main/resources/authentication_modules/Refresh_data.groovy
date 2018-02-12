package authentication_modules

import static base.T_common_base_3_utils.is_null

System.out.println(this.getClass().getSimpleName())

def io_user_authentication = binding.getVariable("io_user_authentication")

if (is_null(io_user_authentication.publicDataFieldSet)) {
    io_user_authentication.failure()
    return
}

if (is_null(io_user_authentication.privateDataFieldSet)) {
    io_user_authentication.failure()
    return
}

if (io_user_authentication.publicDataFieldSet.get("proxy_number") == null ||
        io_user_authentication.publicDataFieldSet.get("old_access_token") == null ||
        io_user_authentication.privateDataFieldSet.get("refresh_token") == null) {
    io_user_authentication.failure()
    return
}

if (io_user_authentication.p_parent_authorization.is_invalid_refresh_jwt(io_user_authentication.privateDataFieldSet.get("refresh_token") as String, io_user_authentication.p_context)) {
    io_user_authentication.failure()
    return
}

def l_refresh_authorization = io_user_authentication.p_parent_authorization.refresh_jwt2authorization(io_user_authentication.privateDataFieldSet.get("refresh_token") as String, io_user_authentication.p_context)

if (l_refresh_authorization.expiryDate.before(new Date())) {
    io_user_authentication.failure()
    return
}

if (io_user_authentication.p_parent_authorization.is_invalid_access_jwt(io_user_authentication.publicDataFieldSet.get("old_access_token") as String, io_user_authentication.p_context)) {
    io_user_authentication.failure()
    return
}

def l_old_access_authorization = io_user_authentication.p_parent_authorization.access_jwt2authorization(io_user_authentication.publicDataFieldSet.get("old_access_token") as String, io_user_authentication.p_context)

if (l_old_access_authorization.scope != l_refresh_authorization.scope) {
    io_user_authentication.failure()
    return
}

if (!l_refresh_authorization.merge_field_maps(l_old_access_authorization.scope.keyFieldMap, l_old_access_authorization.functionalFieldMap)) {
    io_user_authentication.failure()
    return
}

if (!io_user_authentication.p_parent_authorization.scope.keyFieldMap.containsKey("proxy_number")) {
    io_user_authentication.failure()
    return
}

if (io_user_authentication.p_parent_authorization.scope.keyFieldMap.get("proxy_number") != io_user_authentication.publicDataFieldSet.get("proxy_number")) {
    io_user_authentication.failure()
    return
}

io_user_authentication.keyFieldMap = l_refresh_authorization.scope.keyFieldMap
io_user_authentication.functionalFieldMap = l_refresh_authorization.functionalFieldMap

io_user_authentication.success()