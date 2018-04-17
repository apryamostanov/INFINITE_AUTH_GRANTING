package com.a9ae0b01f0ffc.infinite_auth.config.domain_model

import groovy.transform.CompileStatic

import javax.persistence.*

import static base.T_common_base_1_const.GC_ONE_ONLY
import static base.T_common_base_1_const.GC_ZERO
import static com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_base_4_const.GC_STATUS_FAILED
import static com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_base_4_const.GC_STATUS_SUCCESSFUL

@CompileStatic
@Entity
@Table(name = "AuthenticationAttempts")
class AuthenticationAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    String authenticationName

    String authenticationHash

    Date attemptDate

    Date previousAttemptDate

    Integer previousAttemptCount

    Integer currentAttemptCount

    String status

}
