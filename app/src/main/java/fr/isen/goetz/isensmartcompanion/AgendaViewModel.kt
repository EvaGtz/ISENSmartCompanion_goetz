import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import fr.isen.goetz.isensmartcompanion.Course

class AgendaViewModel : ViewModel() {
    var courses = mutableStateListOf<Course>()

    fun addCourse(course: Course) {
        courses.add(course)
    }
}
