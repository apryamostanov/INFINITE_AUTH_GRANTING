package validation_modules

import com.a9ae0b01f0ffc.infinite_auth.granting.Authorization
import groovy.util.slurpersupport.GPathResult
import org.json.JSONObject
import org.json.XML

import javax.ws.rs.core.MultivaluedMap

import static base.T_common_base_1_const.GC_ONE_ONLY
import static base.T_common_base_3_utils.is_not_null
import static base.T_common_base_3_utils.is_null

System.out.println(this.getClass().getSimpleName())

MultivaluedMap<String, String> i_query_parameters = binding.getVariable("i_query_parameters")
def i_method = binding.getVariable("i_method")
def i_body = binding.getVariable("i_body")
def i_jwt = binding.getVariable("i_jwt")
def i_context = binding.getVariable("i_context")
def i_url_path = binding.getVariable("i_url_path")
def i_authorization = binding.getVariable("i_authorization")

String l_card_number
String l_proxy_number
String l_account_number

if (i_method == "POST") {
    if (is_null(i_body)) {
        return false
    }
    try {
        GPathResult l_gpath_result = new XmlSlurper().parseText("<root>" + XML.toString(new JSONObject(i_body)) + "</root>")
        l_card_number = l_gpath_result.depthFirst().findAll { l_element -> l_element.name() == "CardNumber" }
        l_proxy_number = l_gpath_result.depthFirst().findAll { l_element -> l_element.name() == "ProxyNumber" }
        l_account_number = l_gpath_result.depthFirst().findAll { l_element -> l_element.name() == "AccountNumber" }
    } catch (Exception ignored) {
        return false
    }
} else if (i_method == "GET") {
    l_card_number = i_query_parameters.getFirst("CardNumber")
    l_proxy_number = i_query_parameters.getFirst("ProxyNumber")
    l_account_number = i_query_parameters.getFirst("AccountNumber")
} else {
    return false
}
if (is_null(l_card_number) && is_null(l_proxy_number) && is_null(l_account_number)) {
    return false
}

String l_expected_card_number
String l_expected_proxy_number
String l_expected_account_number

l_expected_card_number = i_authorization.scope.keyFieldMap.get("card_number")
l_expected_proxy_number = i_authorization.scope.keyFieldMap.get("proxy_number")
l_expected_account_number = i_authorization.scope.keyFieldMap.get("account_number")


if (is_null(l_expected_card_number) && is_null(l_expected_proxy_number) && is_null(l_expected_account_number)) {
    return false
}

if (is_not_null(l_card_number)) {
    if (l_card_number == l_expected_card_number) {
        return true
    }
}

if (is_not_null(l_proxy_number)) {
    if (l_proxy_number == l_expected_proxy_number) {
        return true
    }
}

if (is_not_null(l_account_number)) {
    if (l_account_number == l_expected_account_number) {
        return true
    }
}

return false