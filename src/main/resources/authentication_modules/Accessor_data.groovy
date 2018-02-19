package authentication_modules

System.out.println(this.getClass().getSimpleName())

def io_user_authentication = binding.getVariable("io_user_authentication")

if (io_user_authentication.publicDataFieldSet == null) {
    System.out.println("1")
    io_user_authentication.failure()
    return
}

if (io_user_authentication.publicDataFieldSet.get("accessor_name") == null ||
        io_user_authentication.publicDataFieldSet.get("platform") == null ||
        io_user_authentication.publicDataFieldSet.get("app_version") == null ||
        io_user_authentication.publicDataFieldSet.get("FIID") == null ||
        io_user_authentication.publicDataFieldSet.get("product_group") == null ||
        io_user_authentication.publicDataFieldSet.get("api_major_version") == null ||
        io_user_authentication.publicDataFieldSet.get("specific_data") == null
) {
    System.out.println("Input fields missing")
    System.out.println("2")
    io_user_authentication.failure()
    return
}

Set l_accessor_set_to_match = io_user_authentication.p_context.p_accessor_type_repository.match_accessors(
        io_user_authentication.publicDataFieldSet.get("accessor_name")
        , io_user_authentication.publicDataFieldSet.get("platform")
        , io_user_authentication.publicDataFieldSet.get("app_version")
        , io_user_authentication.publicDataFieldSet.get("FIID")
        , io_user_authentication.publicDataFieldSet.get("product")
        , io_user_authentication.publicDataFieldSet.get("product_group")
        , io_user_authentication.publicDataFieldSet.get("api_major_version")
        , io_user_authentication.p_context.p_app_conf.granting_endpoint_name
) as Set

if (l_accessor_set_to_match.isEmpty()) {
    System.out.println("3")
    io_user_authentication.failure()
    return
}

if (l_accessor_set_to_match.first().isForbidden == 1) {
    System.out.println("4")
    io_user_authentication.failure()
    return
}


Set l_accessor_set_to_match_scope = io_user_authentication.p_context.p_accessor_type_repository.match_accessors_scope(
        io_user_authentication.publicDataFieldSet.get("accessor_name")
        , io_user_authentication.publicDataFieldSet.get("platform")
        , io_user_authentication.publicDataFieldSet.get("app_version")
        , io_user_authentication.publicDataFieldSet.get("FIID")
        , io_user_authentication.publicDataFieldSet.get("product")
        , io_user_authentication.publicDataFieldSet.get("product_group")
        , io_user_authentication.publicDataFieldSet.get("api_major_version")
        , io_user_authentication.p_context.p_app_conf.granting_endpoint_name
) as Set

if (l_accessor_set_to_match_scope.isEmpty()) {
    System.out.println("5")
    io_user_authentication.failure()
    return
}

if (l_accessor_set_to_match_scope.first().isForbidden == 1) {
    System.out.println("6")
    io_user_authentication.failure()
    return
}

Set l_accessor_set_to_match_authorization = io_user_authentication.p_context.p_accessor_type_repository.match_accessors_authorization(
        io_user_authentication.publicDataFieldSet.get("accessor_name")
        , io_user_authentication.publicDataFieldSet.get("platform")
        , io_user_authentication.publicDataFieldSet.get("app_version")
        , io_user_authentication.publicDataFieldSet.get("FIID")
        , io_user_authentication.publicDataFieldSet.get("product")
        , io_user_authentication.publicDataFieldSet.get("product_group")
        , io_user_authentication.publicDataFieldSet.get("api_major_version")
        , io_user_authentication.p_context.p_app_conf.granting_endpoint_name
) as Set

if (l_accessor_set_to_match_authorization.isEmpty()) {
    System.out.println("7")
    io_user_authentication.failure()
    return
}

if (l_accessor_set_to_match_authorization.first().isForbidden == 1) {
    System.out.println("8")
    io_user_authentication.failure()
    return
}

Set l_accessor_set_to_match_routing = io_user_authentication.p_context.p_accessor_type_repository.match_accessors_routing(
        io_user_authentication.publicDataFieldSet.get("accessor_name")
        , io_user_authentication.publicDataFieldSet.get("platform")
        , io_user_authentication.publicDataFieldSet.get("app_version")
        , io_user_authentication.publicDataFieldSet.get("FIID")
        , io_user_authentication.publicDataFieldSet.get("product")
        , io_user_authentication.publicDataFieldSet.get("product_group")
        , io_user_authentication.publicDataFieldSet.get("api_major_version")
        , io_user_authentication.p_context.p_app_conf.granting_endpoint_name
) as Set

if (l_accessor_set_to_match_routing.isEmpty()) {
    System.out.println("9")
    io_user_authentication.failure()
    return
}

if (l_accessor_set_to_match_routing.first().isForbidden == 1) {
    System.out.println("10")
    io_user_authentication.failure()
    return
}

io_user_authentication.keyFieldMap = new HashMap<String, String>()
io_user_authentication.functionalFieldMap = new HashMap<String, String>()
io_user_authentication.keyFieldMap.put("accessor_id", l_accessor_set_to_match.first().accessorName)
io_user_authentication.keyFieldMap.put("accessor_id_scope", l_accessor_set_to_match_scope.first().accessorName)
io_user_authentication.keyFieldMap.put("accessor_id_authorization", l_accessor_set_to_match_authorization.first().accessorName)
io_user_authentication.keyFieldMap.put("accessor_id_routing", l_accessor_set_to_match_routing.first().accessorName)
io_user_authentication.keyFieldMap.put("language", io_user_authentication.publicDataFieldSet.get("language"))
io_user_authentication.functionalFieldMap.put("FIID", io_user_authentication.publicDataFieldSet.get("FIID"))
io_user_authentication.success()