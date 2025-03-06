package fr.isen.goetz.isensmartcompanion

import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//Not secure to put the API key raw like this but I didn't manage to do otherwise
val apiKey = "AIzaSyDyrL1QqsgY6zBBR7Wb3Gl_0E1onoY1tc4"

suspend fun getGeminiResponse(userInput: String): String {
    return withContext(Dispatchers.IO) {
        try {
            val model = GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = apiKey
            )
            val response = model.generateContent(userInput)
            response.text ?: "No response from AI"
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}