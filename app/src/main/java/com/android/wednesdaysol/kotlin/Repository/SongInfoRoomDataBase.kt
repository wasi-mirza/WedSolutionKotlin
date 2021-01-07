package com.android.wednesdaysol.kotlin.Repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import com.android.wednesdaysol.kotlin.Models.ResultModel

@Database(entities = [ResultModel::class], version = 1)
abstract class SongInfoRoomDataBase : RoomDatabase() {
    abstract fun postInfoDao(): SongInfoDao

    companion object {
        private var INSTANCE: SongInfoRoomDataBase? = null

        fun getInstance(context: Context): SongInfoRoomDataBase? {
            if (INSTANCE == null) {
                synchronized(SongInfoRoomDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context,
                            SongInfoRoomDataBase::class.java, "postinfo_database")
                            .addCallback(sRoomDataBaseCallback)
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }

        val sRoomDataBaseCallback = object:RoomDatabase.Callback(){
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                SongDbAsync(INSTANCE!!).execute()
            }
        };



    }
}