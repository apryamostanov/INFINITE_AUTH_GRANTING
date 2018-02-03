package com.a9ae0b01f0ffc.infinite_auth_granting.authentication_modules

import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_5_context
import com.a9ae0b01f0ffc.infinite_auth_granting.client.T_client_response
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Authentication

import static base.T_common_base_3_utils.is_null

System.out.println(this.getClass().getSimpleName())


T_auth_grant_base_5_context i_context = binding.getVariable("i_context") as T_auth_grant_base_5_context
Authentication io_authentication = binding.getVariable("io_authentication") as Authentication
Map<String, String> o_key_field_map = binding.getVariable("o_key_field_map") as Map<String, String>
Map<String, String> o_functional_field_map = binding.getVariable("o_functional_field_map") as Map<String, String>
Map<String, String> i_functional_field_map = binding.getVariable("i_functional_field_map") as Map<String, String>

if (is_null(io_authentication.publicDataFieldSet)) {
    io_authentication.failure()
    return
}

if (is_null(io_authentication.privateDataFieldSet)) {
    io_authentication.failure()
    return
}

if (io_authentication.publicDataFieldSet.get("username") == null ||
        io_authentication.privateDataFieldSet.get("password") == null) {
    io_authentication.failure()
    return
}

String l_request_body_string = """{"Authenticate": {"requestData": {
  "UniqueID": "${"MDWL" + new Date().format("yymmddHHmmssSSS")}",
  "CardUserId": "${io_authentication.publicDataFieldSet.get("username")}",
  "CurrentPassword": "${io_authentication.privateDataFieldSet.get("password")}",
  "InstitutionID": "${i_functional_field_map.get("fiid")}",
  "UniqueIDFlag": "0"
}}}"""

T_client_response l_client_response = i_context.okhttp_request("https://10.10.99.190:8443/middleware_soapui/Rest/Authenticate", l_request_body_string)
if (l_client_response.p_slurped_response_json.AuthenticateResponse.AuthenticateResult.UniqueID != null ||
        l_client_response.p_slurped_response_json.AuthenticateResponse.AuthenticateResult.UniqueIDFlag == 0 ||
        l_client_response.p_slurped_response_json.AuthenticateResponse.AuthenticateResult.ErrorFound == "No" ||
        l_client_response.p_slurped_response_json.AuthenticateResponse.AuthenticateResult.ErrorNumber == "0" ||
        l_client_response.p_slurped_response_json.AuthenticateResponse.AuthenticateResult.ErrorMessage == "Processed successfully." ||
        l_client_response.p_slurped_response_json.AuthenticateResponse.AuthenticateResult.AccessToken != null ||
        l_client_response.p_slurped_response_json.AuthenticateResponse.AuthenticateResult.RefreshToken != null ||
        l_client_response.p_slurped_response_json.AuthenticateResponse.AuthenticateResult.LoginFlag != null) {
    io_authentication.failure()
    return
}
o_key_field_map.put("proxyNumber", l_client_response.p_slurped_response_json.AuthenticateResponse.AuthenticateResult.ProxyNumber.toString())
o_key_field_map.put("accountNumber", l_client_response.p_slurped_response_json.AuthenticateResponse.AuthenticateResult.AccountNumber.toString())
//o_key_field_map.put("productId", l_client_response.p_slurped_response_json.AuthenticateResponse.AuthenticateResult.AccountNumber.toString())
o_key_field_map.put("username", io_authentication.publicDataFieldSet.get("username"))
//todo: error number
o_functional_field_map.put("cardTypeIdEnhanced", l_client_response.p_slurped_response_json.AuthenticateResponse.AuthenticateResult.CardTypeIDEnhanced.toString())
o_functional_field_map.put("loginFlag", l_client_response.p_slurped_response_json.AuthenticateResponse.AuthenticateResult.LoginFlag.toString())
o_functional_field_map.put("ErrorNumber", l_client_response.p_slurped_response_json.AuthenticateResponse.AuthenticateResult.LoginFlag.toString())

io_authentication.success()