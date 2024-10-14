package com.example.drfitness;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.drfitness.adapters.OnboardingAdapter;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPagerOnboarding;
    private LinearLayout dotIndicatorLayout;
    private Handler slideHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        // Initialize ViewPager2 and LinearLayout for dots
        viewPagerOnboarding = findViewById(R.id.viewPagerOnboarding);
        dotIndicatorLayout = findViewById(R.id.dotIndicatorLayout);

        // Set the adapter for ViewPager2
        OnboardingAdapter adapter = new OnboardingAdapter(this);
        viewPagerOnboarding.setAdapter(adapter);

        // Add PageTransformer for animation
        viewPagerOnboarding.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                if (position < -1) { // [-Infinity,-1)
                    page.setAlpha(0f);
                } else if (position <= 0) { // [-1,0]
                    page.setAlpha(1f);
                    page.setTranslationX(0f);
                    page.setScaleX(1f);
                    page.setScaleY(1f);
                } else if (position <= 1) { // (0,1]
                    page.setAlpha(1 - position);
                    page.setTranslationX(page.getWidth() * -position);
                    float scaleFactor = 0.75f + (1 - 0.75f) * (1 - Math.abs(position));
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
                } else { // (1,+Infinity]
                    page.setAlpha(0f);
                }
            }
        });

        // Setup dot indicators
        setupDotsIndicator(adapter.getItemCount());

        // Update dots on page change
        viewPagerOnboarding.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateDotsIndicator(position);
                // Reset the auto-slide timer whenever a manual swipe is done
                slideHandler.removeCallbacks(slideRunnable);
                slideHandler.postDelayed(slideRunnable, 2000); // Resume auto-slide after 2 seconds
            }
        });

        // Start auto-slide
        slideHandler.postDelayed(slideRunnable, 2000); // Start after an initial delay of 2 seconds

        // Handle Sign Up CardView click
        findViewById(R.id.cardSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnboardingActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        // Handle Login CardView click
        findViewById(R.id.cardLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnboardingActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    // Runnable for auto-slide
    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = viewPagerOnboarding.getCurrentItem();
            int totalItems = viewPagerOnboarding.getAdapter().getItemCount();

            // Move to the next item, or back to the first item if it's the last one
            if (currentItem < totalItems - 1) {
                viewPagerOnboarding.setCurrentItem(currentItem + 1);
            } else {
                viewPagerOnboarding.setCurrentItem(0);
            }

            // Continue the sliding every 2 seconds
            slideHandler.postDelayed(this, 5000);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove callbacks to avoid memory leaks
        slideHandler.removeCallbacks(slideRunnable);
    }

    private void setupDotsIndicator(int pageCount) {
        dotIndicatorLayout.removeAllViews(); // Clear existing dots

        for (int i = 0; i < pageCount; i++) {
            View dot = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    dpToPx(5), // Dot size
                    dpToPx(5)  // Dot size
            );
            params.setMargins(dpToPx(4), 0, dpToPx(4), 0);
            dot.setLayoutParams(params);
            dot.setBackgroundResource(R.drawable.round_dot); // Reference your custom dot shape
            dotIndicatorLayout.addView(dot);
        }

        // Initially highlight the first dot
        updateDotsIndicator(0);
    }

    private void updateDotsIndicator(int selectedPosition) {
        for (int i = 0; i < dotIndicatorLayout.getChildCount(); i++) {
            View dot = dotIndicatorLayout.getChildAt(i);
            if (i == selectedPosition) {
                dot.setBackgroundResource(R.drawable.round_dot_selected); // Highlighted dot
            } else {
                dot.setBackgroundResource(R.drawable.round_dot); // Regular dot
            }
        }
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
