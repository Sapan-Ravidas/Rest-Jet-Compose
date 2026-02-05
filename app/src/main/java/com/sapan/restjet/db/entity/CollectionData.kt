package com.sapan.restjet.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "collections",
    indices = [
        Index(value = ["title"], unique = true)
    ]
)
data class CollectionData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "title") val title: String,

    val description: String? = null,

    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)