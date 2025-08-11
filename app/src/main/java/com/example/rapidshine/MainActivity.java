package com.example.rapidshine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;
import java.util.List;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements ServicesRepository.ServicesCallback {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private ServiceAdapter serviceAdapter;
    private List<Service> serviceList;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    private ServicesRepository servicesRepository;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase services
        mAuth = FirebaseAuth.getInstance();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Initialize service list
        serviceList = new ArrayList<>();

        // Initialize views
        recyclerView = findViewById(R.id.servicesRecyclerView);
        progressBar = findViewById(R.id.progressBar);

        // Set up logout button
        MaterialButton logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> logout());

        // Set up camera button
        MaterialButton cameraButton = findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CameraActivity.class);
            startActivity(intent);
        });

        // Set up RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        serviceAdapter = new ServiceAdapter(serviceList, this);
        recyclerView.setAdapter(serviceAdapter);

        // Log screen view event
        Bundle screenParams = new Bundle();
        screenParams.putString(FirebaseAnalytics.Param.SCREEN_NAME, "Home Screen");
        screenParams.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, screenParams);

        // Load services from Firestore
        servicesRepository = new ServicesRepository();
        servicesRepository.setCallback(this);
        servicesRepository.loadServices();
    }

    private void loadServices() {
        Log.d(TAG, "Loading services from Firestore...");
        showLoading(true);
        servicesRepository.loadServices();
    }

    @Override
    public void onServicesLoaded(List<Service> services) {
        Log.d(TAG, "Services loaded successfully: " + services.size() + " services");
        
        serviceList.clear();
        serviceList.addAll(services);
        serviceAdapter.notifyDataSetChanged();
        
        showLoading(false);
        
        // Log the data loading status
        if (serviceList.isEmpty()) {
            Log.e(TAG, "❌ ERROR: Service list is empty!");
            Toast.makeText(this, "No services available", Toast.LENGTH_SHORT).show();
        } else {
            Log.i(TAG, "✅ SUCCESS: Found " + serviceList.size() + " services.");
        }
    }

    @Override
    public void onServicesError(String error) {
        Log.e(TAG, "Error loading services: " + error);
        showLoading(false);
        Toast.makeText(this, "Failed to load services: " + error, Toast.LENGTH_LONG).show();
        
        // Fallback to hardcoded data if Firestore fails
        loadHardcodedServices();
    }

    private void loadHardcodedServices() {
        Log.w(TAG, "Loading hardcoded services as fallback");
        serviceList.clear();
        
        // Add services with debug logging
        Service deepClean = new Service("Deep Clean", R.drawable.deep_clean, "A thorough deep cleaning service for your home.", 49.99);
        serviceList.add(deepClean);
        Log.d(TAG, "Added service: " + deepClean.getName());
        
        Service expressClean = new Service("Express Clean", R.drawable.express_clean, "Quick and efficient cleaning in a short time.", 29.99);
        serviceList.add(expressClean);
        Log.d(TAG, "Added service: " + expressClean.getName());
        
        Service moveInMagic = new Service("Move-In Magic", R.drawable.move_in_magic, "A complete clean-up service for your new home.", 59.99);
        serviceList.add(moveInMagic);
        Log.d(TAG, "Added service: " + moveInMagic.getName());
        
        Service standardClean = new Service("Standard Clean", R.drawable.standard_clean, "Regular maintenance cleaning for your home.", 39.99);
        serviceList.add(standardClean);
        Log.d(TAG, "Added service: " + standardClean.getName());
        
        Service afterParty = new Service("After Party", R.drawable.after_pary_clean, "Post-event cleanup to restore your space.", 54.99);
        serviceList.add(afterParty);
        Log.d(TAG, "Added service: " + afterParty.getName());
        
        Service guestsComing = new Service("Guests are Coming", R.drawable.guests_are_coming, "Quick preparation clean for expected visitors.", 34.99);
        serviceList.add(guestsComing);
        Log.d(TAG, "Added service: " + guestsComing.getName());
        
        Log.i(TAG, "✅ FALLBACK: Loaded " + serviceList.size() + " hardcoded services.");
        
        // Notify adapter with debug logging
        if (serviceAdapter != null) {
            serviceAdapter.notifyDataSetChanged();
            Log.d(TAG, "✅ Adapter notified of data change");
        } else {
            Log.e(TAG, "❌ ERROR: serviceAdapter is null!");
        }
        
        // Check if RecyclerView is properly set up
        if (recyclerView != null) {
            Log.d(TAG, "✅ RecyclerView is initialized");
            Log.d(TAG, "RecyclerView adapter: " + recyclerView.getAdapter());
            Log.d(TAG, "RecyclerView layout manager: " + recyclerView.getLayoutManager());
        } else {
            Log.e(TAG, "❌ ERROR: recyclerView is null!");
        }
    }

    private void showLoading(boolean show) {
        if (progressBar != null) {
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
        if (recyclerView != null) {
            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        
        // Check if user is signed in and verified
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // User not signed in, go to login
            Log.d(TAG, "No user signed in, redirecting to login");
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            // TEMPORARY: Skip email verification check for testing
            Log.d(TAG, "User signed in, skipping email verification check for testing: " + currentUser.getEmail());
            
            // TODO: Uncomment when ready for production
            // if (!currentUser.isEmailVerified()) {
            //     // User signed in but email not verified, go to verification
            //     Log.d(TAG, "User signed in but email not verified, redirecting to verification");
            //     Intent intent = new Intent(this, EmailVerificationActivity.class);
            //     intent.putExtra("email", currentUser.getEmail());
            //     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //     startActivity(intent);
            //     finish();
            // } else {
            //     // User is signed in and verified, stay on main screen
            //     Log.d(TAG, "User signed in and verified: " + currentUser.getEmail());
            // }
        }
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

