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

    @Query("""select a from TestEntity a where :testLikeProperty like a.testLikeProperty order by a.id desc""")
    Set<TestEntity> find_by_like_column(
            @Param("testLikeProperty") String testLikeProperty
    )

}