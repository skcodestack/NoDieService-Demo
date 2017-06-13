package github.com.nodeaservice;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.List;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2017/6/13
 * Version  1.0
 * Description:
 */


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobWakeUpService extends JobService {

    private JobScheduler service;
    private int JobId=100;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        JobInfo info = new JobInfo.Builder(JobId,new ComponentName(this,JobWakeUpService.class))
                .setPeriodic(2000)
                .build();

        service = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        service.schedule(info);
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e("JobWakeUpService", "JobWakeUpService====>print");
        //开始定时任务
        if(!isServiceWork(this,MessageService.class.getName())){
            //
            startService(new Intent(this,MessageService.class));
        }

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        //停止
        service.cancel(JobId);
//        service.cancelAll();
        return false;
    }

    private boolean isServiceWork(Context context,String serviceName){
        ActivityManager am= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = am.getRunningServices(100);
        if(runningServices == null){
            return false;
        }
        for (ActivityManager.RunningServiceInfo service : runningServices) {
            String className = service.service.getClassName();
            if(className.equals(serviceName)){
                return true;
            }
        }
        return false;

    }
}
