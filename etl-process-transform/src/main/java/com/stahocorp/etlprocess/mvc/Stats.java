package com.stahocorp.etlprocess.mvc;

/**
 * This class is model for MVC display of statistics.
 */
public class Stats {
    private boolean taskEnabled;
    private int filesProcessed;
    private int infoFilesProcessed;
    private int opinionFilesProcessed;
    private int opinionsProcessed;
    private int infosProcessed;
    private boolean finished;

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isTaskEnabled() {
        return taskEnabled;
    }

    public void setTaskEnabled(boolean taskEnabled) {
        this.taskEnabled = taskEnabled;
    }

    public int getFilesProcessed() {
        return filesProcessed;
    }

    public int getInfoFilesProcessed() {
        return infoFilesProcessed;
    }

    public void setInfoFilesProcessed(int infoFilesProcessed) {
        this.infoFilesProcessed = infoFilesProcessed;
    }

    public int getOpinionFilesProcessed() {
        return opinionFilesProcessed;
    }

    public void setOpinionFilesProcessed(int opinionFilesProcessed) {
        this.opinionFilesProcessed = opinionFilesProcessed;
    }

    public int getOpinionsProcessed() {
        return opinionsProcessed;
    }

    public void setOpinionsProcessed(int opinionsProcessed) {
        this.opinionsProcessed = opinionsProcessed;
    }

    public int getInfosProcessed() {
        return infosProcessed;
    }

    public void setInfosProcessed(int infosProcessed) {
        this.infosProcessed = infosProcessed;
    }

    public void setFilesProcessed(int filesProcessed) {
        this.filesProcessed = filesProcessed;
    }
}
