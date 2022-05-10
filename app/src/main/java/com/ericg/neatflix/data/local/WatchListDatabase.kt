package com.ericg.neatflix.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@AutoMigration(from = 1, to = 2)
@Database(
    version = 2,
    entities = [MyListMovie::class],
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class WatchListDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao
}