package com.stahocorp.etlprocess.config;

import com.stahocorp.etlprocess.external.model.ExtractStatistics;
import com.stahocorp.etlprocess.external.model.LoadStatistics;
import com.stahocorp.etlprocess.mvc.Stats;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Statistics of files processing.
 */
public class EtlStatistics {
    public static AtomicInteger filesProcessed = new AtomicInteger(0);
    public static AtomicInteger infosFilesProcessed = new AtomicInteger(0);
    public static AtomicInteger opinionFilesProcessed = new AtomicInteger(0);
    public static AtomicInteger infosProcessed = new AtomicInteger(0);
    public static AtomicInteger opinionsProcessed = new AtomicInteger(0);
    public static AtomicBoolean finished = new AtomicBoolean(false);

    private static LoadStatistics loadStatistics = new LoadStatistics(0,0,0,0, false);

    private static ExtractStatistics extractStatistics = new ExtractStatistics(0,0,0,false);

    public static LoadStatistics getLoadStatistics() {
        return loadStatistics;
    }

    public static AtomicBoolean getFinished() {
        return finished;
    }

    public static void setLoadStatistics(LoadStatistics loadStatistics) {
        EtlStatistics.loadStatistics = loadStatistics;
    }

    public static ExtractStatistics getExtractStatistics() {
        return extractStatistics;
    }

    public static void setExtractStatistics(ExtractStatistics extractStatistics) {
        EtlStatistics.extractStatistics = extractStatistics;
    }

    /**
     * It resets all statistics of processing of files.
     */
    public static void resetStats() {
        filesProcessed.set(0);
        infosProcessed.set(0);
        opinionsProcessed.set(0);
        infosFilesProcessed.set(0);
        opinionFilesProcessed.set(0);
        finished.set(false);
    }

    /**
     * Converts statistics to view model.
     *
     * @return view model of stats
     */
    public static Stats toStats() {
        var x = new Stats();
        x.setFilesProcessed(EtlStatistics.filesProcessed.get());
        x.setInfoFilesProcessed(EtlStatistics.infosFilesProcessed.get());
        x.setOpinionFilesProcessed(EtlStatistics.opinionFilesProcessed.get());
        x.setInfosProcessed(EtlStatistics.infosProcessed.get());
        x.setOpinionsProcessed(EtlStatistics.opinionsProcessed.get());
        x.setFinished(EtlStatistics.getFinished().get());
        return x;
    }
}
