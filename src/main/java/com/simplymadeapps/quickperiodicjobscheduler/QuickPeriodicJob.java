package com.simplymadeapps.quickperiodicjobscheduler;

/**
 * Created by stephenruda on 9/21/17.
 */

public class QuickPeriodicJob {

    private int jobId;
    private PeriodicJob job;

    public QuickPeriodicJob(int jobId, PeriodicJob job) {
        this.jobId = jobId;
        this.job = job;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public void setJob(PeriodicJob job) {
        this.job = job;
    }

    public int getJobId() {
        return jobId;
    }

    public PeriodicJob getJob() {
        return job;
    }
}