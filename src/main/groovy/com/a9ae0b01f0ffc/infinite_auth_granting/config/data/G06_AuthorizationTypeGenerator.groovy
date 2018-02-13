package com.a9ae0b01f0ffc.infinite_auth_granting.config.data

import com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model.AuthorizationType
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_accessor_type_repository
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_authorization_type_repository
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_identity_type_repository
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_scope_type_repository
import org.springframework.stereotype.Component

@Component
class G06_AuthorizationTypeGenerator {

    void generate_data(I_authorization_type_repository p_authorization_repository, I_scope_type_repository p_scope_repository, I_identity_type_repository p_identity_repository, I_accessor_type_repository p_accessor_repository) {
        Set<AuthorizationType> l_entity_set = new HashSet<AuthorizationType>()
        l_entity_set.add(new AuthorizationType(
                authorizationName: "Anonymous",
                scopeSet: p_scope_repository.findByScopeName("Anonymous Services"),
                identitySet: p_identity_repository.findByIdentityName("Owner of Accessor Data"),
                durationSeconds: 1800,
                maxUsageCount: null,
                accessor: p_accessor_repository.findByAccessorName("Any accessor").first(),
                authorizationType: "Access"
        ))
        p_authorization_repository.save(l_entity_set)
        l_entity_set.clear()
        l_entity_set.add(new AuthorizationType(
                authorizationName: "Refresh",
                scopeSet: p_scope_repository.findByScopeName("Main Screen"),
                durationSeconds: 2592000,
                maxUsageCount: null,
                accessor: p_accessor_repository.findByAccessorName("Any accessor").first(),
                authorizationType: "Refresh"
        ))
        l_entity_set.add(new AuthorizationType(
                authorizationName: "Refresh",
                scopeSet: p_scope_repository.findByScopeName("Main Screen"),
                durationSeconds: 1800,
                maxUsageCount: null,
                accessor: p_accessor_repository.findByAccessorName("LMN Multi Currency React (FT2 Development)").first(),
                authorizationType: "Refresh"
        ))
        p_authorization_repository.save(l_entity_set)
        l_entity_set.clear()
        l_entity_set.add(new AuthorizationType(
                authorizationName: "Read",
                scopeSet: p_scope_repository.findByScopeName("Main Screen"),
                identitySet: [p_identity_repository.findByIdentityName("Owner of User Data").first(), p_identity_repository.findByIdentityName("Owner of Refresh Data").first()],
                durationSeconds: 1800,
                maxUsageCount: null,
                refreshAuthorization: p_authorization_repository.findByAuthorizationNameAndAccessor("Refresh", p_accessor_repository.findByAccessorName("Any accessor").first()).first(),
                accessor: p_accessor_repository.findByAccessorName("Any accessor").first(),
                authorizationType: "Access",
                prerequisiteAuthorizationSet: p_authorization_repository.findByAuthorizationName("Anonymous")
        ))
        l_entity_set.add(new AuthorizationType(
                authorizationName: "Read",
                scopeSet: p_scope_repository.findByScopeName("Main Screen"),
                identitySet: [p_identity_repository.findByIdentityName("Owner of User Data").first(), p_identity_repository.findByIdentityName("Owner of Refresh Data").first()],
                durationSeconds: 120,
                maxUsageCount: null,
                refreshAuthorization: p_authorization_repository.findByAuthorizationNameAndAccessor("Refresh", p_accessor_repository.findByAccessorName("LMN Multi Currency React (FT2 Development)").first()).first(),
                accessor: p_accessor_repository.findByAccessorName("LMN Multi Currency React (FT2 Development)").first(),
                authorizationType: "Access",
                prerequisiteAuthorizationSet: p_authorization_repository.findByAuthorizationName("Anonymous")
        ))
        p_authorization_repository.save(l_entity_set)
        l_entity_set.clear()
        l_entity_set.add(new AuthorizationType(
                authorizationName: "Demographic Updates",
                scopeSet: p_scope_repository.findByScopeName("Update Profile"),
                identitySet: [p_identity_repository.findByIdentityName("Owner of User Data").first(), p_identity_repository.findByIdentityName("Owner of Provisioned User Data").first()],
                durationSeconds: 30,
                maxUsageCount: 1,
                accessor: p_accessor_repository.findByAccessorName("Any accessor").first(),
                authorizationType: "Access",
                prerequisiteAuthorizationSet: p_authorization_repository.findByAuthorizationName("Read")
        ))
        l_entity_set.add(new AuthorizationType(
                authorizationName: "Provisioned User Data Usage",
                scopeSet: p_scope_repository.findByScopeName("Prerequisite Usage Only"),
                identitySet: p_identity_repository.findByIdentityName("Owner of User Data and Provisioning Data"),
                durationSeconds: 2592000,
                maxUsageCount: 20,
                accessor: p_accessor_repository.findByAccessorName("Any accessor").first(),
                authorizationType: "Access",
                prerequisiteAuthorizationSet: p_authorization_repository.findByAuthorizationName("Read")
        ))
        l_entity_set.add(new AuthorizationType(
                authorizationName: "Security Updates",
                scopeSet: [p_scope_repository.findByScopeName("Change Password").first(), p_scope_repository.findByScopeName("Change Security Answers").first()],
                identitySet: p_identity_repository.findByIdentityName("Owner of User Data and DOB Data"),
                durationSeconds: 30,
                maxUsageCount: 1,
                accessor: p_accessor_repository.findByAccessorName("Any accessor").first(),
                authorizationType: "Access",
                prerequisiteAuthorizationSet: p_authorization_repository.findByAuthorizationName("Read")
        ))
        l_entity_set.add(new AuthorizationType(
                authorizationName: "Secured Demographic Updates",
                scopeSet: p_scope_repository.findByScopeName("Update Phone"),
                identitySet: [p_identity_repository.findByIdentityName("Owner of OTP Data and User Data").first(), p_identity_repository.findByIdentityName("Owner of OTP Data and Provisioned User Data").first()],
                durationSeconds: 30,
                maxUsageCount: 1,
                accessor: p_accessor_repository.findByAccessorName("Any accessor").first(),
                authorizationType: "Access",
                prerequisiteAuthorizationSet: p_authorization_repository.findByAuthorizationName("Read")
        ))
        p_authorization_repository.save(l_entity_set)
    }

}
