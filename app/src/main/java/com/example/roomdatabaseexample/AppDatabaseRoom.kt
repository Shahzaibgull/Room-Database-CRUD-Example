package com.example.roomdatabaseexample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StudentRoom::class], version = 1)
abstract class AppDatabaseRoom : RoomDatabase() {
    abstract fun studentDao(): StudentDaoRoom

    companion object{
        @Volatile
        private var INSTANCE: AppDatabaseRoom?=null

        fun getDatabase(context: Context): AppDatabaseRoom{
            val tempInstance= INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this)
            {
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabaseRoom::class.java,
                    "app_database"
                ).build()
                INSTANCE=instance
                return instance
            }

        }
    }
}