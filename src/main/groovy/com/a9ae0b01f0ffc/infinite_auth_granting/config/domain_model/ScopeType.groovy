package com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Grant
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Scope
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Authentication
import groovy.transform.CompileStatic

import javax.persistence.*

import static base.T_common_base_1_const.GC_EMPTY_STRING
import static base.T_common_base_1_const.GC_NULL_OBJ_REF

@Entity
@Table(name="ScopeTypes")
//@Table(uniqueConstraints = @UniqueConstraint(columnNames=["scopeName", "accessor"]))
class ScopeType {

    String scopeName = GC_EMPTY_STRING

    @ManyToOne(fetch = FetchType.EAGER)
    @OrderColumn
    AccessorType accessor = GC_NULL_OBJ_REF as AccessorType

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="Scope2service")
    Set<GrantType> grantSet = new HashSet<GrantType>()


    Scope to_user_scope() {
        Scope l_user_scope = new Scope()
        l_user_scope.scopeName = this.scopeName
        l_user_scope.grantSet = new HashSet<Grant>()
        this.grantSet.forEach{l_conf_grant->
            l_user_scope.grantSet.add(l_conf_grant.to_user_grant())
        }
        return l_user_scope
    }

}
