package com.stahocorp.etlprocess.config;

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

    /**
     * It resets all statistics of processing of files.
     */
    public static void resetStats() {
        filesProcessed.set(0);
        infosProcessed.set(0);
        opinionsProcessed.set(0);
        infosFilesProcessed.set(0);
        opinionFilesProcessed.set(0);
    }
}
