package com.example.elasticsearch

import com.example.elasticsearch.model.LogData
import com.example.elasticsearch.repositories.LogDataRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class LogDataServiceTest: AbstractContainerBaseTest() {

    @Autowired
    lateinit var  logDataRepository: LogDataRepository

    @Test
    fun simpleCreateAndFindTest() {
        val logData = LogData("123", "123456", Date(), "djokovic", 12.0, "active")
        logDataRepository.save(logData)
        val persistentData = logDataRepository.findById("123")
        Assertions.assertTrue(persistentData.isPresent)

    }

    @Test
    fun findfuzzyTest() {
        val logData = LogData("123", "123456", Date(), "djokovic", 12.0, "active")
        logDataRepository.save(logData)
        val persistentData = logDataRepository.findHostByFuzzyPageable(logData.host.take(5),1,1)
        Assertions.assertTrue(persistentData.isNotEmpty())
    }

    @Test
    fun findMultiTermTest() {
        val logData = LogData("123", "123456", Date(), "djokovic", 12.0, "active")
        logDataRepository.save(logData)
        val persistentData = logDataRepository.findHostAndDescriptionByMultiMatchQuery(" ${logData.host} ${logData.description}")
        Assertions.assertTrue(persistentData.isNotEmpty())
    }

    @Test
    fun findPhoneticTest() {
        val logData = LogData("123", "123456", Date(), "djokovic", 12.0, "active")
        logDataRepository.save(logData)
        val persistentData = logDataRepository.findByDescription("djokovitsch")
        Assertions.assertTrue(persistentData.isNotEmpty())
    }


}