package com.simplymadeapps.quickperiodicjobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;

/**
 * Created by stephenruda on 9/21/17.
 */

public class QuickPeriodicJobRunner extends JobService {

    /**
     * Look up the QuickPeriodicJob in the QuickPeriodicJobCollection
     * @param jobId The id of the QuickPeriodicJob
     * @return Return the QuickPeriodicJob
     */
    protected QuickPeriodicJob getQuickPeriodicJob(int jobId) {
        return QuickPeriodicJobCollection.getJob(jobId);
    }

    /**
     * This method should not be called by the application manually.
     * @param jobParameters The JobParameters are generated by the QuickPeriodicJobScheduler based off the id and interval
     * @return Return true because the job is async
     */
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        // Get the jobId
        int id = jobParameters.getJobId();

        // Find the job in the collection
        QuickPeriodicJob job = getQuickPeriodicJob(id);

        if(job != null) {
            // Schedule job again
            long interval = jobParameters.getExtras().getLong("interval");
            QuickPeriodicJobScheduler qpjs = new QuickPeriodicJobScheduler(this);
            qpjs.start(id, interval);

            // Run the job
            job.getJob().execute(new QuickJobFinishedCallback() {
                @Override
                public void jobFinished(boolean shouldBeRescheduled) {
                    QuickPeriodicJobRunner.this.jobFinished(jobParameters, shouldBeRescheduled);
                }
            });
        }

        // Return if the job is async
        return true;
    }

    /**
     * The system has stopped the job before we have finished the job
     * @param jobParameters The JobParameters are generated by the QuickPeriodicJobScheduler based off the id and interval
     * @return Return true because we would like the JobManager to automatically reschedule this job
     */
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}