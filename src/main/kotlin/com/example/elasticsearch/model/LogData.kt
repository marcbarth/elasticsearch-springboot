package com.example.elasticsearch.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.util.*

@Document(indexName = "logdataindex", createIndex = false)
data class LogData(
    @Id
    val id: String,

    @Field(type = FieldType.Text, name = "host")
    val host: String,

    @Field(type = FieldType.Date, format = [DateFormat.date_time], name = "date")
    val date: Date,

    @Field(type = FieldType.Text, name = "description")
    val description: String,

    @Field(type = FieldType.Double, name = "size")
    val size: Double,

    @Field(type = FieldType.Text, name = "status")
    val status: String
)