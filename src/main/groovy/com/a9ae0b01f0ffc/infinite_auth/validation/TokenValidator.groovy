package com.a9ae0b01f0ffc.infinite_auth.validation

import com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_base_5_context
import com.a9ae0b01f0ffc.infinite_auth.granting.Authorization

class TokenValidator {

    Boolean validate_token(String i_uri, String i_method, String i_body, String i_jwt, T_auth_grant_base_5_context i_context) {
        Authorization l_authorization = io_user_authentication.p_parent_authorization.refresh_jwt2authorization(io_user_authentication.authenticationData?.privateDataFieldSet?.get("refresh_token") as String, io_user_authentication.p_context)

        if (l_authorization.expiryDate.before(new Date())) {
            io_user_authentication.failure()
            return
        }

        if (io_user_authentication.p_parent_authorization.is_invalid_access_jwt(io_user_authentication.authenticationData?.publicDataFieldSet?.get("old_access_token") as String, io_user_authentication.p_context)) {
            io_user_authentication.failure()
            return
        }
    }

}
