package com.example.threadexample;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button start_thread_button;
    private Handler mainHandler = new Handler();
    private volatile boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_thread_button = findViewById(R.id.start_thread_button);
    }

    public void startThread(View view) {
        stopThread = false;
        //ExampleThread thread = new ExampleThread(10);
        //thread.start();

        ExampleRunnable runnable = new ExampleRunnable(10);
        //runnable.run();   //in UI Thread
        new Thread(runnable).start(); //in worker thread

        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10; i++) {
                    Log.d(TAG, "run: "+i);
                    if(i == 4)
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                start_thread_button.setText("50%");
                            }
                        });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        */

    }

    public void stopThread(View view) {
        stopThread = true;
    }

    private class ExampleThread extends Thread{
        private int seconds;

        ExampleThread(int seconds){
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for(int i=0; i< seconds; i++){
                Log.d(TAG, "startThread: "+i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    private class ExampleRunnable implements Runnable{
        private int seconds;

        ExampleRunnable(int seconds){
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for(int i=0; i< seconds; i++){
                if(stopThread)
                    return;

                Log.d(TAG, "startThread: "+i);
                if(i == 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            start_thread_button.setText("START");
                        }
                    });
                }
                if(i == 4){
                    /*
                    Handler threadHandler =  new Handler(Looper.getMainLooper());
                    threadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            start_thread_button.setText("50%");
                        }
                    });
                    */
                    /*
                    start_thread_button.post(new Runnable() {
                        @Override
                        public void run() {
                            start_thread_button.setText("50%");
                        }
                    });
                    */

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            start_thread_button.setText("50%");
                        }
                    });

                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
