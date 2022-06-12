package com.example.elasticsearch.controller

import com.example.elasticsearch.service.ElasticIndexService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/index")
class IndexController(private val elasticIndexService: ElasticIndexService) {

    @GetMapping("/rebuildIndex")
    fun rebuildIndex( @RequestParam("alias") alias: String) {
        elasticIndexService.rebuildIndex(alias)
    }


    @GetMapping("/delete")
    fun deleteIndex(@RequestParam("alias") alias: String) = elasticIndexService.deleteAliasAndIndex(alias)

}