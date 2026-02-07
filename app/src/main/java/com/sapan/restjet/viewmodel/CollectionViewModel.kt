package com.sapan.restjet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapan.restjet.db.entity.CollectionData
import com.sapan.restjet.db.entity.SavedRequestData
import com.sapan.restjet.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val repository: ClientRepository
) : ViewModel() {

    private val _collections = MutableStateFlow<List<CollectionData>>(emptyList())
    val collections: StateFlow<List<CollectionData>> get() = _collections

    private val _savedRequests = MutableStateFlow<List<SavedRequestData>>(emptyList())
    val savedRequests: StateFlow<List<SavedRequestData>> = _savedRequests.asStateFlow()

    init {
        loadCollections()
    }

    private fun loadCollections() {
        viewModelScope.launch {
            repository.getAllCollections().collect { collections ->
                _collections.value = collections
            }
        }
    }

    fun addCollection(title: String, description: String? = null) {
        viewModelScope.launch {
            repository.addCollection(title, description)
        }
    }

    fun deleteCollection(collection: CollectionData) {
        viewModelScope.launch {
            repository.deleteCollection(collection)
        }
    }

    fun loadSavedRequests(collectionName: String? = null) {
        collectionName?.let {
            viewModelScope.launch {
                repository.getAllSavedRequestForCollection(collectionName).collect { requests ->
                    _savedRequests.value = requests
                }
            }
        }
    }
}