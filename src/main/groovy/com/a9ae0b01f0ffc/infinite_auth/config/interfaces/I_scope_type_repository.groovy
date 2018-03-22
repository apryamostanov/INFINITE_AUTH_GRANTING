package com.a9ae0b01f0ffc.infinite_auth.config.interfaces

import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.AccessorType
import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.AuthorizationType
import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.ScopeType
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@CompileStatic
@RepositoryRestResource
interface I_scope_type_repository extends JpaRepository<ScopeType, Long> {

    Set<ScopeType> findByScopeName(@Param("scopeName") String scopeName)

    Set<ScopeType> findByScopeNameAndAccessor(
            @Param("scopeName") String scopeName, @Param("accessor") AccessorType accessor)


}
