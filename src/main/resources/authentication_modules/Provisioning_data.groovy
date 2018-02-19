package authentication_modules

System.out.println(this.getClass().getSimpleName())

def io_user_authentication = binding.getVariable("io_user_authentication")

if (io_user_authentication.authenticationData?.publicDataFieldSet == null) {
    io_user_authentication.failure()
    return
}

if (io_user_authentication.authenticationData?.publicDataFieldSet?.get("provisioning_public_key") == null ||
        io_user_authentication.authenticationData?.publicDataFieldSet?.get("proxy_number") == null) {
    io_user_authentication.failure()
    return
}

io_user_authentication.keyFieldMap = new HashMap<String, String>()
io_user_authentication.functionalFieldMap = new HashMap<String, String>()
io_user_authentication.keyFieldMap.put("proxy_number", io_user_authentication.authenticationData?.publicDataFieldSet?.get("proxy_number") as String)
io_user_authentication.functionalFieldMap.put("provisioning_public_key", io_user_authentication.authenticationData?.publicDataFieldSet?.get("provisioning_public_key"))
io_user_authentication.success()