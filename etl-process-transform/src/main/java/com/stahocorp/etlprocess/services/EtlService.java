package com.stahocorp.etlprocess.services;

import com.stahocorp.etlprocess.config.EtlProperties;
import com.stahocorp.etlprocess.external.rest.ExtractRestController;
import com.stahocorp.etlprocess.external.rest.LoadRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service used to control flow of all ETL Process.
 */
@Service
public class EtlService {

    @Autowired
    private EtlProperties etlProperties;

    @Autowired
    private ExtractRestController extractRestController;

    @Autowired
    private LoadRestController loadRestController;

    @Autowired
    private WatcherTask watcherTask;

    /**
     * Main function responsible for data flow. It triggers endpoints of all services one after another is done.
     */
    @Async
    public void triggerEtl() {
        try {
            runExtractProcess();
            checkExtractionStatus();
            watcherTask.readAndTransform();
            runLoadProcess();
        } catch (InterruptedException e){
            System.out.println("Execution of ETL process was interrupted");
        }

    }

    /**
     * It checks periodically if extraction process is done yet.
     * @throws InterruptedException in case of interruption
     */
    private void checkExtractionStatus() throws InterruptedException {
        var done = false;
       do {
           Thread.sleep(5000);
            done = isExtractionDone();
            Thread.sleep(1000);
        } while (!done);
    }

    /**
     * It uses rest extract controller to gain information about extraction process.
     * @return state of extraction process completion
     */
    private boolean isExtractionDone() {
        var restTemplate = new RestTemplate();
        var x = extractRestController.getExtractStats(restTemplate);
        return x.getIsFinished();
    }

    /**
     * Calls load process to start its work.
     */
    private void runLoadProcess(){
        var restTemplate = new RestTemplate();
        loadRestController.runLoad(restTemplate);
    }

    /**
     * Calls extract process to start its work.
     */
    private void runExtractProcess() {
        var restTemplateBuilder = new RestTemplateBuilder();
        var restTemplate = ExtractRestController.restTemplateWithTimeout(restTemplateBuilder);
        extractRestController.runExtract(restTemplate);

    }
}
