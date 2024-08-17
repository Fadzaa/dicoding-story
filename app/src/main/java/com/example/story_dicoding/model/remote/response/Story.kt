package com.example.story_dicoding.model.remote.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "story")
data class Story(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("photoUrl")
    val photoUrl: String
): Serializable