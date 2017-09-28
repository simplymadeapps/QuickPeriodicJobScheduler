package com.simplymadeapps.quickperiodicjobscheduler;

/**
 * Created by stephenruda on 9/21/17.
 */

public class QuickPeriodicJob {

    private int jobId;
    private PeriodicJob job;

    /**
     * Create a QuickPerioidicJob object
     * @param jobId The id for the QuickPeriodicJob.  This id will be used for starting and stopping the periodic job.  Your jobId should be unique across your entire application (any instance of JobScheduler in your application should not use this id).
     * @param job The PeriodicJob to be run when this QuickPeriodicJob fires
     */
    public QuickPeriodicJob(int jobId, PeriodicJob job) {
        this.jobId = jobId;
        this.job = job;
    }

    /**
     * @param jobId Set the QuickPeriodicJob id
     */
    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    /**
     * @param job Set the QuickPeriodicJob job
     */
    public void setJob(PeriodicJob job) {
        this.job = job;
    }

    /**
     * @return Get the QuickPeriodicJob id
     */
    public int getJobId() {
        return jobId;
    }

    /**
     * @return Get the QuickPeriodicJob job
     */
    public PeriodicJob getJob() {
        return job;
    }
}