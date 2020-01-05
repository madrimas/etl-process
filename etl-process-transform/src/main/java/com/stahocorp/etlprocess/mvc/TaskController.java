package com.stahocorp.etlprocess.mvc;

import com.stahocorp.etlprocess.config.EtlProperties;
import com.stahocorp.etlprocess.config.EtlStatistics;
import com.stahocorp.etlprocess.external.model.LoadStatistics;
import com.stahocorp.etlprocess.external.rest.LoadRestController;
import com.stahocorp.etlprocess.services.WatcherTask;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/task")
    public String task(Model model) {
        Stats x = new Stats();
        x.setTaskEnabled(etlProperties.isTransformationEnabled());
        model.addAttribute("stats", x);
        model.addAttribute("loadStats", new LoadStatistics());
        return "task";
    }

    @PostMapping(path = "/task", params = "save")
    public String taskSubmit(Model model) {
        etlProperties.setTransformationEnabled(!etlProperties.isTransformationEnabled());
        Stats x = new Stats();
        x.setTaskEnabled(etlProperties.isTransformationEnabled());
        model.addAttribute("stats", x);
        model.addAttribute("loadStats", new LoadStatistics());
        return "task";
    }

    @PostMapping(path = "/task", params = "runOnce")
    public String runOnce(Model model) {
        Stats x = new Stats();
        watcherTask.readAndTransformOnce();
        x.setTaskEnabled(etlProperties.isTransformationEnabled());
        model.addAttribute("stats", x);
        model.addAttribute("loadStats", new LoadStatistics());
        return "task";
    }

    @PostMapping(path = "/task", params = "runLoad")
    public String runLoad(Model model) {
        Stats x = new Stats();
        RestTemplate restTemplate = new RestTemplate();
        LoadStatistics ls = loadRestController.runLoadAndGetStats(restTemplate);
        x.setTaskEnabled(etlProperties.isTransformationEnabled());
        model.addAttribute("stats", x);
        model.addAttribute("loadStats", ls);
        return "task";
    }

    @GetMapping("/stats")
    public String getStats(Model model) {
        Stats x = new Stats();
        x.setFilesProcessed(EtlStatistics.filesProcessed.get());
        x.setInfoFilesProcessed(EtlStatistics.infosFilesProcessed.get());
        x.setOpinionFilesProcessed(EtlStatistics.opinionFilesProcessed.get());
        x.setInfosProcessed(EtlStatistics.infosProcessed.get());
        x.setOpinionsProcessed(EtlStatistics.opinionsProcessed.get());
        model.addAttribute("stats", x);
        return "stats";
    }
}
