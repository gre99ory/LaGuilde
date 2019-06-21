package com.ggpi.laguilde.tools;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.ggpi.laguilde.R;
import com.ggpi.laguilde.activities.EventsActivity;
import com.ggpi.laguilde.activities.SplashActivity;
import com.ggpi.laguilde.models.GGEventModel;
import com.ggpi.laguilde.models.GGGlobals;

import static android.support.v4.app.NotificationCompat.*;

/*
 * Helper Class for notifications management
 */
public class Notificator {

    static public void createNotificationChannel(Context ctx) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = ctx.getString(R.string.channel_name);
            String description = ctx.getString(R.string.channel_description);
            String CHANNEL_ID =  ctx.getString(R.string.channel_id);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = ctx.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    static public void scheduleNotificationForEvent( GGEventModel event ) {
        scheduleNotification( getNotificationForEvent( event ), event.getDate().getTime() );
    }


    /*
     * Returns the notification related to the Event
     */
    static private Notification getNotificationForEvent(GGEventModel event) {
        Context ctx = SplashActivity.getAppContext();

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(ctx, EventsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, 0);

        // Builder builder = new Builder(ctx, channel_id)
        Builder builder;
        builder = new Builder(ctx, ctx.getResources().getString(R.string.channel_id) )
                .setSmallIcon(R.drawable.laguilde_logo)
                .setContentTitle(event.getTitle())
                .setContentText(event.getDescription())
                .setPriority(PRIORITY_DEFAULT)
                .setGroup("LaGuilde")
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                //.setTimeoutAfter(5000)
                // .setUsesChronometer(true)
                .setShowWhen(true)
                //.setWhen( System.currentTimeMillis() - 1000*60*60 )
                .setWhen( event.getDate().getTime() )
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        ;

        // todo: ajouter une image

        return builder.build();
    }


    static private void scheduleNotification(Notification notification, long delay) {
        Context ctx = SplashActivity.getAppContext();

        Intent notificationIntent = new Intent(ctx, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC, delay - AlarmManager.INTERVAL_HALF_HOUR, pendingIntent);

        /*
        ELAPSED_REALTIME
        ELAPSED_REALTIME_WAKEUP
        RTC
        RTC_WAKEUP

        /*
        Uri Unotification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(ctx, Unotification);
        r.play();r.play();
        */
    }

}
