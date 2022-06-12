package com.example.elasticsearch.repositories

import com.example.elasticsearch.model.LogData

interface CustomLogDataRepository {
    fun findBySearchMultiTerm(term: String?): List<LogData>
    fun findByHostFuzzyPageable(term: String, page:Int, size: Int): List<LogData>
}
