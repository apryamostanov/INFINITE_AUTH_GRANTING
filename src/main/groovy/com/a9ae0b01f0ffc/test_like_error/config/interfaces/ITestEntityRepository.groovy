package com.a9ae0b01f0ffc.test_like_error.config.interfaces

import com.a9ae0b01f0ffc.test_like_error.config.domain_model.TestEntity
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@CompileStatic
@RepositoryRestResource
interface ITestEntityRepository extends PagingAndSortingRepository<TestEntity, Long> {

    @Query("""select a from TestEntity a where
         :appName like a.appName
     and :platform like a.platform
     and :appVersion like a.appVersion
     and :fiid like a.fiid
     and nvl(:product, 'Any') like a.product
     and :productGroup like a.productGroup
     and :apiVersionName like a.apiVersionName
     and :grantingEndpointName like a.grantingEndpointName
     order by a.lookupPriority desc""")
    Set<TestEntity> find_entities(
            @Param("appName") String appName
            , @Param("platform") String platform
            , @Param("appVersion") String appVersion
            , @Param("fiid") String fiid
            , @Param("product") String product
            , @Param("productGroup") String productGroup
            , @Param("apiVersionName") String apiVersionName
            , @Param("grantingEndpointName") String endpointName
    )

}