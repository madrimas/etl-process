package com.stahocorp.etlprocess.external.model;

public class ExtractStatistics {
    private int time;
    private int productFilesScrapped;
    private int opinionFilesScrapped;
    private boolean isFinished;

    public ExtractStatistics() {
    }

    public ExtractStatistics(int time, int productFilesScrapped, int opinionFilesScrapped, boolean isFinished) {
        this.time = time;
        this.productFilesScrapped = productFilesScrapped;
        this.opinionFilesScrapped = opinionFilesScrapped;
        this.isFinished = isFinished;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getProductFilesScrapped() {
        return productFilesScrapped;
    }

    public void setProductFilesScrapped(int productFilesScrapped) {
        this.productFilesScrapped = productFilesScrapped;
    }

    public int getOpinionFilesScrapped() {
        return opinionFilesScrapped;
    }

    public void setOpinionFilesScrapped(int opinionFilesScrapped) {
        this.opinionFilesScrapped = opinionFilesScrapped;
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean finished) {
        this.isFinished = finished;
    }
}
