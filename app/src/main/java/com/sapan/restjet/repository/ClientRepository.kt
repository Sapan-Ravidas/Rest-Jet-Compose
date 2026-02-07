package com.sapan.restjet.repository

import androidx.room.Transaction
import com.google.gson.Gson
import com.sapan.restjet.data.RequestState
import com.sapan.restjet.db.dao.CollectionDao
import com.sapan.restjet.db.entity.CollectionData
import com.sapan.restjet.db.entity.SavedRequestData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClientRepository @Inject constructor(
    private val collectionDao: CollectionDao
) {
    val gson = Gson()

    @Transaction
    suspend fun saveRequest(
        filename: String,
        requestState: RequestState,
        collectionName: String,
        collectionDescription: String? = null
    ): Flow<Long> {
        return flow {
            val collection: CollectionData? = collectionDao
                .getCollectionByName(collectionName).
                firstOrNull()
            val collectionId = collection?.id ?:
            collectionDao.insertCollection(
                CollectionData(title = collectionName, description = collectionDescription)
            )
            val requestId: Long = collectionDao.insertRequest(
                SavedRequestData(
                    collectionId = collectionId,
                    name = filename,
                    httpMethod = requestState.action.name,
                    baseUrl = requestState.baseUrl,
                    requestBody = requestState.body,
                    headers = gson.toJson(requestState.headers),
                    queryParams = gson.toJson(requestState.queryParameters),
                    pathUrl = requestState.pathUrl,
                    savedAt = System.currentTimeMillis().toString()
                )
            )
            emit(requestId)
        }
    }

    fun getAllCollections(): Flow<List<CollectionData>> =
        collectionDao.getAllCollections()

    suspend fun addCollection(title: String, description: String? = null): Long {
        val collection = CollectionData(
            title = title,
            description = description
        )
        return collectionDao.insertCollection(collection)
    }

    suspend fun deleteCollection(collection: CollectionData): Int {
        return collectionDao.deleteCollection(collection)
    }

    suspend fun getAllSavedRequestForCollection(collectionName: String): Flow<List<SavedRequestData>> {
        val collection = collectionDao.getCollectionByName(collectionName)
        val collectionId = collection.firstOrNull()?.id ?: -1L
        return if (collectionId != -1L) {
            collectionDao.getAllRequestByCollectionName(collectionName)
        } else {
            flow { emit(emptyList()) }
        }
    }
}