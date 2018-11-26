package com.example.rkjc.news_app_2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;


public class NotificationUtils {
    private static final int NEWS_REMINDER_PENDING_INTENT_ID = 1;
    private static final int NEWS_REMINDER_NOTIFICATION_ID = 2;
    private static final String NEWS_REMINDER_NOTIFICATION_CHANNEL_ID = "update_notification_channel";

    private static final int ACTION_IGNORE_PENDING_INTENT = 10;

    public static void notifyNews(Context context){
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(NEWS_REMINDER_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder = new
                NotificationCompat.Builder(context, NEWS_REMINDER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.icon)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText("New articles")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        "New Articles"))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .addAction(ignoreReminderAction(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(NEWS_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);

        return PendingIntent.getActivity(context, NEWS_REMINDER_PENDING_INTENT_ID,
                startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void clearAllNotifications(Context context){
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

    }

    public static NotificationCompat.Action ignoreReminderAction(Context context){
        Intent ignoreReminderIntent = new Intent(context, NewsUpdateIntentService.class);
        ignoreReminderIntent.setAction(UpdateTask.ACTION_DISMISS_NOTIFICATION);

        PendingIntent ignoreReminderPendingIntent = PendingIntent
                .getService(context, ACTION_IGNORE_PENDING_INTENT, ignoreReminderIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action ignoreReminderAction = new NotificationCompat.Action(R.drawable.ng, "Close", ignoreReminderPendingIntent);
        return ignoreReminderAction;

    }

    private static Bitmap largeIcon(Context context){
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.icon);
        return largeIcon;
    }
}
