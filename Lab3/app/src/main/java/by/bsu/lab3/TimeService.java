package by.bsu.lab3;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TimeService extends Service {

    public static final int ON_TIMER_START = 1;
    public static final int ON_TIMER_STOP = 2;
    public static final int ON_TIMER_RESET = 3;

    public class TimeServiceBinder extends Binder {
        public TimeService getService() {
            return TimeService.this;
        }
    }

    private boolean isWorking = false;
    private int counter = 0;

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getApplicationContext(), counter + " ", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                    if (isWorking)
                        try {
                            ++counter;
                            handler.sendEmptyMessage(0);
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
            }
        });
        thread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (flags == ON_TIMER_START)
            isWorking = true;
        if (flags == ON_TIMER_STOP)
            isWorking = false;
        if (flags == ON_TIMER_RESET)
            counter = 0;
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new TimeServiceBinder();
    }
}
