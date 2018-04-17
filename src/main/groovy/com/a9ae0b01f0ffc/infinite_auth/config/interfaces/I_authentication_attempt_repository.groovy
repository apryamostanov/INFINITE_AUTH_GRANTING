package com.a9ae0b01f0ffc.infinite_auth.config.interfaces

import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.AuthenticationAttempt
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import static com.a9ae0b01f0ffc.infinite_auth.base.T_auth_grant_base_4_const.*

@CompileStatic
@RepositoryRestResource
interface I_authentication_attempt_repository extends JpaRepository<AuthenticationAttempt, Long> {

    @Query("""select a from AuthenticationAttempt a where
         a.authenticationName = :authenticationName
     and a.authenticationHash =:authenticationHash
     and a.attemptDate >= :startSearchDate
     order by a.id desc""")
    Set<AuthenticationAttempt> find_attempt(
            @Param("authenticationName") String authenticationName,
            @Param("authenticationHash") String authenticationHash, @Param("startSearchDate") Date startSearchDate)

}
