package com.simplymadeapps.quickperiodicjobscheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephenruda on 9/21/17.
 */

public class QuickPeriodicJobCollection {

    protected QuickPeriodicJobCollection() {
        // Empty, private constructor.  Should not be used
    }

    private static List<QuickPeriodicJob> jobs = new ArrayList<>();

    /**
     * Add a job to the collection.  You must always have your job in the collection for it to work properly.  This should be called upon application start.
     * @param qpj The QuickPeriodicJob you will eventually schedule using the QuickPeriodicJobScheduler
     */
    public static void addJob(QuickPeriodicJob qpj) {
        jobs.add(qpj);
    }

    /**
     * Add multiple jobs to the collection.  You must always have your job in the collection for it to work properly.  This should be called upon application start.
     * @param qpjs The QuickPeriodicJobs you will eventually schedule using the QuickPeriodicJobScheduler
     */
    public static void addJobs(List<QuickPeriodicJob> qpjs) {
        jobs.addAll(qpjs);
    }

    /**
     * Look up a QuickPeriodicJob in the collection
     * @param jobId The jobId of the QuickPeriodicJob you are looking up
     * @return the QuickPeriodicJob object is returned if it exists.  Returns null if it does not exist in the collection
     */
    public static QuickPeriodicJob getJob(int jobId) {
        for(QuickPeriodicJob job : jobs) {
            if(job.getJobId() == jobId) {
                return job;
            }
        }

        return null;
    }

    /**
     * Get all QuickPeriodicJobs in the collection
     * @return list of QuickPeriodicJobs in collection
     */
    public static List<QuickPeriodicJob> getJobs() {
        return jobs;
    }
}
