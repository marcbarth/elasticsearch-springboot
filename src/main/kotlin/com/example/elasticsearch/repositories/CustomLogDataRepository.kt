package com.example.elasticsearch.repositories

import com.example.elasticsearch.model.LogData

interface CustomLogDataRepository {
    fun findHostAndDescriptionByMultiMatchQuery(term: String?): List<LogData?>
    fun findHostByFuzzyPageable(term: String, page:Int, size: Int): List<LogData?>
}
