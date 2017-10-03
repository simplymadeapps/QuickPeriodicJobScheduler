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
                callback.jobFinished();
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

    @Test
    public void testUpgradeReceiver() {
        Context context = InstrumentationRegistry.getTargetContext();

        QuickPeriodicJob job1 = new QuickPeriodicJob(10, new PeriodicJob() {
            @Override
            public void execute(QuickJobFinishedCallback callback) {
                callback.jobFinished();
            }
        });

        QuickPeriodicJob job2 = new QuickPeriodicJob(11, new PeriodicJob() {
            @Override
            public void execute(QuickJobFinishedCallback callback) {
                callback.jobFinished();
            }
        });

        QuickPeriodicJob job3 = new QuickPeriodicJob(12, new PeriodicJob() {
            @Override
            public void execute(QuickJobFinishedCallback callback) {
                callback.jobFinished();
            }
        });

        QuickPeriodicJobCollection.addJob(job1);
        QuickPeriodicJobCollection.addJob(job2);
        QuickPeriodicJobCollection.addJob(job3);

        QuickPeriodicJobScheduler qpjs = new QuickPeriodicJobScheduler(context);
        qpjs.start(10, 60000l);
        qpjs.start(12, 60000l);

        UpgradeReceiver upgradeReceiver = new UpgradeReceiver();
        upgradeReceiver.onReceive(context, null);

        SystemClock.sleep(10000);

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        boolean tenIsFound = false;
        boolean elevenIsFound = false;
        boolean twelveIsFound = false;
        List<JobInfo> jobInfoList = jobScheduler.getAllPendingJobs();
        for(JobInfo job : jobInfoList) {
            if(job.getId() == 10) {
                tenIsFound = true;
            }
            if(job.getId() == 11) {
                elevenIsFound = true;
            }
            if(job.getId() == 12) {
                twelveIsFound = true;
            }
        }

        Assert.assertTrue(tenIsFound);
        Assert.assertFalse(elevenIsFound);
        Assert.assertTrue(twelveIsFound);
    }
}
