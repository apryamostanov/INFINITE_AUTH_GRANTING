package com.a9ae0b01f0ffc.infinite_auth_granting.config.data

import com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model.GrantType
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_grant_type_repository
import org.springframework.stereotype.Component

@Component
class G02_GrantTypeGenerator {

    void generate_data(I_grant_type_repository p_grant_repository) {
        Set<GrantType> l_entity_set = new HashSet<GrantType>()
        l_entity_set.add(new GrantType(restResourceName: "UserRegistration", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "UserRegistrationValidationParametersEnhanced", method: "GET", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "CardActivationSS", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "CardActivationValidationParameterSSEnhanced", method: "GET", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "ForgotPassword", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "ForgotPasswordValidationParameterEnhanced", method: "GET", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "GetSecretKey", method: "GET", keyFieldRuleName: "[ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "ForgotUserId", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "ForgotUserIdValidationParametersEnhanced", method: "GET", keyFieldRuleName: "[CardNumber, ProxyNumber]"))

        l_entity_set.add(new GrantType(restResourceName: "UpdateCustomerDetails", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "ListStatementDate", method: "GET", keyFieldRuleName: "[CardNumber, ProxyNumber, AccountNumber]"))

        l_entity_set.add(new GrantType(restResourceName: "GetCardDetailEnhanced", method: "GET", keyFieldRuleName: "[CardNumber, ProxyNumber, AccountNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "GetCVC", method: "GET", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "CardSearchEnhanced", method: "GET", keyFieldRuleName: "[CardNumber, ProxyNumber, AccountNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "W2WTransferInitiateEnhanced", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber, AccountNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "GetTransactionHistory", method: "GET", keyFieldRuleName: "[CardNumber, ProxyNumber, AccountNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "CardLockUnlockRequest", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "FXRateSearch", method: "GET", keyFieldRuleName: "[ProductID]"))
        l_entity_set.add(new GrantType(restResourceName: "GetTransactionDetail", method: "GET", keyFieldRuleName: "[CardNumber, ProxyNumber, AccountNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "SetStatementDate", method: "GET", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "SearchWalletAccount", method: "GET", keyFieldRuleName: "[CardNumber, ProxyNumber, AccountNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "StatementInformationMultiCurrency", method: "GET", keyFieldRuleName: "[CardNumber, ProxyNumber, AccountNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "StatementsInformation", method: "GET", keyFieldRuleName: "[CardNumber, ProxyNumber, AccountNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "UpdateWalletPriority", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber, AccountNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "VirtualCardCreate", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "VirtualCardUpdate", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "W2WTransferConfirm", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber, AccountNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "ForgotPIN", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "ForgotPINValidationParameters", method: "GET", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "GenerateOTP", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "SendOTPSMS", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "SendOTPSMS", method: "POST", keyFieldRuleName: ""))
        l_entity_set.add(new GrantType(restResourceName: "ValidateOTP", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "ValidateOTPSMS", method: "GET", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "ValidateOTPSMS", method: "GET", keyFieldRuleName: ""))
        l_entity_set.add(new GrantType(restResourceName: "Document", method: "GET", urlMask: "/%ACCESSORID%/*/*/*"))
        l_entity_set.add(new GrantType(restResourceName: "Document_metadata", method: "GET", urlMask: "/%ACCESSORID%/*/*/*"))
        l_entity_set.add(new GrantType(restResourceName: "GetProductParametersEnhanced", method: "GET", keyFieldRuleName: "[ProductID]"))

        l_entity_set.add(new GrantType(restResourceName: "UpdateProfileWithoutPhone", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))

        l_entity_set.add(new GrantType(restResourceName: "UpdateProfileWithPhone", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber, PhoneNumber]"))

        l_entity_set.add(new GrantType(restResourceName: "ChangePassword", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))

        l_entity_set.add(new GrantType(restResourceName: "SetAnswers", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))

        //
        l_entity_set.add(new GrantType(restResourceName: "AccountCreation", method: "POST", keyFieldRuleName: "[PhoneNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "ValidateIDDocument", method: "POST", keyFieldRuleName: ""))
        l_entity_set.add(new GrantType(restResourceName: "CheckIDDocumentValidation", method: "POST", keyFieldRuleName: ""))
        l_entity_set.add(new GrantType(restResourceName: "GetFee", method: "POST", keyFieldRuleName: ""))
        l_entity_set.add(new GrantType(restResourceName: "FXRateSearch", method: "POST", keyFieldRuleName: ""))
        l_entity_set.add(new GrantType(restResourceName: "ValidateLoad", method: "POST", keyFieldRuleName: ""))
        l_entity_set.add(new GrantType(restResourceName: "FundingAmountInquiry", method: "POST", keyFieldRuleName: ""))
        l_entity_set.add(new GrantType(restResourceName: "CreateEEPaymentRequest", method: "POST", keyFieldRuleName: ""))
        l_entity_set.add(new GrantType(restResourceName: "UpdateEEPaymentResponse", method: "POST", keyFieldRuleName: ""))
        p_grant_repository.save(l_entity_set)
    }


}
