package com.imdbmovies.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdbmovies.document.MovieDocument;
import com.imdbmovies.request.SearchRequestParams;
import com.imdbmovies.utils.Constants;

@Service
public class SearchService {
  private final RestHighLevelClient client;
  private final ObjectMapper objectMapper;

  public SearchService(final RestHighLevelClient client, final ObjectMapper objectMapper) {
    this.client = client;
    this.objectMapper = objectMapper;
  }

  public List<MovieDocument> fetchMovieDocuments(final SearchRequestParams params) throws IOException {
    final SearchRequest searchRequest = new SearchRequest(Constants.INDEX);
    final SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
    searchSourceBuilder.sort(new FieldSortBuilder(params.getSortBy()).order(SortOrder.DESC));
    searchSourceBuilder.from(0);
    searchSourceBuilder.size(params.getSize());
    searchRequest.source(searchSourceBuilder);

    final SearchResponse searchResponse =
        client.search(searchRequest, RequestOptions.DEFAULT);

    return getSearchResults(searchResponse, false);
  }

  public List<MovieDocument> fetchAutoCompletedMovies(final SearchRequestParams params) throws IOException {
    final SearchRequest searchRequest = new SearchRequest(Constants.INDEX);

    final SearchSourceBuilder searchSourceBuilder = constructQuery(new SearchSourceBuilder(), params);
    searchRequest.source(searchSourceBuilder);
    final SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
    return getSearchResults(searchResponse, params.isSearch());
  }

  public List<MovieDocument> fetchGenreMovie(final SearchRequestParams params) throws IOException {
    final SearchRequest searchRequest = new SearchRequest(Constants.INDEX);

    final SearchSourceBuilder searchSourceBuilder = constructQuery(new SearchSourceBuilder(), params);
    searchRequest.source(searchSourceBuilder);
    final SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
    return getSearchResults(searchResponse, params.isSearch());
  }

  private SearchSourceBuilder constructQuery(final SearchSourceBuilder searchSourceBuilder, final SearchRequestParams params) {
    /* create match phrse query */
    if (params.isSearch()) {
      new BoolQueryBuilder();

      /*create query for autocomplete part*/
      final MatchQueryBuilder matchQuery = new MatchQueryBuilder("movie_title.autocomplete", params.getQuery());

      final HighlightBuilder highlightBuilder = new HighlightBuilder();
      final HighlightBuilder.Field highlightMovieTitle = new HighlightBuilder.Field("movie_title.autocomplete");
      highlightBuilder.field(highlightMovieTitle);
      searchSourceBuilder.query(matchQuery);
      searchSourceBuilder.highlighter(highlightBuilder);
    } else if (params.getSearchType().equals("movie")) {
      final MatchPhraseQueryBuilder matchPhraseQuery = new MatchPhraseQueryBuilder("movie_title", params.getQuery());
      searchSourceBuilder.query(matchPhraseQuery);
    } else if (params.getSearchType().equals("genres")) {
      final MatchPhraseQueryBuilder matchPhraseQuery = new MatchPhraseQueryBuilder("genres", params.getQuery());
      searchSourceBuilder.query(matchPhraseQuery);
    }

    return searchSourceBuilder;
  }

  private SearchSourceBuilder filterResponseFeilds(final SearchSourceBuilder searchSourceBuilder) {
    final String[] includeFields = new String[] {"director_name", "genres", "movie_title",
        "actor_1_name", "actor_2_name", "actor_3_name", "num_voted_users", "language", "title_year", "imdb_score"};
    searchSourceBuilder.fetchSource(includeFields, Strings.EMPTY_ARRAY);
    return searchSourceBuilder;
  }

  private List<MovieDocument> getSearchResults(final SearchResponse searchResponse, final Boolean isSearch) {
    final SearchHit[] searchHits = searchResponse.getHits().getHits();
    final List<MovieDocument> movieDocuments = new ArrayList<MovieDocument>();
    for (final SearchHit hit : searchHits) {
      if (isSearch) {
        final Map<String, HighlightField> highlightFields = hit.getHighlightFields();
        final HighlightField highlight = highlightFields.get("movie_title.autocomplete");
        final Text[] fragments = highlight.fragments();
        final String fragmentString = fragments[0].string();
        hit.getSourceAsMap().put("highlightedString", fragmentString);
      }
      movieDocuments.add(objectMapper.convertValue(hit.getSourceAsMap(), MovieDocument.class));
    }

    return movieDocuments;
  }

}
