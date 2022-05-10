package com.ericg.neatflix.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWatchList(movie: MyListMovie)

    @Delete
    suspend fun removeFromWatchList(movie: MyListMovie)

    @Query("SELECT EXISTS (SELECT 1 FROM watch_list_table WHERE mediaId = :mediaId)")
    suspend fun exists(mediaId: Int): Int

    @Query("SELECT * FROM watch_list_table ORDER BY mediaId DESC")
    fun getFullWatchList(): Flow<List<MyListMovie>>

    @Query("SELECT * FROM watch_list_table WHERE title LIKE :searchParam")
    fun searchWatchList(searchParam: String): Flow<List<MyListMovie>>

    @Query("DELETE FROM watch_list_table")
    suspend fun deleteWatchList()
}