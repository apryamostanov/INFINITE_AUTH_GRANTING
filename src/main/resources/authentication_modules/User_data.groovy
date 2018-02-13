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

if (io_user_authentication.publicDataFieldSet.get("username") == null ||
        io_user_authentication.privateDataFieldSet.get("password") == null) {
    io_user_authentication.failure()
    return
}

String l_self_service_login_request_body_string = """<s:Envelope xmlns:s="http://schemas.xmlsoap.org/soap/envelope/">
  <s:Body>
    <SelfServiceLogin xmlns="www.corecard.com">
      <requestData xmlns:header="http://schemas.datacontract.org/2004/07/CoreCardServices" xmlns:d4p1="http://schemas.datacontract.org/2004/07/CoreCardServices.DTO" xmlns:i="http://www.w3.org/2001/XMLSchema-instance">
        <LoginUser xmlns="http://schemas.datacontract.org/2004/07/CoreCardServices">${io_user_authentication.p_conf.GC_CORECARD_API_USERNAME}</LoginUser>
        <UserPassword xmlns="http://schemas.datacontract.org/2004/07/CoreCardServices">${io_user_authentication.p_conf.GC_CORECARD_API_PASSWORD}</UserPassword>
        <IPAddress xmlns="http://schemas.datacontract.org/2004/07/CoreCardServices">${io_user_authentication.p_conf.GC_CORECARD_API_IP}</IPAddress>
        <header:UniqueIDFlag>0</header:UniqueIDFlag>
        <header:UniqueID>${"MDWL"+new Date().format("yyMMddHHmmssSSS")}</header:UniqueID>
        <Source xmlns="http://schemas.datacontract.org/2004/07/CoreCardServices">${io_user_authentication.p_conf.GC_CORECARD_API_SOURCE}</Source>
        <APIVersion xmlns="http://schemas.datacontract.org/2004/07/CoreCardServices">${io_user_authentication.p_conf.GC_CORECARD_API_VERSION}</APIVersion>
        <ApplicationVersion i:nil="true" xmlns="http://schemas.datacontract.org/2004/07/CoreCardServices" />
        <CallerID i:nil="true" xmlns="http://schemas.datacontract.org/2004/07/CoreCardServices" />
        <CalledID i:nil="true" xmlns="http://schemas.datacontract.org/2004/07/CoreCardServices" />
        <SessionID i:nil="true" xmlns="http://schemas.datacontract.org/2004/07/CoreCardServices" />
        <ANI i:nil="true" xmlns="http://schemas.datacontract.org/2004/07/CoreCardServices" />
        <DNS i:nil="true" xmlns="http://schemas.datacontract.org/2004/07/CoreCardServices" />
        <Language i:nil="true" xmlns="http://schemas.datacontract.org/2004/07/CoreCardServices" />
        <RequestDate i:nil="true" xmlns="http://schemas.datacontract.org/2004/07/CoreCardServices" />
        <d4p1:InstitutionID>${io_user_authentication.p_parent_authorization.functionalFieldMap.get("FIID")}</d4p1:InstitutionID>
        <d4p1:CardUserId>${io_user_authentication.publicDataFieldSet.get("username")}</d4p1:CardUserId>
        <d4p1:CurrentPassword>${io_user_authentication.privateDataFieldSet.get("password")}</d4p1:CurrentPassword>
      </requestData>
    </SelfServiceLogin>
  </s:Body>
</s:Envelope>
"""

System.out.println(l_self_service_login_request_body_string)

Proxy l_proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(io_user_authentication.p_conf.GC_PROXY_ADDRESS as String, io_user_authentication.p_conf.GC_PROXY_PORT as Integer))

Request l_request = new Request.Builder().post(RequestBody.create(MediaType.parse(io_user_authentication.p_conf.GC_CORECARD_API_CONTENT_TYPE as String), l_self_service_login_request_body_string)).url(io_user_authentication.p_conf.GC_CORECARD_API_URL as String).addHeader("SOAPAction", "www.corecard.com/ICoreCardServices/SelfServiceLogin").build()

OkHttpClient.Builder l_builder = new OkHttpClient.Builder().hostnameVerifier(io_user_authentication.p_context.get_unsecure_host_name_verifier())//.proxy(l_proxy)
OkHttpClient l_client = l_builder.build()

Response l_response = l_client.newCall(l_request).execute()

String l_response_body = l_response.body().string()
System.out.println(l_response_body)
if (!l_response.isSuccessful()) {
    io_user_authentication.failure()
    return
} else {
    XmlSlurper l_xml_slurper = new XmlSlurper()
    Object l_slurped_xml = l_xml_slurper.parseText(l_response_body)

    if (!(l_slurped_xml.Body.SelfServiceLoginResponse.SelfServiceLoginResult.UniqueID != null &&
            l_slurped_xml.Body.SelfServiceLoginResponse.SelfServiceLoginResult.UniqueIDFlag == 0 &&
            l_slurped_xml.Body.SelfServiceLoginResponse.SelfServiceLoginResult.ErrorFound == "No" &&
            l_slurped_xml.Body.SelfServiceLoginResponse.SelfServiceLoginResult.ErrorNumber == "0" &&
            l_slurped_xml.Body.SelfServiceLoginResponse.SelfServiceLoginResult.ErrorMessage == "Processed successfully." &&
            l_slurped_xml.Body.SelfServiceLoginResponse.SelfServiceLoginResult.LoginFlag != null)) {
        io_user_authentication.failure()
        return
    } else {
        io_user_authentication.keyFieldMap = new HashMap<String, String>()
        io_user_authentication.functionalFieldMap = new HashMap<String, String>()
        io_user_authentication.keyFieldMap.put("proxy_number", l_slurped_xml.Body.SelfServiceLoginResponse.SelfServiceLoginResult.ProxyNumber.toString())
        io_user_authentication.keyFieldMap.put("account_number", l_slurped_xml.Body.SelfServiceLoginResponse.SelfServiceLoginResult.AccountNumber.toString())
        io_user_authentication.keyFieldMap.put("user_name", io_user_authentication.publicDataFieldSet.get("username") as String)
        //io_user_authentication.functionalFieldMap.put("card_type_id_enhanced", l_slurped_xml.AuthenticateResponse.AuthenticateResult.CardTypeIDEnhanced.toString())
        io_user_authentication.functionalFieldMap.put("login_flag", l_slurped_xml.Body.SelfServiceLoginResponse.SelfServiceLoginResult.LoginFlag.toString())
        io_user_authentication.functionalFieldMap.put("error_number", l_slurped_xml.Body.SelfServiceLoginResponse.SelfServiceLoginResult.ErrorNumber.toString())
        if (["2", "4"].contains(io_user_authentication.functionalFieldMap.get("login_flag"))) {
            io_user_authentication.success()
            return
        } else {
            io_user_authentication.failure()
            return
        }
    }
}