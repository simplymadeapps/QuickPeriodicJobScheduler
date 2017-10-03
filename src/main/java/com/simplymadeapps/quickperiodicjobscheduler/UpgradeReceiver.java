package com.simplymadeapps.quickperiodicjobscheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.List;

/**
 * The UpgradeReceiver is responsible for rescheduling the jobs after an app update.
 * The JobScheduler does not retain jobs after an update.  We must reschedule them manually.
 */
public class UpgradeReceiver extends BroadcastReceiver {

    Context context;

    @Override
    public void onReceive(Context context, Intent i) {
        this.context = context;

        // Go through our list of jobs and start any that need to be started
        List<QuickPeriodicJob> jobs = QuickPeriodicJobCollection.getJobs();
        for(QuickPeriodicJob job : jobs) {
            boolean isJobScheduled = isJobScheduled(context, job.getJobId());
            if(isJobScheduled) {
                // Job needs rescheduling
                long interval = getScheduledJobInterval(context, job.getJobId());
                QuickPeriodicJobScheduler qpjs = new QuickPeriodicJobScheduler(context);
                qpjs.start(job.getJobId(), interval);
            }
        }
    }

    protected boolean isJobScheduled(Context context, int jobId) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean("quickperiodicjobscheduler_isjobscheduled_"+jobId, false);
    }

    protected long getScheduledJobInterval(Context context, int jobId) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong("quickperiodicjobscheduler_periodicInterval_"+jobId, 30000l);
    }
}