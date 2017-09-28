package com.simplymadeapps.quickperiodicjobscheduler;

/**
 * Created by stephenruda on 9/20/17.
 */

public interface QuickJobFinishedCallback {
    /**
     * This callback should be called at the end of your PeriodicJob code
     * @param shouldBeRescheduled Set to true if this job should be rescheduled according to the back-off criteria specified at schedule-time. False otherwise.
     */
    public void jobFinished(boolean shouldBeRescheduled);
}