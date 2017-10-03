package com.simplymadeapps.quickperiodicjobscheduler;

/**
 * Created by stephenruda on 9/20/17.
 */

public interface QuickJobFinishedCallback {
    /**
     * This callback should be called at the end of your PeriodicJob code
     */
    public void jobFinished();
}