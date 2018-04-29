package com.a9ae0b01f0ffc.infinite_auth.config.domain_model

import com.a9ae0b01f0ffc.infinite_auth.config.interfaces.I_overridable_by_accessor
import com.a9ae0b01f0ffc.infinite_auth.granting.Authentication
import com.a9ae0b01f0ffc.infinite_auth.granting.Identity

import javax.persistence.*

import static base.T_common_base_1_const.GC_NULL_OBJ_REF

/**
 * Identity Type is a group of Authentication Types<p>
 * Defines which Authentications (challenges) have to be passed within Authorization Granting<p><p>
 * Identity Type is supporting Accessor overriding (using item 1 - Identity Name and Accessor Role "Identity control")
 *
 * */
@Entity
@Table(name="IdentityTypes")
class IdentityType implements I_overridable_by_accessor {

    /**
     *  Identity name.<p>
     *  Naming convention: <b>Owner of [list of authentication names]</b><p>
     *  Non-unique field - there can be more than 1 Identity Type with the same name, but with different Accessor Type (overriding by Accessor Type)<p><p>
     *
     * Example: "Owner of User data and OTP data" <p><p>
     *
     * */
    @Column(unique = false)
    String identityName

    /**
     *  Accessor Type associated with this Identity Type.<p>
     *  Identity Types sharing same Idenity Type Name can override each other within a list of Accessor Types
     *  (with Accessor Role "Identity control") applicable to specific accessor.<p><p>
     *
     * */
    @ManyToOne(fetch = FetchType.EAGER)
    AccessorType accessor = GC_NULL_OBJ_REF as AccessorType

    /**
     *  List of Authentication Types required for this Identity Type.<p>
     *  Note: ALL of the Authentication Types must be present.<p>
     *  This setting is overrideable by Accessor Type (within same Identity Names)<p><p>
     *
     * */
    @ManyToMany(fetch = FetchType.EAGER)
    @OrderColumn
    @JoinTable(name="Authentication2identity")
    Set<AuthenticationType> authenticationSet = new HashSet<AuthenticationType>()

    /**
     *  Identity Type id, unique generated identity field.
     *
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id

    Identity to_user_identity() {
        Identity l_user_identity = new Identity()
        l_user_identity.identityName = this.identityName
        l_user_identity.authenticationSet = new HashSet<Authentication>()
        this.authenticationSet.forEach { l_conf_authentication->
            l_user_identity.authenticationSet.add(l_conf_authentication.to_user_authentication())
        }
        return l_user_identity
    }

    @Override
    AccessorType get_accessor_type() {
        return accessor
    }

    @Override
    String get_name() {
        return identityName
    }

}
