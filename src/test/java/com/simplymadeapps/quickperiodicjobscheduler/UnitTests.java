package com.simplymadeapps.quickperiodicjobscheduler;

import android.app.job.JobParameters;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class UnitTests {

    @Test
    public void testGetPeriodicJob() {
        // Add a dummy job
        QuickPeriodicJob job = new QuickPeriodicJob(1, new PeriodicJob() {
            @Override
            public void execute(QuickJobFinishedCallback callback) {
                callback.jobFinished(false);
            }
        });

        QuickPeriodicJobCollection.addJob(job);

        QuickPeriodicJobRunner qpjr = new QuickPeriodicJobRunner();
        QuickPeriodicJob job_return = qpjr.getQuickPeriodicJob(1);
        Assert.assertNotNull(job_return);
        Assert.assertEquals(job_return.getJobId(), 1);
    }

    @Test
    public void testRunnerReturns() {
        QuickPeriodicJobRunner qpjr = new QuickPeriodicJobRunner();

        JobParameters jp = mock(JobParameters.class);
        doReturn(5).when(jp).getJobId();

        Assert.assertTrue(qpjr.onStartJob(jp));
        Assert.assertTrue(qpjr.onStopJob(jp));
    }

    @Test
    public void testQuickPeriodicJobGettersSetters() {
        QuickPeriodicJob job1 = new QuickPeriodicJob(1, new PeriodicJob() {
            @Override
            public void execute(QuickJobFinishedCallback callback) {
                System.out.println("1");
            }
        });

        QuickPeriodicJob job2 = new QuickPeriodicJob(2, new PeriodicJob() {
            @Override
            public void execute(QuickJobFinishedCallback callback) {
                System.out.println("2");
            }
        });

        Assert.assertEquals(job1.getJobId(), 1);
        Assert.assertEquals(job2.getJobId(), 2);
        Assert.assertNotNull(job1.getJob());
        Assert.assertNotNull(job2.getJob());

        job1.setJobId(3);
        job2.setJobId(4);
        job1.setJob(null);
        job2.setJob(null);

        Assert.assertEquals(job1.getJobId(), 3);
        Assert.assertEquals(job2.getJobId(), 4);
        Assert.assertNull(job1.getJob());
        Assert.assertNull(job2.getJob());
    }

    @Test
    public void testQuickPeriodicJobCollection() {
        QuickPeriodicJob job1 = new QuickPeriodicJob(1, new PeriodicJob() {
            @Override
            public void execute(QuickJobFinishedCallback callback) {
                System.out.println("1");
            }
        });

        QuickPeriodicJob job2 = new QuickPeriodicJob(2, new PeriodicJob() {
            @Override
            public void execute(QuickJobFinishedCallback callback) {
                System.out.println("2");
            }
        });

        QuickPeriodicJob job3 = new QuickPeriodicJob(3, new PeriodicJob() {
            @Override
            public void execute(QuickJobFinishedCallback callback) {
                System.out.println("3");
            }
        });

        QuickPeriodicJobCollection.addJob(job1);

        Assert.assertEquals(QuickPeriodicJobCollection.getJobs().size(), 1);
        Assert.assertEquals(QuickPeriodicJobCollection.getJob(1).getJobId(), 1);

        List<QuickPeriodicJob> list = new ArrayList<>();
        list.add(job2);
        list.add(job3);

        QuickPeriodicJobCollection.addJobs(list);
        Assert.assertEquals(QuickPeriodicJobCollection.getJobs().size(), 3);
        Assert.assertEquals(QuickPeriodicJobCollection.getJob(2).getJobId(), 2);
        Assert.assertEquals(QuickPeriodicJobCollection.getJob(3).getJobId(), 3);

        Assert.assertNull(QuickPeriodicJobCollection.getJob(5));
    }

    @Test
    public void testEmptyConsturctor() {
        // No assertations can be made because the constructor is empty
        QuickPeriodicJobCollection qpjc = new QuickPeriodicJobCollection();
    }
}
