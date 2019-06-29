package com.consumeapi

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_adapter.view.*

class ItemAdapter(val movieList: ArrayList<Movies>, val context: Context) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    val basePictURL = "https://image.tmdb.org/t/p/w780"
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {

        val view: View = LayoutInflater.from(context).inflate(R.layout.item_adapter, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val movies = movieList[position]

        holder.itemView.title.text = movies.title
        holder.itemView.release.text = movies.release
        holder.itemView.rating.text = movies.rating
        val url = basePictURL + movies.poster

        Picasso.get()
            .load(url)
            .error(R.drawable.no_image)
            .into(holder.itemView.imgmovie)
    }
}