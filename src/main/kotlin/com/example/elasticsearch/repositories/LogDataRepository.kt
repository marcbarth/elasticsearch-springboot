package com.example.elasticsearch.repositories

import com.example.elasticsearch.model.LogData
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface LogDataRepository : ElasticsearchRepository<LogData?, String?>, CustomLogDataRepository {
    fun findByDescription(description: String?): List<LogData?>
}