package authentication_modules

import groovy.json.JsonSlurper
import okhttp3.*

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
        io_user_authentication.publicDataFieldSet.get("otp_id") == null ||
        io_user_authentication.publicDataFieldSet.get("phone_number") == null ||
        io_user_authentication.privateDataFieldSet.get("otp") == null) {
    io_user_authentication.failure()
    return
}

String l_validate_otp_sms_request_body_string = """{
  "ValidateOTPSMS": {
    "requestData": {
      "UniqueID": "${"MDWL"+new Date().format("yyMMddHHmmssSSS")}",
      "UniqueIDFlag": "0",
      "ProxyNumber": "${io_user_authentication.publicDataFieldSet.get("proxy_number")}",
      "OTP": "${io_user_authentication.privateDataFieldSet.get("otp")}",
      "OTPUniqueId": ${io_user_authentication.publicDataFieldSet.get("otp_id")},
      "PhoneNumber": "${io_user_authentication.publicDataFieldSet.get("phone_number")}"
    }
  }
}
"""

System.out.println(l_validate_otp_sms_request_body_string)

Proxy l_proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(io_user_authentication.p_conf.GC_PROXY_ADDRESS as String, io_user_authentication.p_conf.GC_PROXY_PORT as Integer))

Request l_validate_otp_sms_request = new Request.Builder().post(RequestBody.create(MediaType.parse(io_user_authentication.p_conf.GC_CONTENT_TYPE as String), l_validate_otp_sms_request_body_string)).url(io_user_authentication.p_conf.GC_RESOURCE_SERVER_URL + "/ValidateOTPSMS" as String).build()

OkHttpClient.Builder l_builder = new OkHttpClient.Builder().hostnameVerifier(io_user_authentication.p_context.get_unsecure_host_name_verifier()).proxy(l_proxy)
OkHttpClient l_client = l_builder.build()

Response l_validate_otp_sms_response = l_client.newCall(l_validate_otp_sms_request).execute()

String l_validate_otp_sms_response_body = l_validate_otp_sms_response.body().string()
System.out.println(l_validate_otp_sms_response_body)
if (!l_validate_otp_sms_response.isSuccessful()) {
    io_user_authentication.failure()
    return
} else {
    JsonSlurper l_json_slurper = new JsonSlurper()
    Object l_slurped_validate_otp_sms_reponse = l_json_slurper.parseText(l_validate_otp_sms_response_body)

    if (!(l_slurped_validate_otp_sms_reponse.ValidateOTPSMSResponse.ValidateOTPSMSResult.UniqueID != null &&
            l_slurped_validate_otp_sms_reponse.ValidateOTPSMSResponse.ValidateOTPSMSResult.UniqueIDFlag == 0 &&
            l_slurped_validate_otp_sms_reponse.ValidateOTPSMSResponse.ValidateOTPSMSResult.ErrorFound == "No" &&
            l_slurped_validate_otp_sms_reponse.ValidateOTPSMSResponse.ValidateOTPSMSResult.ErrorNumber == "0" &&
            l_slurped_validate_otp_sms_reponse.ValidateOTPSMSResponse.ValidateOTPSMSResult.ErrorMessage == "Processed successfully.")) {
        io_user_authentication.failure()
        return
    } else {


                io_user_authentication.keyFieldMap = new HashMap<String, String>()
                io_user_authentication.functionalFieldMap = new HashMap<String, String>()
                io_user_authentication.keyFieldMap.put("proxy_number", io_user_authentication.publicDataFieldSet.get("proxy_number"))
                io_user_authentication.keyFieldMap.put("phone_number", io_user_authentication.publicDataFieldSet.get("phone_number"))
                    io_user_authentication.success()
                    return
    }
}