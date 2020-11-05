package com.hallo.helloworld;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.HelloWorld.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import android.widget.Switch;



public class HomeActivity extends AppCompatActivity {

    private Switch sw;
    private WifiManager wm;

    private Button btnStartJob;
    private Button btnCancelJob;

    private static final String TAG = HomeActivity.class.getSimpleName();

    private FrameLayout fragmentHolder;

    public HomeActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnStartJob = findViewById(R.id.startJob);
        btnStartJob = findViewById(R.id.cancelJob);

        TabLayout tabLayout = findViewById(R.id.tabBar);
        TabItem fragmentTop = findViewById(R.id.fragmentLeft);
        TabItem fragmentBottom = findViewById(R.id.fragmentRight);
        final ViewPager viewPager = findViewById(R.id.viewPager);

        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0) {
                    pagerAdapter.notifyDataSetChanged();
                }else if(tab.getPosition() == 1){
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        sw = findViewById(R.id.wifi_switch);
        BroadcastRec();
    }
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }

    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }
    public void notifOn (String message, Context context){
        String CHANNEL_ID = "MY_NOTIF";
        NotificationChannel mChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, "My channel", NotificationManager.IMPORTANCE_HIGH);
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(mChannel);
        }
        Notification notification = new NotificationCompat.Builder(HomeActivity.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_wifi)
                .setContentTitle("STATUS WIFI :")
                .setContentText(message)
                .build();
        int notificationID = 0;
        notificationManager.notify(notificationID, notification);
    }
    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, wm.WIFI_STATE_UNKNOWN);

            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:

                    sw.setChecked(true);
                    sw.setText("WIFI ON");
                    notifOn("WIFI ON",context);
                    break;

                case WifiManager.WIFI_STATE_DISABLED:
                    sw.setChecked(false);

                    sw.setText("WIFI OFF");
                    notifOn("WIFI OFF",context);
                    break;
            }


        }

    };
    private void BroadcastRec() {
        final WifiManager wifiManager = (WifiManager)
                getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !wifiManager.isWifiEnabled()) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                        Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                        HomeActivity.this.startActivityForResult(panelIntent, 1);
                    } else {
                        wifiManager.setWifiEnabled(true);
                    }
                } else if (!isChecked && wifiManager.isWifiEnabled()) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                        HomeActivity.this.startActivityForResult(panelIntent, 1);
                    } else {
                        wifiManager.setWifiEnabled(false);
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void scheduleJob(View view){
        ComponentName componentName = new ComponentName(getApplicationContext(), MyJobService.class);
        JobInfo info = new JobInfo.Builder(123,componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000) //dilakukan setiap 15 menit
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if(resultCode == JobScheduler.RESULT_SUCCESS){
            Log.i(TAG, "scheduleJob: Job Scheduled");
        }else{
            Log.i(TAG, "scheduleJob: Job Scheduling Failed");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void cancelJob(View view){
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.i(TAG, "cancelJob: Job Cancel Job");
    }
}
