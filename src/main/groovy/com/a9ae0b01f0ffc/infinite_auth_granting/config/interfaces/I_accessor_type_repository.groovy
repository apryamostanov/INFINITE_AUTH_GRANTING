package com.a9ae0b01f0ffc.infinite_auth_granting.config.interfaces

import com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const
import com.a9ae0b01f0ffc.infinite_auth_granting.config.domain_model.AccessorType
import groovy.transform.CompileStatic
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

import static com.a9ae0b01f0ffc.infinite_auth_granting.base.T_auth_grant_base_4_const.*

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
     and :grantingEndpointName like a.grantingEndpointName
     order by a.lookupPriority desc""")
    Set<AccessorType> matchAccessors(
            @Param("appName") String appName
            , @Param("platform") String platform
            , @Param("appVersion") String appVersion
            , @Param("fiid") String fiid
            , @Param("product") String product
            , @Param("productGroup") String productGroup
            , @Param("apiVersionName") String apiVersionName
            , @Param("grantingEndpointName") String endpointName
    )

    @Query("""select a from AccessorType a where 'Scope control' member of a.roleSet and a.accessorName = :accessorName""")
    Set<AccessorType> find_scope_accessor_by_name(@Param("accessorName") String accessorName)

    @Query("""select a from AccessorType a where 'Authorization control' member of a.roleSet and a.accessorName = :accessorName""")
    Set<AccessorType> find_authorization_accessor_by_name(@Param("accessorName") String accessorName)

    @Query("""select a from AccessorType a where 'Access control' member of a.roleSet and a.accessorName = :accessorName""")
    Set<AccessorType> find_access_accessor_by_name(@Param("accessorName") String accessorName)

    @Query("""select a from AccessorType a where 'Routing control' member of a.roleSet and a.accessorName = :accessorName""")
    Set<AccessorType> find_routing_accessor_by_name(@Param("accessorName") String accessorName)

}