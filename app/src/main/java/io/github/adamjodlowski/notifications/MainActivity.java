package io.github.adamjodlowski.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;
    public static int NOTIFICATION_ID = 1;
    public static final String KEY_NOTIFICATION_REPLY = "KEY_NOTIFICATION_REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(buttonClickListener);
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Create PendingIntent to take us to DetailsActivity
            // as a result of notification action
            Intent detailsIntent = new Intent(MainActivity.this, DetailsActivity.class);
            detailsIntent.putExtra("EXTRA_DETAILS_ID", 42);
            PendingIntent detailsPendingIntent = PendingIntent.getActivity(
                    MainActivity.this,
                    0,
                    detailsIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

            // Define PendingIntent for Reply action
            PendingIntent replyPendingIntent = null;
            // Call Activity on platforms that don't support DirectReply natively
            if (Build.VERSION.SDK_INT < 24) {
                replyPendingIntent = detailsPendingIntent;
            } else { // Call BroadcastReceiver on platforms supporting DirectReply
                replyPendingIntent = PendingIntent.getBroadcast(
                        MainActivity.this,
                        0,
                        new Intent(MainActivity.this, ReplyReceiver.class),
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
            }

            // Create RemoteInput and attach it to Notification Action
            RemoteInput remoteInput = new RemoteInput.Builder(KEY_NOTIFICATION_REPLY)
                    .setLabel("Reply")
                    .build();
            NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                    android.R.drawable.ic_menu_save, "Provide ID", replyPendingIntent)
                    .addRemoteInput(remoteInput)
                    .build();

            // NotificationCompat Builder takes care of backwards compatibility and
            // provides clean API to create rich notifications
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("Something important happened")
                    .setContentText("See the details")
                    .setAutoCancel(true)
                    .setContentIntent(detailsPendingIntent)
                    .addAction(replyAction)
                    .addAction(android.R.drawable.ic_menu_compass, "Details", detailsPendingIntent)
                    .addAction(android.R.drawable.ic_menu_directions, "Show Map", detailsPendingIntent);

            // Obtain NotificationManager system service in order to show the notification
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, mBuilder.build());

        }
    };
}
