package com.example.elasticsearch.service

import com.example.elasticsearch.config.IndexMapping
import com.example.elasticsearch.data.DataGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.elasticsearch.ElasticsearchStatusException
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.CreateIndexRequest
import org.elasticsearch.client.indices.GetIndexRequest
import org.elasticsearch.xcontent.XContentType
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.system.measureTimeMillis


@Component
class ElasticIndexService(
    private val restHighLevelClient: RestHighLevelClient,
    private val dataGenerator: DataGenerator
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    fun findIndex(indexName: String): List<String> {

        return try {
            restHighLevelClient
                .indices()
                .get(GetIndexRequest("$indexName*"), RequestOptions.DEFAULT).indices.toList()
        } catch (exception: ElasticsearchStatusException) {
            emptyList()
        }

    }


    fun createIndex(indexSuffix: String): String {

        val indexName = indexSuffix + "_" + DateTimeFormatter
            .ofPattern("yyyy-MM-dd-HH-mm-ss")
            .withZone(ZoneId.systemDefault())
            .format(Instant.now())
            .toString()
            .lowercase()

        restHighLevelClient.indices()
            .create(CreateIndexRequest(indexName).source(IndexMapping.logDataMapping), RequestOptions.DEFAULT)

        logger.info("Created index {}", indexName)
        return indexName
    }

    fun createAlias(aliasName: String, indexName: String) {

        restHighLevelClient.indices().updateAliases(
            IndicesAliasesRequest().addAliasAction(
                IndicesAliasesRequest.AliasActions.add().alias(aliasName).index(indexName)
            ), RequestOptions.DEFAULT
        )
        logger.info("Created alias {} on {} ", aliasName, indexName)

    }

    fun deleteAliasAndIndex(aliasName: String) {

        val indicesAliasesRequest = IndicesAliasesRequest()
        val previousIndices = findIndex(aliasName)
        val deleteAliasActions =
            previousIndices.map { IndicesAliasesRequest.AliasActions.remove().index(it).alias(aliasName) }
        deleteAliasActions.forEach { indicesAliasesRequest.addAliasAction(it) }
        val delta1 = measureTimeMillis {
            restHighLevelClient.indices().updateAliases(indicesAliasesRequest, RequestOptions.DEFAULT)
        }
        logger.info("Delete alias and index took {}ms", delta1)
        previousIndices.forEach { restHighLevelClient.indices().delete(DeleteIndexRequest(it), RequestOptions.DEFAULT) }

    }

    fun rebuildIndex(aliasName: String) {
        val previousIndices = findIndex(aliasName)
        val indexName = createIndex(aliasName)
        val objectMapper = ObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        val bulkRequest = BulkRequest().add(
            dataGenerator.randomLogDataList()
                .map { logData ->
                    IndexRequest()
                        .index(indexName).id(logData.id)
                        .source(objectMapper.writeValueAsString(logData), XContentType.JSON)
                }
        )

        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT)
        switchAndRemoveAliasAndIndex(indexName, aliasName, previousIndices)

    }

    private fun switchAndRemoveAliasAndIndex(indexName: String, aliasName: String, previousIndices: List<String>) {
        val indicesAliasesRequest = IndicesAliasesRequest()
        val actions = previousIndices.map { IndicesAliasesRequest.AliasActions.remove().index(it).alias(aliasName) }
        actions.forEach { indicesAliasesRequest.addAliasAction(it) }
        indicesAliasesRequest.addAliasAction(IndicesAliasesRequest.AliasActions.add().alias(aliasName).index(indexName))
        val delta1 = measureTimeMillis {
            restHighLevelClient.indices().updateAliases(indicesAliasesRequest, RequestOptions.DEFAULT)
            previousIndices.forEach {
                restHighLevelClient.indices().delete(DeleteIndexRequest(it), RequestOptions.DEFAULT)
            }
        }
        logger.info("Switching indices took {}ms", delta1)
    }

}

