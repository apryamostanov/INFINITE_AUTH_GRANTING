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

    @Query("""select a, scopeSet, identitySet from AuthorizationType a
        join a.identitySet identitySet
        join a.scopeSet scopeSet
        join a.accessor authorizationAccessor
        join scopeSet.accessor scopeAccessor
        
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
        
        and coalesce(:appName2, 'Any') like scopeAccessor.appName 
        and coalesce(:platform2, 'Any') like scopeAccessor.platform 
        and coalesce(:appVersion2, 'Any') like scopeAccessor.appVersion 
        and coalesce(:fiid2, 'Any') like scopeAccessor.fiid 
        and coalesce(:product2, 'Any') like scopeAccessor.product 
        and coalesce(:productGroup2, 'Any') like scopeAccessor.productGroup 
        and coalesce(:apiVersionName2, 'Any') like scopeAccessor.apiVersionName 
        and coalesce(:grantingEndpointName2, 'Any') like scopeAccessor.grantingEndpointName 
        
        order by authorizationAccessor.lookupPriority desc, scopeAccessor.lookupPriority desc""")
    Set<Object[]> match_authorizations(
            @Param("scopeName") String scopeName
            , @Param("identityName") String identityName
            //Authorization
            , @Param("appName") String appName
            , @Param("platform") String platform
            , @Param("appVersion") String appVersion
            , @Param("fiid") String fiid
            , @Param("product") String product
            , @Param("productGroup") String productGroup
            , @Param("apiVersionName") String apiVersionName
            , @Param("grantingEndpointName") String endpointName
            //Scope
            , @Param("appName2") String appName2
            , @Param("platform2") String platform2
            , @Param("appVersion2") String appVersion2
            , @Param("fiid2") String fiid2
            , @Param("product2") String product2
            , @Param("productGroup2") String productGroup2
            , @Param("apiVersionName2") String apiVersionName2
            , @Param("grantingEndpointName2") String endpointName2
            //
    )


}
