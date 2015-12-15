package ir.geekart.sleepservice;

/**
 * Created by sajjad on 12/14/15.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.preference.PreferenceManager;

import java.io.IOException;

public class ExampleService extends Service {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Notification n;
    NotificationManager notificationManager;
    private int notifId=292312;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate() {
        // Init the SharedPreferences and Editor
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

        // Set up the buffering notification
        notificationManager = (NotificationManager) getApplicationContext()
                .getSystemService(NOTIFICATION_SERVICE);
        Context context = getApplicationContext();

        String notifTitle = context.getResources().getString(R.string.app_name);
        String notifMessage = context.getResources().getString(R.string.buffering);




        Notification.Builder builder = new Notification.Builder(context);
        builder.setAutoCancel(false);
        builder.setTicker(notifMessage);
        builder.setContentTitle(notifTitle);
        builder.setContentText("Please Wait");
        builder.setSmallIcon(android.R.drawable.ic_btn_speak_now);
        builder.setOngoing(true);
        builder.setNumber(100);

        n = builder.getNotification();
        notificationManager.notify(notifId, n);


    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStart(Intent intent, int startId) {

        notificationManager.cancelAll();

        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setAutoCancel(false);
        builder.setTicker("Service Started");
        builder.setContentTitle("Example Service");
        builder.setContentText("Service Started");
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        builder.setOngoing(true);
        builder.setNumber(100);

        n = builder.getNotification();
        notificationManager.notify(notifId, n);
    }

    @Override
    public void onDestroy() {
        notificationManager.cancelAll();
    }

}
