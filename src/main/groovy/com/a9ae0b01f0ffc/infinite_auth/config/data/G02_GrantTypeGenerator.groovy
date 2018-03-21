package com.a9ae0b01f0ffc.infinite_auth.config.data

import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.GrantType
import com.a9ae0b01f0ffc.infinite_auth.config.interfaces.I_grant_type_repository
import org.springframework.stereotype.Component

@Component
class G02_GrantTypeGenerator {

    void generate_data(I_grant_type_repository p_grant_repository) {
        Thread.currentThread()
        Set<GrantType> l_entity_set = new HashSet<GrantType>()
        l_entity_set.add(new GrantType(restResourceName: "UserRegistration", method: "POST", keyFieldRuleName: "NA"))
        l_entity_set.add(new GrantType(restResourceName: "UserRegistrationValidationParametersEnhanced", method: "GET", keyFieldRuleName: "NA"))
        l_entity_set.add(new GrantType(restResourceName: "CardActivationSS", method: "POST", keyFieldRuleName: "NA"))
        l_entity_set.add(new GrantType(restResourceName: "CardActivationValidationParameterSSEnhanced", method: "GET", keyFieldRuleName: "NA"))
        l_entity_set.add(new GrantType(restResourceName: "ForgotPassword", method: "POST", keyFieldRuleName: "NA"))
        l_entity_set.add(new GrantType(restResourceName: "ForgotPasswordValidationParameterEnhanced", method: "GET", keyFieldRuleName: "NA"))
        l_entity_set.add(new GrantType(restResourceName: "GetSecretKey", method: "GET", keyFieldRuleName: "NA"))
        l_entity_set.add(new GrantType(restResourceName: "ForgotUserId", method: "POST", keyFieldRuleName: "NA"))
        l_entity_set.add(new GrantType(restResourceName: "ForgotUserIdValidationParametersEnhanced", method: "GET", keyFieldRuleName: "NA"))

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
        l_entity_set.add(new GrantType(restResourceName: "SendOTPSMS", method: "POST", keyFieldRuleName: "NA", maxUsageCountWithinScope: 3))
        l_entity_set.add(new GrantType(restResourceName: "ValidateOTP", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "ValidateOTPSMS", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))
        l_entity_set.add(new GrantType(restResourceName: "ValidateOTPSMS", method: "POST", keyFieldRuleName: "NA", maxUsageCountWithinScope: 9))
        l_entity_set.add(new GrantType(restResourceName: "Document", method: "GET", keyFieldRuleName: "NA", urlMask: ".*?\\/%accessor_id%\\/.*?\\/.*"))
        l_entity_set.add(new GrantType(restResourceName: "Document_metadata", method: "GET", keyFieldRuleName: "NA", urlMask: ".*?\\/%accessor_id%\\/.*?\\/.*"))
        l_entity_set.add(new GrantType(restResourceName: "GetProductParametersEnhanced", method: "GET", keyFieldRuleName: "[ProductID]"))

        l_entity_set.add(new GrantType(restResourceName: "UpdateProfileWithoutPhone", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))

        l_entity_set.add(new GrantType(restResourceName: "UpdateProfileWithPhone", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber, PhoneNumber]"))

        l_entity_set.add(new GrantType(restResourceName: "ChangePassword", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))

        l_entity_set.add(new GrantType(restResourceName: "SetAnswers", method: "POST", keyFieldRuleName: "[CardNumber, ProxyNumber]"))

        //
        l_entity_set.add(new GrantType(restResourceName: "AccountCreation", method: "POST", keyFieldRuleName: "[PhoneNumber]", maxUsageCountWithinScope: 1))
        l_entity_set.add(new GrantType(restResourceName: "ValidateIDDocument", method: "POST", keyFieldRuleName: "NA", maxUsageCountWithinScope: 5))
        l_entity_set.add(new GrantType(restResourceName: "CheckIDDocumentValidation", method: "POST", keyFieldRuleName: "NA"))
        l_entity_set.add(new GrantType(restResourceName: "GetFee", method: "POST", keyFieldRuleName: "NA"))
        l_entity_set.add(new GrantType(restResourceName: "FXRateSearch", method: "GET", keyFieldRuleName: "NA"))
        l_entity_set.add(new GrantType(restResourceName: "ValidateLoad", method: "POST", keyFieldRuleName: "NA"))
        l_entity_set.add(new GrantType(restResourceName: "FundingAmountInquiry", method: "POST", keyFieldRuleName: "NA"))
        l_entity_set.add(new GrantType(restResourceName: "CreateEEPaymentRequest", method: "POST", keyFieldRuleName: "NA"))
        l_entity_set.add(new GrantType(restResourceName: "UpdateEEPaymentResponse", method: "POST", keyFieldRuleName: "NA", maxUsageCountWithinScope: 1))
        p_grant_repository.save(l_entity_set)
    }


}
