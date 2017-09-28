package com.simplymadeapps.quickperiodicjobscheduler;

/**
 * Created by stephenruda on 9/21/17.
 */

public abstract class PeriodicJob {
    /**
     * Executes this block of code when your periodic job fires.  Use the callback when finished to release the resources.
     * @param callback use jobFinished() on the callback object to notify the JobScheduler that your job has completed
     */
    public abstract void execute(QuickJobFinishedCallback callback);
}
