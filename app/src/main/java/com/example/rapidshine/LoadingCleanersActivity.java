package com.example.rapidshine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingCleanersActivity extends AppCompatActivity {

    private static final long LOADING_DELAY = 6000; // 6 seconds
    private Handler loadingHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_cleaners);

        // Get the service details from the intent
        String serviceName = getIntent().getStringExtra("serviceName");
        String serviceDescription = getIntent().getStringExtra("serviceDescription");
        double servicePrice = getIntent().getDoubleExtra("servicePrice", 0.0);
        boolean bringSupplies = getIntent().getBooleanExtra("bringSupplies", false);

        // Set up back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            if (loadingHandler != null) {
                loadingHandler.removeCallbacksAndMessages(null);
            }
            finish();
        });

        // Delay for 6 seconds, then start CleanerSelectionActivity
        loadingHandler = new Handler(Looper.getMainLooper());
        loadingHandler.postDelayed(() -> {
            Intent intent = new Intent(this, CleanerSelectionActivity.class);
            // Pass along all the details
            intent.putExtra("serviceName", serviceName);
            intent.putExtra("serviceDescription", serviceDescription);
            intent.putExtra("servicePrice", servicePrice);
            intent.putExtra("bringSupplies", bringSupplies);
            startActivity(intent);
            finish(); // Close this activity
        }, LOADING_DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loadingHandler != null) {
            loadingHandler.removeCallbacksAndMessages(null);
        }
    }
} 