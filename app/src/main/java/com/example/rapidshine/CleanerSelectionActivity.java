package com.example.rapidshine;

import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CleanerSelectionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CleanerAdapter cleanerAdapter;
    private List<Cleaner> cleanerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_selection);

        // Set up back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.cleanersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize cleaner list with PNG profile images
        cleanerList = new ArrayList<>();
        cleanerList.add(new Cleaner(
            "Anna Johnson", 
            25.99, 
            4.8f, 
            R.drawable.cleaner_anna  // Using the PNG image for Anna
        ));
        cleanerList.add(new Cleaner(
            "Sarah Smith", 
            20.50, 
            4.5f, 
            R.drawable.cleaner_sarah  // Using the PNG image for Sarah
        ));
        cleanerList.add(new Cleaner(
            "John Davis", 
            30.00, 
            5.0f, 
            R.drawable.cleaner_john  // Using the PNG image for John
        ));

        cleanerAdapter = new CleanerAdapter(cleanerList, this);
        recyclerView.setAdapter(cleanerAdapter);
    }
}
