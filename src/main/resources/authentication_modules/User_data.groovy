package authentication_modules

import groovy.json.JsonSlurper
import okhttp3.*

import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

import static base.T_common_base_3_utils.is_not_null
import static base.T_common_base_3_utils.is_null
import static base.T_common_base_3_utils.not

System.out.println(this.getClass().getSimpleName())

def io_user_authentication = binding.getVariable("io_user_authentication")

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
        <d4p1:CardUserId>${io_user_authentication.authenticationData?.publicDataFieldMap?.get("username")}</d4p1:CardUserId>
        <d4p1:CurrentPassword>${io_user_authentication.authenticationData?.privateDataFieldMap?.get("password")}</d4p1:CurrentPassword>
      </requestData>
    </SelfServiceLogin>
  </s:Body>
</s:Envelope>
"""

System.out.println(l_self_service_login_request_body_string)

Proxy l_proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(io_user_authentication.p_conf.GC_PROXY_ADDRESS as String, io_user_authentication.p_conf.GC_PROXY_PORT as Integer))

Request l_self_service_login_request = new Request.Builder().post(RequestBody.create(MediaType.parse(io_user_authentication.p_conf.GC_CORECARD_API_CONTENT_TYPE as String), l_self_service_login_request_body_string)).url(io_user_authentication.p_conf.GC_CORECARD_API_URL as String).addHeader("SOAPAction", "www.corecard.com/ICoreCardServices/SelfServiceLogin").build()
SSLContext sslContext = SSLContext.getInstance("TLS");
sslContext.init(null, [new X509TrustManager() {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new ArrayList<X509Certificate>().toArray() as X509Certificate[];
    }
}] as TrustManager[], null);

OkHttpClient.Builder l_builder = new OkHttpClient.Builder().hostnameVerifier(io_user_authentication.p_context.get_unsecure_host_name_verifier()).sslSocketFactory(sslContext.getSocketFactory(),
        new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new ArrayList<X509Certificate>().toArray() as X509Certificate[];
            }
        }).proxy(l_proxy)
OkHttpClient l_client = l_builder.build()

Response l_self_service_login_response = l_client.newCall(l_self_service_login_request).execute()

String l_self_service_login_response_body = l_self_service_login_response.body().string()
System.out.println(l_self_service_login_response_body)
if (!l_self_service_login_response.isSuccessful()) {
    io_user_authentication.failure()
    return
} else {
    XmlSlurper l_xml_slurper = new XmlSlurper()
    Object l_slurped_self_service_login_response = l_xml_slurper.parseText(l_self_service_login_response_body)

    if (!(l_slurped_self_service_login_response.Body.SelfServiceLoginResponse.SelfServiceLoginResult.UniqueID != null &&
            l_slurped_self_service_login_response.Body.SelfServiceLoginResponse.SelfServiceLoginResult.UniqueIDFlag == 0 &&
            l_slurped_self_service_login_response.Body.SelfServiceLoginResponse.SelfServiceLoginResult.ErrorFound == "No" &&
            l_slurped_self_service_login_response.Body.SelfServiceLoginResponse.SelfServiceLoginResult.ErrorNumber == "0" &&
            l_slurped_self_service_login_response.Body.SelfServiceLoginResponse.SelfServiceLoginResult.ErrorMessage == "Processed successfully." &&
            l_slurped_self_service_login_response.Body.SelfServiceLoginResponse.SelfServiceLoginResult.LoginFlag != null)) {
        io_user_authentication.failure()
        return
    } else {
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
                <d4p1:ProxyNumber>${l_slurped_self_service_login_response.Body.SelfServiceLoginResponse.SelfServiceLoginResult.ProxyNumber.toString()}</d4p1:ProxyNumber>
              </requestData>
            </GetCardDetail>
          </s:Body>
        </s:Envelope>
        """
        System.out.println(l_get_card_details_request_body_string)
        Request l_get_card_details_request = new Request.Builder().post(RequestBody.create(MediaType.parse(io_user_authentication.p_conf.GC_CORECARD_API_CONTENT_TYPE as String), l_get_card_details_request_body_string)).url(io_user_authentication.p_conf.GC_CORECARD_API_URL as String).addHeader("SOAPAction", "www.corecard.com/ICoreCardServices/GetCardDetail").build()
        Response l_get_card_details_response = l_client.newCall(l_get_card_details_request).execute()
        String l_get_card_details_response_body = l_get_card_details_response.body().string()
        System.out.println(l_get_card_details_response_body)

        if (!l_get_card_details_response.isSuccessful()) {
            io_user_authentication.failure()
            return
        } else {
            Object l_slurped_get_card_details_response = l_xml_slurper.parseText(l_get_card_details_response_body)
            if (!(l_slurped_get_card_details_response.Body.GetCardDetailResponse.GetCardDetailResult.UniqueID != null &&
                    l_slurped_get_card_details_response.Body.GetCardDetailResponse.GetCardDetailResult.UniqueIDFlag == 0 &&
                    l_slurped_get_card_details_response.Body.GetCardDetailResponse.GetCardDetailResult.ErrorFound == "No" &&
                    l_slurped_get_card_details_response.Body.GetCardDetailResponse.GetCardDetailResult.ErrorNumber == "0" &&
                    l_slurped_get_card_details_response.Body.GetCardDetailResponse.GetCardDetailResult.ErrorMessage == "Processed successfully.")) {
                io_user_authentication.failure()
                return
            } else {
                def l_product_id = l_slurped_get_card_details_response.Body.GetCardDetailResponse.GetCardDetailResult.ProductDetail.ProductID
                if (is_not_null(io_user_authentication.p_parent_authorization?.scope?.keyFieldMap?.get("allowed_products"))) {
                    String l_allowed_products = io_user_authentication.p_parent_authorization.scope.keyFieldMap.get("allowed_products")
                    if (not(l_allowed_products.contains("," + l_product_id + ","))) {
                        io_user_authentication.failure()
                        return
                    }
                }
                def l_status_type_description = l_slurped_get_card_details_response.Body.GetCardDetailResponse.GetCardDetailResult.CardDetail.CardTypeDescription
                def l_card_type_id_enhanced
                if (l_status_type_description == "Primary Card") {
                    l_card_type_id_enhanced = 1
                } else if (l_status_type_description == "Backup Primary Card") {
                    l_card_type_id_enhanced = 2
                } else if (l_status_type_description == "Secondary Card") {
                    l_card_type_id_enhanced = 3
                } else if (l_status_type_description == "Backup Secondary Card") {
                    l_card_type_id_enhanced = 4
                } else if (l_status_type_description == "Virtual Secondary Card") {
                    l_card_type_id_enhanced = 5
                } else {
                    l_card_type_id_enhanced = 3
                }
                io_user_authentication.keyFieldMap = new HashMap<String, String>()
                io_user_authentication.functionalFieldMap = new HashMap<String, String>()
                io_user_authentication.keyFieldMap.put("proxy_number", l_slurped_self_service_login_response.Body.SelfServiceLoginResponse.SelfServiceLoginResult.ProxyNumber.toString())
                io_user_authentication.keyFieldMap.put("account_number", l_slurped_self_service_login_response.Body.SelfServiceLoginResponse.SelfServiceLoginResult.AccountNumber.toString())
                io_user_authentication.keyFieldMap.put("user_name", io_user_authentication.authenticationData?.publicDataFieldMap?.get("username") as String)
                io_user_authentication.keyFieldMap.put("product_id", l_product_id.toString())
                io_user_authentication.functionalFieldMap.put("card_type_id_enhanced", l_card_type_id_enhanced.toString())
                io_user_authentication.functionalFieldMap.put("login_flag", l_slurped_self_service_login_response.Body.SelfServiceLoginResponse.SelfServiceLoginResult.LoginFlag.toString())
                io_user_authentication.functionalFieldMap.put("error_number", l_slurped_self_service_login_response.Body.SelfServiceLoginResponse.SelfServiceLoginResult.ErrorNumber.toString())
                if (["2", "4"].contains(io_user_authentication.functionalFieldMap.get("login_flag"))) {
                    io_user_authentication.success()
                    return
                } else {
                    io_user_authentication.failure()
                    return
                }
            }
        }
    }
}