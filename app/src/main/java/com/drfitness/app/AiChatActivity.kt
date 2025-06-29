package com.drfitness.app

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.drfitness.app.databinding.ActivityAiChatBinding
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class AiChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAiChatBinding
    private lateinit var chatAdapter: ChatAdapter
    private val chatMessages = mutableListOf<ChatMessage>()
    private lateinit var chat: com.google.ai.client.generativeai.Chat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAiChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // 1. Setup the UI components first
        setupRecyclerView()

        // 2. NOW initialize the chat, which can safely add a message
        initializeChat()

        // Send button listener
        binding.buttonSend.setOnClickListener {
            val userMessage = binding.textFieldMessage.editText?.text.toString()
            if (userMessage.isNotBlank()) {
                sendMessage(userMessage)
            }
        }
    }
    private fun initializeChat() {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "YOUR_API_KEY_HERE") {
            Toast.makeText(this, "API Key not found...", Toast.LENGTH_LONG).show()
            return
        }

        val systemInstructionText = "You are Dr. Fitness, a friendly and encouraging AI fitness coach. Your primary goal is to help users find a workout plan. " +
                "Engage in a brief, natural conversation. Ask clarifying questions if needed. " +
                "When you have enough information to recommend a plan, you MUST respond with the special format `PLAN:{Level}:{BodyPart}` and nothing else in that specific message. " +
                "For example, if the user says 'I want an easy leg workout', you must reply with 'PLAN:Beginner:Legs'. " +
                "If the user just says 'hello', greet them back and ask what they'd like to work on. " +
                "Available levels: Beginner, Intermediate, Advanced. " +
                "Available body parts: Full Body, Legs, Bicep/Triceps, Shoulder, Back, Abs."

        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = apiKey,
            systemInstruction = com.google.ai.client.generativeai.type.content { text(systemInstructionText) }
        )

        // Start a chat session with the initial greeting already in its history
        chat = generativeModel.startChat(
            history = listOf(
                com.google.ai.client.generativeai.type.content(role = "model") {
                    text("Hello! How can I help you plan your workout today?")
                }
            )
        )

        // Add that initial greeting to our app's visual chat list
        addMessage("Hello! How can I help you plan your workout today?", Participant.MODEL)
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(chatMessages)
        binding.recyclerViewChat.apply {
            layoutManager = LinearLayoutManager(this@AiChatActivity).apply {
                stackFromEnd = true // Messages start from the bottom
            }
            adapter = chatAdapter
        }
    }

    private fun addMessage(text: String, participant: Participant) {
        chatMessages.add(ChatMessage(text, participant))
        chatAdapter.notifyItemInserted(chatMessages.size - 1)
        binding.recyclerViewChat.scrollToPosition(chatMessages.size - 1) // Auto-scroll to the new message
    }

    private fun sendMessage(userMessage: String) {
        addMessage(userMessage, Participant.USER)
        binding.textFieldMessage.editText?.text?.clear()
        setLoading(true)

        lifecycleScope.launch {
            try {
                // The key change: send the message to the ongoing chat session
                val response = chat.sendMessage(userMessage)

                response.text?.let {
                    processAiResponse(it)
                }
            } catch (e: Exception) {
                addMessage("An error occurred: ${e.message}", Participant.MODEL)
            } finally {
                setLoading(false)
            }
        }
    }

    private fun processAiResponse(responseText: String) {
        if (responseText.startsWith("PLAN:")) {
            val parts = responseText.removePrefix("PLAN:").split(":")
            if (parts.size == 2) {
                val level = parts[0].trim()
                val bodyPart = parts[1].trim()

                addMessage("Great! I've found a $level workout for your $bodyPart. Let's get started.", Participant.MODEL)
                navigateToWorkoutDetail(level, bodyPart)

            } else {
                addMessage("Sorry, I received a plan in a format I don't understand.", Participant.MODEL)
            }
        } else {
            addMessage(responseText, Participant.MODEL)
        }
    }

    private fun navigateToWorkoutDetail(level: String, bodyPart: String) {
        // We reuse the WorkoutDetailActivity we already built!
        val intent = android.content.Intent(this, WorkoutDetailActivity::class.java).apply {
            putExtra("USER_LEVEL", level)
            putExtra("BODY_PART", bodyPart)
        }
        startActivity(intent)
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.buttonSend.isEnabled = !isLoading
    }
}