package com.stahocorp.etlprocess.mvc;

import com.stahocorp.etlprocess.config.EtlProperties;
import com.stahocorp.etlprocess.config.EtlStatistics;
import com.stahocorp.etlprocess.external.model.ExtractStatistics;
import com.stahocorp.etlprocess.external.model.LoadStatistics;
import com.stahocorp.etlprocess.external.rest.ExtractRestController;
import com.stahocorp.etlprocess.external.rest.LoadRestController;
import com.stahocorp.etlprocess.services.EtlService;
import com.stahocorp.etlprocess.services.WatcherTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class TaskController {

    @Autowired
    private EtlProperties etlProperties;

    @Autowired
    private EtlService etlService;

    private @Autowired
    WatcherTask watcherTask;

    @Autowired
    private LoadRestController loadRestController;

    @Autowired
    private ExtractRestController extractRestController;

    /**
     * It returns inital data for main view in application
     *
     * @param model transfers data from controller to view
     * @return name of view resolved by thymeleaf framework
     */
    @GetMapping("/task")
    public String task(Model model) {
        fillModelWithStatistics(model);
        return "task";
    }

    /**
     * This method is calling extract service and triggers process to scrap data from website.
     *
     * @param model transfers data from controller to view
     * @return name of view resolved by thymeleaf framework
     */
    @PostMapping(path = "/task", params = "runExtract")
    public String runExtract(Model model) {
        RestTemplateBuilder rtp = new RestTemplateBuilder();
        RestTemplate restTemplate = ExtractRestController.restTemplateWithTimeout(rtp);
        extractRestController.runExtract(restTemplate);
        fillModelWithStatistics(model);
        return "task";
    }

    /**
     * This method is calling transform service and triggers process to transform data from input directory.
     *
     * @param model transfers data from controller to view
     * @return name of view resolved by thymeleaf framework
     */
    @PostMapping(path = "/task", params = "runTransform")
    public String runOnce(Model model) {
        watcherTask.readAndTransformAsync();
        fillModelWithStatistics(model);
        return "task";
    }

    /**
     * This method is calling load service and triggers process to load data from input directory to database.
     *
     * @param model transfers data from controller to view
     * @return name of view resolved by thymeleaf framework
     */
    @PostMapping(path = "/task", params = "runLoad")
    public String runLoad(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        loadRestController.runLoad(restTemplate);
        fillModelWithStatistics(model);
        return "task";
    }

    /**
     * This method is calling load service and triggers cleaning of database.
     *
     * @param model transfers data from controller to view
     * @return name of view resolved by thymeleaf framework
     */
    @PostMapping(path = "/task", params = "cleanDb")
    public String cleanDb(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        loadRestController.cleanDatabase(restTemplate);
        fillModelWithStatistics(model);
        return "task";
    }

    /**
     * This method is calling all services to perform ETL process from the beginning to the end.
     *
     * @param model transfers data from controller to view
     * @return name of view resolved by thymeleaf framework
     */
    //TODO: implementation of whole processing
    @PostMapping(path = "/task", params = "triggerEtl")
    public String triggerEtl(Model model) {
        etlService.triggerEtl();
        fillModelWithStatistics(model);
        return "task";
    }

    /**
     * This method is calling all services to gain its statistics and display them in GUI.
     *
     * @param model transfers data from controller to view
     * @return name of view resolved by thymeleaf framework
     */
    @PostMapping(path = "/task", params = "refresh")
    public String refresh(Model model) {
        RestTemplateBuilder rtb = new RestTemplateBuilder();
        RestTemplate restTemplate = ExtractRestController.restTemplate(rtb);
        ExtractStatistics extractStatistics = extractRestController.getExtractStats(restTemplate);

        RestTemplate restTemplate1 = new RestTemplate();
        LoadStatistics loadStatistics = loadRestController.getStats(restTemplate1);

        EtlStatistics.setLoadStatistics(loadStatistics);
        EtlStatistics.setExtractStatistics(extractStatistics);

        fillModelWithStatistics(model);
        return "task";
    }

    /**
     * Method used for filling model with statistics from services.
     *
     * @param model transfers data from controller to view
     */
    private void fillModelWithStatistics(Model model) {
        model.addAttribute("stats", EtlStatistics.toStats());
        model.addAttribute("loadStats", EtlStatistics.getLoadStatistics());
        model.addAttribute("extractStats", EtlStatistics.getExtractStatistics());
    }
}
