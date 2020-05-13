package com.imdbmovies.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imdbmovies.adaptor.FloatTypeAdapter;
import com.imdbmovies.adaptor.IntTypeAdapter;
import com.imdbmovies.document.MovieDocument;
import com.imdbmovies.utils.Constants;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class IndexService {
	private final RestHighLevelClient client;
	private final ObjectMapper objectMapper;
	
    private static Integer id= 1;
	 
	@Autowired
	public IndexService(RestHighLevelClient client, ObjectMapper objectMapper) {
	    this.client = client;
	    this.objectMapper = objectMapper;
	}
	
	public String createMovieDocuments(MovieDocument document) throws Exception {
		Map<String, Object> documentMapper = objectMapper.convertValue(document, Map.class);
        IndexRequest indexRequest = new IndexRequest(Constants.INDEX)
                .source(documentMapper);
		return indexRequest.id();
    }
	
	public String createBulkMovieDocuments() throws Exception {
		Object movieListObj = new JSONParser().parse(getStringFromFile(Constants.FILE_NAME));
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
        IndexRequest indexRequest = new IndexRequest("test")
                .source(documentMapper);
        request.add(indexRequest);
	}

	/**
	 * Get JSON from file in a JSON tree format.
	 *
	 * @param fileName file which needs to be read into JSON.
	 * @return
	 */
	private String getStringFromFile(String fileName) throws IOException {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		InputStream in = classLoader.getResourceAsStream(fileName);
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = in.read(buffer)) != -1) {
			result.write(buffer, 0, length);
		}
		return result.toString(StandardCharsets.UTF_8.name());
	}
}
