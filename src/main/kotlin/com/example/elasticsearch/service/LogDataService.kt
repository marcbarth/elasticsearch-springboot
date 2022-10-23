package com.example.elasticsearch.service

import com.example.elasticsearch.model.LogData
import com.example.elasticsearch.repositories.LogDataRepository
import org.springframework.stereotype.Service

@Service
class LogDataService(private val logDataRepository: LogDataRepository) {

    fun findAll(): List<LogData?> {
        return logDataRepository.findAll().toList()
    }

    fun findByDescription(description: String): List<LogData?> {
        return logDataRepository.findByDescription(description)
    }

    fun findBySearchMultiTerm(term: String): List<LogData?> {
        return logDataRepository.findHostAndDescriptionByMultiMatchQuery(term)
    }

    fun findBySearchFuzzyTerm(term: String): List<LogData?> {
        return logDataRepository.findHostByFuzzyPageable(term,0,10)
    }

    fun saveLogData(logData: LogData): LogData {
        return logDataRepository.save(logData)
    }

    fun saveAllLogData(logDataList: List<LogData>): Iterable<LogData> {
        return logDataRepository.saveAll(logDataList)
    }

}