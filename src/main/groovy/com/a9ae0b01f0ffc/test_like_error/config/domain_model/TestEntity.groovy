package com.a9ae0b01f0ffc.test_like_error.config.domain_model

import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@CompileStatic
@Entity
class TestEntity {
    
    @ElementCollection(fetch = FetchType.EAGER)
    Set<String> roleSet = new HashSet<String>()

    
    Integer lookupPriority

    
    String appName

    
    @Column(unique = true)
    String accessorName 


    String platform 


    String osName 


    String appVersion 


    String fiid 


    String product 


    String productGroup 


    Integer isForbidden


    String grantingEndpointName 


    String validationEndpointName 


    String resourceEndpointGroupName 


    String apiVersionName 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id


}
