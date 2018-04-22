package com.a9ae0b01f0ffc.infinite_auth.config.interfaces

import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.AuthorizationType
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@CompileStatic
@RepositoryRestResource
interface I_authorization_type_repository extends JpaRepository<AuthorizationType, Long> {

    Set<AuthorizationType> findByAuthorizationName(@Param("authorizationName") String authorizationName)

    @Query("""select a from AuthorizationType a
        join a.scopeSet scopeSet
        where scopeSet.scopeName = :scopeName""")
    Set<AuthorizationType> match_authorizations(
            @Param("scopeName") String scopeName
    )


}
