package com.example.elasticsearch.repositories

import com.example.elasticsearch.model.LogData
import org.elasticsearch.common.unit.Fuzziness
import org.elasticsearch.index.query.MultiMatchQueryBuilder
import org.elasticsearch.index.query.Operator
import org.elasticsearch.index.query.QueryBuilders.matchQuery
import org.elasticsearch.index.query.QueryBuilders.multiMatchQuery
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.stereotype.Repository

@Repository
internal class CustomLogDataRepositoryImpl(private val elasticsearchRestTemplate: ElasticsearchRestTemplate) :
    CustomLogDataRepository {

    override fun findBySearchMultiTerm(term: String?): List<LogData> {

        val searchQuery = NativeSearchQueryBuilder()
            .withQuery(
                multiMatchQuery(term)
                    .field("host")
                    .field("description")
                    .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
            )
            .build()

        return elasticsearchRestTemplate
            .search(searchQuery, LogData::class.java)
            .searchHits.map { it.content }
    }

    override fun findByHostFuzzyPageable(term: String, page:Int, size: Int): List<LogData> {

        val searchQuery = NativeSearchQueryBuilder()
            .withQuery(
                matchQuery("host", term)
                    .operator(Operator.AND)
                    .fuzziness(Fuzziness.ONE)
            )
            .withPageable(PageRequest.of(0, 10))
            .build()

        return elasticsearchRestTemplate
            .search(searchQuery, LogData::class.java)
            .searchHits.map { it.content }
    }

}
