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

public class GuardService extends Service {
    private Context mContext;
    private int ID=0X00021;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

    }

        @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            //assert uri
            String path = "file:///android_asset/xiaoxin.wav";
            Notification.Builder builder = new Notification.Builder(mContext);
            Notification notification = builder
                    .setContentText("GuardService")
                    .setSmallIcon(R.drawable.ting)
                    .setSound(Uri.parse(path))
                    .build();

            startForeground(ID,notification);

            bindService(new Intent(GuardService.this,MessageService.class),mServiceConnection,BIND_WAIVE_PRIORITY);

            return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnect.Stub(){

        };
    }

    public ServiceConnection  mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("GuardService", "GuardService====>onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            startService(new Intent(GuardService.this,MessageService.class));
            bindService(new Intent(GuardService.this,MessageService.class),mServiceConnection,BIND_WAIVE_PRIORITY);
        }
    };

}
