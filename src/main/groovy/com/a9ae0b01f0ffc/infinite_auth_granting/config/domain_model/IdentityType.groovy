package com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model

import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Accessor
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Authentication
import com.a9ae0b01f0ffc.infinite_auth_granting.domain_model.Identity
import groovy.transform.CompileStatic

import javax.persistence.*

@Entity
class IdentityType {

    String resourceName = this.getClass().getSimpleName()

    @Column(unique = true)
    String identityName

    @ManyToMany(fetch = FetchType.EAGER)
    @OrderColumn
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
