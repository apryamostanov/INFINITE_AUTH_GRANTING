package com.a9ae0b01f0ffc.infinite_auth.config.data

import com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_base_4_const
import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.AccessorType
import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.GrantType
import com.a9ae0b01f0ffc.infinite_auth.config.interfaces.I_accessor_type_repository
import com.a9ae0b01f0ffc.infinite_auth.config.interfaces.I_grant_type_repository
import org.springframework.stereotype.Component

@Component
class G02_GrantTypeGenerator {

    void generate_data(I_grant_type_repository p_grant_repository, I_accessor_type_repository p_accessor_repository) {
        Thread.currentThread()
        Set<GrantType> l_entity_set = new HashSet<GrantType>()

        AccessorType l_grant_any_accessor = p_accessor_repository.find_accessor_by_name("Grant: Any accessor", T_auth_grant_base_4_const.GC_ACCESSOR_TYPE_GRANT_CONTROL).first()

        l_entity_set.add(new GrantType(grantName: "POST UserRegistration", restResourceName: "UserRegistration", method: "POST", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET UserRegistrationValidationParametersEnhanced", restResourceName: "UserRegistrationValidationParametersEnhanced", method: "GET", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST CardActivationSS", restResourceName: "CardActivationSS", method: "POST", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET CardActivationValidationParameterSSEnhanced", restResourceName: "CardActivationValidationParameterSSEnhanced", method: "GET", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST ForgotPassword", restResourceName: "ForgotPassword", method: "POST", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET ForgotPasswordValidationParameterEnhanced", restResourceName: "ForgotPasswordValidationParameterEnhanced", method: "GET", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET GetSecretKey", restResourceName: "GetSecretKey", method: "GET", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST ForgotUserId", restResourceName: "ForgotUserId", method: "POST", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST ForgotUserIdEnhanced", restResourceName: "ForgotUserIdEnhanced", method: "POST", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET ForgotUserIdValidationParametersEnhanced", restResourceName: "ForgotUserIdValidationParametersEnhanced", method: "GET", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET ForgotUserIdValidationParameterEnhanced", restResourceName: "ForgotUserIdValidationParameterEnhanced", method: "GET", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST SendActivationCode", restResourceName: "SendActivationCode", method: "POST", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET FXRateSearchEnhanced", restResourceName: "FXRateSearchEnhanced", method: "GET", validationModuleName: "[ProductID]",  accessor: l_grant_any_accessor))

        l_entity_set.add(new GrantType(grantName: "POST AddressLookup", restResourceName: "AddressLookup", method: "POST", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST AccountCreationWithKYC", restResourceName: "AccountCreationWithKYC", method: "POST", validationModuleName: "[MobileNumber,HomePhoneNumber,WorkPhoneNumber]",  accessor: l_grant_any_accessor))
        //l_entity_set.add(new GrantType(grantName: "POST AddressLookup", restResourceName: "AddressLookup", method: "POST", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        //l_entity_set.add(new GrantType(grantName: "POST AddressLookup", restResourceName: "AddressLookup", method: "POST", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        //l_entity_set.add(new GrantType(grantName: "POST AddressLookup", restResourceName: "AddressLookup", method: "POST", validationModuleName: "NA",  accessor: l_grant_any_accessor))

        l_entity_set.add(new GrantType(grantName: "POST UpdateCustomerDetails", restResourceName: "UpdateCustomerDetails", method: "POST", validationModuleName: "[CardNumber, ProxyNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET ListStatementDate", restResourceName: "ListStatementDate", method: "GET", validationModuleName: "[CardNumber, ProxyNumber, AccountNumber]",  accessor: l_grant_any_accessor))

        l_entity_set.add(new GrantType(grantName: "GET GetCardDetailEnhanced", restResourceName: "GetCardDetailEnhanced", method: "GET", validationModuleName: "[CardNumber, ProxyNumber, AccountNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET GetCVC", restResourceName: "GetCVC", method: "GET", validationModuleName: "[CardNumber, ProxyNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET GetFees", restResourceName: "GetFees", method: "GET", validationModuleName: "[ProductID,ProxyNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET CardSearchEnhanced", restResourceName: "CardSearchEnhanced", method: "GET", validationModuleName: "[CardNumber, ProxyNumber, AccountNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST W2WTransferInitiateEnhanced", restResourceName: "W2WTransferInitiateEnhanced", method: "POST", validationModuleName: "[CardNumber, ProxyNumber, AccountNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET GetTransactionHistory", restResourceName: "GetTransactionHistory", method: "GET", validationModuleName: "[CardNumber, ProxyNumber, AccountNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST CardLockUnlockRequest", restResourceName: "CardLockUnlockRequest", method: "POST", validationModuleName: "[CardNumber, ProxyNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET FXRateSearch", restResourceName: "FXRateSearch", method: "GET", validationModuleName: "[ProductID]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET GetTransactionDetail", restResourceName: "GetTransactionDetail", method: "GET", validationModuleName: "[CardNumber, ProxyNumber, AccountNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET SetStatementDate", restResourceName: "SetStatementDate", method: "GET", validationModuleName: "[CardNumber, ProxyNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET SearchWalletAccount", restResourceName: "SearchWalletAccount", method: "GET", validationModuleName: "[CardNumber, ProxyNumber, AccountNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET RetrieveProductWallets", restResourceName: "RetrieveProductWallets", method: "GET", validationModuleName: "[ProductID]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET StatementInformationMultiCurrency", restResourceName: "StatementInformationMultiCurrency", method: "GET", validationModuleName: "[CardNumber, ProxyNumber, AccountNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET StatementsInformation", restResourceName: "StatementsInformation", method: "GET", validationModuleName: "[CardNumber, ProxyNumber, AccountNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST UpdateWalletPriority", restResourceName: "UpdateWalletPriority", method: "POST", validationModuleName: "[CardNumber, ProxyNumber, AccountNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST VirtualCardCreate", restResourceName: "VirtualCardCreate", method: "POST", validationModuleName: "[CardNumber, ProxyNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST VirtualCardUpdate", restResourceName: "VirtualCardUpdate", method: "POST", validationModuleName: "[CardNumber, ProxyNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST W2WTransferConfirm", restResourceName: "W2WTransferConfirm", method: "POST", validationModuleName: "[CardNumber, ProxyNumber, AccountNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST W2WTransferConfirmEnhanced", restResourceName: "W2WTransferConfirmEnhanced", method: "POST", validationModuleName: "[CardNumber, ProxyNumber, AccountNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST ForgotPIN", restResourceName: "ForgotPIN", method: "POST", validationModuleName: "[CardNumber, ProxyNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET ForgotPINValidationParameters", restResourceName: "ForgotPINValidationParameters", method: "GET", validationModuleName: "[CardNumber, ProxyNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST RevealPIN", restResourceName: "RevealPIN", method: "POST", validationModuleName: "[CardNumber, ProxyNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET RevealPINValidationParameter", restResourceName: "RevealPINValidationParameter", method: "GET", validationModuleName: "[CardNumber, ProxyNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST GenerateOTP", restResourceName: "GenerateOTP", method: "POST", validationModuleName: "[CardNumber, ProxyNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST SendOTPSMS", restResourceName: "SendOTPSMS", method: "POST", validationModuleName: "[CardNumber, ProxyNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST SendOTPSMS", restResourceName: "SendOTPSMS", method: "POST", validationModuleName: "NA", maxUsageCountWithinAuthorization: 3,  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST ValidateOTP", restResourceName: "ValidateOTP", method: "POST", validationModuleName: "[CardNumber, ProxyNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST ValidateOTPSMS", restResourceName: "ValidateOTPSMS", method: "POST", validationModuleName: "[CardNumber, ProxyNumber]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST ValidateOTPSMS", restResourceName: "ValidateOTPSMS", method: "POST", validationModuleName: "NA", maxUsageCountWithinAuthorization: 9,  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET Document", restResourceName: "Document", method: "GET", validationModuleName: "NA", urlMask: ".*?\\/%accessor_id%\\/.*?\\/.*",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET Document_metadata", restResourceName: "Document_metadata", method: "GET", validationModuleName: "NA", urlMask: ".*?\\/%accessor_id%\\/.*?\\/.*",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET GetProductParametersEnhanced", restResourceName: "GetProductParametersEnhanced", method: "GET", validationModuleName: "[ProductID]",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET GetProductParameters", restResourceName: "GetProductParameters", method: "GET", validationModuleName: "[ProductID]",  accessor: l_grant_any_accessor))

        l_entity_set.add(new GrantType(grantName: "POST UpdateProfileWithoutPhone", restResourceName: "UpdateProfileWithoutPhone", method: "POST", validationModuleName: "[CardNumber, ProxyNumber]",  accessor: l_grant_any_accessor))

        l_entity_set.add(new GrantType(grantName: "POST UpdateProfileWithPhone", restResourceName: "UpdateProfileWithPhone", method: "POST", validationModuleName: "[CardNumber, ProxyNumber, PhoneNumber]",  accessor: l_grant_any_accessor))

        l_entity_set.add(new GrantType(grantName: "POST ChangePassword", restResourceName: "ChangePassword", method: "POST", validationModuleName: "[CardNumber, ProxyNumber]",  accessor: l_grant_any_accessor))

        l_entity_set.add(new GrantType(grantName: "POST UpdateSecurityQuestionAnswer", restResourceName: "UpdateSecurityQuestionAnswer", method: "POST", validationModuleName: "[CardNumber, ProxyNumber]",  accessor: l_grant_any_accessor))

        //
        l_entity_set.add(new GrantType(grantName: "POST AccountCreation", restResourceName: "AccountCreation", method: "POST", validationModuleName: "[PhoneNumber]", maxUsageCountWithinAuthorization: 1,  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST ValidateIDDocument", restResourceName: "ValidateIDDocument", method: "POST", validationModuleName: "NA", maxUsageCountWithinAuthorization: 5,  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET CheckIDDocumentValidation", restResourceName: "CheckIDDocumentValidation", method: "GET", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET GetFee", restResourceName: "GetFee", method: "GET", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "GET FXRateSearch", restResourceName: "FXRateSearch", method: "GET", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST ValidateLoad", restResourceName: "ValidateLoad", method: "POST", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST FundingAmountInquiry", restResourceName: "FundingAmountInquiry", method: "POST", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST CreateEEPaymentRequest", restResourceName: "CreateEEPaymentRequest", method: "POST", validationModuleName: "NA",  accessor: l_grant_any_accessor))
        l_entity_set.add(new GrantType(grantName: "POST UpdateEEPaymentResponse", restResourceName: "UpdateEEPaymentResponse", method: "POST", validationModuleName: "NA", maxUsageCountWithinAuthorization: 1,  accessor: l_grant_any_accessor))
        p_grant_repository.save(l_entity_set)
    }


}
