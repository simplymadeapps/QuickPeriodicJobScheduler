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

    public QuickPeriodicJobScheduler(Context context) {
        this.context = context;
    }

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

    public void stop(int jobId) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(jobId);
    }
}
