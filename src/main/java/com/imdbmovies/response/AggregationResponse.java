package com.imdbmovies.response;

import java.util.List;
import java.util.Map;

public class AggregationResponse {
    private final List<Map<String, String>> aggResult;

    public AggregationResponse(final List<Map<String, String>> aggResult) {
        this.aggResult = aggResult;
    }

    public List<Map<String, String>> getAggResult() {
        return aggResult;
    }
}
