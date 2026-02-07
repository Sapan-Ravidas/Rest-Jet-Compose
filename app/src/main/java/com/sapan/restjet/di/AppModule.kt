package com.sapan.restjet.di

import android.content.Context
import com.sapan.restjet.RestJetApplication
import com.sapan.restjet.db.ClientDatabase
import com.sapan.restjet.db.dao.CollectionDao
import com.sapan.restjet.repository.ClientRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ClientDatabase {
        return ClientDatabase.getInstance(context as RestJetApplication)
    }

    @Provides
    @Singleton
    fun provideCollectionRepository(collectionDao: CollectionDao): ClientRepository {
        return ClientRepository(collectionDao)
    }

    @Provides
    @Singleton
    fun provideCollectionDao(database: ClientDatabase): CollectionDao {
        return database.collectionDao()
    }
}