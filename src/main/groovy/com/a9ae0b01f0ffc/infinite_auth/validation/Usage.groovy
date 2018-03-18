package com.a9ae0b01f0ffc.infinite_auth.validation

import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@CompileStatic
@Entity
@Table(name="AuthorizationRevocation")
class Usage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long authorizationId

    Date usageDate

    String restResourceName

}
