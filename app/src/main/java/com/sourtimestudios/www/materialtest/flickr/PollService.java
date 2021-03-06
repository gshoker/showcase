package com.sourtimestudios.www.materialtest.flickr;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Gallery;

//import com.sourtime.exhibits.Gallery;
//import com.sourtime.exhibits.R;

import com.sourtimestudios.www.materialtest.R;

import java.util.ArrayList;

/**
 * Created by user on 23/11/14.
 */
public class PollService extends IntentService {
    private static final String TAG = "PollService";

    private static final int POLL_INTERVAL = 15 * 1000; // 15 seconds
    public static final String PREF_IS_ALARM_ON = "isAlarmOn";

    public static final String ACTION_SHOW_NOTIFICATION =
            "com.sourtime.exhibits.SHOW_NOTIFICATION";

    public static final String PERM_PRIVATE = "com.sourtime.exhibits.PRIVATE";

    public PollService(){
        super(TAG);
//        Log.i(TAG, "PollService instantiated");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        Log.i(TAG, "PollService onhandleintent");

        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);


        @SuppressWarnings("deprecation")
        boolean isNetworkAvailable = cm.getBackgroundDataSetting()&&
                cm.getActiveNetworkInfo() != null;
        if(!isNetworkAvailable){
            Log.i(TAG, "bgdata: " + cm.getBackgroundDataSetting() + "cm.getActive: " + cm.getActiveNetworkInfo());
            return;
        }
        Log.i(TAG, "Received an intent: " + intent);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String query = prefs.getString(FlickrFetchr.PREF_SEARCH_QUERY, null);
        String lastResultId = prefs.getString(FlickrFetchr.PREF_LAST_RESULT_ID,null);

        ArrayList<GalleryItem> items;
        if(query != null){
            items = new FlickrFetchr().search(query);

        }else{
            items = new FlickrFetchr().fetchItems();
        }
        if(items.size() == 0)
            return;

        String resultId = items.get(0).getmId();

        if(!resultId.equals(lastResultId)){
            Log.i(TAG, "Got new result " + resultId);

            Resources r = getResources();
            PendingIntent pi = PendingIntent
                    .getActivity(this,0,new Intent(this, Gallery.class),0);

            Notification notification = new NotificationCompat.Builder(this)
                    .setTicker(r.getString(R.string.new_pictures_title))
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                    .setContentTitle(r.getString(R.string.new_pictures_title))
                    .setContentText(r.getString(R.string.new_pictures_text))
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build();

//            NotificationManager notificationManager = (NotificationManager)
//                    getSystemService(NOTIFICATION_SERVICE);
//
//            notificationManager.notify(0, notification);
//
//            sendBroadcast(new Intent(ACTION_SHOW_NOTIFICATION),PERM_PRIVATE);

         showBackgroundNotification(0,notification);


        }else{
            Log.i(TAG, "Got same result " + resultId);
        }

        prefs.edit()
                .putString(FlickrFetchr.PREF_LAST_RESULT_ID,resultId)
                .commit();
    }

    public static void setServiceAlarm(Context context, boolean isOn){
        Intent i  = new Intent(context, PollService.class);
        PendingIntent pi = PendingIntent.getService(context,0,i,0);

        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);

        if(isOn){
            alarmManager.setRepeating(AlarmManager.RTC,
                    System.currentTimeMillis(),POLL_INTERVAL,pi);
//            Log.i(TAG, ""+POLL_INTERVAL);
        }else{
            alarmManager.cancel(pi);
            pi.cancel();
        }

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_IS_ALARM_ON,isOn)
                .commit();
    }

    public static boolean isServiceAlarmOn(Context context){
        Intent i = new Intent(context, PollService.class);
        PendingIntent pi  = PendingIntent.getService(context,0,i,PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }

    void showBackgroundNotification(int requestCode, Notification notification){
        Intent i = new Intent(ACTION_SHOW_NOTIFICATION);
        i.putExtra("REQUEST_CODE",requestCode);
        i.putExtra("NOTIFICATION",notification);

        sendOrderedBroadcast(i,PERM_PRIVATE,null,null,
                Activity.RESULT_OK,null,null);
    }
}
