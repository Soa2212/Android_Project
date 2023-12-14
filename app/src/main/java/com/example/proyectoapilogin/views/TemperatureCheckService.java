package com.example.proyectoapilogin.views;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.proyectoapilogin.R;

public class TemperatureCheckService extends Service {
    private static final String CHANNEL_ID = "temperatura_excedida";
    private boolean isNotificationSent = false;
    private final Handler handler = new Handler();
    private final int delayMillis = 1000;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkTemperature();
                handler.postDelayed(this, delayMillis);
            }
        }, delayMillis);
        return START_STICKY; // El servicio se reiniciará si el sistema lo mata
    }

    private void checkTemperature() {
        long longValue = sharedPreferences.getLong("temperatura", Double.doubleToLongBits(30.0));
        double maxTemperature = Double.longBitsToDouble(longValue);
        long sensorTemperature = sharedPreferences.getLong("sensorTemperature", Double.doubleToLongBits(30.0));
        double temperaturaSensor = Double.longBitsToDouble(sensorTemperature);
        if (temperaturaSensor > maxTemperature) {
            if (!isNotificationSent) {
                Log.d("Notificacion", "Se envió la notificación");
                sendTemperatureNotification();
                isNotificationSent = true;
            }
        } else {
            isNotificationSent = false;
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendTemperatureNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.foco_1)
                .setContentTitle("TEMPERATURA EXCEDIDA")
                .setContentText("Se requieren acciones en la habitación")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        int notificationId = (int) System.currentTimeMillis();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            notificationManager.notify(notificationId, builder.build());
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // No es necesario para este servicio
    }
}