package authentication_modules

import com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_base_4_const

//import com.a9ae0b01f0ffc.infinite_auth.granting.Authentication

System.out.println(this.getClass().getSimpleName())

def io_user_authentication = binding.getVariable("io_user_authentication")
//Authentication io_user_authentication = binding.getVariable("io_user_authentication")

Set l_accessor_set_to_match = io_user_authentication.p_context.p_accessor_type_repository.match_accessors(
        io_user_authentication.authenticationData?.publicDataFieldMap?.get("accessor_name")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("platform")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("app_version")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("FIID")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("product")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("product_group")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("api_major_version")
        , io_user_authentication.p_context.p_app_conf.granting_endpoint_name
        , T_auth_grant_base_4_const.GC_ACCESSOR_TYPE_ACCESS_CONTROL
) as Set

if (l_accessor_set_to_match.isEmpty()) {
    System.out.println("3")
    io_user_authentication.failure()
    return
}

if (l_accessor_set_to_match.first().isForbidden) {
    System.out.println("4")
    io_user_authentication.failure()
    return
}


Set l_accessor_set_to_match_scope = io_user_authentication.p_context.p_accessor_type_repository.match_accessors(
        io_user_authentication.authenticationData?.publicDataFieldMap?.get("accessor_name")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("platform")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("app_version")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("FIID")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("product")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("product_group")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("api_major_version")
        , io_user_authentication.p_context.p_app_conf.granting_endpoint_name
        , T_auth_grant_base_4_const.GC_ACCESSOR_TYPE_SCOPE_CONTROL
) as Set

if (l_accessor_set_to_match_scope.isEmpty()) {
    System.out.println("5")
    io_user_authentication.failure()
    return
}

if (l_accessor_set_to_match_scope.first().isForbidden) {
    System.out.println("6")
    io_user_authentication.failure()
    return
}

Set l_accessor_set_to_match_authorization = io_user_authentication.p_context.p_accessor_type_repository.match_accessors(
        io_user_authentication.authenticationData?.publicDataFieldMap?.get("accessor_name")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("platform")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("app_version")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("FIID")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("product")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("product_group")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("api_major_version")
        , io_user_authentication.p_context.p_app_conf.granting_endpoint_name
        , T_auth_grant_base_4_const.GC_ACCESSOR_TYPE_AUTHORIZATION_CONTROL
) as Set

if (l_accessor_set_to_match_authorization.isEmpty()) {
    System.out.println("7")
    io_user_authentication.failure()
    return
}

if (l_accessor_set_to_match_authorization.first().isForbidden) {
    System.out.println("8")
    io_user_authentication.failure()
    return
}

Set l_accessor_set_to_match_routing = io_user_authentication.p_context.p_accessor_type_repository.match_accessors(
        io_user_authentication.authenticationData?.publicDataFieldMap?.get("accessor_name")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("platform")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("app_version")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("FIID")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("product")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("product_group")
        , io_user_authentication.authenticationData?.publicDataFieldMap?.get("api_major_version")
        , io_user_authentication.p_context.p_app_conf.granting_endpoint_name
        , T_auth_grant_base_4_const.GC_ACCESSOR_TYPE_ROUTING_CONTROL
) as Set

if (l_accessor_set_to_match_routing.isEmpty()) {
    System.out.println("9")
    io_user_authentication.failure()
    return
}

if (l_accessor_set_to_match_routing.first().isForbidden) {
    System.out.println("10")
    io_user_authentication.failure()
    return
}

io_user_authentication.keyFieldMap = new HashMap<String, String>()
io_user_authentication.functionalFieldMap = new HashMap<String, String>()
io_user_authentication.keyFieldMap.put("accessor_id", l_accessor_set_to_match.first().accessorName)
io_user_authentication.keyFieldMap.put("allowed_products", l_accessor_set_to_match.first().allowedProducts)
io_user_authentication.keyFieldMap.put("granting_endpoint_name", io_user_authentication.p_context.app_conf().granting_endpoint_name)
io_user_authentication.keyFieldMap.put("validation_endpoint_group_name", l_accessor_set_to_match_routing.first().validationEndpointGroupName)
io_user_authentication.keyFieldMap.put("resource_endpoint_name", l_accessor_set_to_match_routing.first().resourceEndpointName)
io_user_authentication.functionalFieldMap.put("language", io_user_authentication.authenticationData?.publicDataFieldMap?.get("language"))
io_user_authentication.functionalFieldMap.put("FIID", io_user_authentication.authenticationData?.publicDataFieldMap?.get("FIID"))
io_user_authentication.success()