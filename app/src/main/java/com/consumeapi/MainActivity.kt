package com.consumeapi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_adapter.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val movieList = ArrayList<Movies>()
    private lateinit var itemAdapter: ItemAdapter
    private val URL = "https://api.themoviedb.org/3/discover/movie"
    private val TOKEN = "api_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAdapter()
        getDiscoverMovie()
    }

    private fun initAdapter(){
        itemAdapter = ItemAdapter(movieList, this)
        val layoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
    }

    private fun getDiscoverMovie(){
        AndroidNetworking.get(URL)
            .addQueryParameter("api_key", TOKEN)
            .addQueryParameter("sort_by", "popularity.desc")
            .addQueryParameter("year", "2019")
            .setTag("fetch")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener{

                override fun onResponse(response: JSONObject?) {
                    val itemResponse = response!!.getJSONArray("results")
                    movieList.clear()

                    try{
                        for(i in 0 until itemResponse.length()){
                            val value = itemResponse.getJSONObject(i)
                            val title = value.getString("title")
                            val releaseDate = value.getString("release_date")
                            val rating = value.getString("vote_average")
                            val imageMovie = value.getString("backdrop_path")

                            val movies = Movies(title, releaseDate, rating, imageMovie)
                            movieList.add(movies)
                        }
                    } catch (e: JSONException){
                        Log.e("jsonErr", e.message)
                    }

                    recyclerview.adapter = itemAdapter
                }

                override fun onError(anError: ANError?) {
                    Log.e("TAG", anError.toString())
                }
            })
    }
}
