package com.example.levelup_gamer.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.levelup_gamer.data.database.converters.Converters
import com.example.levelup_gamer.data.database.converters.MapConverter
import com.example.levelup_gamer.data.database.dao.ProductDao
import com.example.levelup_gamer.data.models.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class, MapConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "levelup_gamer_database"
                )
                // If the schema changes, this will delete and recreate the database.
                // Perfect for development.
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
