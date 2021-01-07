package com.android.wednesdaysol.kotlin.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.android.wednesdaysol.kotlin.Models.ResultModel
import com.android.wednesdaysol.kotlin.Repository.SongRoomDBRepository
import com.android.wednesdaysol.kotlin.Repository.WebServiceRepository

class SongsListViewModel(application: Application) : AndroidViewModel(application) {

    private val songRoomDBRepository: SongRoomDBRepository
    val allPosts: LiveData<List<ResultModel>>
    var webServiceRepository: WebServiceRepository
    var retroObservable: LiveData<List<ResultModel>>? = null
    fun searchResult(keyword: String?) {
        retroObservable = webServiceRepository.providesWebService(keyword)
    }

    init {
        songRoomDBRepository = SongRoomDBRepository(application)
        webServiceRepository = WebServiceRepository(application)

        //postRoomDBRepository.insertPosts(retroObservable.getValue());
        allPosts = songRoomDBRepository.allPosts
    }
}