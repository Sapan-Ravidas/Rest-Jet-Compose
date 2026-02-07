package com.sapan.restjet.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sapan.restjet.db.entity.CollectionData
import com.sapan.restjet.db.entity.SavedRequestData
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollection(collection: CollectionData): Long

    @Insert
    suspend fun insertRequest(request: SavedRequestData): Long

    @Update
    suspend fun updateCollection(collection: CollectionData): Int

    @Update
    suspend fun updateRequest(request: SavedRequestData): Int

    @Delete
    suspend fun deleteCollection(collection: CollectionData): Int

    @Delete
    suspend fun deleteRequest(request: SavedRequestData): Int

    @Query("SELECT * FROM collections ORDER BY created_at DESC")
    fun getAllCollections(): Flow<List<CollectionData>>

    @Query("SELECT * from collections WHERE title = :name")
    fun getCollectionByName(name: String): Flow<CollectionData?>

    @Query("SELECT * FROM saved_requests WHERE " +
            "(SELECT collection_id FROM collections WHERE title = :name) " +
            "ORDER BY created_at DESC")
    fun getAllRequestByCollectionName(name: String): Flow<List<SavedRequestData>>

    @Query("SELECT * FROM saved_requests WHERE name = :name AND " +
            "(SELECT collection_id FROM collections WHERE title = :collectionName)")
    fun getRequestByNameAndCollectionName(name: String, collectionName: String): Flow<SavedRequestData>
}