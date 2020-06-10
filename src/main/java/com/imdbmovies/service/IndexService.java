package com.imdbmovies.service;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imdbmovies.adaptor.FloatTypeAdapter;
import com.imdbmovies.adaptor.IntTypeAdapter;
import com.imdbmovies.controller.IndexController;
import com.imdbmovies.document.MovieDocument;
import com.imdbmovies.utils.Constants;
import com.imdbmovies.utils.JsonUtil;

@Service
public class IndexService {
  private static final Logger log = LoggerFactory.getLogger(IndexController.class);
  private static Integer id = 1;
  private final RestHighLevelClient client;
  private final ObjectMapper objectMapper;
  private final JsonUtil jsonUtil = new JsonUtil();

  public IndexService(final RestHighLevelClient client, final ObjectMapper objectMapper) {
    this.client = client;
    this.objectMapper = objectMapper;
  }

  public String createMovieDocuments(final MovieDocument document) throws Exception {
    final Map<String, Object> documentMapper = objectMapper.convertValue(document, Map.class);
    final IndexRequest indexRequest = new IndexRequest(Constants.INDEX)
        .source(documentMapper);
    return indexRequest.id();
  }

  public void createBulkMovieDocuments() throws Exception {

    if(!indexExist()) {
      final Boolean indexCreationResponse = createIndexMapping();
      log.info(" index mapping creation response is " +indexCreationResponse);

      final BulkResponse bulkResponse = indexBulkDocuments();
      if (bulkResponse.hasFailures()) {
        log.error("Some of the bulk operations has failures");
      }
      for (final BulkItemResponse bulkItemResponse : bulkResponse) {
        final DocWriteResponse itemResponse = bulkItemResponse.getResponse();
        switch (bulkItemResponse.getOpType()) {
          case INDEX:
          case CREATE:
            final IndexResponse indexResponse = (IndexResponse) itemResponse;
            log.info("index resp " + indexResponse.getResult().toString());
            break;
          case UPDATE:
            break;
          case DELETE:
        }
      }
    }
  }

  private boolean indexExist() throws IOException {
    final GetIndexRequest request = new GetIndexRequest(Constants.INDEX);
    final boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
    if(exists) {
      log.info("Index " +Constants.INDEX.toString() + " is already present");
    }
    return exists;
  }

  private BulkResponse indexBulkDocuments() throws ParseException, IOException {
    final Object movieListObj = new JSONParser().parse(jsonUtil.getStringFromFile(Constants.FILE_NAME));
    final JSONArray movieList = (JSONArray) movieListObj;
    final BulkRequest indexRequest = createBulkRequest(movieList);
    final BulkResponse bulkResponse = client.bulk(indexRequest, RequestOptions.DEFAULT);
    return bulkResponse;

  }

  private Boolean createIndexMapping() throws Exception {
    final String mapping = jsonUtil.getStringFromFile(Constants.INDEX_MAPPING_FILE);
    final CreateIndexRequest request = new CreateIndexRequest(Constants.INDEX);
    request.source(mapping, XContentType.JSON);
    final CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
    return createIndexResponse.isAcknowledged();
  }

  private BulkRequest createBulkRequest(final JSONArray movieList) {
    final BulkRequest request = new BulkRequest();
    movieList.forEach(movie -> parseMovieListObject((JSONObject) movie, request, id++));
    return request;
  }

  private void parseMovieListObject(final JSONObject movieObject, final BulkRequest request, final int id) {
    final Gson gson = new GsonBuilder()
        .registerTypeAdapter(int.class, new IntTypeAdapter())
        .registerTypeAdapter(Integer.class, new IntTypeAdapter())
        .registerTypeAdapter(Float.class, new FloatTypeAdapter())
        .registerTypeAdapter(float.class, new FloatTypeAdapter())
        .create();
    final MovieDocument movie = gson.fromJson(movieObject.toJSONString(), MovieDocument.class);
    final Map<String, Object> documentMapper = objectMapper.convertValue(movie, Map.class);
    final IndexRequest indexRequest = new IndexRequest(Constants.INDEX)
        .source(documentMapper);
    request.add(indexRequest);
  }
}
