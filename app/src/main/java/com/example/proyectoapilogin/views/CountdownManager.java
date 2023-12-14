package com.example.proyectoapilogin.views;

import android.os.CountDownTimer;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class CountdownManager {
    private static CountdownManager instance;
    private static final String COUNTDOWN_PREFS = "countdown_prefs";
    private static final String COUNTDOWN_TIME = "countdown_time";

    private CountdownManager(Context context) {
        // Initialize your manager, if needed
    }

    public static CountdownManager getInstance(Context context) {
        if (instance == null) {
            instance = new CountdownManager(context);
        }
        return instance;
    }

    public void startCountdown(long millisInFuture, final CountdownListener listener) {
        new CountDownTimer(millisInFuture, 1000) {
            public void onTick(long millisUntilFinished) {
                listener.onTick(millisUntilFinished);
            }

            public void onFinish() {
                listener.onFinish();
            }
        }.start();
    }

    public interface CountdownListener {
        void onTick(long millisUntilFinished);

        void onFinish();
    }
}
