package com.tanganthony.myyelp.ui.result

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.annotation.ExperimentalCoilApi
import com.tanganthony.myyelp.network.YelpApiStatus
import com.tanganthony.myyelp.ui.appbar.ViewModel

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ResultScreen(viewModel: ViewModel) {
    val status by viewModel.apiStatus
    val queryCuisine by viewModel.queryCuisine
    val cuisine by viewModel.cuisine
    val location by viewModel.location

    LaunchedEffect(key1 = queryCuisine) {
        if (viewModel.restaurants.isEmpty()) {
            viewModel.getRestaurant(cuisine, location)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when(status) {
            YelpApiStatus.IN_PROGRESS -> {
//                SpinnerScreen()
            }
            YelpApiStatus.DONE -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    itemsIndexed(items = viewModel.restaurants) { _, restaurant ->
                        RestaurantCard(restaurant)
                    }
                }
            }
            YelpApiStatus.ERROR -> {
                ErrorScreen()
            }
        }
    }
}
