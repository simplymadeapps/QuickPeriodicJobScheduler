package com.simplymadeapps.quickperiodicjobscheduler;

/**
 * Created by stephenruda on 9/20/17.
 */

public interface QuickJobFinishedCallback {
    public void jobFinished(boolean shouldBeRescheduled);
}