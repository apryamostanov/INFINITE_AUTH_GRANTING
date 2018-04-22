package com.a9ae0b01f0ffc.infinite_auth.config.data

import com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_base_4_const
import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.ScopeType
import com.a9ae0b01f0ffc.infinite_auth.config.interfaces.I_accessor_type_repository
import com.a9ae0b01f0ffc.infinite_auth.config.interfaces.I_grant_type_repository
import com.a9ae0b01f0ffc.infinite_auth.config.interfaces.I_scope_type_repository
import org.springframework.stereotype.Component

@Component
class G03_ScopeTypeGenerator {

    void generate_data(I_scope_type_repository p_scope_repository, I_grant_type_repository p_grant_repository, I_accessor_type_repository p_accessor_repository) {
        Set<ScopeType> l_entity_set = new HashSet<ScopeType>()

        l_entity_set.add(new ScopeType(scopeName: "Prerequisite Usage Only", accessor: p_accessor_repository.find_accessor_by_name("Scope: Any accessor", T_auth_grant_base_4_const.GC_ACCESSOR_TYPE_SCOPE_CONTROL).first()
        ))

        l_entity_set.add(new ScopeType(scopeName: "Anonymous Services", accessor: p_accessor_repository.find_accessor_by_name("Scope: Any White Labeled", T_auth_grant_base_4_const.GC_ACCESSOR_TYPE_SCOPE_CONTROL).first(), grantSet: [
                p_grant_repository.findByRestResourceName("UserRegistration").first(),
                p_grant_repository.findByRestResourceName("UserRegistrationValidationParametersEnhanced").first(),
                p_grant_repository.findByRestResourceName("CardActivationSS").first(),
                p_grant_repository.findByRestResourceName("CardActivationValidationParameterSSEnhanced").first(),
                p_grant_repository.findByRestResourceName("ForgotPassword").first(),
                p_grant_repository.findByRestResourceName("ForgotPasswordValidationParameterEnhanced").first(),
                p_grant_repository.findByRestResourceName("GetSecretKey").first(),
                p_grant_repository.findByRestResourceName("ForgotUserId").first(),
                p_grant_repository.findByRestResourceName("ForgotUserIdValidationParametersEnhanced").first()
        ]))

        l_entity_set.add(new ScopeType(scopeName: "User Services", accessor: p_accessor_repository.find_accessor_by_name("Scope: Any White Labeled Single Currency", T_auth_grant_base_4_const.GC_ACCESSOR_TYPE_SCOPE_CONTROL).first(), grantSet: [
                p_grant_repository.findByRestResourceName("CardLockUnlockRequest").first(),
                p_grant_repository.findByRestResourceName("CardSearchEnhanced").first(),
                p_grant_repository.findByRestResourceName("ChangePassword").first(),
                p_grant_repository.findByRestResourceName("GetCardDetailEnhanced").first(),
                p_grant_repository.findByRestResourceName("GetCVC").first(),
                p_grant_repository.findByRestResourceName("GetTransactionDetail").first(),
                p_grant_repository.findByRestResourceName("GetTransactionHistory").first(),
                p_grant_repository.findByRestResourceName("StatementsInformation").first(),
                p_grant_repository.findByRestResourceName("UpdateCustomerDetails").first(),
                p_grant_repository.findByRestResourceName("VirtualCardCreate").first(),
                p_grant_repository.findByRestResourceName("VirtualCardUpdate").first()
        ]))

        l_entity_set.add(new ScopeType(scopeName: "User Services", accessor: p_accessor_repository.find_accessor_by_name("Scope: Any White Labeled Multi Currency", T_auth_grant_base_4_const.GC_ACCESSOR_TYPE_SCOPE_CONTROL).first(), grantSet: [
                p_grant_repository.findByRestResourceName("CardLockUnlockRequest").first(),
                p_grant_repository.findByRestResourceName("CardSearchEnhanced").first(),
                p_grant_repository.findByRestResourceName("ChangePassword").first(),
                p_grant_repository.findByRestResourceNameAndKeyFieldRuleName("FXRateSearch", "[ProductID]").first(),
                p_grant_repository.findByRestResourceName("GetCardDetailEnhanced").first(),
                p_grant_repository.findByRestResourceName("GetCVC").first(),
                p_grant_repository.findByRestResourceName("GetTransactionDetail").first(),
                p_grant_repository.findByRestResourceName("GetTransactionHistory").first(),
                p_grant_repository.findByRestResourceName("ListStatementDate").first(),
                p_grant_repository.findByRestResourceName("SearchWalletAccount").first(),
                p_grant_repository.findByRestResourceName("StatementInformationMultiCurrency").first(),
                p_grant_repository.findByRestResourceName("UpdateCustomerDetails").first(),
                p_grant_repository.findByRestResourceName("UpdateWalletPriority").first(),
                p_grant_repository.findByRestResourceName("VirtualCardCreate").first(),//TODO: under question
                p_grant_repository.findByRestResourceName("VirtualCardUpdate").first(),//TODO: under question
                p_grant_repository.findByRestResourceName("W2WTransferConfirm").first(),
                p_grant_repository.findByRestResourceName("W2WTransferInitiateEnhanced").first()
        ]))

        l_entity_set.add(new ScopeType(scopeName: "Anonymous Services", accessor: p_accessor_repository.find_accessor_by_name("Scope: Any LMN", T_auth_grant_base_4_const.GC_ACCESSOR_TYPE_SCOPE_CONTROL).first(), grantSet: [
                p_grant_repository.findByRestResourceName("UserRegistration").first(),
                p_grant_repository.findByRestResourceName("UserRegistrationValidationParametersEnhanced").first(),
                p_grant_repository.findByRestResourceName("CardActivationSS").first(),
                p_grant_repository.findByRestResourceName("CardActivationValidationParameterSSEnhanced").first(),
                p_grant_repository.findByRestResourceName("ForgotPassword").first(),
                p_grant_repository.findByRestResourceName("ForgotPasswordValidationParameterEnhanced").first(),
                p_grant_repository.findByRestResourceName("GetSecretKey").first(),
                p_grant_repository.findByRestResourceName("ForgotUserId").first(),
                p_grant_repository.findByRestResourceName("ForgotUserIdValidationParametersEnhanced").first(),
                p_grant_repository.findByRestResourceNameAndKeyFieldRuleName("SendOTPSMS", "NA").first(),
                p_grant_repository.findByRestResourceNameAndKeyFieldRuleName("ValidateOTPSMS", "NA").first()
        ]))

        l_entity_set.add(new ScopeType(scopeName: "Main Screen", accessor: p_accessor_repository.find_accessor_by_name("Scope: Any LMN", T_auth_grant_base_4_const.GC_ACCESSOR_TYPE_SCOPE_CONTROL).first(), grantSet: [
                p_grant_repository.findByRestResourceName("GetCardDetailEnhanced").first(),
                p_grant_repository.findByRestResourceName("GetCVC").first(),
                p_grant_repository.findByRestResourceName("CardActivationSS").first(),
                p_grant_repository.findByRestResourceName("CardActivationValidationParameterSSEnhanced").first(),
                p_grant_repository.findByRestResourceName("CardSearchEnhanced").first(),
                p_grant_repository.findByRestResourceName("UserRegistration").first(),
                p_grant_repository.findByRestResourceName("UserRegistrationValidationParametersEnhanced").first(),
                p_grant_repository.findByRestResourceName("ForgotPassword").first(),
                p_grant_repository.findByRestResourceName("ForgotPasswordValidationParameterEnhanced").first(),
                p_grant_repository.findByRestResourceName("W2WTransferInitiateEnhanced").first(),
                p_grant_repository.findByRestResourceName("GetTransactionHistory").first(),
                p_grant_repository.findByRestResourceName("CardLockUnlockRequest").first(),
                p_grant_repository.findByRestResourceName("FXRateSearch").first(),
                p_grant_repository.findByRestResourceName("GetSecretKey").first(),
                p_grant_repository.findByRestResourceName("GetTransactionDetail").first(),
                p_grant_repository.findByRestResourceName("SetStatementDate").first(),
                p_grant_repository.findByRestResourceName("SearchWalletAccount").first(),
                p_grant_repository.findByRestResourceName("StatementInformationMultiCurrency").first(),
                p_grant_repository.findByRestResourceName("StatementsInformation").first(),
                p_grant_repository.findByRestResourceName("UpdateWalletPriority").first(),
                p_grant_repository.findByRestResourceName("VirtualCardCreate").first(),
                p_grant_repository.findByRestResourceName("VirtualCardUpdate").first(),
                p_grant_repository.findByRestResourceName("W2WTransferConfirm").first(),
                p_grant_repository.findByRestResourceName("ForgotPIN").first(),
                p_grant_repository.findByRestResourceName("ForgotPINValidationParameters").first(),
                p_grant_repository.findByRestResourceName("GenerateOTP").first(),
                p_grant_repository.findByRestResourceNameAndKeyFieldRuleName("SendOTPSMS", "[CardNumber, ProxyNumber]").first(),
                p_grant_repository.findByRestResourceName("ValidateOTP").first(),
                p_grant_repository.findByRestResourceNameAndKeyFieldRuleName("ValidateOTPSMS", "[CardNumber, ProxyNumber]").first(),
                p_grant_repository.findByRestResourceName("ForgotUserId").first(),
                p_grant_repository.findByRestResourceName("ForgotUserIdValidationParametersEnhanced").first(),
                p_grant_repository.findByRestResourceName("Document").first(),
                p_grant_repository.findByRestResourceName("Document_metadata").first(),
                p_grant_repository.findByRestResourceName("GetProductParametersEnhanced").first()
        ]))

        l_entity_set.add(new ScopeType(scopeName: "Update Profile", accessor: p_accessor_repository.find_accessor_by_name("Scope: Any LMN", T_auth_grant_base_4_const.GC_ACCESSOR_TYPE_SCOPE_CONTROL).first(), grantSet: [
                p_grant_repository.findByRestResourceName("UpdateProfileWithoutPhone").first()
        ]))

        l_entity_set.add(new ScopeType(scopeName: "Update Phone", accessor: p_accessor_repository.find_accessor_by_name("Scope: Any LMN", T_auth_grant_base_4_const.GC_ACCESSOR_TYPE_SCOPE_CONTROL).first(), grantSet: [
                p_grant_repository.findByRestResourceName("UpdateProfileWithPhone").first()
        ]))

        l_entity_set.add(new ScopeType(scopeName: "Change Password", accessor: p_accessor_repository.find_accessor_by_name("Scope: Any LMN", T_auth_grant_base_4_const.GC_ACCESSOR_TYPE_SCOPE_CONTROL).first(), grantSet: [
                p_grant_repository.findByRestResourceName("ChangePassword").first()
        ]))

        l_entity_set.add(new ScopeType(scopeName: "Change Security Answers", accessor: p_accessor_repository.find_accessor_by_name("Scope: Any LMN", T_auth_grant_base_4_const.GC_ACCESSOR_TYPE_SCOPE_CONTROL).first(), grantSet: [
                p_grant_repository.findByRestResourceName("UpdateSecurityQuestionAnswer").first()
        ]))

        l_entity_set.add(new ScopeType(scopeName: "Customer Onboarding", accessor: p_accessor_repository.find_accessor_by_name("Scope: Any LMN", T_auth_grant_base_4_const.GC_ACCESSOR_TYPE_SCOPE_CONTROL).first(), grantSet: [
                p_grant_repository.findByRestResourceName("AccountCreation").first(),
                p_grant_repository.findByRestResourceName("ValidateIDDocument").first(),
                p_grant_repository.findByRestResourceName("CheckIDDocumentValidation").first(),
                p_grant_repository.findByRestResourceName("GetFee").first(),
                p_grant_repository.findByRestResourceNameAndKeyFieldRuleName("FXRateSearch", "NA").first(),
                p_grant_repository.findByRestResourceName("ValidateLoad").first(),
                p_grant_repository.findByRestResourceName("FundingAmountInquiry").first(),
                p_grant_repository.findByRestResourceName("CreateEEPaymentRequest").first(),
                p_grant_repository.findByRestResourceName("UpdateEEPaymentResponse").first()
        ]))


        p_scope_repository.save(l_entity_set)

    }
}