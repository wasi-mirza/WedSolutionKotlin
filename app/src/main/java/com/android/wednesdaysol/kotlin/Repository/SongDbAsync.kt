package com.android.wednesdaysol.kotlin.Repository

import android.os.AsyncTask

public class SongDbAsync(db:SongInfoRoomDataBase): AsyncTask<Void,Void,Void>() {
    private val  mDao: SongInfoDao

    init{
        mDao =  db.postInfoDao()
    }


    override fun doInBackground(vararg params: Void): Void? {
        /*mDao.deleteAll()
        var user = User("Chandra", "SW")
        mDao.insert(user)
        user = User("Mohan", "student")
        mDao.insert(user)*/
        return null
    }


}