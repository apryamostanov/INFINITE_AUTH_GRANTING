package com.a9ae0b01f0ffc.infinite_auth.config.interfaces

import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.FailedAuthenticationAttempt
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@CompileStatic
@RepositoryRestResource
interface I_failed_authentication_attempt_repository extends JpaRepository<FailedAuthenticationAttempt, Long> {

    @Query("""select a from FailedAuthenticationAttempt a where
         a.authenticationName = :authenticationName
     and a.authenticationHash =:authenticationHash
     and a.attemptDate >= :startSearchDate
     order by a.id desc""")
    Set<FailedAuthenticationAttempt> find_attempt(
            @Param("authenticationName") String authenticationName,
            @Param("authenticationHash") String authenticationHash, @Param("startSearchDate") Date startSearchDate)

}
