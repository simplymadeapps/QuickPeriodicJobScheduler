# Quick Periodic Job Scheduler
A library for Android that allows you to run periodic jobs more frequently than 15 minutes apart.

[![codecov](https://codecov.io/gh/simplymadeapps/QuickPeriodicJobScheduler/branch/master/graph/badge.svg?token=7ZCnsUO8fL)](https://codecov.io/gh/simplymadeapps/QuickPeriodicJobScheduler)
[![CircleCI](https://circleci.com/gh/simplymadeapps/QuickPeriodicJobScheduler.svg?style=svg)](https://circleci.com/gh/simplymadeapps/QuickPeriodicJobScheduler)

## THIS PROJECT IS DEPRECATED
I am no longer using this library in any of my projects and therefore will not be updating it.  I decided to move all of my continuously running work into a Foreground Service.

## What now?
You may continue to use this project but I will not be updating it any further.  If you need to make any changes I would recommend forking the library and changing what you need.
I had originally created this project because I wanted to avoid using a Foreground Service as showing a constant notification was not preferred.  After testing both scenarios, running a Foreground Service was much more consistent and is (in my opinion) the ideal way to run constant background work.

---

# Purpose
Using the JobScheduler on Android 5 and Android 6, you could schedule periodic jobs to run just a few minutes apart.  Starting in Android 7, all periodic jobs ran at least 15 minutes apart.  This library will allow you to schedule a periodic job to fire more frequently than 15 minutes on Android 7 and higher.
# Getting Started
Add it in your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```
Add the following to the dependencies section of app/build.gradle:
```
compile 'com.github.simplymadeapps:QuickPeriodicJobScheduler:-SNAPSHOT'
```
# Usage
Add job(s) to the collection - this should be called in your Application's onCreate().  Whenever the job fires, it will run whatever code is specified in your PeriodicJob's execute() method.
```javascript
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initJobs();
    }
    
    public void initJobs() {
    	int jobId = 1;
    	QuickPeriodicJob job = new QuickPeriodicJob(jobId, new PeriodicJob() {
        	@Override
        	public void execute(QuickJobFinishedCallback callback) {
            	SomeJobClass.performJob();
                
                // When you have done all your work in the job, call jobFinished to release the resources
                callback.jobFinished();
        	}
    	});

    	QuickPeriodicJobCollection.addJob(job);
    }
    
    public static class SomeJobClass {
        public static void performJob() {
            System.out.println("Job Fired");
        }
    }
}
```
When you are ready to schedule the job, you can do so using the jobId associated with the QuickPeriodicJob that you added to your collection above.
```javascript
QuickPeriodicJobScheduler jobScheduler = new QuickPeriodicJobScheduler(context);
jobScheduler.start(1, 60000l); // Run job with jobId=1 every 60 seconds
```
This would schedule the QuickPeriodicJob we added to our QuickPeriodicJobCollection above.  Every 60 seconds, you should see "Job Fired" printed in the console.
Once scheduled, your job will run in the foreground or background near the interval you specified.  If you would like to stop the periodic job, use `jobScheduler.stop(int jobId)`. 
# Additional Information
1)  The QuickPeriodicJob will survive reboots.  There is no need to reschedule after rebooting.

2)  You should not need this library to schedule frequent periodic jobs on Android 5 and Android 6 because they can already do so by default.

3)  QuickPeriodicJobScheduler is still built upon Android's JobScheduler.  Avoid using any jobIds that you are already using with the default JobScheduler.

4)  If you change any code in the PeriodicJob's execute() method, it will automatically use the new code.  There should never be a need to reschedule the job.  The job will only be unscheduled if the QuickPeriodicJob is not added to the collection in Application onCreate() or if `jobScheduler.stop()` is called.

5)  In some instances, running your project within Android Studio will unschedule the jobs.   The JobScheduler does not persist scheduled jobs between application updates so we use the MY_PACKAGE_REPLACED broadcast to reschedule the jobs automatically.  This broadcast does not always fire when updating the app from Android Studio.

5)  Be mindful of the device going into doze mode.  Jobs might be delayed if the device is in doze mode.
# Contributing

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request

# License
The library is available as open source under the terms of the [MIT License](http://opensource.org/licenses/MIT).
