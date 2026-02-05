package com.sapan.restjet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "saved_requests",
    foreignKeys = [
        ForeignKey(
            entity = CollectionData::class,
            parentColumns = ["id"],
            childColumns = ["collection_id"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ]
)
data class SavedRequestData (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    @ColumnInfo("collection_id") val collectionId: Long? = null,

    val name: String,

    @ColumnInfo(name = "http_method") val httpMethod: String,

    @ColumnInfo(name = "base_url") val baseUrl: String,

    @ColumnInfo(name = "path_url") val pathUrl: String,

    val headers: String,

    @ColumnInfo(name = "query_params") val queryParams: String,

    @ColumnInfo(name = "request_body") val requestBody: String,

    @ColumnInfo(name = "created_at") val savedAt: String
    )