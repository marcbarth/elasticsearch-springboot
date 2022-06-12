package com.example.elasticsearch.data

import com.example.elasticsearch.model.LogData
import org.springframework.stereotype.Component
import java.util.*

@Component
class DataGenerator {

   fun randomLogDataList(): List<LogData> {
       fun randomDigits() = Random().nextInt(1000, 9999).toString()
       fun randomStatus() = listOf("active", "inactive").asSequence().shuffled().first()
       fun randomLogData() = LogData(
           id = UUID.randomUUID().toString(),
           host = "xa" + randomDigits(),
           date = Date(),
           description = "Beste Grüsse, V. Petković",
           size = 1.2,
           status = randomStatus()
       )
       return (1 until 10).map { randomLogData() }
    }

}
