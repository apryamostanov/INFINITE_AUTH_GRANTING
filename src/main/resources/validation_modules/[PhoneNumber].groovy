package validation_modules

import com.a9ae0b01f0ffc.infinite_auth.granting.Authorization
import groovy.util.slurpersupport.GPathResult
import org.json.JSONObject
import org.json.XML

import javax.ws.rs.core.MultivaluedMap

import static base.T_common_base_1_const.GC_ONE_ONLY
import static base.T_common_base_3_utils.is_not_null
import static base.T_common_base_3_utils.is_not_null
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

String l_phone_number

if (i_method == "POST") {
    if (is_null(i_body)) {
        return false
    }
    try {
        GPathResult l_gpath_result = new XmlSlurper().parseText("<root>" + XML.toString(new JSONObject(i_body)) + "</root>")
        l_phone_number = l_gpath_result.depthFirst().findAll { l_element -> l_element.name() == "PhoneNumber" }
    } catch (Exception ignored) {
        return false
    }
} else if (i_method == "GET") {
    l_phone_number = i_query_parameters.getFirst("PhoneNumber")
} else {
    return false
}
if (is_null(l_phone_number)) {
    return false
}

String l_expected_phone_number
l_expected_phone_number = i_authorization.scope.keyFieldMap.get("phone_number")

if (is_null(l_expected_phone_number)) {
    return false
}

if (is_not_null(l_phone_number)) {
    if (l_phone_number == l_expected_phone_number) {
        return true
    }
}

return false