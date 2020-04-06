package com.imdbmovies.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdbmovies.document.MovieDocument;
import com.imdbmovies.request.SearchRequestParams;
import com.imdbmovies.utils.Constants;

@Service
public class SearchService {
	

	private RestHighLevelClient client;
	
	private ObjectMapper objectMapper;
	
	@Autowired
	public SearchService(RestHighLevelClient client, ObjectMapper objectMapper) {
	    this.client = client;
	    this.objectMapper = objectMapper;
	}
	
	
	public List<MovieDocument> fetchDocuments(SearchRequestParams params) throws IOException {
		SearchRequest searchRequest = new SearchRequest(Constants.INDEX);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.sort(new FieldSortBuilder(params.getSortBy()).order(SortOrder.DESC));
		searchSourceBuilder.from(0);
		searchSourceBuilder.size(params.getSize());
		searchRequest.source(searchSourceBuilder);
		
		SearchResponse searchResponse =
                client.search(searchRequest, RequestOptions.DEFAULT);
		
		return getSerachResults(searchResponse);
	}
	
	private List<MovieDocument> getSerachResults(SearchResponse searchResponse) {
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		List<MovieDocument> movieDocuments = new ArrayList<MovieDocument>();
		for(SearchHit hit : searchHits) {
			movieDocuments.add(objectMapper.convertValue(hit.getSourceAsMap(), MovieDocument.class));
		}
		return movieDocuments;
	}
	
	

}
