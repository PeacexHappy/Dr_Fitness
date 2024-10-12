package com.example.drfitness.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drfitness.R;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {

    private Context context;
    private int[] images = {R.drawable.onboard1, R.drawable.onboard2, R.drawable.onboard3};
    private String[] quotes = {"Quote 1", "Quote 2", "Quote 3"};

    public OnboardingAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.onboarding_item, parent, false);
        return new OnboardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        // Check bounds to avoid ArrayIndexOutOfBoundsException
        if (position < images.length) {
            holder.imageView.setImageResource(images[position]);
            holder.textView.setText(quotes[position]);
        }


        // Apply animation to the second image
        if (position == 1) {
            holder.imageView.setTranslationX(holder.itemView.getWidth()); // Initially off-screen

            holder.imageView.animate()
                    .translationX(0f) // Slide in from right
                    .setDuration(500) // Animation duration
                    .start();
        } else {
            // Reset translation for other images
            holder.imageView.setTranslationX(0f);
        }
    }

    @Override
    public int getItemCount() {
        return images.length; // Return the number of items in the array
    }

    public static class OnboardingViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView; // Import ImageView
        TextView textView;

        public OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageOnboarding);
            textView = itemView.findViewById(R.id.tvDescription);
        }
    }
}