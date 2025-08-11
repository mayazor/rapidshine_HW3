package com.example.rapidshine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.Toast;

public class LoadingCleanersActivity extends AppCompatActivity {

    private static final long LOADING_DELAY = 6000; // 6 seconds
    private Handler loadingHandler;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_cleaners);

        // Initialize Firebase services
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();

        // Log screen view event
        Bundle screenParams = new Bundle();
        screenParams.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Loading Cleaners");
        screenParams.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "LoadingCleanersActivity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, screenParams);

        // Get the service details from the intent
        String serviceName = getIntent().getStringExtra("serviceName");
        String serviceDescription = getIntent().getStringExtra("serviceDescription");
        double servicePrice = getIntent().getDoubleExtra("servicePrice", 0.0);
        boolean bringSupplies = getIntent().getBooleanExtra("bringSupplies", false);

        // Log service selection event
        Bundle serviceParams = new Bundle();
        serviceParams.putString("service_name", serviceName);
        serviceParams.putDouble("service_price", servicePrice);
        serviceParams.putBoolean("bring_supplies", bringSupplies);
        mFirebaseAnalytics.logEvent("service_selected", serviceParams);

        // Set up back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            // Log back button click
            mFirebaseAnalytics.logEvent("loading_cleaners_back_clicked", null);
            finish();
        });

        // Start loading timer
        loadingHandler = new Handler(Looper.getMainLooper());
        loadingHandler.postDelayed(() -> {
            // Navigate to cleaner selection after loading delay
            Intent intent = new Intent(LoadingCleanersActivity.this, CleanerSelectionActivity.class);
            intent.putExtra("serviceName", serviceName);
            intent.putExtra("serviceDescription", serviceDescription);
            intent.putExtra("servicePrice", servicePrice);
            intent.putExtra("bringSupplies", bringSupplies);
            startActivity(intent);
            finish();
        }, LOADING_DELAY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        Log.d("LoadingCleaners", "User logging out");
        
        // Cancel the loading timer
        if (loadingHandler != null) {
            loadingHandler.removeCallbacksAndMessages(null);
        }
        
        // Sign out from Firebase
        mAuth.signOut();
        
        // Log logout event
        Bundle logoutParams = new Bundle();
        logoutParams.putString("user_email", mAuth.getCurrentUser() != null ? mAuth.getCurrentUser().getEmail() : "unknown");
        mFirebaseAnalytics.logEvent("user_logout", logoutParams);
        
        // Show logout message
        Toast.makeText(this, getString(R.string.logged_out_success), Toast.LENGTH_SHORT).show();
        
        // Navigate back to login
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up handler to prevent memory leaks
        if (loadingHandler != null) {
            loadingHandler.removeCallbacksAndMessages(null);
        }
    }
} 