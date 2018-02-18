package com.a9ae0b01f0ffc.infinite_auth_granting.config.data

import com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model.GrantType
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_grant_type_repository
import org.springframework.stereotype.Component

@Component
class G02_GrantTypeGenerator {

    void generate_data(I_grant_type_repository p_grant_repository) {
        Set<GrantType> l_entity_set = new HashSet<GrantType>()
        l_entity_set.add(new GrantType(restResourceName: "UserRegistration", method: "POST", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "UserRegistrationValidationParametersEnhanced", method: "GET", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "CardActivationSS", method: "POST", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "CardActivationValidationParameterSSEnhanced", method: "GET", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "ForgotPassword", method: "POST", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "ForgotPasswordValidationParameterEnhanced", method: "GET", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "GetSecretKey", method: "GET", keyFieldSet: ["ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "ForgotUserId", method: "POST", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "ForgotUserIdValidationParametersEnhanced", method: "GET", keyFieldSet: ["CardNumber", "ProxyNumber"]))

        l_entity_set.add(new GrantType(restResourceName: "UpdateCustomerDetails", method: "POST", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "ListStatementDate", method: "GET", keyFieldSet: ["CardNumber", "ProxyNumber"]))

        l_entity_set.add(new GrantType(restResourceName: "GetCardDetailEnhanced", method: "GET", keyFieldSet: ["CardNumber", "ProxyNumber", "AccountNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "GetCVC", method: "GET", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "CardSearchEnhanced", method: "GET", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "W2WTransferInitiateEnhanced", method: "POST", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "GetTransactionHistory", method: "GET", keyFieldSet: ["CardNumber", "ProxyNumber", "AccountNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "CardLockUnlockRequest", method: "POST", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "FXRateSearch", method: "GET", keyFieldSet: ["ProductID"]))
        l_entity_set.add(new GrantType(restResourceName: "GetTransactionDetail", method: "GET", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "SetStatementDate", method: "GET", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "SearchWalletAccount", method: "GET", keyFieldSet: ["CardNumber", "ProxyNumber", "AccountNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "StatementInformationMultiCurrency", method: "GET", keyFieldSet: ["CardNumber", "ProxyNumber", "AccountNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "StatementsInformation", method: "GET", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "UpdateWalletPriority", method: "POST", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "VirtualCardCreate", method: "POST", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "VirtualCardUpdate", method: "POST", keyFieldSet: ["CardNumber", "ProxyNumber", "AccountNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "W2WTransferConfirm", method: "POST", keyFieldSet: ["CardNumber", "ProxyNumber", "AccountNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "ForgotPIN", method: "POST", keyFieldSet: ["CardNumber", "ProxyNumber", "AccountNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "ForgotPINValidationParameters", method: "GET", keyFieldSet: ["CardNumber", "ProxyNumber", "AccountNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "GenerateOTP", method: "POST", keyFieldSet: ["CardNumber", "ProxyNumber", "AccountNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "SendOTPSMS", method: "POST", keyFieldSet: ["CardNumber", "ProxyNumber", "AccountNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "ValidateOTP", method: "POST", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "ValidateOTPSMS", method: "GET", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        l_entity_set.add(new GrantType(restResourceName: "Document", method: "GET", urlMask: "/%ACCESSORID%/*/*/*"))
        l_entity_set.add(new GrantType(restResourceName: "Document_metadata", method: "GET", urlMask: "/%ACCESSORID%/*/*/*"))
        l_entity_set.add(new GrantType(restResourceName: "GetProductParametersEnhanced", method: "GET", keyFieldSet: ["ProductID"]))

        l_entity_set.add(new GrantType(restResourceName: "UpdateProfileWithoutPhone", method: "POST", keyFieldSet: ["CardNumber", "ProxyNumber"]))

        l_entity_set.add(new GrantType(restResourceName: "UpdateProfileWithPhone", method: "POST", keyFieldSet: ["CardNumber", "ProxyNumber", "PhoneNumber"]))

        l_entity_set.add(new GrantType(restResourceName: "ChangePassword", method: "POST", keyFieldSet: ["CardNumber", "ProxyNumber"]))

        l_entity_set.add(new GrantType(restResourceName: "SetAnswers", method: "POST", keyFieldSet: ["CardNumber", "ProxyNumber"]))
        p_grant_repository.save(l_entity_set)
    }


}
