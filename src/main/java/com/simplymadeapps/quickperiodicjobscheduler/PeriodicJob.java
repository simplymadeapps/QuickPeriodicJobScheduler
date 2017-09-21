package com.simplymadeapps.quickperiodicjobscheduler;

/**
 * Created by stephenruda on 9/21/17.
 */

public abstract class PeriodicJob {
    public abstract void execute(QuickJobFinishedCallback callback);
}
