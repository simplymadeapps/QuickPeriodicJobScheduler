package com.simplymadeapps.quickperiodicjobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;

/**
 * Created by stephenruda on 9/21/17.
 */

public class QuickPeriodicJobScheduler {

    Context context;

    /**
     * Instantiate a job scheduler used for starting and stopping jobs
     * @param context Context necessary for accessing the JOB_SCHEDULER_SERVICE
     */
    public QuickPeriodicJobScheduler(Context context) {
        this.context = context;
    }

    /**
     * Start a QuickPeriodicJob
     * @param jobId The id of the QuickPeriodicJob to start.  It must match an id of a QuickPeriodicJob that was added using QuickPeriodicJobCollection.add().  If the job does not exist, nothing will happen.
     * @param periodicInterval The interval in milliseconds that you want this job to run (example 30000 would be 30 seconds)
     */
    public void start(int jobId, long periodicInterval) {
        ComponentName component = new ComponentName(context, QuickPeriodicJobRunner.class);
        JobInfo.Builder builder = new JobInfo.Builder(jobId, component);
        builder.setOverrideDeadline(periodicInterval);
        builder.setMinimumLatency(periodicInterval);
        PersistableBundle bundle = new PersistableBundle();
        bundle.putLong("interval",periodicInterval);
        builder.setExtras(bundle);
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
    }

    /**
     * Stop a QuickPeriodicJob
     * @param jobId The id of the QuickPeriodicJob to stop.  If the job does not exist, nothing will happen.
     */
    public void stop(int jobId) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(jobId);
    }
}
