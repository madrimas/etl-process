package com.stahocorp.etlprocess.external.rest;

import com.stahocorp.etlprocess.config.EtlProperties;
import com.stahocorp.etlprocess.external.model.ExtractStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Component
public class ExtractRestController {

    @Autowired
    private EtlProperties etlProperties;

    public static RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    public static RestTemplate restTemplateWithTimeout(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(1))
                .setReadTimeout(Duration.ofMillis(50))
                .build();
    }

    public void runExtract(RestTemplate restTemplate){
        String stringBuilder = "http://" +
                etlProperties.getExtractProcessHost() +
                ':' +
                etlProperties.getExtractProcessPort() +
                etlProperties.getExtractProcessStartCommand();
        try {
            restTemplate.getForObject(stringBuilder, Object.class);

        } catch (Exception ignore) { }
    }

    public ExtractStatistics getExtractStats(RestTemplate restTemplate){
        String stringBuilder = "http://" +
                etlProperties.getExtractProcessHost()
                + ':'
                + etlProperties.getExtractProcessPort()
                + '/'
                + etlProperties.getExtractProcessStatsCommand();

        return restTemplate.getForObject(stringBuilder, ExtractStatistics.class);
    }



}
