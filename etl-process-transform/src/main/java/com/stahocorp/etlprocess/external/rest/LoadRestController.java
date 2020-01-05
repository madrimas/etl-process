package com.stahocorp.etlprocess.external.rest;

import com.stahocorp.etlprocess.config.EtlProperties;
import com.stahocorp.etlprocess.external.model.LoadStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LoadRestController {

    @Autowired
    private EtlProperties etlProperties;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }


    public LoadStatistics runLoadAndGetStats(RestTemplate restTemplate){
        String stringBuilder = "http://" +
                etlProperties.getLoadProcessUrl() +
                ':' +
                etlProperties.getLoadProcessPort() +
                '/' +
                etlProperties.getLoadProcessCommand();
        return restTemplate.getForObject(stringBuilder, LoadStatistics.class);
    }
}
