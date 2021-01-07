package com.android.wednesdaysol.kotlin.Views

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.wednesdaysol.R
import com.android.wednesdaysol.kotlin.ViewModels.SongsListViewModel
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private var keywordEditText: TextInputEditText? = null
    private var mBtnSearchSong: Button? = null
    var adapter: SongsListAdapter? = null
    var retroViewModel: SongsListViewModel? = null
    var progressDialog: ProgressDialog? = null
    var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retro_fit_room)
        retroViewModel = ViewModelProviders.of(this).get(SongsListViewModel::class.java)
        initViews()
        setAdapter()
        progressDialog = ProgressDialog.show(this, "Loading...", "Please wait...", true)
        retroViewModel!!.allPosts!!.observe(this, { resultModels ->
            adapter!!.setWords(resultModels)
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
        })
        mBtnSearchSong!!.setOnClickListener {
            val status = getConnectivityStatusString(this@MainActivity)
            if (status == "0") {
                Toast.makeText(this@MainActivity, "No Internet Connectivity", Toast.LENGTH_SHORT).show()
            }
            performSearch(keywordEditText!!.text.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        val status = getConnectivityStatusString(this@MainActivity)
        if (status == "0") {
            Toast.makeText(this@MainActivity, "No Internet Connectivity", Toast.LENGTH_SHORT).show()
        }
    }

    fun performSearch(key: String?) {
        retroViewModel!!.searchResult(key)
    }

    private fun initViews() {
        recyclerView = findViewById<View>(R.id.post_list) as RecyclerView
        keywordEditText = findViewById(R.id.fragment_songsearch_keyword)
        mBtnSearchSong = findViewById(R.id.fragment_songsearch_search)
    }

    private fun setAdapter() {
        adapter = SongsListAdapter(this)
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = GridLayoutManager(this, 2)
    }

    companion object {
        fun getConnectivityStatusString(context: Context): String? {
            var status: String? = null
            val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            if (activeNetwork != null) {
                if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                    status = "1"
                    return status
                } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                    status = "2"
                    return status
                }
            } else {
                status = "0"
                return status
            }
            return status
        }
    }
}