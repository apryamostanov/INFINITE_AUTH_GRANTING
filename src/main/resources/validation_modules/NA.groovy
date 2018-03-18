package validation_modules

import com.a9ae0b01f0ffc.infinite_auth.granting.Authorization
import groovy.util.slurpersupport.GPathResult
import org.json.JSONObject
import org.json.XML

import javax.ws.rs.core.MultivaluedMap

import static base.T_common_base_1_const.GC_ONE_ONLY
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

return true