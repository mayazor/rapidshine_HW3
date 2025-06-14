package com.example.rapidshine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import java.util.List;

public class CleanerAdapter extends RecyclerView.Adapter<CleanerAdapter.CleanerViewHolder> {

    private List<Cleaner> cleanerList;
    private Context context;

    public CleanerAdapter(List<Cleaner> cleanerList, Context context) {
        this.cleanerList = cleanerList;
        this.context = context;
    }

    @NonNull
    @Override
    public CleanerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cleaner_item, parent, false);
        return new CleanerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CleanerViewHolder holder, int position) {
        Cleaner cleaner = cleanerList.get(position);
        
        // Set text information
        holder.cleanerName.setText(cleaner.getName());
        holder.hourlyRate.setText(String.format("%.2f₪", cleaner.getHourlyRate()));
        holder.ratingBar.setRating(cleaner.getRating());

        // Map imageName to drawable resource
        int imageResId = context.getResources().getIdentifier(
            cleaner.getImageName(), "drawable", context.getPackageName()
        );
        if (imageResId != 0) {
            Glide.with(context)
                .load(imageResId)
                .apply(new RequestOptions()
                    .centerCrop()
                    .transform(new CircleCrop()))
                .into(holder.cleanerImage);
        } else {
            // Optionally set a default image if not found
            holder.cleanerImage.setImageResource(R.mipmap.ic_launcher);
        }

        // Handle book button click
        holder.bookButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, CleanerDetailsActivity.class);
            intent.putExtra("cleanerName", cleaner.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cleanerList.size();
    }

    static class CleanerViewHolder extends RecyclerView.ViewHolder {
        TextView cleanerName, hourlyRate;
        RatingBar ratingBar;
        ImageView cleanerImage;
        MaterialButton bookButton;

        public CleanerViewHolder(@NonNull View itemView) {
            super(itemView);
            cleanerName = itemView.findViewById(R.id.cleanerName);
            hourlyRate = itemView.findViewById(R.id.hourlyRate);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            cleanerImage = itemView.findViewById(R.id.profileImage);
            bookButton = itemView.findViewById(R.id.bookButton);
        }
    }
}
