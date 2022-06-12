package com.example.elasticsearch.repositories;

import com.example.elasticsearch.model.LogData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface LogDataRepository extends ElasticsearchRepository<LogData, String>, CustomLogDataRepository {
    List<LogData> findByDescription(String description);
}
