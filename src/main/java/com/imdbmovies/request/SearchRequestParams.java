package com.imdbmovies.request;

public class SearchRequestParams {
  private String sortBy;
  private String sortOrder;
  private String query;
  private int size;
  private boolean search;
  private String searchType;

  public String getSearchType() {
    return searchType;
  }

  public void setSearchType(final String searchType) {
    this.searchType = searchType;
  }

  public boolean isSearch() {
    return search;
  }

  public void setSearch(final boolean search) {
    this.search = search;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(final String query) {
    this.query = query;
  }

  public String getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(final String sortOrder) {
    this.sortOrder = sortOrder;
  }

  public int getSize() {
    return size;
  }

  public void setSize(final int size) {
    this.size = size;
  }

  public String getSortBy() {
    return sortBy;
  }

  public void setSortBy(final String sortBy) {
    this.sortBy = sortBy;
  }
}
