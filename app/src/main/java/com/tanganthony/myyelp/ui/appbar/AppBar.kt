package com.tanganthony.myyelp.ui.appbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AppBar(
    searchState: SearchState,
    cuisine: String,
    location: String,
    onCuisineChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when(searchState) {
        SearchState.DONE -> {
            DefaultAppBar(
                cuisine = cuisine,
                location = location,
                onSearchTriggered = onSearchTriggered
            )
        }
        SearchState.IN_PROGRESS -> {
            SearchAppBar(
                cuisine = cuisine,
                onCuisineChange = onCuisineChange,
                location = location,
                onLocationChange = onLocationChange,
                onSearchClicked = onSearchClicked
            )
        }
    }
}

@Composable
fun DefaultAppBar(
    cuisine: String,
    location: String,
    onSearchTriggered: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "$cuisine in $location")
        },
        actions = {
            IconButton(
                onClick = { onSearchTriggered() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Composable
fun SearchAppBar(
    cuisine: String,
    onCuisineChange: (String) -> Unit,
    location: String,
    onLocationChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        Column( modifier = Modifier.fillMaxWidth() ) {
            TextField(
                modifier = Modifier.focusRequester(focusRequester)
                    .fillMaxWidth(),
                value = cuisine,
                onValueChange = {
                    onCuisineChange(it)
                },
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.subtitle1.fontSize
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    cursorColor = MaterialTheme.colors.onPrimary.copy(ContentAlpha.medium)
                ),
                placeholder = {
                    Text(
                        modifier = Modifier.alpha(ContentAlpha.medium),
                        text = "Cuisine ie, Seafood...",
                        color = MaterialTheme.colors.onPrimary
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchClicked(cuisine)
                        focusManager.clearFocus()
                    }
                )
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = location,
                onValueChange = {
                    onLocationChange(it)
                },
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.subtitle1.fontSize
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    cursorColor = MaterialTheme.colors.onPrimary.copy(ContentAlpha.medium)
                ),
                placeholder = {
                    Text(
                        modifier = Modifier.alpha(ContentAlpha.medium),
                        text = "Location ie, SanFrancisco...",
                        color = MaterialTheme.colors.onPrimary
                    )
                },
                singleLine = true,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onSearchClicked(location)
                            focusManager.clearFocus()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchClicked(cuisine)
                        focusManager.clearFocus()
                    }
                )
            )
        }
    }
}


@Composable
@Preview
fun DefaultAppBarPreview() {
    DefaultAppBar(
        cuisine = "Seafood",
        location = "Oakland, CA",
        onSearchTriggered = {}
    )
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(
        cuisine = "Seafood",
        location = "Oakland, CA",
        onCuisineChange = {},
        onLocationChange = {},
        onSearchClicked = {}
    )
}
