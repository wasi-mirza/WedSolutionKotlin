package com.android.wednesdaysol.kotlin.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "post_info")
class ResultModel {
    @PrimaryKey
    @ColumnInfo(name = "artistId")
    @SerializedName("artistId")
    var id = 0

    @ColumnInfo(name = "trackName")
    @SerializedName("trackName")
    var trackName: String? = null

    @ColumnInfo(name = "artistName")
    @SerializedName("artistName")
    var artistName: String? = null

    @ColumnInfo(name = "artworkUrl60")
    @SerializedName("artworkUrl60")
    var artworkUrl60: String? = null

    @ColumnInfo(name = "releaseDate")
    @SerializedName("releaseDate")
    var releaseDate: String? = null
    override fun toString(): String {
        return "ClassPojo [id = $id, trackName = $trackName, artistName = $artistName]"
    }
}