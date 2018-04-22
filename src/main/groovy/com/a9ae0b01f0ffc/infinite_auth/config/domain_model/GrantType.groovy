package com.a9ae0b01f0ffc.infinite_auth.config.domain_model

import com.a9ae0b01f0ffc.infinite_auth.config.interfaces.I_overridable_by_accessor
import com.a9ae0b01f0ffc.infinite_auth.granting.Grant
import groovy.transform.CompileStatic

import javax.persistence.*

import static base.T_common_base_1_const.GC_EMPTY_STRING
import static base.T_common_base_1_const.GC_NULL_OBJ_REF

@CompileStatic
@Entity
@Table(name="ServiceTypes")
class GrantType implements I_overridable_by_accessor {

    String grantName = GC_EMPTY_STRING

    String restResourceName = GC_EMPTY_STRING

    String method = GC_EMPTY_STRING

    /*Priority 2*/
    /*/{$username}*/
    String urlMask = GC_EMPTY_STRING

    /*Priority 3*/
    String keyFieldRuleName = GC_EMPTY_STRING

    Integer maxUsageCountWithinScope

    @ManyToOne(fetch = FetchType.EAGER)
    AccessorType accessor = GC_NULL_OBJ_REF as AccessorType

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
        l_user_grant.maxUsageCountWithinScope = this.maxUsageCountWithinScope
        l_user_grant.grantTypeId = this.id
        return l_user_grant
    }

    @Override
    AccessorType get_accessor_type() {
        return accessor
    }

    @Override
    String get_name() {
        return grantName
    }

}
