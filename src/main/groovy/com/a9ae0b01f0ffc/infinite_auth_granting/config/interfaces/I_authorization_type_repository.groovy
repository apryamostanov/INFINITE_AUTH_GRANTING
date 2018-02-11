package com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces

import com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model.AccessorType
import com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model.AuthorizationType
import com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model.ScopeType
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@CompileStatic
@RepositoryRestResource
interface I_authorization_type_repository extends PagingAndSortingRepository<AuthorizationType, Long> {


    Set<AuthorizationType> findByAuthorizationName(@Param("authorizationName") String authorizationName)

    Set<AuthorizationType> findByAuthorizationNameAndAccessor(
            @Param("authorizationName") String authorizationName, @Param("accessor") AccessorType accessor)


    @Query("""select a from AuthorizationType a
        join a.identitySet identitySet
        join a.scopeSet scopeSet
        join a.accessor accessor
        join scopeSet.accessor scopeAccessor
        where scopeSet.scopeName = :scopeName
        and (identitySet.identityName = :identityName or :identityName is null)
        and a.authorizationType = 'Access'
        
        and :appName like accessor.appName
        and :platform like accessor.platform
        and :appVersion like accessor.appVersion
        and :fiid like accessor.fiid
        and nvl(:product, 'Any') like accessor.product
        and :productGroup like accessor.productGroup
        and :apiVersionName like accessor.apiVersionName
        and :endpointName like accessor.endpointName
        
        and :appName like scopeAccessor.appName
        and :platform like scopeAccessor.platform
        and :appVersion like scopeAccessor.appVersion
        and :fiid like scopeAccessor.fiid
        and nvl(:product, 'Any') like scopeAccessor.product
        and :productGroup like scopeAccessor.productGroup
        and :apiVersionName like scopeAccessor.apiVersionName
        and :endpointName like scopeAccessor.endpointName
        
        order by accessor.lookupPriority desc, scopeAccessor.lookupPriority desc""")
    Set<AuthorizationType> matchAuthorizations(
            @Param("scopeName") String scopeName
            , @Param("identityName") String identityName
            //
            , @Param("appName") String appName
            , @Param("platform") String platform
            , @Param("appVersion") String appVersion
            , @Param("fiid") String fiid
            , @Param("product") String product
            , @Param("productGroup") String productGroup
            , @Param("apiVersionName") String apiVersionName
            , @Param("endpointName") String endpointName
    )

}
