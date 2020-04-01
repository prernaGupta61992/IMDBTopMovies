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
	
	public Boolean createIndex() throws IOException {
		CreateIndexRequest request = new CreateIndexRequest("movie");
		request.settings(Settings.builder()
		        .put("index.number_of_shards", 1)
		        .put("index.number_of_replicas", 2)
		);
//		request.mapping(
//		        "{\n" +
//		        "  \"properties\": {\n" +
//		        "    \"message\": {\n" +
//		        "      \"type\": \"text\"\n" +
//		        "    }\n" +
//		        "  }\n" +
//		        "}", 
//		        XContentType.JSON);
		CreateIndexResponse indexResponse = client.indices().create(request, RequestOptions.DEFAULT);
		System.out.println("response id: "+indexResponse.index());
		return indexResponse.isAcknowledged();
	}
	
	
	public String createProfileDocument(Customer document) throws Exception {
		System.out.println("document value is"+document.toString());
        Map<String, Object> documentMapper = objectMapper.convertValue(document, Map.class);

        IndexRequest indexRequest = new IndexRequest("customer")
                .source(documentMapper);

        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);

        return indexResponse
                .getResult()
                .name();
    }

}
