package com.sapan.restjet.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sapan.restjet.RestJetApplication
import com.sapan.restjet.db.dao.CollectionDao
import com.sapan.restjet.db.entity.CollectionData
import com.sapan.restjet.db.entity.SavedRequestData

@Database(
    entities = [
        CollectionData::class,
        SavedRequestData::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ClientDatabase: RoomDatabase() {

    abstract fun collectionDao(): CollectionDao

    companion object {
        const val DATABASE_NAME = "client_db"

        @Volatile
        private var INSTANCE: ClientDatabase? = null

        fun getInstance(application: RestJetApplication): ClientDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(application).also {
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(application: RestJetApplication): ClientDatabase =
             Room.databaseBuilder(
                    application.applicationContext,
                    ClientDatabase::class.java,
                    DATABASE_NAME
                    )
                    .fallbackToDestructiveMigration()
                    .build()

    }
}