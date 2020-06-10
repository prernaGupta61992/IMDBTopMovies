package com.imdbmovies.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.ParsedScriptedMetric;
import org.elasticsearch.search.aggregations.metrics.ScriptedMetricAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import com.imdbmovies.request.AggregationRequestParams;
import com.imdbmovies.response.AggregationResponse;
import com.imdbmovies.utils.Constants;

@Service
public class AggregationService
{
    private final RestHighLevelClient client;

    public AggregationService(final RestHighLevelClient restHighLevelClient)
    {
        this.client = restHighLevelClient;
    }

    public AggregationResponse getGenresAggregatedData(final AggregationRequestParams params) throws Exception
    {
        final SearchRequest searchRequest = new SearchRequest(Constants.INDEX);

        final SearchSourceBuilder searchSourceBuilder = constructQuery(new SearchSourceBuilder(), params);
        searchRequest.source(searchSourceBuilder);
        final SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        return new AggregationResponse(getAggregatedResults(searchResponse, params));
    }

    public AggregationResponse getActorsAggregatedData() throws Exception
    {
        final SearchRequest searchRequest = new SearchRequest(Constants.INDEX);

        final SearchSourceBuilder searchSourceBuilder = constructScriptedQuery(new SearchSourceBuilder());
        searchRequest.source(searchSourceBuilder);
        final SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        final ParsedScriptedMetric buckets = searchResponse.getAggregations().get("merged_actors");
        final List<Object> test = (List<Object>) buckets.aggregation();
        final HashMap<String, Map<String, String>> first = (HashMap<String, Map<String, String>>) test.get(0);
        first.get("actors_map");
        System.out.println("there");
        return null;
    }


    private SearchSourceBuilder constructScriptedQuery(final SearchSourceBuilder searchSourceBuilder)
    {
        final ScriptedMetricAggregationBuilder aggregation =
                AggregationBuilders.scriptedMetric("merged_actors").initScript(new Script("state.actors_map=[:]"))
                .mapScript(new Script("def actor_keys = ['actor_1_name', 'actor_2_name', 'actor_3_name'];for (def key : actor_keys)"
                        + "{def actor_name = doc[key + '.keyword'].value;if (state.actors_map.containsKey(actor_name)) {state.actors_map[actor_name] += 1;} "
                        + "else {state.actors_map[actor_name] = 1;}}"))
                .combineScript(new Script("return state")).reduceScript(new Script("return states"));
        searchSourceBuilder.aggregation(aggregation);
        searchSourceBuilder.size(0);
        return searchSourceBuilder;
    }

    private SearchSourceBuilder constructQuery(final SearchSourceBuilder searchSourceBuilder, final AggregationRequestParams params)
    {
        final AggregationBuilder aggregation = AggregationBuilders.terms(params.getAggName()).field(params.getAggField());
        searchSourceBuilder.aggregation(aggregation);
        return searchSourceBuilder;
    }

    private List<Map<String, String>> getAggregatedResults(final SearchResponse searchResponse, final AggregationRequestParams params)
    {
        final Terms buckets = searchResponse.getAggregations().get(params.getAggName());
        final List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Map<String, String> map;
        for (final Terms.Bucket bucket : buckets.getBuckets())
        {
            map = new HashMap<String, String>();
            map.put("key", (String) bucket.getKey());
            map.put("total", Long.toString(bucket.getDocCount()));
            result.add(map);
        }
        return result;
    }


}
