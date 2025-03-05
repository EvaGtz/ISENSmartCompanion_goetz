package fr.isen.goetz.isensmartcompanion

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.Divider
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Brush

data class Course(
    val courseName: String,
    val courseTime: String,
    val courseRoom: String
)

data class AgendaEvent(
    val eventName: String,
    val eventDate: String
)

@Composable
fun AgendaScreen(courses: List<Course>, events: List<AgendaEvent>) {
    val courses = remember { mutableStateListOf(*courses.toTypedArray()) }
    var showDialog by remember { mutableStateOf(false) }
    var courseName by remember { mutableStateOf("") }
    var courseRoom by remember { mutableStateOf("") }
    var courseTime by remember { mutableStateOf("") }

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
            .padding(16.dp)
    ) {
        // Title of the Agenda
        Text(
            text = "AGENDA",
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

        Spacer(modifier = Modifier.height(16.dp))

        // Display Courses
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "COURS",
                fontSize = 22.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.Filled.Backpack,
                contentDescription = "Backpack Icon",
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // List of Courses
        LazyColumn {
            items(courses) { course ->
                CourseItem(course = course, onDelete = { courses.remove(course) }) // Pass onDelete here
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Add Course Button
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD00000)
            )
        ) {
            Text(
                "+ Ajouter un cours",
                color = Color.White
            )
        }


        Spacer(modifier = Modifier.height(20.dp))

        // Display Events
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "EVENEMENTS",
                fontSize = 22.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.Filled.Event,
                contentDescription = "Backpack Icon",
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // List of Events
        LazyColumn {
            items(events) { event ->
                EventItem(event = event)
            }
        }

        // Show Dialog when button is clicked
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Ajouter un cours",
                            fontWeight = FontWeight.Bold,
                        )
                    }
                },
                text = {
                    Column {
                        OutlinedTextField(
                            value = courseName,
                            onValueChange = { courseName = it },
                            label = { Text("Matière") },
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color(0xFFD00000), // Red border when focused
                                unfocusedIndicatorColor = Color.Gray, // Gray border when not focused
                                focusedLabelColor = Color(0xFFD00000), // Red label when focused
                                unfocusedLabelColor = Color.Gray // Gray label when not focused
                            )
                        )
                        OutlinedTextField(
                            value = courseRoom,
                            onValueChange = { courseRoom = it },
                            label = { Text("Salle") },
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color(0xFFD00000), // Red border when focused
                                unfocusedIndicatorColor = Color.Gray, // Gray border when not focused
                                focusedLabelColor = Color(0xFFD00000), // Red label when focused
                                unfocusedLabelColor = Color.Gray // Gray label when not focused
                            )
                        )
                        OutlinedTextField(
                            value = courseTime,
                            onValueChange = { courseTime = it },
                            label = { Text("Heure") },
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color(0xFFD00000), // Red border when focused
                                unfocusedIndicatorColor = Color.Gray, // Gray border when not focused
                                focusedLabelColor = Color(0xFFD00000), // Red label when focused
                                unfocusedLabelColor = Color.Gray // Gray label when not focused
                            )
                        )
                    }
                },
                confirmButton = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                if (courseName.isNotBlank() && courseRoom.isNotBlank() && courseTime.isNotBlank()) {
                                    courses.add(Course(courseName, courseTime, courseRoom))
                                    showDialog = false
                                    courseName = ""
                                    courseRoom = ""
                                    courseTime = ""
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black
                            )
                        ) {
                            Text(
                                "Ajouter",
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp)) // Add space between buttons

                        Button(
                            onClick = {
                                showDialog = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black
                            )
                        ) {
                            Text(
                                "Annuler",
                                color = Color.White
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun CourseItem(course: Course, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .border(1.dp, Color(0xFFD00000), shape = RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Row for Course Name and Course Room (Aligned to Top-Right)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = course.courseName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = course.courseRoom,
                    fontSize = 16.sp,
                    color = Color(0xFFD00000)
                )
            }
            Text(
                text = course.courseTime,
                fontSize = 18.sp,
                color = Color.Gray
            )
            // Trash Bin Icon (Delete Button)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = Color(0xFFD00000)
                    )
                }
            }
        }
    }
}

@Composable
fun EventItem(event: AgendaEvent) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .border(1.dp, Color(0xFFD00000), shape = RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = event.eventName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = event.eventDate,
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}