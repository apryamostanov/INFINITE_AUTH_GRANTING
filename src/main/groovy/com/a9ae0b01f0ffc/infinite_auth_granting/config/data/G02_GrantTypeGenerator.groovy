package com.a9ae0b01f0ffc.infinite_auth_granting.config.data

import com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model.GrantType
import com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces.I_grant_type_repository
import org.springframework.stereotype.Component

@Component
class G02_GrantTypeGenerator {

    void generate_data(I_grant_type_repository p_grant_repository) {
        Set<GrantType> l_entity_set = new HashSet<GrantType>()
        l_entity_set.add(new GrantType(restResourceName: "UserRegistration", method: "POST", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        l_entity_set.add(new GrantType(restResourceName: "UserRegistrationValidationParametersEnhanced", method: "GET", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        l_entity_set.add(new GrantType(restResourceName: "CardActivationSS", method: "POST", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        l_entity_set.add(new GrantType(restResourceName: "CardActivationValidationParameterSSEnhanced", method: "GET", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        l_entity_set.add(new GrantType(restResourceName: "ForgotPassword", method: "POST", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        l_entity_set.add(new GrantType(restResourceName: "ForgotPasswordValidationParameterEnhanced", method: "GET", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        l_entity_set.add(new GrantType(restResourceName: "GetSecretKey", method: "GET", keyFieldSetJson: """["ProxyNumber"]"""))
        l_entity_set.add(new GrantType(restResourceName: "ForgotUserId", method: "POST", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        l_entity_set.add(new GrantType(restResourceName: "ForgotUserIdValidationParametersEnhanced", method: "GET", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))

        l_entity_set.add(new GrantType(restResourceName: "UpdateCustomerDetails", method: "POST", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        l_entity_set.add(new GrantType(restResourceName: "ListStatementDate", method: "GET", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))

        l_entity_set.add(new GrantType(restResourceName: "GetCardDetailEnhanced", method: "GET", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}", "AccountNumber"]"""))
        l_entity_set.add(new GrantType(restResourceName: "GetCVC", method: "GET", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        l_entity_set.add(new GrantType(restResourceName: "CardSearchEnhanced", method: "GET", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        l_entity_set.add(new GrantType(restResourceName: "W2WTransferInitiateEnhanced", method: "POST", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        l_entity_set.add(new GrantType(restResourceName: "GetTransactionHistory", method: "GET", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}", "AccountNumber"]"""))
        l_entity_set.add(new GrantType(restResourceName: "CardLockUnlockRequest", method: "POST", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        l_entity_set.add(new GrantType(restResourceName: "FXRateSearch", method: "GET", keyFieldSetJson: """["ProductID"]"""))
        l_entity_set.add(new GrantType(restResourceName: "GetTransactionDetail", method: "GET", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        l_entity_set.add(new GrantType(restResourceName: "SetStatementDate", method: "GET", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        l_entity_set.add(new GrantType(restResourceName: "SearchWalletAccount", method: "GET", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}", "AccountNumber"]"""))
        l_entity_set.add(new GrantType(restResourceName: "StatementInformationMultiCurrency", method: "GET", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}", "AccountNumber"]"""))
        l_entity_set.add(new GrantType(restResourceName: "StatementsInformation", method: "GET", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        l_entity_set.add(new GrantType(restResourceName: "UpdateWalletPriority", method: "POST", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        l_entity_set.add(new GrantType(restResourceName: "VirtualCardCreate", method: "POST", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        l_entity_set.add(new GrantType(restResourceName: "VirtualCardUpdate", method: "POST", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}", "AccountNumber"]"""))
        l_entity_set.add(new GrantType(restResourceName: "W2WTransferConfirm", method: "POST", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}", "AccountNumber"]"""))
        l_entity_set.add(new GrantType(restResourceName: "ForgotPIN", method: "POST", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}", "AccountNumber"]"""))
        l_entity_set.add(new GrantType(restResourceName: "ForgotPINValidationParameters", method: "GET", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}", "AccountNumber"]"""))
        l_entity_set.add(new GrantType(restResourceName: "GenerateOTP", method: "POST", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}", "AccountNumber"]"""))
        l_entity_set.add(new GrantType(restResourceName: "SendOTPSMS", method: "POST", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}", "AccountNumber"]"""))
        l_entity_set.add(new GrantType(restResourceName: "ValidateOTP", method: "POST", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        l_entity_set.add(new GrantType(restResourceName: "ValidateOTPSMS", method: "GET", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        l_entity_set.add(new GrantType(restResourceName: "Document", method: "GET", urlMask: "/%ACCESSORID%/*/*/*"))
        l_entity_set.add(new GrantType(restResourceName: "Document_metadata", method: "GET", urlMask: "/%ACCESSORID%/*/*/*"))
        l_entity_set.add(new GrantType(restResourceName: "GetProductParametersEnhanced", method: "GET", keyFieldSetJson: """["ProductID"]"""))

        l_entity_set.add(new GrantType(restResourceName: "UpdateProfileWithoutPhone", method: "POST", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))

        l_entity_set.add(new GrantType(restResourceName: "UpdateProfileWithPhone", method: "POST", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}", "PhoneNumber"]"""))

        l_entity_set.add(new GrantType(restResourceName: "ChangePassword", method: "POST", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))

        l_entity_set.add(new GrantType(restResourceName: "SetAnswers", method: "POST", keyFieldSetJson: """["{parameter: CardNumber|ProxyNumber}"]"""))
        p_grant_repository.save(l_entity_set)
    }


}
