package com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Grant
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Scope
import groovy.transform.CompileStatic

import javax.persistence.*

import static base.T_common_base_1_const.GC_EMPTY_STRING

@CompileStatic
@Entity
class GrantType {


    String resourceName = this.getClass().getSimpleName()

    String restResourceName = GC_EMPTY_STRING

    String method = GC_EMPTY_STRING

    /*Priority 2*/
    /*/{$username}*/
    String urlMask = GC_EMPTY_STRING

    /*Priority 3*/
    @ElementCollection(fetch = FetchType.EAGER)
    Set<String> keyFieldSet = new HashSet<String>()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id

    Grant to_user_grant() {
        Grant l_user_grant = new Grant()
        l_user_grant.restResourceName = this.restResourceName
        l_user_grant.method = this.method
        l_user_grant.urlMask = this.urlMask
        l_user_grant.keyFieldSet = new HashSet<String>()
        l_user_grant.keyFieldSet.addAll(this.keyFieldSet)
        return l_user_grant
    }

}
