package com.drfitness.app

// A mock database object to hold all our workout plans.
object WorkoutDatabase {

    fun getPlan(level: String, bodyPart: String): WorkoutPlan? {
        return allPlans.find { it.level.equals(level, ignoreCase = true) && it.bodyPart.equals(bodyPart, ignoreCase = true) }
    }

    private val allPlans = listOf(
        // --- BEGINNER WORKOUTS ---
        WorkoutPlan(
            level = "Beginner",
            bodyPart = "Full Body",
            exercises = listOf(
                Exercise("Jumping Jacks", 3, "30 secs", "url_to_jumping_jacks_gif"),
                Exercise("Bodyweight Squats", 3, "12 reps", "url_to_squats_gif"),
                Exercise("Push-ups (on knees)", 3, "10 reps", "url_to_pushups_gif"),
                Exercise("Plank", 3, "30 secs", "url_to_plank_gif"),
                Exercise("Glute Bridges", 3, "15 reps", "url_to_glute_bridges_gif")
            )
        ),
        WorkoutPlan(
            level = "Beginner",
            bodyPart = "Legs",
            exercises = listOf(
                Exercise("Bodyweight Squats", 3, "15 reps", "url_to_squats_gif"),
                Exercise("Lunges", 3, "10 reps per leg", "url_to_lunges_gif"),
                Exercise("Calf Raises", 3, "20 reps", "url_to_calf_raises_gif"),
                Exercise("Glute Bridges", 3, "15 reps", "url_to_glute_bridges_gif")
            )
        ),

        // --- INTERMEDIATE WORKOUTS ---
        WorkoutPlan(
            level = "Intermediate",
            bodyPart = "Legs",
            exercises = listOf(
                Exercise("Goblet Squats", 4, "12 reps", "url_to_goblet_squats_gif"),
                Exercise("Dumbbell Lunges", 3, "12 reps per leg", "url_to_db_lunges_gif"),
                Exercise("Leg Press", 4, "10 reps", "url_to_leg_press_gif"),
                Exercise("Hamstring Curls", 3, "15 reps", "url_to_hamstring_curls_gif")
            )
        ),
        // ... we can add many more plans here later for other levels and body parts.
        WorkoutPlan(
            level = "Intermediate",
            bodyPart = "Bicep/Tricep",
            exercises = listOf(
                Exercise("Dumbbell Bicep Curls", 4, "12 reps", "url"),
                Exercise("Tricep Dips (on bench)", 3, "15 reps", "url"),
                Exercise("Hammer Curls", 4, "12 reps", "url"),
                Exercise("Tricep Pushdowns", 3, "15 reps", "url")
            )
        )
    )
}