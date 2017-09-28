# Quick Periodic Job Scheduler
A library for Android that allows you to run periodic jobs more frequently than 15 minutes apart.
# Purpose
Using the JobScheduler on Android 5 and Android 6, you could schedule periodic jobs to run just a few minutes apart.  Starting in Android 7, all periodic jobs ran at least 15 minutes apart.  This library will allow you to schedule a periodic job to fire more frequently than 15 minutes on Android 7 and higher.
# Getting Started
Add dependency to build.gradle:
```
compile 'com.github.simplymadeapps:QuickPeriodicJobScheduler:-SNAPSHOT' // Use the git commit hash to specify a specific version. SNAPSHOT will be the latest version
```
# Usage
Add job(s) to the collection - this should be called in your Application's onCreate().  Whenever the job fires, it will run whatever code is specified in your PeriodicJob's execute() method.
```javascript
@Override
    public void onCreate() {
    super.onCreate();
    int jobId = 1;
    QuickPeriodicJob job = new QuickPeriodicJob(jobId, new PeriodicJob() {
        @Override
        public void execute(QuickJobFinishedCallback callback) {
            System.out.println("Job Fired");
            callback.jobFinished(false);
        }
    });

    QuickPeriodicJobCollection.addJob(job);
}
```
When you are ready to schedule the job, you can do so using the jobId associated with the QuickPeriodicJob that you added to your collection above.
```javascript
QuickPeriodicJobScheduler jobScheduler = new QuickPeriodicJobScheduler(context);
jobScheduler.start(1, 60000l); // Run job with jobId=1 every 60 seconds
```
This would schedule the QuickPeriodicJob we added to our QuickPeriodicJobCollection above.  Every 60 seconds, you should see "Job Fired" printed in the console.
Once scheduled, your job will run in the foreground or background near the interval you specified.  If you would like to stop the periodic job, use `jobScheduler.stop(1)`. 
# Additional Information
1)  The QuickPeriodicJob will survive reboots.  There is no need to reschedule after rebooting.

2)  This library is only targeted for Android  7 and higher (that is minSdkVersion=24).  You should not need this library to schedule frequent periodic jobs on Android 5 and Android 6 because they can already do so by default.

3)  QuickPeriodicJobScheduler is still built upon Android's JobScheduler.  Avoid using any jobIds that you are already using with the default JobScheduler.

4)  If you change any code in the PeriodicJob's execute() method, it will automatically use the new code.  There should never be a need to reschedule the job.  The job will only be unscheduled if the QuickPeriodicJob is not added to the collection in Application onCreate() or if `jobScheduler.stop()` is called.

5)  Be mindful of the device going into doze mode.  Jobs might be delayed if the device is in doze mode.