package github.com.nodeaservice;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2017/6/13
 * Version  1.0
 * Description:
 */

public class MessageService extends Service {

    private String TAG = "MessageService";

    private int ID=0X00022;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.e(TAG, "MessageService====>print");

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //assert uri
        String path = "file:///android_asset/xiaoxin.wav";
        Notification.Builder builder = new Notification.Builder(mContext);
        Notification notification = builder
                .setContentText("messageservice")
                .setSmallIcon(R.drawable.ting)
                .setSound(Uri.parse(path))
                .build();

        startForeground(ID,notification);


        bindService(new Intent(MessageService.this,GuardService.class),mServiceConnection,BIND_WAIVE_PRIORITY);

        return START_STICKY;
    }
    public ServiceConnection  mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "MessageService====>onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            startService(new Intent(MessageService.this,GuardService.class));
            bindService(new Intent(MessageService.this,GuardService.class),mServiceConnection,BIND_WAIVE_PRIORITY);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnect.Stub() {

        };
    }
}
