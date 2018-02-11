package authentication_modules

System.out.println(this.getClass().getSimpleName())

def io_user_authentication = binding.getVariable("io_user_authentication")

if (io_user_authentication.publicDataFieldSet == null) {
    io_user_authentication.failure()
    return
}

if (io_user_authentication.publicDataFieldSet.get("app_name") == null ||
        io_user_authentication.publicDataFieldSet.get("platform") == null ||
        io_user_authentication.publicDataFieldSet.get("app_version") == null ||
        io_user_authentication.publicDataFieldSet.get("fiid") == null ||
        io_user_authentication.publicDataFieldSet.get("product_group") == null ||
        io_user_authentication.publicDataFieldSet.get("api_version_name") == null ||
        io_user_authentication.publicDataFieldSet.get("endpoint_name") == null ||
        io_user_authentication.publicDataFieldSet.get("manufacturer") == null ||
        io_user_authentication.publicDataFieldSet.get("model") == null ||
        io_user_authentication.publicDataFieldSet.get("os_version") == null) {
    io_user_authentication.failure()
    return
}

Set l_accessor_set_to_match = io_user_authentication.p_context.p_accessor_type_repository.matchAccessors(
        io_user_authentication.publicDataFieldSet.get("app_name")
        , io_user_authentication.publicDataFieldSet.get("platform")
        , io_user_authentication.publicDataFieldSet.get("app_version")
        , io_user_authentication.publicDataFieldSet.get("fiid")
        , io_user_authentication.publicDataFieldSet.get("product")
        , io_user_authentication.publicDataFieldSet.get("product_group")
        , io_user_authentication.publicDataFieldSet.get("api_version_name")
        , io_user_authentication.publicDataFieldSet.get("endpoint_name")
) as Set

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
io_user_authentication.keyFieldMap.put("accessor_name", l_accessor_set_to_match.first().accessorName)
io_user_authentication.functionalFieldMap.put("fiid", l_accessor_set_to_match.first().fiid)
io_user_authentication.success()