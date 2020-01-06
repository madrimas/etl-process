package com.stahocorp.etlprocess.external.model;

public class LoadStatistics {
    private int addedProductsAmount;
    private int modifiedProductsAmount;
    private int addedOpinionsAmount;
    private int modifiedOpinionsAmount;
    private boolean finished;

    public LoadStatistics() {
    }

    /**
     *
     * @param addedProductsAmount - added Products Amount
     * @param modifiedProductsAmount - modified Products Amount
     * @param addedOpinionsAmount - added Opinions Amount
     * @param modifiedOpinionsAmount - modified Opinions Amount
     * @param finished - true if process is finished, false if not
     */
    public LoadStatistics(int addedProductsAmount, int modifiedProductsAmount, int addedOpinionsAmount, int modifiedOpinionsAmount, boolean finished) {
        this.addedProductsAmount = addedProductsAmount;
        this.modifiedProductsAmount = modifiedProductsAmount;
        this.addedOpinionsAmount = addedOpinionsAmount;
        this.modifiedOpinionsAmount = modifiedOpinionsAmount;
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getAddedProductsAmount() {
        return addedProductsAmount;
    }

    public void setAddedProductsAmount(int addedProductsAmount) {
        this.addedProductsAmount = addedProductsAmount;
    }

    public int getModifiedProductsAmount() {
        return modifiedProductsAmount;
    }

    public void setModifiedProductsAmount(int modifiedProductsAmount) {
        this.modifiedProductsAmount = modifiedProductsAmount;
    }

    public int getAddedOpinionsAmount() {
        return addedOpinionsAmount;
    }

    public void setAddedOpinionsAmount(int addedOpinionsAmount) {
        this.addedOpinionsAmount = addedOpinionsAmount;
    }

    public int getModifiedOpinionsAmount() {
        return modifiedOpinionsAmount;
    }

    public void setModifiedOpinionsAmount(int modifiedOpinionsAmount) {
        this.modifiedOpinionsAmount = modifiedOpinionsAmount;
    }
}
