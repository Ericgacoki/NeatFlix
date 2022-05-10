package com.ericg.neatflix.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ericg.neatflix.model.Movie

@Database(entities = [MyListMovie::class], version = 1, exportSchema = false)
abstract class WatchListDatabase: RoomDatabase() {
    abstract val moviesDao : MoviesDao
}