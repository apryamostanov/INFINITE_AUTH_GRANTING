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
        io_user_authentication.privateDataFieldSet.get("DOB") == null) {
    io_user_authentication.failure()
    return
}


        String l_get_card_details_request_body_string = """<s:Envelope xmlns:s="http://schemas.xmlsoap.org/soap/envelope/">
          <s:Body>
            <GetCardDetail xmlns="www.corecard.com">
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
                <d4p1:LoginUserLevel i:nil="true"/>
                <d4p1:AccountNumber i:nil="true"/>
                <d4p1:CardNumber i:nil="true"/>
                <d4p1:ProxyNumber>${io_user_authentication.publicDataFieldSet.get("proxy_number")}</d4p1:ProxyNumber>
              </requestData>
            </GetCardDetail>
          </s:Body>
        </s:Envelope>
        """
        System.out.println(l_get_card_details_request_body_string)
        OkHttpClient.Builder l_builder = new OkHttpClient.Builder().hostnameVerifier(io_user_authentication.p_context.get_unsecure_host_name_verifier())//.proxy(l_proxy)
        OkHttpClient l_client = l_builder.build()
        Request l_get_card_details_request = new Request.Builder().post(RequestBody.create(MediaType.parse(io_user_authentication.p_conf.GC_CORECARD_API_CONTENT_TYPE as String), l_get_card_details_request_body_string)).url(io_user_authentication.p_conf.GC_CORECARD_API_URL as String).addHeader("SOAPAction", "www.corecard.com/ICoreCardServices/GetCardDetail").build()
        Response l_get_card_details_response = l_client.newCall(l_get_card_details_request).execute()
        String l_get_card_details_response_body = l_get_card_details_response.body().string()
        System.out.println(l_get_card_details_response_body)

        if (!l_get_card_details_response.isSuccessful()) {
            io_user_authentication.failure()
            return
        } else {
            XmlSlurper l_xml_slurper = new XmlSlurper()
            Object l_slurped_get_card_details_response = l_xml_slurper.parseText(l_get_card_details_response_body)
            if (!(l_slurped_get_card_details_response.Body.GetCardDetailResponse.GetCardDetailResult.UniqueID != null &&
                    l_slurped_get_card_details_response.Body.GetCardDetailResponse.GetCardDetailResult.UniqueIDFlag == 0 &&
                    l_slurped_get_card_details_response.Body.GetCardDetailResponse.GetCardDetailResult.ErrorFound == "No" &&
                    l_slurped_get_card_details_response.Body.GetCardDetailResponse.GetCardDetailResult.ErrorNumber == "0" &&
                    l_slurped_get_card_details_response.Body.GetCardDetailResponse.GetCardDetailResult.ErrorMessage == "Processed successfully.")) {
                io_user_authentication.failure()
                return
            } else {
                def l_dob = l_slurped_get_card_details_response.Body.GetCardDetailResponse.GetCardDetailResult.CustomerDetail.DateofBirth
                if (l_dob == io_user_authentication.privateDataFieldSet.get("DOB")) {
                    io_user_authentication.success()
                    return
                } else {
                    io_user_authentication.failure()
                    return
                }
            }
        }
