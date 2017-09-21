package com.simplymadeapps.quickperiodicjobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;

/**
 * Created by stephenruda on 9/21/17.
 */

public class QuickPeriodicJobRunner extends JobService {

    private QuickPeriodicJob getQuickPeriodicJob(int jobId) {
        return QuickPeriodicJobCollection.getJob(jobId);
    }

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        int id = jobParameters.getJobId();
        long interval = jobParameters.getExtras().getLong("interval");

        QuickPeriodicJob job = getQuickPeriodicJob(id);

        if(job != null) {
            // Schedule job again
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

        // Return if the job is async or not
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}