package com.hfad.workout;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;


public class StopwatchFragment extends Fragment
        implements View.OnClickListener{

    private int seconds = 0;
    private boolean running = false;
    private boolean wasRunningBeforeStopped = false;

    public StopwatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("tag", "onCreate() called");
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_stopwatch, container, false);
        Button startButton = (Button)view.findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        Button stopButton = (Button)view.findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);
        Button resetButton = (Button)view.findViewById(R.id.reset_button);
        resetButton.setOnClickListener(this);
        runTimer(view);
        return view;
    }

    @Override
    public void onStart() {
        Log.i("tag", "onStart() called");
        super.onStart();
        if (wasRunningBeforeStopped) {
            running = true;
        }
    }

    @Override
    public void onResume() {
        Log.i("tag", "onResume() called");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i("tag", "onPause() called");
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.i("tag", "onSaveInstanceState() called");
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
    }

    @Override
    public void onStop() {
        Log.i("tag", "onStop() called");
        super.onStop();
        wasRunningBeforeStopped = running;
        running = false;
    }

    @Override
    public void onDestroy() {
        Log.i("tag", "onDestroy() called");
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_button:
                onClickStart();
                break;
            case R.id.stop_button:
                onClickStop();
                break;
            case R.id.reset_button:
                onClickReset();
                break;
        }
    }

    //Start the stopwatch running when the Start button is clicked.
    private void onClickStart() {
        running = true;
    }

    //Stop the stopwatch running when the Stop button is clicked.
    private void onClickStop() {
        running = false;
    }

    //Reset the stopwatch when the Reset button is clicked.
    private void onClickReset() {
        running = false;
        seconds = 0;
    }

    private void runTimer(View view) {
        final TextView timeView = (TextView) view.findViewById(R.id.time_view);


        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, secs);

                timeView.setText(time);
                if (running) {
                    seconds++;
                }

                handler.postDelayed(this, 1000);
            }
        });
    }
}
