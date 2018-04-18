package com.a9ae0b01f0ffc.infinite_auth.config.interfaces

import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.AccessorType
import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.AuthorizationType
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@CompileStatic
@RepositoryRestResource
interface I_authorization_type_repository extends JpaRepository<AuthorizationType, Long> {


    Set<AuthorizationType> findByAuthorizationName(@Param("authorizationName") String authorizationName)

    Set<AuthorizationType> findByAuthorizationNameAndAccessor(
            @Param("authorizationName") String authorizationName, @Param("accessor") AccessorType accessor)

    @Query("""select a, scopeSet, identitySet, authenticationAccessor from AuthorizationType a
        join a.identitySet identitySet
        join identitySet.authenticationSet authenticationSet
        join a.scopeSet scopeSet
        join a.accessor authorizationAccessor
        join scopeSet.accessor scopeAccessor
        join identitySet.accessor identityAccessor
        join authenticationSet.accessor authenticationAccessor
        
        where scopeSet.scopeName = coalesce(:scopeName, 'Any')
        and (identitySet.identityName = :identityName or :identityName is null)
        and a.authorizationType = 'Access'
        
        and coalesce(:appName, 'Any') like authorizationAccessor.appName 
        and coalesce(:platform, 'Any') like authorizationAccessor.platform 
        and coalesce(:appVersion, 'Any') like authorizationAccessor.appVersion 
        and coalesce(:fiid, 'Any') like authorizationAccessor.fiid 
        and coalesce(:product, 'Any') like authorizationAccessor.product 
        and coalesce(:productGroup, 'Any') like authorizationAccessor.productGroup 
        and coalesce(:apiVersionName, 'Any') like authorizationAccessor.apiVersionName 
        and coalesce(:grantingEndpointName, 'Any') like authorizationAccessor.grantingEndpointName
        
        and coalesce(:appName, 'Any') like scopeAccessor.appName 
        and coalesce(:platform, 'Any') like scopeAccessor.platform 
        and coalesce(:appVersion, 'Any') like scopeAccessor.appVersion 
        and coalesce(:fiid, 'Any') like scopeAccessor.fiid 
        and coalesce(:product, 'Any') like scopeAccessor.product 
        and coalesce(:productGroup, 'Any') like scopeAccessor.productGroup 
        and coalesce(:apiVersionName, 'Any') like scopeAccessor.apiVersionName 
        and coalesce(:grantingEndpointName, 'Any') like scopeAccessor.grantingEndpointName
        
        and coalesce(:appName, 'Any') like identityAccessor.appName
        and coalesce(:platform, 'Any') like identityAccessor.platform
        and coalesce(:appVersion, 'Any') like identityAccessor.appVersion
        and coalesce(:fiid, 'Any') like identityAccessor.fiid
        and coalesce(:product, 'Any') like identityAccessor.product
        and coalesce(:productGroup, 'Any') like identityAccessor.productGroup
        and coalesce(:apiVersionName, 'Any') like identityAccessor.apiVersionName
        and coalesce(:grantingEndpointName, 'Any') like identityAccessor.grantingEndpointName
        
        and coalesce(:appName, 'Any') like authenticationAccessor.appName
        and coalesce(:platform, 'Any') like authenticationAccessor.platform
        and coalesce(:appVersion, 'Any') like authenticationAccessor.appVersion
        and coalesce(:fiid, 'Any') like authenticationAccessor.fiid
        and coalesce(:product, 'Any') like authenticationAccessor.product
        and coalesce(:productGroup, 'Any') like authenticationAccessor.productGroup
        and coalesce(:apiVersionName, 'Any') like authenticationAccessor.apiVersionName
        and coalesce(:grantingEndpointName, 'Any') like authenticationAccessor.grantingEndpointName
        
        order by authorizationAccessor.lookupPriority desc, scopeAccessor.lookupPriority desc, identityAccessor.lookupPriority desc, authenticationAccessor.lookupPriority desc""")
    Set<Object[]> match_authorizations(
            @Param("scopeName") String scopeName
            , @Param("identityName") String identityName

            , @Param("appName") String appName
            , @Param("platform") String platform
            , @Param("appVersion") String appVersion
            , @Param("fiid") String fiid
            , @Param("product") String product
            , @Param("productGroup") String productGroup
            , @Param("apiVersionName") String apiVersionName
            , @Param("grantingEndpointName") String endpointName
    )


}
