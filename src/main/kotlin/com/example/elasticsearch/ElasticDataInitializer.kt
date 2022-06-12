package com.example.elasticsearch

import com.example.elasticsearch.service.ElasticIndexService
import com.example.elasticsearch.data.DataGenerator
import com.example.elasticsearch.service.LogDataService
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class ElasticDataInitializer(
    private val logDataService: LogDataService,
    private val dataGenerator: DataGenerator,
    private val elasticIndexService: ElasticIndexService
) : ApplicationListener<ApplicationReadyEvent> {

    override fun onApplicationEvent(event: ApplicationReadyEvent) {

        val aliasName = "logdataindex"
        if (elasticIndexService.findIndex(aliasName).isEmpty()) {
            val indexName = elasticIndexService.createIndex(aliasName)
            elasticIndexService.createAlias(aliasName, indexName)
            logDataService.saveAllLogData(dataGenerator.randomLogDataList())
        }

    }
}