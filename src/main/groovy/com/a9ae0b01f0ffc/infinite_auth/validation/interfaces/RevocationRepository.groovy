package com.a9ae0b01f0ffc.infinite_auth.validation.interfaces

import com.a9ae0b01f0ffc.infinite_auth.validation.Revocation
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@CompileStatic
@RepositoryRestResource
interface RevocationRepository extends JpaRepository<Revocation, Long> {

    Set<Revocation> findByAuthorizationId(@Param("authorizationId") Long authorizationId)

}
