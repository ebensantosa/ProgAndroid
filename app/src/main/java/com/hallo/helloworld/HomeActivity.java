package com.hallo.helloworld;

import android.Manifest;
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
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.hallo.HelloWorld.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity {
    private SharedPrefManager sharedPreferenceConfig;
    private Switch sw;
    private WifiManager wm;
    private Button button;
    SharedPrefManager sharedPrefManager;
    private Button btnStartJob;
    private Button btnCancelJob;
    private static final String TAG = HomeActivity.class.getSimpleName();

    private FrameLayout fragmentHolder;

    public static final int CAMERA_REQUEST_CODE = 102;
    public  static final int CAMERA_PERMISSION_CODE = 101;
    public static final int GALLERY_REQUEST_CODE = 105;
    private ImageView image;
    private Button btnCamera,galleryBtn;;
    String currentPhotoPath;

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
        sharedPrefManager = new SharedPrefManager(this);

        button=findViewById(R.id.keluar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(HomeActivity.this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();

            }
        });

        image = findViewById(R.id.imageCapture);
        btnCamera = findViewById(R.id.cameraBtn);
        galleryBtn = findViewById(R.id.galleryBtn);


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
            }
        });
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });

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


    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else{
            openCamera();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.length < 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }else{
                Toast.makeText(this, "Camera permission required", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            Bitmap imageThumbnail = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(imageThumbnail);
        }
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