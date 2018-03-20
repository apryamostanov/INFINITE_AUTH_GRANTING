package com.a9ae0b01f0ffc.infinite_auth.config.data

import com.a9ae0b01f0ffc.infinite_auth.config.interfaces.*
import com.a9ae0b01f0ffc.infinite_auth.validation.Revocation
import com.a9ae0b01f0ffc.infinite_auth.validation.interfaces.RevocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class T_data_generator {

    @Autowired
    private I_accessor_type_repository p_accessor_repository
    @Autowired
    private I_authentication_type_repository p_authentication_repository
    @Autowired
    private I_identity_type_repository p_identity_repository
    @Autowired
    private I_scope_type_repository p_scope_repository
    @Autowired
    private I_grant_type_repository p_grant_repository
    @Autowired
    private I_authorization_type_repository p_authorization_repository
    @Autowired
    private RevocationRepository p_revocation_repository

    @Autowired
    G01_AccessorTypeGenerator p_AccessorGenerator
    @Autowired
    G02_GrantTypeGenerator p_GrantGenerator
    @Autowired
    G03_ScopeTypeGenerator p_ScopeGenerator
    @Autowired
    G04_AuthenticationTypeGenerator p_AuthenticationGenerator
    @Autowired
    G05_IdentityTypeGenerator p_IdentityGenerator
    @Autowired
    G06_AuthorizationTypeGenerator p_AuthorizationGenerator

    void generate_data() {
        p_AccessorGenerator.generate_data(p_accessor_repository)
        p_GrantGenerator.generate_data(p_grant_repository)
        p_ScopeGenerator.generate_data(p_scope_repository, p_grant_repository, p_accessor_repository)
        p_AuthenticationGenerator.generate_data(p_authentication_repository)
        p_IdentityGenerator.generate_data(p_identity_repository, p_authentication_repository)
        p_AuthorizationGenerator.generate_data(p_authorization_repository, p_scope_repository, p_identity_repository, p_accessor_repository)
        p_revocation_repository.save([new Revocation(authorizationId: 180320133744528)])
    }

}
