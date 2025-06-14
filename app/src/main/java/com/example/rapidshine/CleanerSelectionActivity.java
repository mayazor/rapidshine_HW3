package com.example.rapidshine;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class CleanerSelectionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CleanerAdapter cleanerAdapter;
    private List<Cleaner> cleanerList;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_selection);

        // Initialize Firebase Analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cleaners")
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    cleanerList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        try {
                            Cleaner cleaner = document.toObject(Cleaner.class);
                            if (cleaner != null) {
                                cleanerList.add(cleaner);
                            }
                        } catch (Exception e) {
                            // Log the error
                            Log.e("CleanerSelection", "Error converting document: " + document.getId(), e);
                        }
                    }
                    cleanerAdapter.notifyDataSetChanged();
                    
                    // Log success
                    Log.d("CleanerSelection", "Successfully loaded " + cleanerList.size() + " cleaners");
                } else {
                    // Log the error
                    Log.e("CleanerSelection", "Error getting documents: ", task.getException());
                }
            });
    }
}
