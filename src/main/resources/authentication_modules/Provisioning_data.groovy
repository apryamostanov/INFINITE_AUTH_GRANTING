package authentication_modules

System.out.println(this.getClass().getSimpleName())

def io_user_authentication = binding.getVariable("io_user_authentication")

io_user_authentication.keyFieldMap = new HashMap<String, String>()
io_user_authentication.functionalFieldMap = new HashMap<String, String>()
io_user_authentication.keyFieldMap.put("proxy_number", io_user_authentication.authenticationData?.publicDataFieldMap?.get("proxy_number") as String)
io_user_authentication.functionalFieldMap.put("provisioning_public_key", io_user_authentication.authenticationData?.publicDataFieldMap?.get("provisioning_public_key"))
io_user_authentication.success()