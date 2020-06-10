package com.imdbmovies.request;

public class AggregationRequestParams {
  private String aggField;
  private String aggName;

  public String getAggName() {
    return aggName;
  }

  public void setAggName(final String aggName) {
    this.aggName = aggName;
  }

  public String getAggField() {
    return aggField;
  }

  public void setAggField(final String aggField) {
    this.aggField = aggField;
  }
}
