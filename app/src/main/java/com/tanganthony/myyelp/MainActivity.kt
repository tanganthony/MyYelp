package com.tanganthony.myyelp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.tanganthony.myyelp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private val TAG: String? = MainActivity::class.simpleName
    private lateinit var binding: ActivityMainBinding
    private val restaurants = mutableListOf<YelpRestaurant>()
    private val adapter = RestaurantsAdapter(this, restaurants)
    private val retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val yelpService = retrofit.create(YelpService::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvRestaurants.layoutManager = LinearLayoutManager(this)
        binding.rvRestaurants.adapter = adapter

        searchYelp("lunch", "94105")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.miSearch) {
            Log.i(TAG, "Tapped on search")
            showAlertDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog() {
        val searchFormView = LayoutInflater.from(this).inflate(R.layout.dialog_search, null)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Search Yelp")
            .setView(searchFormView)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            val search = searchFormView.findViewById<EditText>(R.id.etSearch).text.toString()
            val location = searchFormView.findViewById<EditText>(R.id.etLocation).text.toString()

            if (search.trim().isEmpty() || location.trim().isEmpty()) {
                Toast.makeText(
                    this,
                    "Must input both search item and location",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            searchYelp(search, location)
            dialog.dismiss()
        }
    }

    private fun searchYelp(searchItem: String, location: String) {
        yelpService.searchRestaurants("Bearer $API_KEY", searchItem, location)
            .enqueue(object : Callback<YelpSearchResult> {

                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<YelpSearchResult>,
                    response: Response<YelpSearchResult>
                ) {
                    Log.i(TAG, "onResponse $response")
                    val body = response.body()
                    if (body == null) {
                        Log.w(TAG, "Did not receive valid response body from Yelp API... exiting")
                        return
                    } else {
                        restaurants.clear()
                        restaurants.addAll(body.restaurants)
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                    Log.i(TAG, "onResponse $t")
                }
            })
    }

}