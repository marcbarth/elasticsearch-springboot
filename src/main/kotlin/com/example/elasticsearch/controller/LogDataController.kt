package com.example.elasticsearch.controller

import com.example.elasticsearch.model.LogData
import com.example.elasticsearch.service.LogDataService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/logdata")
class LogDataController(private val logDataService: LogDataService) {

    //find Operations
    @GetMapping
    fun listAll(): List<LogData?> {
        return logDataService.findAll()
    }

    @GetMapping("/simplesearch")
    fun searchLogDataByHost(@RequestParam("description") description: String?): List<LogData?> {
        return logDataService.findByDescription(description)
    }

    @GetMapping("/multisearch")
    fun searchLogDataByMultiTerm(@RequestParam("term") term: String): List<LogData> {
        return logDataService.findBySearchMultiTerm(term)
    }

    @GetMapping("/fuzzysearch")
    fun searchLogDataByFuzzyTerm(@RequestParam("term") term: String): List<LogData> {
        return logDataService.findBySearchFuzzyTerm(term)
    }

    //save Operations
    @PostMapping
    fun addLogData(@RequestBody logData: LogData): LogData {
        return logDataService.saveLogData(logData)
    }

    @PostMapping("/createInBulk")
    fun addLogDataInBulk(@RequestBody logDataList: List<LogData>): List<LogData> {
        return logDataService.saveAllLogData(logDataList) as List<LogData>
    }


}