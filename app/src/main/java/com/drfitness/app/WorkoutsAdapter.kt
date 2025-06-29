package com.drfitness.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.drfitness.app.databinding.ItemHorizontalCarouselBinding
import com.drfitness.app.databinding.ItemSectionHeaderBinding
import com.drfitness.app.databinding.ItemWorkoutCategoryBinding
import androidx.navigation.findNavController

class WorkoutsAdapter(private val screenItems: List<WorkoutScreenItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Define constants for our different view types
    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_FEATURED_CAROUSEL = 1
        private const val VIEW_TYPE_CATEGORY_GRID = 2
    }

    // --- ViewHolder for the Section Header ---
    class HeaderViewHolder(val binding: ItemSectionHeaderBinding) : RecyclerView.ViewHolder(binding.root)

    // --- ViewHolder for the Horizontal Carousel ---
    class FeaturedCarouselViewHolder(val binding: ItemHorizontalCarouselBinding) : RecyclerView.ViewHolder(binding.root)

    // We will add the ViewHolder for the Category Grid later

    override fun getItemViewType(position: Int): Int {
        return when (screenItems[position]) {
            is WorkoutScreenItem.Header -> VIEW_TYPE_HEADER
            is WorkoutScreenItem.FeaturedCarousel -> VIEW_TYPE_FEATURED_CAROUSEL
            is WorkoutScreenItem.CategoryGrid -> VIEW_TYPE_CATEGORY_GRID // We'll handle this soon
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> HeaderViewHolder(ItemSectionHeaderBinding.inflate(inflater, parent, false))
            VIEW_TYPE_FEATURED_CAROUSEL -> FeaturedCarouselViewHolder(ItemHorizontalCarouselBinding.inflate(inflater, parent, false))
            VIEW_TYPE_CATEGORY_GRID -> FeaturedCarouselViewHolder(ItemHorizontalCarouselBinding.inflate(inflater, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = screenItems[position]) {
            is WorkoutScreenItem.Header -> {
                (holder as HeaderViewHolder).binding.headerTitle.text = item.title
            }
            is WorkoutScreenItem.FeaturedCarousel -> {
                // Here we set up the inner, horizontal RecyclerView
                val featuredAdapter = FeaturedWorkoutAdapter(item.featuredWorkouts)
                (holder as FeaturedCarouselViewHolder).binding.carouselRecyclerView.adapter = featuredAdapter
            }
            // We will add the binding logic for the Category Grid later
            is WorkoutScreenItem.CategoryGrid -> {
                val categoryAdapter = CategoryGridAdapter(item.categories)
                val gridLayoutManager = GridLayoutManager(holder.itemView.context, 2)

                // This is the new part
                val spacingInPixels = holder.itemView.resources.getDimensionPixelSize(R.dimen.grid_spacing)
                val itemDecoration = GridSpacingItemDecoration(2, spacingInPixels, true)

                (holder as FeaturedCarouselViewHolder).binding.carouselRecyclerView.apply {
                    // Remove any previous decorations to avoid adding them multiple times
                    if (itemDecorationCount > 0) {
                        removeItemDecorationAt(0)
                    }
                    addItemDecoration(itemDecoration) // Apply our new spacing

                    layoutManager = gridLayoutManager
                    adapter = categoryAdapter
                }
            }
        }
    }

    override fun getItemCount(): Int = screenItems.size
}


// --- INNER ADAPTER for the horizontal "Featured Workout" carousel ---
class FeaturedWorkoutAdapter(private val featuredWorkouts: List<FeaturedWorkout>) :
    RecyclerView.Adapter<FeaturedWorkoutAdapter.FeaturedViewHolder>() {

    class FeaturedViewHolder(val binding: com.drfitness.app.databinding.ItemFeaturedWorkoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedViewHolder {
        val binding = com.drfitness.app.databinding.ItemFeaturedWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeaturedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeaturedViewHolder, position: Int) {
        val workout = featuredWorkouts[position]
        holder.binding.featuredTitle.text = workout.title
        holder.binding.featuredImage.load(workout.imageUrl)
    }

    override fun getItemCount(): Int = featuredWorkouts.size
}

// --- INNER ADAPTER for the "Workout Category" grid ---
class CategoryGridAdapter(
    private val categories: List<WorkoutCategory>
) : RecyclerView.Adapter<CategoryGridAdapter.CategoryViewHolder>() { // Removed the listener from constructor

    class CategoryViewHolder(val binding: com.drfitness.app.databinding.ItemWorkoutCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = com.drfitness.app.databinding.ItemWorkoutCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.categoryName.text = category.name
        holder.binding.categoryImage.load(category.imageUrl)

        // Set the click listener on the item view
        holder.itemView.setOnClickListener {
            // Create the navigation action, passing the category name as an argument
            val action = WorkoutsFragmentDirections.actionNavigationWorkoutsToWorkoutListFragment(category.name)
            // Perform the navigation
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = categories.size
}