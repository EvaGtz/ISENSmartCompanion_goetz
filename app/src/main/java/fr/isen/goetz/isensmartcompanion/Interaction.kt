package fr.isen.goetz.isensmartcompanion

//The Interaction entity will represent the question and answer pairs, and we will store them in a Room database.

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "interactions")
data class Interaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,  // Unique ID for each interaction
    val question: String,
    val answer: String,
    val date: Long = System.currentTimeMillis()  // Store the timestamp for the date of interaction
)
