package com.a9ae0b01f0ffc.infinite_auth.config.domain_model

import groovy.transform.CompileStatic

import javax.persistence.*

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

    @Transient
    Integer previousAttemptCount

    Integer currentAttemptCount

    String status

}
