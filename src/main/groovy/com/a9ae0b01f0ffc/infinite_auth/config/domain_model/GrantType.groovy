package com.a9ae0b01f0ffc.infinite_auth.config.domain_model

import com.a9ae0b01f0ffc.infinite_auth.granting.Grant
import groovy.transform.CompileStatic

import javax.persistence.*

import static base.T_common_base_1_const.GC_EMPTY_STRING

@CompileStatic
@Entity
@Table(name="ServiceTypes")
class GrantType {

    String restResourceName = GC_EMPTY_STRING

    String method = GC_EMPTY_STRING

    /*Priority 2*/
    /*/{$username}*/
    String urlMask = GC_EMPTY_STRING

    /*Priority 3*/
    String keyFieldRuleName = GC_EMPTY_STRING

    Integer maxUsageCountWithinScope

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id

    Grant to_user_grant() {
        Grant l_user_grant = new Grant()
        l_user_grant.restResourceName = this.restResourceName
        l_user_grant.method = this.method
        l_user_grant.urlMask = this.urlMask
        l_user_grant.validationModuleName = new HashSet<String>()
        l_user_grant.validationModuleName = this.keyFieldRuleName
        return l_user_grant
    }

}
