package com.android.wednesdaysol.kotlin.Repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.android.wednesdaysol.kotlin.Models.ResultModel

class SongRoomDBRepository(application: Application) {
    private val songInfoDao: SongInfoDao
    var allPosts: LiveData<List<ResultModel>>

    init {
        val db = SongInfoRoomDataBase.getInstance(application)
        songInfoDao = db!!.postInfoDao()
        allPosts = songInfoDao!!.allPosts
    }

    fun insertPosts(resultModel: List<ResultModel>) {
        insertAsyncTask(songInfoDao).execute(resultModel)
    }


    class insertAsyncTask internal  constructor(userDao: SongInfoDao): AsyncTask<List<ResultModel>, Void, Void>(){
        private  var mAsyncUserDao: SongInfoDao
        init {
            mAsyncUserDao = userDao
        }
        override fun doInBackground(vararg p0: List<ResultModel>): Void? {
            mAsyncUserDao.insert(p0[0])
            return null

        }
    }

}