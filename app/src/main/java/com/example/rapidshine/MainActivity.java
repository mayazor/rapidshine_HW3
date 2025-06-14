package com.example.rapidshine;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ServiceAdapter serviceAdapter;
    private List<Service> serviceList;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Log screen view event
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Home Screen");
        params.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity");
        mFirebaseAnalytics.logEvent("home_screen_opened", params);

        // Initialize RecyclerView with GridLayoutManager
        recyclerView = findViewById(R.id.servicesRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Initialize services with their specific images
        serviceList = new ArrayList<>();
        serviceList.add(new Service(
            "Deep Clean", 
            R.drawable.deep_clean,
            "A thorough deep cleaning service for your home.", 
            49.99
        ));
        serviceList.add(new Service(
            "Express Clean", 
            R.drawable.express_clean,
            "Quick and efficient cleaning in a short time.", 
            29.99
        ));
        serviceList.add(new Service(
            "Move-In Magic", 
            R.drawable.move_in_magic,
            "A complete clean-up service for your new home.", 
            59.99
        ));
        serviceList.add(new Service(
            "Standard Clean",
            R.drawable.standard_clean,
            "Regular maintenance cleaning for your home.",
            39.99
        ));
        serviceList.add(new Service(
            "After Party",
            R.drawable.after_pary_clean,
            "Post-event cleanup to restore your space.",
            54.99
        ));
        serviceList.add(new Service(
            "Guests are Coming",
            R.drawable.guests_are_coming,
            "Quick preparation clean for expected visitors.",
            34.99
        ));

        // Set up the adapter
        serviceAdapter = new ServiceAdapter(serviceList, this);
        recyclerView.setAdapter(serviceAdapter);

        // Log the data loading status
        if (serviceList.isEmpty()) {
            Log.e("MainActivity", "❌ ERROR: Service list is empty!");
        } else {
            Log.i("MainActivity", "✅ SUCCESS: Found " + serviceList.size() + " services.");
        }
    }
}

