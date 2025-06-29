package com.drfitness.app

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.drfitness.app.databinding.ItemChatMessageBinding

class ChatAdapter(private val messages: List<ChatMessage>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(val binding: ItemChatMessageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        holder.binding.textViewMessage.text = message.text

        val context = holder.itemView.context

        if (message.participant == Participant.USER) {
            // User's message
            holder.binding.textViewMessage.background = ContextCompat.getDrawable(context, R.drawable.chat_bubble_user)
            holder.binding.textViewMessage.setTextColor(ContextCompat.getColor(context, R.color.white))

            // Set the gravity of the parent LinearLayout to END
            holder.binding.messageRoot.gravity = Gravity.END

        } else {
            // AI's message
            holder.binding.textViewMessage.background = ContextCompat.getDrawable(context, R.drawable.chat_bubble_model)
            holder.binding.textViewMessage.setTextColor(ContextCompat.getColor(context, R.color.text_primary_light))

            // Set the gravity of the parent LinearLayout to START
            holder.binding.messageRoot.gravity = Gravity.START
        }
    }
}