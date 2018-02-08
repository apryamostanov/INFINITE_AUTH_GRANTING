package com.a9ae0b01f0ffc.infinite_auth_granting.config.data

import com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model.AuthenticationType
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_authentication_type_repository
import org.springframework.stereotype.Component

@Component
class G04_AuthenticationTypeGenerator {

    void generate_data(I_authentication_type_repository p_authentication_repository) {
        Set<AuthenticationType> l_entity_set = new HashSet<AuthenticationType>()
        l_entity_set.add(new AuthenticationType(authenticationName: "Accessor_data"))
        l_entity_set.add(new AuthenticationType(authenticationName: "User_data"))
        l_entity_set.add(new AuthenticationType(authenticationName: "Refresh_data"))
        l_entity_set.add(new AuthenticationType(authenticationName: "OTP_data"))
        l_entity_set.add(new AuthenticationType(authenticationName: "Provisioned_user_data"))
        l_entity_set.add(new AuthenticationType(authenticationName: "Provisioning_data"))
        l_entity_set.add(new AuthenticationType(authenticationName: "DOB_data"))
        p_authentication_repository.save(l_entity_set)
    }

}
