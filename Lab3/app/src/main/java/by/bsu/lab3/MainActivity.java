package by.bsu.lab3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean isBound = true;
    private TimeService timeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, TimeService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
        isBound = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBound)
            unbindService(serviceConnection);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TimeService.TimeServiceBinder binder = (TimeService.TimeServiceBinder) service;
            timeService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @SuppressLint("WrongConstant")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_start_item: {
                Intent intent = new Intent(getApplicationContext(), TimeService.class);
                timeService.onStartCommand(intent, TimeService.ON_TIMER_START, 0);
                return true;
            }
            case R.id.menu_stop_item: {
                Intent intent = new Intent(getApplicationContext(), TimeService.class);
                timeService.onStartCommand(intent, TimeService.ON_TIMER_STOP, 0);
                return true;
            }
            case R.id.menu_reset_item: {
                Intent intent = new Intent(getApplicationContext(), TimeService.class);
                timeService.onStartCommand(intent, TimeService.ON_TIMER_RESET, 0);
                return true;

            }
        }
        return super.onOptionsItemSelected(item);
    }
}