/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.youngyeehomies.hssapp.Control;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.youngyeehomies.hssapp.Boundary.LoginActivity;
import com.example.youngyeehomies.hssapp.Boundary.ViewAppointmentDetailsActivity;
import com.example.youngyeehomies.hssapp.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 *
 * Created by Unto Kuuranne.
 */
public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 0;
    private NotificationManager mNotificationManager;

    SessionManager session;

    public GcmIntentService() {
        super("GcmIntentService");
    }
    public static final String TAG = "HSS";

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        // The getMessageType() intent parameter must be the intent you received in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM will be
             * extended in the future with new message types, just ignore any message types you're
             * not interested in, or that you don't recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error: " + extras.toString(), extras);
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server: " + extras.toString(), extras);
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // This loop represents the service doing some work.
                /*
                for (int i = 0; i < 5; i++) {
                    Log.i(TAG, "Working... " + (i + 1)
                            + "/5 @ " + SystemClock.elapsedRealtime());
                    try {
                        Thread.sleep(200); // NOTE: remove?
                    } catch (InterruptedException e) {
                    }
                }
                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
                */
                // Post notification of received message.
                //sendNotification("Received: " + extras.toString());
                sendNotification(extras.getString("message", "No message set :("), extras);
                Log.i(TAG, "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    /*
    ** Put the message into a notification and post it.
    ** This is just one simple example of what you might choose to do with
    ** a GCM message.
    */

    private void sendNotification(String msg, Bundle extras) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        int uniqueID = extras.getInt("android.support.content.wakelockid", NOTIFICATION_ID);
        PendingIntent contentIntent;

        if(session == null) {
            session = new SessionManager(getApplicationContext());
        }

        String accountToken = session.getUserToken();

        if(accountToken.equals("notLoggedIn") && extras.getString("type", "").equals("reminder")) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.putExtra("AppointmentID", extras.getString("apptID", "1"));
            contentIntent = PendingIntent.getActivity(getApplicationContext(), uniqueID,
                    loginIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        else if(accountToken.equals("notLoggedIn") && extras.getString("type", "").equals("instructions")) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.putExtra("AppointmentID", extras.getString("apptID", "1"));
            contentIntent = PendingIntent.getActivity(getApplicationContext(), uniqueID,
                    loginIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        else if(accountToken.equals("notLoggedIn")) {
            contentIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, LoginActivity.class), 0);
        }
        else if(extras.getString("type", "").equals("reminder")) {
            Intent viewDetailsIntent = new Intent(this, ViewAppointmentDetailsActivity.class);
            Log.i(TAG, "reminder apptID is " + extras.getString("apptID"));
            int parsed = Integer.parseInt(extras.getString("apptID", "1"));
            Log.i(TAG, "parsed is " + extras.getString("apptID"));
            viewDetailsIntent.putExtra("AppointmentID", parsed);
            contentIntent = PendingIntent.getActivity(getApplicationContext(), uniqueID,
                    viewDetailsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        else if(extras.getString("type", "").equals("instructions")) {
            Intent viewDetailsIntent = new Intent(this, ViewAppointmentDetailsActivity.class);
            Log.i(TAG, "instruction apptID is " + extras.getString("apptID"));
            viewDetailsIntent.putExtra("AppointmentID", Integer.parseInt(extras.getString("apptID", "1")));
            contentIntent = PendingIntent.getActivity(getApplicationContext(), uniqueID,
                    viewDetailsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        else {
            contentIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, LoginActivity.class), 0);
        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("HSS")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);


        Notification nf = mBuilder.build();
        nf.flags |= Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(uniqueID, nf);

    }
}
