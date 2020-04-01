package com.example.topIMDBMovies.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.topIMDBMovies.document.Customer;
import com.example.topIMDBMovies.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class IndexService {
	
	
	private RestHighLevelClient client;
	
	private ObjectMapper objectMapper;
	 
	@Autowired
	public IndexService(RestHighLevelClient client, ObjectMapper objectMapper) {
	    this.client = client;
	    this.objectMapper = objectMapper;
	}
	
	public String createMovieDocuments(Customer document) throws Exception {
		Map<String, Object> documentMapper = objectMapper.convertValue(document, Map.class);

        IndexRequest indexRequest = new IndexRequest(Constants.INDEX)
                .source(documentMapper);

        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);

        return indexResponse
                .getResult()
                .name();
    }

}
