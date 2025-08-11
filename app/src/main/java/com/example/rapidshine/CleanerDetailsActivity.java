package com.example.rapidshine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.Toast;

public class CleanerDetailsActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_details);

        // Initialize Firebase services
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();

        // Log screen view event
        Bundle screenParams = new Bundle();
        screenParams.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Cleaner Details");
        screenParams.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "CleanerDetailsActivity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, screenParams);

        // Get cleaner name from intent
        String cleanerName = getIntent().getStringExtra("cleanerName");
        
        // Find views
        TextView titleText = findViewById(R.id.titleText);
        TextView ageText = findViewById(R.id.ageText);
        TextView reviewsText = findViewById(R.id.reviewsText);
        TextView experienceText = findViewById(R.id.experienceText);
        TextView cleansText = findViewById(R.id.cleansText);
        TextView aboutTitle = findViewById(R.id.aboutTitle);
        TextView aboutText = findViewById(R.id.aboutText);
        TextView hourlyRateText = findViewById(R.id.hourlyRateText);
        Button bookButton = findViewById(R.id.bookButton);
        Button chooseDifferentButton = findViewById(R.id.chooseDifferentButton);

        // Set cleaner-specific information
        if (cleanerName != null) {
            // Log cleaner view event
            Bundle cleanerParams = new Bundle();
            cleanerParams.putString("cleaner_name", cleanerName);
            mFirebaseAnalytics.logEvent("cleaner_details_viewed", cleanerParams);

            // Set cleaner information based on name
            switch (cleanerName) {
                case "Sarah Johnson":
                    titleText.setText(getString(R.string.cleaner_sarah_johnson));
                    ageText.setText(getString(R.string.age_format, 28));
                    reviewsText.setText(getString(R.string.reviews_format, 127));
                    experienceText.setText(getString(R.string.experience_format, 5));
                    cleansText.setText(getString(R.string.cleans_completed_format, 1234));
                    aboutTitle.setText(getString(R.string.about_format, "Sarah"));
                    aboutText.setText(getString(R.string.sarah_description));
                    hourlyRateText.setText(getString(R.string.hourly_rate_format, 25));
                    break;
                case "John Smith":
                    titleText.setText(getString(R.string.cleaner_john_smith));
                    ageText.setText(getString(R.string.age_format, 32));
                    reviewsText.setText(getString(R.string.reviews_format, 89));
                    experienceText.setText(getString(R.string.experience_format, 7));
                    cleansText.setText(getString(R.string.cleans_completed_format, 2156));
                    aboutTitle.setText(getString(R.string.about_format, "John"));
                    aboutText.setText(getString(R.string.john_description));
                    hourlyRateText.setText(getString(R.string.hourly_rate_format, 28));
                    break;
                case "Anna Davis":
                    titleText.setText(getString(R.string.cleaner_anna_davis));
                    ageText.setText(getString(R.string.age_format, 25));
                    reviewsText.setText(getString(R.string.reviews_format, 203));
                    experienceText.setText(getString(R.string.experience_format, 3));
                    cleansText.setText(getString(R.string.cleans_completed_format, 987));
                    aboutTitle.setText(getString(R.string.about_format, "Anna"));
                    aboutText.setText(getString(R.string.anna_description));
                    hourlyRateText.setText(getString(R.string.hourly_rate_format, 22));
                    break;
                default:
                    titleText.setText(cleanerName);
                    ageText.setText(getString(R.string.age_format, 30));
                    reviewsText.setText(getString(R.string.reviews_format, 150));
                    experienceText.setText(getString(R.string.experience_format, 4));
                    cleansText.setText(getString(R.string.cleans_completed_format, 1500));
                    aboutTitle.setText(getString(R.string.about_format, cleanerName));
                    aboutText.setText(getString(R.string.default_cleaner_description));
                    hourlyRateText.setText(getString(R.string.hourly_rate_format, 25));
                    break;
            }
        }

        // Set up button click listeners
        bookButton.setOnClickListener(v -> {
            // Log booking event
            Bundle bookingParams = new Bundle();
            bookingParams.putString("cleaner_name", cleanerName);
            mFirebaseAnalytics.logEvent("cleaner_booked", bookingParams);
            
            // Show booking confirmation
            Log.d("CleanerDetails", "Cleaner booked: " + cleanerName);
        });

        chooseDifferentButton.setOnClickListener(v -> {
            // Log choose different event
            Bundle chooseParams = new Bundle();
            chooseParams.putString("cleaner_name", cleanerName);
            mFirebaseAnalytics.logEvent("choose_different_cleaner", chooseParams);
            
            // Go back to cleaner selection
            finish();
        });
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
        Log.d("CleanerDetails", "User logging out");
        
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
} 