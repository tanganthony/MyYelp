package com.tanganthony.myyelp.ui.appbar

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanganthony.myyelp.API_KEY
import com.tanganthony.myyelp.network.YelpApiService
import com.tanganthony.myyelp.network.YelpApiStatus
import com.tanganthony.myyelp.network.YelpRestaurant
import kotlinx.coroutines.launch

class ViewModel : ViewModel() {

    // API
    private val apiService = YelpApiService.getInstance()

    private val _apiStatus: MutableState<YelpApiStatus> = mutableStateOf(YelpApiStatus.IN_PROGRESS)
    val apiStatus: State<YelpApiStatus> = _apiStatus

    var restaurants:List<YelpRestaurant?> by mutableStateOf(listOf())

    fun getRestaurant(queryCuisine: String, queryLocation: String) {
        viewModelScope.launch {
            try {
                _apiStatus.value = YelpApiStatus.IN_PROGRESS

                val list = apiService.getRestaurants(
                    "Bearer $API_KEY",
                    queryCuisine,
                    queryLocation
                )
                restaurants = list.restaurants
                _apiStatus.value = YelpApiStatus.DONE

            } catch (e: Exception) {
                restaurants = ArrayList()
                _apiStatus.value = YelpApiStatus.ERROR
            }
        }
    }


    // UI
    private val _searchState: MutableState<SearchState> = mutableStateOf(SearchState.DONE)
    val searchState: State<SearchState> = _searchState

    fun updateSearchState(newValue: SearchState) {
        _searchState.value = newValue
    }

    private val _cuisine: MutableState<String> = mutableStateOf("Seafood")
    val cuisine: State<String> = _cuisine

    fun updateCuisine(newValue: String) {
        _cuisine.value = newValue
    }

    private val _location: MutableState<String> = mutableStateOf("San Francisco, CA")
    val location: State<String> = _location

    fun updateLocation(newValue: String) {
        _location.value = newValue
    }

    private val _queryCuisine: MutableState<String> = mutableStateOf("")
    val queryCuisine: State<String> = _queryCuisine

    fun updateQueryCuisine(newValue: String) {
        _queryCuisine.value = newValue
    }

    private val _queryLocation: MutableState<String> = mutableStateOf("")
    val queryLocation: State<String> = _queryLocation

    fun updateQueryLocation(newValue: String) {
        _queryLocation.value = newValue
    }
}
