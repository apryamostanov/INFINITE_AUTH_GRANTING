package com.a9ae0b01f0ffc.infinite_auth.config.interfaces

import com.a9ae0b01f0ffc.infinite_auth.config.domain_model.AccessorType
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@CompileStatic
@RepositoryRestResource
interface I_accessor_type_repository extends JpaRepository<AccessorType, Long> {

    //Known H2 bug: https://github.com/h2database/h2database/issues/882
    @Query("""select a from AccessorType a where
         :appName like a.appName
     and :platform like a.platform
     and :appVersion like a.appVersion
     and :fiid like a.fiid
     and coalesce(:product, 'Any') like a.product
     and coalesce(:productGroup, 'Any') like a.productGroup
     and :apiVersionName like a.apiVersionName
     and :grantingEndpointName like a.grantingEndpointName
     and :accessorRole = a.accessorRole
     order by a.lookupPriority desc""")
    LinkedHashSet<AccessorType> match_accessors(
            @Param("appName") String appName
            , @Param("platform") String platform
            , @Param("appVersion") String appVersion
            , @Param("fiid") String fiid
            , @Param("product") String product
            , @Param("productGroup") String productGroup
            , @Param("apiVersionName") String apiVersionName
            , @Param("grantingEndpointName") String endpointName
            , @Param("accessorRole") String accessorRole
    )

    @Query("""select a from AccessorType a where :accessorRole = a.accessorRole and a.accessorName = :accessorName""")
    Set<AccessorType> find_accessor_by_name(@Param("accessorName") String accessorName, @Param("accessorRole") String accessorRole)

}