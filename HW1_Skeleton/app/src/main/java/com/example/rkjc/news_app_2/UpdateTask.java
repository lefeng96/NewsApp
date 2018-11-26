package com.example.rkjc.news_app_2;

import android.content.Context;
import android.util.Log;


public class UpdateTask {

    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
    public static final String UPDATE_NEWS = "update_news";

    public static void executeTask(Context context, String action){
        if(ACTION_DISMISS_NOTIFICATION.equals(action)){
            NotificationUtils.clearAllNotifications(context);
        } else if(UPDATE_NEWS.equals(action)){
            UpdateTask instance = new UpdateTask();
            instance.update();
            NotificationUtils.notifyNews(context);
        }

    }

        public void update(){
        }
}
