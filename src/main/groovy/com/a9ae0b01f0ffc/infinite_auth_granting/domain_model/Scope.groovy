package com.a9ae0b01f0ffc.infinite_auth_granting.domain_model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper

import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_6_util.okhttp_request

@JsonIgnoreProperties(ignoreUnknown = true)
class Scope {

    String scopeName
    Accessor accessor
    Set<Authorization> authorizationSet = new HashSet<Authorization>()

    static Set<Scope> create_from_configuration(String i_scopes_url) {
        ObjectMapper l_object_mapper = new ObjectMapper()
        Set<Scope> l_scope_set = new HashSet<Scope>()
        def (Object l_scopes_json, Object l_scope_response, String l_scope_body) = okhttp_request(i_scopes_url)
        for (l_scope_json in l_scopes_json?._embedded?.scopes) {
            Scope l_scope = l_object_mapper.readValue(l_scope_body, Scope.class)
            l_scope_set.add(l_scope)
            l_scope.setAccessor(Accessor.create_from_configuration(l_scope_json?._links?.accessor?.href))
            l_scope.getAuthorizationSet().addAll(Authorization.create_from_configuration(l_scope_json?._links?.authorizationSet?.href))
        }
        return l_scope_set
    }

}