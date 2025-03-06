package fr.isen.goetz.isensmartcompanion

//The DAO will provide the necessary methods to insert, delete, and retrieve the interactions from the database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface InteractionDao {
    @Insert
    suspend fun insertInteraction(interaction: Interaction)

    @Query("SELECT * FROM interaction")
    fun getAllInteractions(): Flow<List<Interaction>>

    @Delete
    suspend fun deleteInteraction(interaction: Interaction)

    @Query("DELETE FROM interaction")
    suspend fun deleteAllInteractions()
}