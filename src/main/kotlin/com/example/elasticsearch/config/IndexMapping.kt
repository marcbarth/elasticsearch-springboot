package com.example.elasticsearch.config


import org.elasticsearch.xcontent.XContentBuilder
import org.elasticsearch.xcontent.XContentFactory

object IndexMapping {
    val logDataMapping: XContentBuilder = XContentFactory.jsonBuilder()
        .startObject()
            .startObject("settings")
                .startObject("index")
                    .startObject("analysis")
                        .startObject("filter")
                            .startObject("snowball_german_umlaut")
                                .field("type", "snowball")
                                .field("name", "German2")
                            .endObject()
                            .startObject("my_daitch_mokotoff")
                                .field("type", "phonetic")
                                .field("name", "daitch_mokotoff")
                                .field("replace", false)
                            .endObject()
                        .endObject()
                        .startObject("analyzer")
                            .startObject("description_search")
                                .field("tokenizer", "standard")
                                .array("filter", "lowercase","snowball_german_umlaut","my_daitch_mokotoff","asciifolding")
                            .endObject()
                        .endObject()
                    .endObject()
                .endObject()
            .endObject()
            .startObject("mappings")
                .startObject("properties")
                    .startObject("description")
                        .field("type", "text")
                        .field("analyzer", "description_search")
                    .endObject()
                .endObject()
            .endObject()
        .endObject()
}