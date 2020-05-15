package com.imdbmovies.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
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
    @Bean(destroyMethod = "close")
    public RestHighLevelClient client() {
        RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", 9500, "http")));
        return client;
    }
}