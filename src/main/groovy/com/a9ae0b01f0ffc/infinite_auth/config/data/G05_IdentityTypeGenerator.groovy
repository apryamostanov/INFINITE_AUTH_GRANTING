package com.a9ae0b01f0ffc.infinite_auth.config.data

import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.IdentityType
import com.a9ae0b01f0ffc.infinite_auth.config.interfaces.I_authentication_type_repository
import com.a9ae0b01f0ffc.infinite_auth.config.interfaces.I_identity_type_repository
import org.springframework.stereotype.Component

@Component
class G05_IdentityTypeGenerator {

    void generate_data(I_identity_type_repository p_identity_repository, I_authentication_type_repository p_authentication_repository) {
        Set<IdentityType> l_entity_set = new HashSet<IdentityType>()
        l_entity_set.add(new IdentityType(identityName: "Owner of Accessor Data", authenticationSet: [p_authentication_repository.findByAuthenticationName("Accessor_data").first()]))
        l_entity_set.add(new IdentityType(identityName: "Owner of User Data", authenticationSet: [p_authentication_repository.findByAuthenticationName("User_data").first()]))
        l_entity_set.add(new IdentityType(identityName: "Owner of Refresh Data", authenticationSet: [p_authentication_repository.findByAuthenticationName("Refresh_data").first()]))
        l_entity_set.add(new IdentityType(identityName: "Owner of OTP Data and User Data", authenticationSet: [p_authentication_repository.findByAuthenticationName("OTP_data").first(), p_authentication_repository.findByAuthenticationName("User_data").first()]))
        l_entity_set.add(new IdentityType(identityName: "Owner of OTP Data and Provisioned User Data", authenticationSet: [p_authentication_repository.findByAuthenticationName("OTP_data").first(), p_authentication_repository.findByAuthenticationName("Provisioned_user_data").first()]))
        l_entity_set.add(new IdentityType(identityName: "Owner of User Data and DOB Data", authenticationSet: [p_authentication_repository.findByAuthenticationName("User_data").first(), p_authentication_repository.findByAuthenticationName("DOB_data").first()]))
        l_entity_set.add(new IdentityType(identityName: "Owner of Provisioned User Data", authenticationSet: [p_authentication_repository.findByAuthenticationName("Provisioned_user_data").first()]))
        l_entity_set.add(new IdentityType(identityName: "Owner of User Data and Provisioning Data", authenticationSet: [p_authentication_repository.findByAuthenticationName("User_data").first(), p_authentication_repository.findByAuthenticationName("Provisioning_data").first()]))//todo: add user data here
        l_entity_set.add(new IdentityType(identityName: "Owner of OTP Data", authenticationSet: [p_authentication_repository.findByAuthenticationName("OTP_data").first()]))
        p_identity_repository.save(l_entity_set)
    }

}
