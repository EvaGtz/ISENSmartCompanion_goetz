package fr.isen.goetz.isensmartcompanion

//The Interaction entity will represent the question and answer pairs, and we will store them in a Room database.

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import androidx.room.ColumnInfo

@Entity(tableName = "interaction")
data class Interaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "question") val question: String,
    @ColumnInfo(name = "answer") val answer: String,  // Non-nullable String
    @ColumnInfo(name = "date") val date: Long // Timestamp, for example
)
