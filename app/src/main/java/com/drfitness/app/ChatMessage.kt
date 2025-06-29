package com.drfitness.app

// Represents a single message in our chat.
data class ChatMessage(
    val text: String,
    val participant: Participant
)

// Enum to identify who sent the message.
enum class Participant {
    USER, MODEL, ERROR
}