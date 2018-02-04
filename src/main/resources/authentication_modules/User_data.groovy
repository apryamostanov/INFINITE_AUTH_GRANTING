package authentication_modules

import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Authentication
import groovy.json.JsonSlurper
import okhttp3.*

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

if (io_user_authentication.publicDataFieldSet.get("username") == null ||
        io_user_authentication.privateDataFieldSet.get("password") == null) {
    io_user_authentication.failure()
    return
}

String l_request_body_string = """{"Authenticate": {"requestData": {
  "UniqueID": "${"MDWL" + new Date().format("yymmddHHmmssSSS")}",
  "CardUserId": "${io_user_authentication.publicDataFieldSet.get("username")}",
  "CurrentPassword": "${io_user_authentication.privateDataFieldSet.get("password")}",
  "InstitutionID": "${io_user_authentication.p_parent_authorization.functionalFieldMap.get("fiid")}",
  "UniqueIDFlag": "0"
}}}"""

Proxy l_proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(io_user_authentication.p_conf.GC_PROXY_ADDRESS as String, io_user_authentication.p_conf.GC_PROXY_PORT as Integer))

Request l_request = new Request.Builder().post(RequestBody.create(MediaType.parse(io_user_authentication.p_conf.GC_CONTENT_TYPE as String), l_request_body_string)).url(io_user_authentication.p_conf.GC_RESOURCE_SERVER_URL + io_user_authentication.p_conf.GC_RESOURCE_AUTHENTICATE as String).build()

OkHttpClient.Builder l_builder = new OkHttpClient.Builder().hostnameVerifier(io_user_authentication.p_context.get_unsecure_host_name_verifier()).proxy(l_proxy)
OkHttpClient l_client = l_builder.build()

Response l_response = l_client.newCall(l_request).execute()


String l_response_body = l_response.body().string()
if (!l_response.isSuccessful()) {
    io_user_authentication.failure()
    return
} else {
    JsonSlurper l_configuration_api_response_json_slurper = new JsonSlurper()
    Object l_slurped_conf_api_response_json = l_configuration_api_response_json_slurper.parseText(l_response_body)

    if (!(l_slurped_conf_api_response_json.AuthenticateResponse.AuthenticateResult.UniqueID != null &&
            l_slurped_conf_api_response_json.AuthenticateResponse.AuthenticateResult.UniqueIDFlag == 0 &&
            l_slurped_conf_api_response_json.AuthenticateResponse.AuthenticateResult.ErrorFound == "No" &&
            l_slurped_conf_api_response_json.AuthenticateResponse.AuthenticateResult.ErrorNumber == "0" &&
            l_slurped_conf_api_response_json.AuthenticateResponse.AuthenticateResult.ErrorMessage == "Processed successfully." &&
            l_slurped_conf_api_response_json.AuthenticateResponse.AuthenticateResult.LoginFlag != null)) {
        io_user_authentication.failure()
        return
    } else {
        io_user_authentication.keyFieldMap = new HashMap<String, String>()
        io_user_authentication.functionalFieldMap = new HashMap<String, String>()
        io_user_authentication.keyFieldMap.put("proxyNumber", l_slurped_conf_api_response_json.AuthenticateResponse.AuthenticateResult.ProxyNumber.toString())
        io_user_authentication.keyFieldMap.put("accountNumber", l_slurped_conf_api_response_json.AuthenticateResponse.AuthenticateResult.AccountNumber.toString())
        io_user_authentication.keyFieldMap.put("username", io_user_authentication.publicDataFieldSet.get("username"))
        io_user_authentication.functionalFieldMap.put("cardTypeIdEnhanced", l_slurped_conf_api_response_json.AuthenticateResponse.AuthenticateResult.CardTypeIDEnhanced.toString())
        io_user_authentication.functionalFieldMap.put("loginFlag", l_slurped_conf_api_response_json.AuthenticateResponse.AuthenticateResult.LoginFlag.toString())
        io_user_authentication.functionalFieldMap.put("errorNumber", l_slurped_conf_api_response_json.AuthenticateResponse.AuthenticateResult.ErrorNumber.toString())
        if (l_slurped_conf_api_response_json.AuthenticateResponse.AuthenticateResult.AccessToken != null &&
                l_slurped_conf_api_response_json.AuthenticateResponse.AuthenticateResult.RefreshToken != null) {
            io_user_authentication.success()
            return
        } else {
            io_user_authentication.failure()
            return
        }
    }
}