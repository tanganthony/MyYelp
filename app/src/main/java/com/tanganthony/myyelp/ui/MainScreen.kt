package com.tanganthony.myyelp.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.tanganthony.myyelp.ui.appbar.AppBar
import com.tanganthony.myyelp.ui.appbar.SearchState
import com.tanganthony.myyelp.ui.appbar.ViewModel
import com.tanganthony.myyelp.ui.result.ResultScreen

@Composable
fun MainScreen(viewModel: ViewModel) {
    val searchState by viewModel.searchState
    val cuisine by viewModel.cuisine
    val location by viewModel.location

    Scaffold(
        topBar = {
            AppBar(
                searchState = searchState,
                cuisine = cuisine,
                onCuisineChange = { cuisine ->
                    viewModel.updateCuisine(cuisine)
                },
                location = location,
                onLocationChange = { location ->
                    viewModel.updateLocation(location)
                },
                onSearchClicked = {
                    if (cuisine.isEmpty() || location.isEmpty()) return@AppBar

                    if (cuisine != viewModel.queryCuisine.value || location != viewModel.queryLocation.value) {
                        viewModel.restaurants = ArrayList()
                    }
                    viewModel.updateQueryCuisine(cuisine)
                    viewModel.updateQueryLocation(location)
                    viewModel.updateSearchState(SearchState.DONE)
                },
                onSearchTriggered = {
                    viewModel.updateCuisine("")
                    viewModel.updateLocation("")
                    viewModel.updateSearchState(SearchState.IN_PROGRESS)
                }
            )
        },
    ) {
        ResultScreen(viewModel = viewModel)
    }
}
