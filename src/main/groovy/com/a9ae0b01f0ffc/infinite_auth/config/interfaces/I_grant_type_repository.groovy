package com.a9ae0b01f0ffc.infinite_auth.config.interfaces

import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.GrantType
import groovy.transform.CompileStatic
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@CompileStatic
@RepositoryRestResource
interface I_grant_type_repository extends PagingAndSortingRepository<GrantType, Long> {

    Set<GrantType> findByRestResourceName(@Param("restResourceName") String restResourceName)

    Set<GrantType> findByRestResourceNameAndKeyFieldRuleName(@Param("restResourceName") String restResourceName, @Param("keyFieldRuleName") String keyFieldRuleName)

    Set<GrantType> findByRestResourceNameAndKeyFieldRuleNameAndMaxUsageCountWithinScope(@Param("restResourceName") String restResourceName
            , @Param("keyFieldRuleName") String keyFieldRuleName
            , @Param("maxUsageCountWithinScope") Integer maxUsageCountWithinScope
    )

}
