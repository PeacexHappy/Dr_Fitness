package com.drfitness.app

// Represents a single exercise, e.g., "Squats"
data class Exercise(
    val name: String,
    val sets: Int,
    val reps: String, // Using String for flexibility, e.g., "12 reps" or "30 secs"
    val imageUrl: String // URL for an image/gif of the exercise
)

// Represents a complete workout plan for a specific day/body part
data class WorkoutPlan(
    val level: String,
    val bodyPart: String,
    val exercises: List<Exercise>
)

// Represents a single item in the "Featured" carousel
data class FeaturedWorkout(
    val title: String,
    val imageUrl: String,
    // These would be used to find the actual plan when clicked
    val level: String,
    val bodyPart: String
)

// Represents a single item in the "Categories" grid
data class WorkoutCategory(
    val name: String,
    val imageUrl: String
)

// This sealed class represents all possible items on our Workouts screen.
sealed class WorkoutScreenItem {
    data class Header(val title: String) : WorkoutScreenItem()
    data class FeaturedCarousel(val featuredWorkouts: List<FeaturedWorkout>) : WorkoutScreenItem()
    data class CategoryGrid(val categories: List<WorkoutCategory>) : WorkoutScreenItem()
}