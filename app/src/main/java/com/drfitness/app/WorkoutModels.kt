package com.drfitness.app

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// We make Exercise Parcelable so we can pass it to the detail screen.
@Parcelize
data class Exercise(
    val name: String,
    val sets: Int,
    val reps: String,
    val imageUrl: String,
    val instructions: String,
    val tags: List<String> = listOf()
) : Parcelable

// We make WorkoutPlan Parcelable in case we ever need to pass a whole plan.
@Parcelize
data class WorkoutPlan(
    val title: String,
    val description: String,
    val level: String,
    val category: String,
    val imageUrl: String,
    val exercises: List<Exercise>
) : Parcelable

// These models are only used inside the WorkoutsFragment, so they don't need to be Parcelable.
data class FeaturedWorkout(
    val title: String,
    val imageUrl: String
)

data class WorkoutCategory(
    val name: String,
    val imageUrl: String
)

// This sealed class helps build the complex WorkoutsFragment screen.
sealed class WorkoutScreenItem {
    data class Header(val title: String) : WorkoutScreenItem()
    data class FeaturedCarousel(val featuredWorkouts: List<FeaturedWorkout>) : WorkoutScreenItem()
    data class CategoryGrid(val categories: List<WorkoutCategory>) : WorkoutScreenItem()
}

data class PhysioCategory(
    val name: String,
    val description: String,
    val imageUrl: String
)