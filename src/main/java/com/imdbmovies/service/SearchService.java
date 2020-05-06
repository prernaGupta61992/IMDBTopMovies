package com.imdbmovies.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.search.join.ScoreMode;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
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
	
	
	public List<MovieDocument> fetchMovieDocuments(SearchRequestParams params) throws IOException {
		SearchRequest searchRequest = new SearchRequest(Constants.INDEX);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.sort(new FieldSortBuilder(params.getSortBy()).order(SortOrder.DESC));
		searchSourceBuilder.from(0);
		searchSourceBuilder.size(params.getSize());
		searchRequest.source(searchSourceBuilder);
		
		SearchResponse searchResponse =
                client.search(searchRequest, RequestOptions.DEFAULT);
		
		return getSerachResults(searchResponse, false);
	}
	
	
	
	public List<MovieDocument> fetchAutoCompletedMovies(SearchRequestParams params) throws IOException {
		SearchRequest searchRequest = new SearchRequest(Constants.INDEX);
		
		SearchSourceBuilder searchSourceBuilder = constructQuery(new SearchSourceBuilder(), params);
		searchRequest.source(searchSourceBuilder);
		
		
		
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		
		
		return getSerachResults(searchResponse, params.isSearch());
	}
	
	
	public List<MovieDocument> fetchGenreMovie(SearchRequestParams params) throws IOException {
		SearchRequest searchRequest = new SearchRequest(Constants.INDEX);
		
		SearchSourceBuilder searchSourceBuilder = constructQuery(new SearchSourceBuilder(), params);
		searchRequest.source(searchSourceBuilder);
		
		
		
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		
		
		return getSerachResults(searchResponse, params.isSearch());
	}
	
	private SearchSourceBuilder constructQuery(SearchSourceBuilder searchSourceBuilder,SearchRequestParams params ) {
		/* create match phrse query */
		
	
		if(params.isSearch()) {
			BoolQueryBuilder boolQuery = new BoolQueryBuilder();
			
			/*create query for autocomplete part*/
			MatchQueryBuilder matchQuery = new MatchQueryBuilder("movie_title.autocomplete", params.getQuery());
			
			HighlightBuilder highlightBuilder = new HighlightBuilder(); 
			HighlightBuilder.Field highlightMovieTitle = new HighlightBuilder.Field("movie_title.autocomplete");
			highlightBuilder.field(highlightMovieTitle);
			
			
			
			searchSourceBuilder.query(matchQuery);
			searchSourceBuilder.highlighter(highlightBuilder);
		} else if(params.getSearchType().equals("movie")){
			MatchPhraseQueryBuilder matchPhraseQuery = new MatchPhraseQueryBuilder("movie_title", params.getQuery());
			searchSourceBuilder.query(matchPhraseQuery);
		} else if(params.getSearchType().equals("genres")){
			MatchPhraseQueryBuilder matchPhraseQuery = new MatchPhraseQueryBuilder("genres", params.getQuery());
			searchSourceBuilder.query(matchPhraseQuery);
		}
		
		return searchSourceBuilder;
	}
	
	
	private SearchSourceBuilder filterResponseFeilds(SearchSourceBuilder searchSourceBuilder) {
		String[] includeFields = new String[] {"director_name", "genres", "movie_title",
				"actor_1_name", "actor_2_name", "actor_3_name","num_voted_users", "language", "title_year", "imdb_score"};
		searchSourceBuilder.fetchSource(includeFields, Strings.EMPTY_ARRAY);
		return searchSourceBuilder;
	}
	
	private List<MovieDocument> getSerachResults(SearchResponse searchResponse, final Boolean isSearch) {
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		List<MovieDocument> movieDocuments = new ArrayList<MovieDocument>();
		for(SearchHit hit : searchHits) {
			if(isSearch) {
				Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			    HighlightField highlight = highlightFields.get("movie_title.autocomplete"); 
			    Text[] fragments = highlight.fragments();  
			    String fragmentString = fragments[0].string();
			    hit.getSourceAsMap().put("highlightedString", fragmentString);
			}
			movieDocuments.add(objectMapper.convertValue(hit.getSourceAsMap(), MovieDocument.class));
		}
		
		return movieDocuments;
	}
	
	

}
