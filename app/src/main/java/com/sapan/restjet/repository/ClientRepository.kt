package com.sapan.restjet.repository

import com.google.gson.Gson
import com.sapan.restjet.data.RequestState
import com.sapan.restjet.db.dao.CollectionDao
import com.sapan.restjet.db.entity.SavedRequestData
import javax.inject.Inject

class ClientRepository @Inject constructor(
    private val collectionDao: CollectionDao
) {
    val gson = Gson()

    fun saveRequest(filename: String, collectionName: String, requestState: RequestState): Long {
        
    }
}