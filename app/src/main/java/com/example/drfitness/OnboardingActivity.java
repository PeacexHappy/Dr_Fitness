package com.example.drfitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.drfitness.adapters.OnboardingAdapter;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPagerOnboarding;
    private LinearLayout dotIndicatorLayout;

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
                // Apply animation based on position
                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.setAlpha(0f);
                } else if (position <= 0) { // [-1,0]
                    // Use the default slide transition when moving to the left page
                    page.setAlpha(1f);
                    page.setTranslationX(0f);
                    page.setScaleX(1f);
                    page.setScaleY(1f);
                } else if (position <= 1) { // (0,1]
                    // Fade the page out.
                    page.setAlpha(1 - position);

                    // Counteract the default slide transformation.
                    page.setTranslationX(page.getWidth() * -position);

                    // Scale the page down (between MIN_SCALE and 1)
                    float scaleFactor = 0.75f + (1 - 0.75f) * (1 - Math.abs(position));
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
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
            }
        });

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

    private void setupDotsIndicator(int pageCount) {
        dotIndicatorLayout.removeAllViews(); // Clear existing dots

        for (int i = 0; i < pageCount; i++) {
            View dot = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    dpToPx(10), // Dot size
                    dpToPx(10)  // Dot size
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
