package com.imdbmovies.service;

import java.io.FileReader;
import java.util.Map;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imdbmovies.adaptor.FloatTypeAdapter;
import com.imdbmovies.adaptor.IntTypeAdapter;
import com.imdbmovies.document.MovieDocument;
import com.imdbmovies.utils.Constants;

@Service
public class IndexService {
	
	
	private RestHighLevelClient client;
	
	private ObjectMapper objectMapper;
	
    Integer id= 1;
	 
	@Autowired
	public IndexService(RestHighLevelClient client, ObjectMapper objectMapper) {
	    this.client = client;
	    this.objectMapper = objectMapper;
	}
	
	public String createMovieDocuments(MovieDocument document) throws Exception {
		Map<String, Object> documentMapper = objectMapper.convertValue(document, Map.class);

        IndexRequest indexRequest = new IndexRequest(Constants.INDEX)
                .source(documentMapper);

        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);

        return indexResponse
                .getResult()
                .name();
    }
	
	public String createBulkMovieDocuments() throws Exception {
		Object movieListObj = new JSONParser().parse(new FileReader(Constants.FILE_NAME)); 
        JSONArray movieList = (JSONArray) movieListObj;       
        BulkRequest request = createBulkRequest(movieList);
        BulkResponse bulkresp=client.bulk(request, RequestOptions.DEFAULT);
        return bulkresp.status().toString();
    }
	
	
	private BulkRequest createBulkRequest(JSONArray movieList) {
		BulkRequest request = new BulkRequest();
		movieList.forEach( movie -> parseMovieListObject((JSONObject) movie, request,id++));
		return request;
	}
	
	private void parseMovieListObject(JSONObject movieObject, BulkRequest request, int id) {
		Gson gson = new GsonBuilder()
			    .registerTypeAdapter(int.class, new IntTypeAdapter())
			    .registerTypeAdapter(Integer.class, new IntTypeAdapter())
			    .registerTypeAdapter(Float.class, new FloatTypeAdapter())
			    .registerTypeAdapter(float.class, new FloatTypeAdapter())
			    .create();
		MovieDocument movie = gson.fromJson(movieObject.toJSONString(), MovieDocument.class);
        
        Map<String, Object> documentMapper = objectMapper.convertValue(movie, Map.class);

        IndexRequest indexRequest = new IndexRequest(Constants.INDEX).id(Integer.toString(id))
                .source(documentMapper);
        
        request.add(indexRequest);
	}

}
