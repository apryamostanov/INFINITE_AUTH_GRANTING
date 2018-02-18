package com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces

import com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model.AccessorType
import com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model.AuthorizationType
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
        join a.accessor authorizationAccessor
        join scopeSet.accessor scopeAccessor
        where scopeSet.scopeName = nvl(:scopeName, 'Any')
        and (identitySet.identityName = :identityName or :identityName is null)
        and a.authorizationType = 'Access'
        
        and nvl(:appName, 'Any') like authorizationAccessor.appName 
        and nvl(:platform, 'Any') like authorizationAccessor.platform 
        and nvl(:appVersion, 'Any') like authorizationAccessor.appVersion 
        and nvl(:fiid, 'Any') like authorizationAccessor.fiid 
        and nvl(:product, 'Any') like authorizationAccessor.product 
        and nvl(:productGroup, 'Any') like authorizationAccessor.productGroup 
        and nvl(:apiVersionName, 'Any') like authorizationAccessor.apiVersionName 
        and nvl(:grantingEndpointName, 'Any') like authorizationAccessor.grantingEndpointName 
        
        and nvl(:appName, 'Any') like scopeAccessor.appName 
        and nvl(:platform, 'Any') like scopeAccessor.platform 
        and nvl(:appVersion, 'Any') like scopeAccessor.appVersion 
        and nvl(:fiid, 'Any') like scopeAccessor.fiid 
        and nvl(:product, 'Any') like scopeAccessor.product 
        and nvl(:productGroup, 'Any') like scopeAccessor.productGroup 
        and nvl(:apiVersionName, 'Any') like scopeAccessor.apiVersionName 
        and nvl(:grantingEndpointName, 'Any') like scopeAccessor.grantingEndpointName 
        
        and (scopeAccessor.appName like authorizationAccessor.appName  or scopeAccessor.appName = authorizationAccessor.appName)
        and (scopeAccessor.platform like authorizationAccessor.platform  or scopeAccessor.platform = authorizationAccessor.platform)
        and (scopeAccessor.appVersion like authorizationAccessor.appVersion  or scopeAccessor.appVersion = authorizationAccessor.appVersion)
        and (scopeAccessor.fiid like authorizationAccessor.fiid  or scopeAccessor.fiid = authorizationAccessor.fiid)
        and (scopeAccessor.product like authorizationAccessor.product  or scopeAccessor.product = authorizationAccessor.product)
        and (scopeAccessor.productGroup like authorizationAccessor.productGroup  or scopeAccessor.productGroup = authorizationAccessor.productGroup)
        and (scopeAccessor.apiVersionName like authorizationAccessor.apiVersionName  or scopeAccessor.apiVersionName = authorizationAccessor.apiVersionName)
        and (scopeAccessor.grantingEndpointName like authorizationAccessor.grantingEndpointName  or scopeAccessor.grantingEndpointName = authorizationAccessor.grantingEndpointName)
        
        order by authorizationAccessor.lookupPriority desc, scopeAccessor.lookupPriority desc""")
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
            , @Param("grantingEndpointName") String endpointName
    )

}
