package com.a9ae0b01f0ffc.infinite_auth.config.data

import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.AuthenticationType
import com.a9ae0b01f0ffc.infinite_auth.config.interfaces.I_accessor_type_repository
import com.a9ae0b01f0ffc.infinite_auth.config.interfaces.I_authentication_type_repository
import org.springframework.stereotype.Component

import static com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_base_4_const.*

@Component
class G04_AuthenticationTypeGenerator {

    void generate_data(I_authentication_type_repository p_authentication_repository, I_accessor_type_repository i_accessor_type_repository) {
        Set<AuthenticationType> l_entity_set = new HashSet<AuthenticationType>()
        l_entity_set.add(new AuthenticationType(authenticationName: "Accessor_data", accessor: i_accessor_type_repository.find_authentication_accessor_by_name("Authentication: Any accessor").first(),
                mandatoryPublicFieldNames: [
                        "accessor_name",
                        "platform",
                        "app_version",
                        "FIID",
                        "api_major_version",
                        "specific_data"
                ],
                mandatoryPrivateFieldNames: [
                ],
                lockoutMaxAttemptCount: GC_AUTHENTICATION_LOCKOUT_COUNT_NEVER,
                lockoutDurationSeconds: GC_AUTHENTICATION_LOCKOUT_DURATION_ZERO
        ))
        l_entity_set.add(new AuthenticationType(authenticationName: "User_data", accessor: i_accessor_type_repository.find_authentication_accessor_by_name("Authentication: Any accessor").first(),
                mandatoryPublicFieldNames: [
                        "username"
                ],
                mandatoryPrivateFieldNames: [
                        "password"
                ],
                lockoutMaxAttemptCount: GC_AUTHENTICATION_LOCKOUT_COUNT_NEVER,
                lockoutDurationSeconds: GC_AUTHENTICATION_LOCKOUT_DURATION_ZERO
        ))
        l_entity_set.add(new AuthenticationType(authenticationName: "Refresh_data", accessor: i_accessor_type_repository.find_authentication_accessor_by_name("Authentication: Any accessor").first(),
                mandatoryPublicFieldNames: [
                        "proxy_number",
                        "old_access_token"
                ],
                mandatoryPrivateFieldNames: [
                        "refresh_token"
                ],
                lockoutMaxAttemptCount: GC_AUTHENTICATION_LOCKOUT_COUNT_NEVER,
                lockoutDurationSeconds: GC_AUTHENTICATION_LOCKOUT_DURATION_ZERO
        ))
        l_entity_set.add(new AuthenticationType(authenticationName: "OTP_data", accessor: i_accessor_type_repository.find_authentication_accessor_by_name("Authentication: Any accessor").first(),
                mandatoryPublicFieldNames: [
                        "proxy_number",
                        //"otp_id", //todo: this is needed
                        "phone_number"
                ],
                mandatoryPrivateFieldNames: [
                        "otp"
                ],
                lockoutMaxAttemptCount: GC_AUTHENTICATION_LOCKOUT_COUNT_NEVER,
                lockoutDurationSeconds: GC_AUTHENTICATION_LOCKOUT_DURATION_ZERO
        ))
        l_entity_set.add(new AuthenticationType(authenticationName: "Provisioned_user_data", accessor: i_accessor_type_repository.find_authentication_accessor_by_name("Authentication: Any accessor").first(),
                mandatoryPublicFieldNames: [
                        "provisioned_data_unique_id",
                        "proxy_number",
                        "provisioned_user_data_usage_authorization"
                ],
                mandatoryPrivateFieldNames: [
                        "provisioned_data_signature"
                ],
                lockoutMaxAttemptCount: GC_AUTHENTICATION_LOCKOUT_COUNT_NEVER,
                lockoutDurationSeconds: GC_AUTHENTICATION_LOCKOUT_DURATION_ZERO
        ))
        l_entity_set.add(new AuthenticationType(authenticationName: "Provisioning_data", accessor: i_accessor_type_repository.find_authentication_accessor_by_name("Authentication: Any accessor").first(),
                mandatoryPublicFieldNames: [
                        "provisioning_public_key",
                        "proxy_number"
                ],
                mandatoryPrivateFieldNames: [

                ],
                lockoutMaxAttemptCount: GC_AUTHENTICATION_LOCKOUT_COUNT_NEVER,
                lockoutDurationSeconds: GC_AUTHENTICATION_LOCKOUT_DURATION_ZERO
        ))
        l_entity_set.add(new AuthenticationType(authenticationName: "DOB_data", accessor: i_accessor_type_repository.find_authentication_accessor_by_name("Authentication: Any accessor").first(),
                mandatoryPublicFieldNames: [
                        "proxy_number"
                ],
                mandatoryPrivateFieldNames: [
                        "DOB"
                ],
                lockoutMaxAttemptCount: 3,
                lockoutDurationSeconds: 60*60
        ))
        p_authentication_repository.save(l_entity_set)
    }

}
