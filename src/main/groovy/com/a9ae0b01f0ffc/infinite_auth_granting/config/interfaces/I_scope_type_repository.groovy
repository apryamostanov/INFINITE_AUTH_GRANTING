package com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces

import com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model.AccessorType
import com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model.ScopeType
import groovy.transform.CompileStatic
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@CompileStatic
@RepositoryRestResource
interface I_scope_type_repository extends PagingAndSortingRepository<ScopeType, Long> {

    Set<ScopeType> findByScopeName(@Param("scopeName") String scopeName)

    /*@Query("""select s from ScopeType s where
        s.scopeName = :scopeName and ((:accessor is not null and s.accessor = :accessor) or (:accessor is null and s.accessor is null))""")*/

    Set<ScopeType> findByScopeNameAndAccessor(
            @Param("scopeName") String scopeName, @Param("accessor") AccessorType accessor)


}
