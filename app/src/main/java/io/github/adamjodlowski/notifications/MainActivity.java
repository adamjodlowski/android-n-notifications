package io.github.adamjodlowski.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private int notificationId = 1;

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

            // NotificationCompat Builder takes care of backwards compatibility and
            // provides clean API to create rich notifications
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("Something important happened")
                    .setContentText("See the details")
                    .setContentIntent(detailsPendingIntent)
                    .setAutoCancel(true);

            // Obtain NotificationManager system service in order to show the notification
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId, mBuilder.build());

        }
    };
}
