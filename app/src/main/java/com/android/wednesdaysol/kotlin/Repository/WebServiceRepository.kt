package com.android.wednesdaysol.kotlin.Repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.wednesdaysol.kotlin.Models.ResultModel
import okhttp3.OkHttpClient
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class WebServiceRepository(var application: Application) {

    var webserviceResponseList: List<ResultModel> = ArrayList()

    companion object {
        private fun providesOkHttpClientBuilder(): OkHttpClient {
            val httpClient = OkHttpClient.Builder()
            return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                    .connectTimeout(1200, TimeUnit.SECONDS).build()
        }
    }

    fun providesWebService(keyword: String?): LiveData<List<ResultModel>> {
        val data = MutableLiveData<List<ResultModel>>()
        val response = ""
        try {
            val retrofit = Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesOkHttpClientBuilder())
                    .build()

            //Defining retrofit api service
            val service = retrofit.create(APIService::class.java)
            //  response = service.makeRequest().execute().body();
            service.search_songs(keyword)!!.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    Log.d("Repository", "Response::::" + response.body())
                    webserviceResponseList = parseJson(response.body())
                    val postRoomDBRepository = SongRoomDBRepository(application)
                    postRoomDBRepository.insertPosts(webserviceResponseList)
                    data.setValue(webserviceResponseList)
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    Log.d("Repository", "Failed:::")
                }


            })
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("TAG", "providesWebService: " + e.message)
        }

        //  return retrofit.create(ResultModel.class);
        return data
    }

    private fun parseJson(response: String?): List<ResultModel> {
        val apiResults: MutableList<ResultModel> = ArrayList()
        val jsonObject: JSONObject

//        JSONArray jsonArray;
        try {
            jsonObject = JSONObject(response)
            val jsonArray = jsonObject.getJSONArray("results")
            //            jsonArray = new JSONArray(response);
            for (i in 0 until jsonArray.length()) {
                val `object` = jsonArray.getJSONObject(i)
                val mMovieModel = ResultModel()
                mMovieModel.id = i.toString().toInt()
                mMovieModel.artistName = `object`.getString("artistName")
                mMovieModel.trackName = `object`.getString("trackName")
                mMovieModel.artworkUrl60 = `object`.getString("artworkUrl100")
                mMovieModel.releaseDate = `object`.getString("releaseDate")
                apiResults.add(mMovieModel)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.i(javaClass.simpleName, apiResults.size.toString())
        return apiResults
    }

}