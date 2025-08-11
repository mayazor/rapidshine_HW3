package com.example.rapidshine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class CleanerSelectionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CleanerAdapter cleanerAdapter;
    private List<Cleaner> cleanerList;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_selection);

        // Initialize Firebase services
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();

        // Log screen view event
        Bundle screenParams = new Bundle();
        screenParams.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Cleaner Selection");
        screenParams.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "CleanerSelectionActivity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, screenParams);

        // Set up back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            // Log back button click
            mFirebaseAnalytics.logEvent("cleaner_selection_back_clicked", null);
            finish();
        });

        recyclerView = findViewById(R.id.cleanersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cleanerList = new ArrayList<>();
        cleanerAdapter = new CleanerAdapter(cleanerList, this);
        recyclerView.setAdapter(cleanerAdapter);

        // Try to load from Firestore first, with fallback to hardcoded data
        loadCleanersFromFirestore();
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
        Log.d("CleanerSelection", "User logging out");
        
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

    private void loadCleanersFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cleaners")
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                    cleanerList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        try {
                            Cleaner cleaner = document.toObject(Cleaner.class);
                            if (cleaner != null) {
                                cleanerList.add(cleaner);
                            }
                        } catch (Exception e) {
                            Log.e("CleanerSelection", "Error converting document: " + document.getId(), e);
                        }
                    }
                    cleanerAdapter.notifyDataSetChanged();
                    Log.d("CleanerSelection", "Successfully loaded " + cleanerList.size() + " cleaners from Firestore");
                } else {
                    Log.w("CleanerSelection", "Firestore failed or empty, loading hardcoded data");
                    loadHardcodedCleaners();
                }
            })
            .addOnFailureListener(e -> {
                Log.e("CleanerSelection", "Firestore connection failed", e);
                loadHardcodedCleaners();
            });
    }

    private void loadHardcodedCleaners() {
        cleanerList.clear();
        
        // Add hardcoded cleaners as fallback
        Cleaner anna = new Cleaner("Anna Johnson", 25.99, 4.8f, R.drawable.cleaner_anna);
        anna.setImageName("cleaner_anna");
        anna.setAge(28);
        anna.setReviews(78);
        anna.setExperienceYears(2);
        anna.setCleansCompleted(182);
        anna.setAbout("Meet Anna, your dedicated cleaner who not only excels at making homes spotless but also brings a cheerful presence into every room.");
        cleanerList.add(anna);

        Cleaner john = new Cleaner("John Davis", 30.00, 4.9f, R.drawable.cleaner_john);
        john.setImageName("cleaner_john");
        john.setAge(29);
        john.setReviews(171);
        john.setExperienceYears(3);
        john.setCleansCompleted(210);
        john.setAbout("John is a hardworking and reliable cleaner known for his efficiency and professionalism.");
        cleanerList.add(john);

        Cleaner sarah = new Cleaner("Sarah Smith", 20.50, 4.7f, R.drawable.cleaner_sarah);
        sarah.setImageName("cleaner_sarah");
        sarah.setAge(32);
        sarah.setReviews(80);
        sarah.setExperienceYears(5);
        sarah.setCleansCompleted(91);
        sarah.setAbout("Meet Sarah, an experienced and detail-oriented cleaner with a passion for creating a tidy and refreshing environment.");
        cleanerList.add(sarah);

        cleanerAdapter.notifyDataSetChanged();
        Log.d("CleanerSelection", "Loaded " + cleanerList.size() + " hardcoded cleaners");
    }
}
