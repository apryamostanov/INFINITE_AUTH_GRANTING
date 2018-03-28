package com.a9ae0b01f0ffc.infinite_auth.validation

import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.GrantType
import com.a9ae0b01f0ffc.infinite_auth.granting.Grant
import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OrderColumn
import javax.persistence.Table

@CompileStatic
@Entity
@Table(name="AuthorizationUsage")
class Usage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long authorizationUsageId

    @OrderColumn(nullable = false)
    Long authorizationId

    Date usageDate

    @ManyToOne(fetch = FetchType.EAGER)
    @OrderColumn
    GrantType usedGrantType

}
