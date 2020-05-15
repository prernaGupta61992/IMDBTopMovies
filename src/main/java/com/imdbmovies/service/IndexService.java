package com.imdbmovies.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imdbmovies.adaptor.FloatTypeAdapter;
import com.imdbmovies.adaptor.IntTypeAdapter;
import com.imdbmovies.document.MovieDocument;
import com.imdbmovies.utils.Constants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

@Service
public class IndexService {
    private static Integer id = 1;
    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;

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
        BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
        String bulkResponseStatus = bulkResponse.status().toString();
        if (bulkResponse.hasFailures()) {
            System.out.println("Some of the bulk operations had failures");
        }
        for (BulkItemResponse bulkItemResponse : bulkResponse) {
            DocWriteResponse itemResponse = bulkItemResponse.getResponse();
            switch (bulkItemResponse.getOpType()) {
                case INDEX:
                case CREATE:
                    IndexResponse indexResponse = (IndexResponse) itemResponse;
                    System.out.println("index resp " + indexResponse.getResult().toString());
                    break;
                case UPDATE:
                    UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                    break;
                case DELETE:
                    DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
            }
        }
        return bulkResponseStatus;
    }

    private BulkRequest createBulkRequest(JSONArray movieList) {
        BulkRequest request = new BulkRequest();
        movieList.forEach(movie -> parseMovieListObject((JSONObject) movie, request, id++));
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
