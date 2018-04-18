package com.a9ae0b01f0ffc.infinite_auth.config.domain_model

import com.a9ae0b01f0ffc.infinite_auth.granting.Authentication
import com.a9ae0b01f0ffc.infinite_auth.granting.Identity

import javax.persistence.*

import static base.T_common_base_1_const.GC_NULL_OBJ_REF

@Entity
@Table(name="IdentityTypes")
class IdentityType {

    @Column(unique = false)
    String identityName

    @ManyToOne(fetch = FetchType.EAGER)
    AccessorType accessor = GC_NULL_OBJ_REF as AccessorType

    @ManyToMany(fetch = FetchType.EAGER)
    @OrderColumn
    @JoinTable(name="Authentication2identity")
    Set<AuthenticationType> authenticationSet = new HashSet<AuthenticationType>()

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

}
