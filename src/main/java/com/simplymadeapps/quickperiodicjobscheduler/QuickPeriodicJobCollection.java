package com.simplymadeapps.quickperiodicjobscheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephenruda on 9/21/17.
 */

public class QuickPeriodicJobCollection {

    private static List<QuickPeriodicJob> jobs = new ArrayList<>();

    public static void addJob(QuickPeriodicJob qpj) {
        jobs.add(qpj);
    }

    public static void addJobs(List<QuickPeriodicJob> qpjs) {
        jobs.addAll(qpjs);
    }

    public static QuickPeriodicJob getJob(int jobId) {
        for(QuickPeriodicJob job : jobs) {
            if(job.getJobId() == jobId) {
                return job;
            }
        }

        return null;
    }

    public static List<QuickPeriodicJob> getJobs() {
        return jobs;
    }
}
