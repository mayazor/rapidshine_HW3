package com.example.rapidshine;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.analytics.FirebaseAnalytics;

public class CleanerDetailsActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_details);

        // Initialize Firebase Analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

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

            switch (cleanerName) {
                case "Anna Johnson":
                    ageText.setText("Age: 28");
                    reviewsText.setText("78 reviews");
                    experienceText.setText("Years of Experience: 2 years");
                    cleansText.setText("Cleans Completed: 182 cleans");
                    hourlyRateText.setText("25.99₪ per hour");
                    aboutTitle.setText("About Anna:");
                    aboutText.setText("Meet Anna, your dedicated cleaner who not only excels at making homes spotless but also brings a cheerful presence into every room. Anna is a fitness enthusiast who loves yoga and believes in cleansing both the home and mind. Her attention to detail ensures a pristine environment where you can relax and rejuvenate.");
                    bookButton.setText("Book Anna Now");
                    break;

                case "John Davis":
                    ageText.setText("Age: 29");
                    reviewsText.setText("171 reviews");
                    experienceText.setText("Years of Experience: 3 years");
                    cleansText.setText("Cleans Completed: 210 cleans");
                    hourlyRateText.setText("30.00₪ per hour");
                    aboutTitle.setText("About John:");
                    aboutText.setText("John is a hardworking and reliable cleaner known for his efficiency and professionalism. With three years in the industry, he specializes in quick yet thorough cleanings, ensuring clients always come home to a fresh and tidy space. John takes pride in his attention to detail, whether it's dusting, vacuuming, or deep-cleaning kitchens and bathrooms. In his free time, he enjoys hiking and believes that a clean home leads to a clear mind.");
                    bookButton.setText("Book John Now");
                    break;

                case "Sarah Smith":
                    ageText.setText("Age: 32");
                    reviewsText.setText("80 reviews");
                    experienceText.setText("Years of Experience: 5 years");
                    cleansText.setText("Cleans Completed: 91 cleans");
                    hourlyRateText.setText("20.50₪ per hour");
                    aboutTitle.setText("About Sarah:");
                    aboutText.setText("Meet Sarah, an experienced and detail-oriented cleaner with a passion for creating a tidy and refreshing environment. With five years of experience, Sarah has mastered the art of deep cleaning, ensuring every home she touches sparkles. She has a keen eye for organization and enjoys helping families maintain a stress-free space. Outside of work, Sarah loves gardening and believes in using eco-friendly cleaning products to keep homes safe and sustainable.");
                    bookButton.setText("Book Sarah Now");
                    break;
            }
        }

        // Handle "Choose a Different Cleaner" button click
        chooseDifferentButton.setOnClickListener(v -> {
            // Log button click event
            Bundle params = new Bundle();
            params.putString("cleaner_name", cleanerName);
            mFirebaseAnalytics.logEvent("choose_different_cleaner_clicked", params);
            finish(); // This will go back to the cleaner selection screen
        });

        // Handle "Book Now" button click
        bookButton.setOnClickListener(v -> {
            // Log button click event
            Bundle params = new Bundle();
            params.putString("cleaner_name", cleanerName);
            mFirebaseAnalytics.logEvent("book_cleaner_clicked", params);
            // Add your booking logic here
        });
    }
} 