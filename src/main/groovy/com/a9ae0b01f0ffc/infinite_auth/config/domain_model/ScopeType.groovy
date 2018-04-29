package com.a9ae0b01f0ffc.infinite_auth.config.domain_model

import com.a9ae0b01f0ffc.infinite_auth.config.interfaces.I_overridable_by_accessor
import com.a9ae0b01f0ffc.infinite_auth.granting.Grant
import com.a9ae0b01f0ffc.infinite_auth.granting.Scope

import javax.persistence.*

import static base.T_common_base_1_const.GC_EMPTY_STRING
import static base.T_common_base_1_const.GC_NULL_OBJ_REF

/**
 * Scope Type is a group of Grant Types<p>
 * Defines which Grants (APIs) are allowed within a Authorization<p><p>
 * Scope Type is supporting Accessor overriding (using item 1 - Scope Name and Accessor Role "Scope control")
 *
 * */
@Entity
@Table(name="ScopeTypes")
class ScopeType implements I_overridable_by_accessor {

    /**
     *  Scope name.<p>
     *  Naming convention: <b>Either a name of specific screen/activity/functionality on the Client application - or a logical group of services</b><p>
     *  Non-unique field - there can be more than 1 Scope Type with the same name, but with different Accessor Type (overriding by Accessor Type)<p><p>
     *
     * Example: "Anonymous Services" <p><p>
     *
     * */
    String scopeName = GC_EMPTY_STRING

    /**
     *  Accessor Type associated with this Scope Type.<p>
     *  Scope Types sharing same Scope Type Name can override each other within a list of Accessor Types
     *  (with Accessor Role "Scope control") applicable to specific accessor.<p><p>
     *
     * */
    @ManyToOne(fetch = FetchType.EAGER)
    AccessorType accessor = GC_NULL_OBJ_REF as AccessorType

    /**
     *  Identity Type id, unique generated identity field.
     *
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id

    /**
     *  List of Grant Types accessible using this Authorization Type.<p>
     *  Note: ALL of the Grant Types are allowed.<p>
     *  This setting is overrideable by Accessor Type (within same Scope Names)<p><p>
     *
     * */
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

    @Override
    AccessorType get_accessor_type() {
        return accessor
    }

    @Override
    String get_name() {
        return scopeName
    }

}
