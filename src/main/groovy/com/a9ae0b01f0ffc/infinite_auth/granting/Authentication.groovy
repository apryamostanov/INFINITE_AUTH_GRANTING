package com.a9ae0b01f0ffc.infinite_auth.granting

import com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_base_5_context
import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.AuthenticationType
import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.FailedAuthenticationAttempt
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.time.TimeCategory
import sun.misc.BASE64Encoder

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

import static base.T_common_base_1_const.*
import static base.T_common_base_3_utils.is_null
import static base.T_common_base_3_utils.not
import static com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_base_4_const.*

@JsonIgnoreProperties(ignoreUnknown = true)
class Authentication {
    String authenticationName

    @JsonProperty("authentication_data")
    Authentication_data authenticationData

    @JsonIgnore
    Authorization p_parent_authorization

    @JsonIgnore
    HashMap<String, String> keyFieldMap

    @JsonIgnore
    HashMap<String, String> functionalFieldMap

    @JsonProperty("status")
    String authenticationStatus = GC_STATUS_NEW

    @JsonIgnore
    def p_conf

    @JsonIgnore
    T_auth_grant_base_5_context p_context

    void failure() {
        this.authenticationStatus = GC_STATUS_FAILED
    }

    void success() {
        this.authenticationStatus = GC_STATUS_SUCCESSFUL
    }

    void common_authentication_validation(AuthenticationType i_conf_authentication, T_auth_grant_base_5_context i_context, Authorization i_parent_authorization) {
        if (authenticationName != i_conf_authentication.authenticationName) {
            failure()
            return
        }
        if (is_mandatory_fields_missing(i_conf_authentication)) {
            failure()
            return
        }
        FailedAuthenticationAttempt l_new_attempt = create_authentication_attempt(i_conf_authentication, i_context)
        if (l_new_attempt.status == GC_STATUS_FAILED) {
            failure()
            return
        }
        functionalFieldMap = new HashMap<>()
        keyFieldMap = new HashMap<>()
        p_context = i_context
        p_parent_authorization = i_parent_authorization
        p_conf = p_context.get_authentication_config_holder().run(i_context.app_conf().authenticationConfigFileName + i_context.app_conf().authenticationModulesExtension, new Binding())
        Binding l_binding = new Binding()
        l_binding.setVariable("io_user_authentication", this)
        i_context.get_authentication_runner().run(authenticationName + i_context.app_conf().authenticationModulesExtension, l_binding)
        authenticationData?.privateDataFieldMap = GC_NULL_OBJ_REF as HashMap<String, String>
        if (authenticationStatus == GC_STATUS_FAILED) {
            l_new_attempt.status = GC_STATUS_FAILED
            l_new_attempt.currentAttemptCount = nvl(l_new_attempt.previousAttemptCount, GC_ZERO) + GC_ONE_ONLY
        } else if (authenticationStatus == GC_STATUS_SUCCESSFUL) {
            l_new_attempt.status = GC_STATUS_SUCCESSFUL
            l_new_attempt.currentAttemptCount = GC_ZERO
        }
        if (i_conf_authentication.lockoutMaxAttemptCount != GC_AUTHENTICATION_LOCKOUT_COUNT_NEVER && authenticationStatus == GC_STATUS_FAILED) {
            i_context.p_authentication_attempt_repository.save([l_new_attempt])
        }
    }

    Boolean is_mandatory_fields_missing(AuthenticationType i_conf_authentication) {
        if ((not(i_conf_authentication.mandatoryPublicFieldNames.isEmpty()) && authenticationData.publicDataFieldMap == null)
                || (not(i_conf_authentication.mandatoryPrivateFieldNames.isEmpty()) && authenticationData.privateDataFieldMap == null)) {
            return true
        }
        if ((not(i_conf_authentication.mandatoryPublicFieldNames.isEmpty()) && authenticationData.publicDataFieldMap.isEmpty())
                || (not(i_conf_authentication.mandatoryPrivateFieldNames.isEmpty()) && authenticationData.privateDataFieldMap.isEmpty())) {
            return true
        }
        for (l_field_name in i_conf_authentication.mandatoryPublicFieldNames) {
            if (not(authenticationData.publicDataFieldMap.containsKey(l_field_name))
                    || is_null(authenticationData.publicDataFieldMap.get(l_field_name))) {
                return true
            }
        }
        for (l_field_name in i_conf_authentication.mandatoryPrivateFieldNames) {
            if (not(authenticationData.privateDataFieldMap.containsKey(l_field_name))
                    || is_null(authenticationData.privateDataFieldMap.get(l_field_name))) {
                return true
            }
        }
        return false
    }

    String hash_code(T_auth_grant_base_5_context i_context) {
        String l_unhashed_authentication_string = GC_EMPTY_STRING
        LinkedList<String> l_sorted_field_names = authenticationData.publicDataFieldMap.keySet().sort()
        l_sorted_field_names.each {
            l_unhashed_authentication_string += authenticationData.publicDataFieldMap.get(it)
        }//todo: add keys and delimiters
        MessageDigest l_message_digest = MessageDigest.getInstance(i_context.p_app_conf.authentication_hash_type)
        byte[] l_authentication_hash_bytes = l_message_digest.digest(l_unhashed_authentication_string.getBytes(StandardCharsets.UTF_8))
        String l_authentication_hash_base64_encoded = new BASE64Encoder().encode(l_authentication_hash_bytes)
        return l_authentication_hash_base64_encoded
    }

    FailedAuthenticationAttempt create_authentication_attempt(AuthenticationType i_conf_authentication, T_auth_grant_base_5_context i_context) {
        Date l_current_date = new Date()
        FailedAuthenticationAttempt l_new_authentication_attempt = new FailedAuthenticationAttempt(
                authenticationName: authenticationName,
                status: GC_STATUS_NEW,
                authenticationHash: hash_code(i_context),
                attemptDate: l_current_date
        )
        if (i_conf_authentication.lockoutMaxAttemptCount == GC_AUTHENTICATION_LOCKOUT_COUNT_NEVER) {
            return l_new_authentication_attempt
        }
        Date l_start_search_date = l_current_date
        use(TimeCategory) {
            l_start_search_date = l_current_date - i_conf_authentication.lockoutDurationSeconds.seconds
        }
        Set<FailedAuthenticationAttempt> l_search_attempts = i_context.p_authentication_attempt_repository.find_attempt(authenticationName, hash_code(i_context), l_start_search_date)
        if (l_search_attempts.isEmpty()) {
            return l_new_authentication_attempt
        }
        FailedAuthenticationAttempt l_previous_attempt = l_search_attempts.first()
        if (l_previous_attempt.currentAttemptCount >= i_conf_authentication.lockoutMaxAttemptCount) {
            //lockout
            use(TimeCategory) {
                if (l_current_date < l_previous_attempt.attemptDate + i_conf_authentication.lockoutDurationSeconds.seconds) {
                    //still locked
                    l_new_authentication_attempt.status = GC_STATUS_FAILED
                }
            }
        }
        //not locked out or already unlocked
        l_new_authentication_attempt.previousAttemptCount = l_previous_attempt.currentAttemptCount
        return l_new_authentication_attempt
    }

}