package com.hallo.helloworld;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class MyJobService extends JobService {

    private static final String TAG = MyJobService.class.getSimpleName();
    private boolean jobCancel = false;
    Context context;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "StartJob");
        context = getApplicationContext();
        doBackground(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob: cancel");
        jobCancel = true;
        return true;
    }

    private void doBackground(final JobParameters params){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<10; i++) {
                    Log.i(TAG, "run: " + i);
                    Handler handler = new Handler(getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String toast = "JALANKAN!";
                            Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
                        }
                    });

                    if (jobCancel) {
                        return;
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        Log.e(TAG, "run: ", e.getCause());
                    }
                }

                Log.i(TAG, "run: JOB FINISHED");
            }
        }).start();
    }
}