package com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces

import com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model.AuthenticationType
import groovy.transform.CompileStatic
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@CompileStatic
@RepositoryRestResource
interface I_authentication_type_repository extends PagingAndSortingRepository<AuthenticationType, Long> {

    Set<AuthenticationType> findByAuthenticationName(@Param("authenticationName") String authenticationName)

}
