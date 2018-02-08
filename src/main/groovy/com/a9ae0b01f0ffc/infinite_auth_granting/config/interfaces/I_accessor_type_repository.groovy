package com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces

import com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model.AccessorType
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@CompileStatic
@RepositoryRestResource
interface I_accessor_type_repository extends PagingAndSortingRepository<AccessorType, Long> {

    Set<AccessorType> findByAccessorName(@Param("accessorName") String accessorName)

    @Query("""select a from AccessorType a where
         :appName like a.appName
     and :platform like a.platform
     and :appVersion like a.appVersion
     and :fiid like a.fiid
     and nvl(:product, 'Any') like a.product
     and :productGroup like a.productGroup
     and :apiVersionName like a.apiVersionName
     and :endpointName like a.endpointName
     order by a.lookupPriority desc""")
    Set<AccessorType> matchAccessors(
            @Param("appName") String appName
            , @Param("platform") String platform
            , @Param("appVersion") String appVersion
            , @Param("fiid") String fiid
            , @Param("product") String product
            , @Param("productGroup") String productGroup
            , @Param("apiVersionName") String apiVersionName
            , @Param("endpointName") String endpointName
    )

}