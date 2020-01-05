package com.stahocorp.etlprocess.mvc;

import com.stahocorp.etlprocess.config.EtlProperties;
import com.stahocorp.etlprocess.config.EtlStatistics;
import com.stahocorp.etlprocess.external.model.ExtractStatistics;
import com.stahocorp.etlprocess.external.model.LoadStatistics;
import com.stahocorp.etlprocess.external.rest.ExtractRestController;
import com.stahocorp.etlprocess.external.rest.LoadRestController;
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

    private @Autowired
    WatcherTask watcherTask;

    @Autowired
    private LoadRestController loadRestController;

    @Autowired
    private ExtractRestController extractRestController;

    @GetMapping("/task")
    public String task(Model model) {
        model.addAttribute("extractStats", EtlStatistics.getExtractStatistics());
        model.addAttribute("stats", getTransformationStats());
        model.addAttribute("loadStats", EtlStatistics.getLoadStatistics());
        return "task";
    }

    @PostMapping(path = "/task", params = "save")
    public String taskSubmit(Model model) {
        etlProperties.setTransformationEnabled(!etlProperties.isTransformationEnabled());
        model.addAttribute("stats", getTransformationStats());
        model.addAttribute("loadStats", EtlStatistics.getLoadStatistics());
        model.addAttribute("extractStats", EtlStatistics.getExtractStatistics());
        return "task";
    }

    @PostMapping(path = "/task", params = "runExtract")
    public String runExtract(Model model) {
        RestTemplateBuilder rtp = new RestTemplateBuilder();
        RestTemplate restTemplate = ExtractRestController.restTemplateWithTimeout(rtp);
        extractRestController.runExtract(restTemplate);
        model.addAttribute("stats", getTransformationStats());
        model.addAttribute("loadStats", EtlStatistics.getLoadStatistics());
        model.addAttribute("extractStats", EtlStatistics.getExtractStatistics());
        return "task";
    }

    @PostMapping(path = "/task", params = "runTransform")
    public String runOnce(Model model) {
        watcherTask.readAndTransformOnce();
        model.addAttribute("stats", getTransformationStats());
        model.addAttribute("loadStats", EtlStatistics.getLoadStatistics());
        model.addAttribute("extractStats", EtlStatistics.getExtractStatistics());
        return "task";
    }

    @PostMapping(path = "/task", params = "runLoad")
    public String runLoad(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        loadRestController.runLoad(restTemplate);
        model.addAttribute("stats", getTransformationStats());
        model.addAttribute("loadStats", EtlStatistics.getLoadStatistics());
        model.addAttribute("extractStats", EtlStatistics.getExtractStatistics());
        return "task";
    }

    @PostMapping(path = "/task", params = "refresh")
    public String refresh(Model model) {
        RestTemplateBuilder rtb = new RestTemplateBuilder();
        RestTemplate restTemplate = ExtractRestController.restTemplate(rtb);
        ExtractStatistics extractStatistics = extractRestController.getExtractStats(restTemplate);

        RestTemplate restTemplate1 = new RestTemplate();
        LoadStatistics loadStatistics = loadRestController.getStats(restTemplate1);

        EtlStatistics.setLoadStatistics(loadStatistics);
        EtlStatistics.setExtractStatistics(extractStatistics);

        model.addAttribute("stats", getTransformationStats());
        model.addAttribute("loadStats", EtlStatistics.getLoadStatistics());
        model.addAttribute("extractStats", EtlStatistics.getExtractStatistics());
        return "task";
    }


    @GetMapping("/stats")
    public String getStats(Model model) {
        model.addAttribute("stats", getTransformationStats());
        return "stats";
    }

    private Stats getTransformationStats() {
        var x = new Stats();
        x.setTaskEnabled(etlProperties.isTransformationEnabled());
        x.setFilesProcessed(EtlStatistics.filesProcessed.get());
        x.setInfoFilesProcessed(EtlStatistics.infosFilesProcessed.get());
        x.setOpinionFilesProcessed(EtlStatistics.opinionFilesProcessed.get());
        x.setInfosProcessed(EtlStatistics.infosProcessed.get());
        x.setOpinionsProcessed(EtlStatistics.opinionsProcessed.get());
        return x;
    }
}
