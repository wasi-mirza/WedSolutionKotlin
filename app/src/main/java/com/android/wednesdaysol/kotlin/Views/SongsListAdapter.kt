package com.android.wednesdaysol.kotlin.Views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.wednesdaysol.R
import com.android.wednesdaysol.kotlin.Models.ResultModel
import com.android.wednesdaysol.kotlin.Views.SongsListAdapter.PostViewHolder
import com.bumptech.glide.Glide

class SongsListAdapter internal constructor(context: Context?) : RecyclerView.Adapter<PostViewHolder>() {
    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val songName: TextView
        val artistName: TextView
        val releaseDate: TextView
        val imageView: ImageView

        init {
            imageView = itemView.findViewById(R.id.imageView)
            songName = itemView.findViewById(R.id.songName)
            artistName = itemView.findViewById(R.id.artistName)
            releaseDate = itemView.findViewById(R.id.releaseDate)
        }
    }

    private val mInflater: LayoutInflater
    private var mUsers // Cached copy of users
            : List<ResultModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = mInflater.inflate(R.layout.adapter_retro_post_item, parent, false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        if (mUsers != null) {
            val current = mUsers!![position]
            holder.songName.text = current.trackName
            holder.artistName.text = current.artistName
            holder.releaseDate.text = current.releaseDate!!.substring(0, 10)
            if (current.artworkUrl60 != null) {
                val imageUrl2 = current.artworkUrl60
                Glide.with(holder.itemView)
                        .load(imageUrl2)
                        .into(holder.imageView)
            }
        } else {
            // Covers the case of data not being ready yet.
            // holder.userNameView.setText("No Word");
        }
    }

    fun setWords(users: List<ResultModel>?) {
        mUsers = users
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (mUsers != null) {
            mUsers!!.size
        } else {
            0
        }
    }

    init {
        mInflater = LayoutInflater.from(context)
    }
}