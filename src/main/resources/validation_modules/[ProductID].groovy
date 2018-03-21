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

String l_product_id

if (i_method == "POST") {
    if (is_null(i_body)) {
        return false
    }
    try {
        GPathResult l_gpath_result = new XmlSlurper().parseText("<root>" + XML.toString(new JSONObject(i_body)) + "</root>")
        l_product_id = l_gpath_result.depthFirst().findAll { l_element -> l_element.name() == "ProductID" }
    } catch (Exception ignored) {
        return false
    }
} else if (i_method == "GET") {
    l_product_id = i_query_parameters.getFirst("ProductID")
} else {
    return false
}
if (is_null(l_product_id)) {
    return false
}

String l_expected_product_id
l_expected_product_id = i_authorization.scope.keyFieldMap.get("product_id")

if (is_null(l_expected_product_id)) {
    return false
}

if (is_not_null(l_product_id)) {
    if (l_product_id == l_product_id) {
        return true
    }
}

return false