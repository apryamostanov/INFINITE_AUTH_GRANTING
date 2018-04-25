package com.a9ae0b01f0ffc.infinite_auth.config.domain_model

import com.a9ae0b01f0ffc.infinite_auth.config.interfaces.I_overridable_by_accessor
import com.a9ae0b01f0ffc.infinite_auth.granting.Authorization
import groovy.transform.CompileStatic

import javax.persistence.*

import static base.T_common_base_1_const.GC_NULL_OBJ_REF

@CompileStatic
@Entity
@Table(name = "TokenTemplates")
//@Table(uniqueConstraints = @UniqueConstraint(columnNames=["accessor", "authorizationType", "scope", "identity"]))
class AuthorizationType implements I_overridable_by_accessor {

    String authorizationName

    @ManyToOne(fetch = FetchType.EAGER)
    AccessorType accessor = GC_NULL_OBJ_REF as AccessorType

    @ManyToMany(fetch = FetchType.EAGER)
    @OrderColumn
    @JoinTable(name = "Identity2authorization")
    Set<IdentityType> identitySet = new HashSet<IdentityType>()

    @ManyToMany(fetch = FetchType.EAGER)
    @OrderColumn
    @JoinTable(name = "Authorization2scope")
    Set<ScopeType> scopeSet = new HashSet<ScopeType>()

    Integer durationSeconds

    Integer maxUsageCount

    Integer refreshDurationSeconds

    Integer refreshMaxUsageCount

    String refreshAuthorizationName

    Boolean isRefreshAllowed

    @ManyToMany(fetch = FetchType.EAGER)
    @OrderColumn
    @JoinTable(name = "Authorizations2prerequisites")
    /**
     * Any of these
     */
    Set<AuthorizationType> prerequisiteAuthorizationSet

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id

    Set<Authorization> to_user_authorizations(String i_scope_name) {
        Set<Authorization> l_user_authorizations = new HashSet<Authorization>()
        ScopeType l_scope_type = this.scopeSet.first()
        if (l_scope_type.scopeName == i_scope_name) {
            for (IdentityType l_identity_type in this.identitySet) {
                    Authorization l_user_authorization = new Authorization()
                    l_user_authorization.authorizationName = this.authorizationName
                    l_user_authorization.authorizationType = "Access"
                    l_user_authorization.identity = l_identity_type.to_user_identity()
                    l_user_authorization.scope = l_scope_type.to_user_scope()
                    l_user_authorization.durationSeconds = this.durationSeconds
                    l_user_authorization.maxUsageCount = this.maxUsageCount
                    l_user_authorizations.add(l_user_authorization)
            }
        }
        return l_user_authorizations
    }

    @Override
    AccessorType get_accessor_type() {
        return accessor
    }

    @Override
    String get_name() {
        return authorizationName
    }

}
