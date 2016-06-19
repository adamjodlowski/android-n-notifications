package io.github.adamjodlowski.notifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Log.d("PLAYGROUND", "Details ID: " + getIntent().getIntExtra("EXTRA_DETAILS_ID", -1));
    }
}
