package com.example.rapidshine;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ServiceDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        // Get data from intent
        String cleanerName = getIntent().getStringExtra("cleanerName");
        double hourlyRate = getIntent().getDoubleExtra("cleanerHourlyRate", 0.0);
        float rating = getIntent().getFloatExtra("cleanerRating", 0.0f);

        // Set up views (you'll need to create these in your layout)
        TextView nameTextView = findViewById(R.id.cleanerNameDetail);
        TextView rateTextView = findViewById(R.id.hourlyRateDetail);
        TextView ratingTextView = findViewById(R.id.ratingDetail);

        // Display the data
        nameTextView.setText(cleanerName);
        rateTextView.setText(String.format("$%.2f per hour", hourlyRate));
        ratingTextView.setText(String.format("Rating: %.1f/5.0", rating));
    }
}


