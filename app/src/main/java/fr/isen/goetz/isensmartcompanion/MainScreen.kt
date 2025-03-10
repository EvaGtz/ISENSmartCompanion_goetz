package fr.isen.goetz.isensmartcompanion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MainScreen() {
    val context =
        LocalContext.current //Local context for calling methods like startActivity
    var userInput by remember { mutableStateOf(TextFieldValue("")) }
    var messages by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) } //List of user-input and AI responses

    //AppDatabase references
    val database = AppDatabase.getDatabase(context)
    val interactionDao = database.interactionDao()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f)),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Title of the main page
        Text(
            text = "ACCUEIL",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Divider(
            color = Color(0xFFD00000),
            thickness = 2.dp,
            modifier = Modifier.padding(top = 4.dp)
        )

        //Title ISEN
        Spacer(modifier = Modifier.height(20.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "ISEN",
                fontSize = 72.sp,
                color = Color(0xFFD00000),
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Smart Companion",
                fontSize = 19.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Messages between user and AI
        LazyColumn(
            modifier = Modifier.weight(0.5f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { (user, aiResponse) ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        "MOI: $user",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 13.sp
                    )
                    Text(
                        "SMART COMPANION: $aiResponse",
                        color = Color.Black,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        //INPUT
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.Gray, shape = MaterialTheme.shapes.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //Input of what we want to ask the AI
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .background(Color.White, shape = MaterialTheme.shapes.medium),
                placeholder = { Text("Posez moi une question...") }
            )

            //Red send button
            IconButton(
                onClick = {
                    val inputText = userInput.text
                    if (inputText.isNotEmpty()) {
                        userInput = TextFieldValue("")
                        messages = messages + (inputText to "Chargement...") //Loading message

                        //Call the AI response in a coroutine
                        CoroutineScope(Dispatchers.Main).launch {
                            val response =
                                getGeminiResponse(inputText) //Get the AI response

                            //Provide a default response if the response is null
                            val responseText = response ?: "No response available"

                            //Replace loading message with AI response
                            messages = messages.dropLast(1) + (inputText to responseText)

                            //Save to Room database after receiving response
                            val interaction = Interaction(
                                question = inputText,
                                answer = responseText, //Pass the responseText
                                date = System.currentTimeMillis() //Save the current timestamp
                            )

                            //Save to database (no need for a second CoroutineScope)
                            CoroutineScope(Dispatchers.IO).launch {
                                interactionDao.insertInteraction(interaction) //Save interaction to database
                            }
                        }
                    }
                },
                modifier = Modifier
                    .size(50.dp)
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                    .background(Color(0xFFD00000), shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowUpward,
                    contentDescription = "Envoyer",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AssistantUIPreview() {
    MainScreen()
}