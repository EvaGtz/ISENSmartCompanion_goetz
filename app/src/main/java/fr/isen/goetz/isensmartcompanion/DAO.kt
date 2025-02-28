package fr.isen.goetz.isensmartcompanion

//The DAO will provide the necessary methods to insert, delete, and retrieve the interactions from the database.

import androidx.room.*

@Dao
interface InteractionDao {
    // Insert a new interaction
    @Insert
    suspend fun insertInteraction(interaction: Interaction)

    // Get all interactions, ordered by date
    @Query("SELECT * FROM interactions ORDER BY date DESC")
    suspend fun getAllInteractions(): List<Interaction>

    // Delete a specific interaction
    @Delete
    suspend fun deleteInteraction(interaction: Interaction)

    // Delete all interactions
    @Query("DELETE FROM interactions")
    suspend fun deleteAllInteractions()
}
