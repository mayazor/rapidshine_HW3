package com.example.rapidshine;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import android.os.Bundle;
import android.util.Log;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private List<Service> serviceList;
    private Context context;
    private FirebaseAnalytics mFirebaseAnalytics;

    public ServiceAdapter(List<Service> serviceList, Context context) {
        this.serviceList = serviceList;
        this.context = context;
        this.mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = serviceList.get(position);
        Log.d("ServiceAdapter", "Binding service at position " + position + ": " + service.getName());
        
        // Set text information
        holder.serviceName.setText(service.getName());
        Log.d("ServiceAdapter", "Set service name: " + service.getName());

        // Load and display the image
        Glide.with(context)
            .load(service.getImageResource())
            .into(holder.serviceImage);
        Log.d("ServiceAdapter", "Loading image for service: " + service.getName() + " with resource: " + service.getImageResource());

        // Handle click events
        holder.itemView.setOnClickListener(v -> {
            Log.d("ServiceAdapter", "Service clicked: " + service.getName());
            // Log service click event
            Bundle params = new Bundle();
            params.putString("service_name", service.getName());
            params.putDouble("service_price", service.getPrice());
            mFirebaseAnalytics.logEvent("service_clicked", params);
            
            showSuppliesDialog(service);
        });
    }

    private void showSuppliesDialog(Service service) {
        // Create a styled message with different text sizes
        String title = "Before we look for a cleaner available in your area-\n";
        String question = "Will you need us to bring cleaning products?";
        SpannableString message = new SpannableString(title + question);
        message.setSpan(new RelativeSizeSpan(0.9f), 0, title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        message.setSpan(new RelativeSizeSpan(1.1f), title.length(), title.length() + question.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_Supplies);
        builder.setMessage(message)
            .setPositiveButton("Yes, please!", (dialog, which) -> {
                // Log supplies selection event
                Bundle params = new Bundle();
                params.putString("service_name", service.getName());
                params.putBoolean("bring_supplies", true);
                mFirebaseAnalytics.logEvent("supplies_selection", params);

                Intent intent = new Intent(context, LoadingCleanersActivity.class);
                intent.putExtra("serviceName", service.getName());
                intent.putExtra("serviceDescription", service.getDescription());
                intent.putExtra("servicePrice", service.getPrice());
                intent.putExtra("bringSupplies", true);
                context.startActivity(intent);
            })
            .setNegativeButton("No, I've got it covered!", (dialog, which) -> {
                // Log supplies selection event
                Bundle params = new Bundle();
                params.putString("service_name", service.getName());
                params.putBoolean("bring_supplies", false);
                mFirebaseAnalytics.logEvent("supplies_selection", params);

                Intent intent = new Intent(context, LoadingCleanersActivity.class);
                intent.putExtra("serviceName", service.getName());
                intent.putExtra("serviceDescription", service.getDescription());
                intent.putExtra("servicePrice", service.getPrice());
                intent.putExtra("bringSupplies", false);
                context.startActivity(intent);
            })
            .setCancelable(true);

        builder.show();
    }

    @Override
    public int getItemCount() {
        int count = serviceList.size();
        Log.d("ServiceAdapter", "getItemCount called, returning: " + count);
        return count;
    }

    static class ServiceViewHolder extends RecyclerView.ViewHolder {
        ImageView serviceImage;
        TextView serviceName;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceImage = itemView.findViewById(R.id.serviceImage);
            serviceName = itemView.findViewById(R.id.serviceName);
        }
    }
}
