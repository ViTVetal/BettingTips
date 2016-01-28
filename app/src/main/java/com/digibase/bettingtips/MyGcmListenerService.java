package com.digibase.bettingtips;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.digibase.bettingtips.ui.activities.MainActivity;
import com.google.android.gms.gcm.GcmListenerService;

import digibase.com.bettingtips.R;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "myLogs";
    private SharedPreferences sharedPreferences;
    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);
        int id = data.getInt("id");
        int categoryId = data.getInt("category");

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        if(sharedPreferences.getBoolean(QuickstartPreferences.NOTIFICATION, true))
            sendNotification(message, id, categoryId);
        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message, int id, int categoryId) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("categoryId", categoryId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_title_bar)
                .setLargeIcon(((BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.ic_launcher)).getBitmap())
                .setContentTitle(getResources().getString(R.string.notific_title))
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        if(sharedPreferences.getBoolean(QuickstartPreferences.NOTIFICATION_SOUND, true))
            notificationBuilder.setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(id /* ID of notification */, notificationBuilder.build());
    }
}