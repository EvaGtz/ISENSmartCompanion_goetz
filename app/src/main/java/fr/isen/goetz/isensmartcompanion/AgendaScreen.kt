package fr.isen.goetz.isensmartcompanion

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
import androidx.compose.material3.Divider
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

data class Course(
    val courseName: String,
    val courseTime: String
)

data class AgendaEvent(
    val eventName: String,
    val eventDate: String
)

@Composable
fun AgendaScreen(courses: List<Course>, events: List<AgendaEvent>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
        Text(
            text = "COURS",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // List of Courses
        LazyColumn {
            items(courses) { course ->
                CourseItem(course = course)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Display Events
        Text(
            text = "EVENEMENTS",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // List of Events
        LazyColumn {
            items(events) { event ->
                EventItem(event = event)
            }
        }
    }
}

@Composable
fun CourseItem(course: Course) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = course.courseName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = course.courseTime,
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun EventItem(event: AgendaEvent) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
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