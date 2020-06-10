package com.imdbmovies.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Primary
public class ElasticsearchConfig {

  /**
   * Creates a Elasticsearch client from config
   *
   * @return Elasticsearch client
   */

  @Value("${elasticsearch.host}")
  private String esHost;

  @Value("${elasticsearch.port}")
  private String esPort;

  @Bean(destroyMethod = "close")
  public RestHighLevelClient client() {

    final RestHighLevelClient client = new RestHighLevelClient(
        RestClient.builder(new HttpHost(esHost, Integer.parseInt(esPort), "http")));
    return client;
  }
}