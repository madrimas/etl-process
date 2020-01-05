package com.stahocorp.etlprocess.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@PropertySource("classpath:application.properties")
public class EtlProperties {

    @Value("${files.input}")
    private String inputDir;

    @Value("${files.output}")
    private String outputDir;

    @Value("${files.done}")
    private String doneDir;

    @Value("${transformation.enabled}")
    private boolean transformationEnabled;

    @Value("${external.load.rest.host}")
    private String loadProcessUrl;

    @Value("${external.load.rest.port}")
    private String loadProcessPort;

    @Value("${external.load.rest.start}")
    private String loadProcessCommand;

    public String getLoadProcessCommand() {
        return loadProcessCommand;
    }

    public String getLoadProcessUrl() {
        return loadProcessUrl;
    }

    public String getLoadProcessPort() {
        return loadProcessPort;
    }

    public String getInputDir() {
        return inputDir;
    }

    public Path getInputDirAsPath(){
        return Paths.get(inputDir);
    }

    public void setTransformationEnabled(boolean transformationEnabled) {
        this.transformationEnabled = transformationEnabled;
    }

    public boolean isTransformationEnabled() {
        return transformationEnabled;
    }

    public Path getOutputDirAsPath(){
        return Paths.get(outputDir);
    }

    public Path getDoneDirAsPath(){
        return Paths.get( doneDir);
    }
}
