package com.simplymadeapps.quickperiodicjobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class InstrumentedTests {

    @Test
    public void testStart() {
        Context context = InstrumentationRegistry.getTargetContext();
        QuickPeriodicJobScheduler qpjs = new QuickPeriodicJobScheduler(context);
        qpjs.start(2, 30000l);

        SystemClock.sleep(1000);

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        List<JobInfo> jobInfoList = jobScheduler.getAllPendingJobs();
        JobInfo jobInfo = null;
        for(JobInfo job : jobInfoList) {
            if(job.getId() == 2) {
                jobInfo = job;
            }
        }

        Assert.assertEquals(jobInfo.getMaxExecutionDelayMillis(), 30000l);
        Assert.assertEquals(jobInfo.getMinLatencyMillis(), 30000l);
        Assert.assertEquals(jobInfo.getId(), 2);
        Assert.assertEquals(jobInfo.getExtras().getLong("interval"), 30000l);
        Assert.assertNotNull(jobInfo);
    }

    @Test
    public void testStop() {
        Context context = InstrumentationRegistry.getTargetContext();
        QuickPeriodicJobScheduler qpjs = new QuickPeriodicJobScheduler(context);
        qpjs.stop(1);
    }

    @Test
    public void testScheduleAJobThatExist() {
        Context context = InstrumentationRegistry.getTargetContext();

        QuickPeriodicJob job = new QuickPeriodicJob(1, new PeriodicJob() {
            @Override
            public void execute(QuickJobFinishedCallback callback) {
                callback.jobFinished(false);
            }
        });

        QuickPeriodicJobCollection.addJob(job);

        QuickPeriodicJobScheduler qpjs = new QuickPeriodicJobScheduler(context);
        qpjs.start(1, 0l);

        SystemClock.sleep(10000);
    }

    @Test
    public void testScheduleAJobThatDoesntExist() {
        Context context = InstrumentationRegistry.getTargetContext();

        QuickPeriodicJobScheduler qpjs = new QuickPeriodicJobScheduler(context);
        qpjs.start(5, 0l);

        SystemClock.sleep(10000);
    }
}
