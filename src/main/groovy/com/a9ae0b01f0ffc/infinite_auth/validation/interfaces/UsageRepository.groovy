package com.a9ae0b01f0ffc.infinite_auth.validation.interfaces

import com.a9ae0b01f0ffc.infinite_auth.validation.Usage
import groovy.transform.CompileStatic
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@CompileStatic
@RepositoryRestResource
interface UsageRepository extends PagingAndSortingRepository<Usage, Long> {

    Set<Usage> findByAuthorizationId(@Param("authorizationId") Long authorizationId)

    Set<Usage> findByAuthorizationIdAndRestResourceName(@Param("authorizationId") Long authorizationId, @Param("restResourceName") String restResourceName)

}