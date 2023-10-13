package com.tanganthony.myyelp.ui.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.tanganthony.myyelp.R
import com.tanganthony.myyelp.network.YelpRestaurant
import com.tanganthony.myyelp.network.YelpCategories
import com.tanganthony.myyelp.network.YelpLocation

@ExperimentalCoilApi
@Composable
fun RestaurantCard(restaurant: YelpRestaurant?) {

    Card(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp, 4.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row( modifier = Modifier.padding(8.dp) ) {
            if (restaurant?.imageUrl != null) {
                RestaurantImage(
                    imageUrl = restaurant.imageUrl,
                    modifier = Modifier.size(100.dp)
                )
            }
            Column {
                val textPaddingValues = PaddingValues(
                    start = 16.dp,
                    end = 8.dp,
                    bottom = 8.dp
                )
                Text(
                    modifier = Modifier.padding(textPaddingValues),
                    text = restaurant?.name ?: "No Name",
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row {
                    RatingBar(
                        rating = restaurant?.rating?.toFloat() ?: 0.0f,
                        modifier = Modifier.height(20.dp).padding(start = 16.dp)
                    )
                    Text(
                        modifier = Modifier.padding(textPaddingValues),
                        text = "${restaurant?.numReviews ?: 0 } Reviews",
                        style = MaterialTheme.typography.body2,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    modifier = Modifier.padding(textPaddingValues),
                    text = "${restaurant?.location?.address}, ${restaurant?.location?.city}",
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.padding(textPaddingValues),
                    text = restaurant?.categories?.get(0)?.title ?: "no category",
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun RestaurantImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    roundedCornerRadius: Float = 25f
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = imageUrl).apply(block = fun ImageRequest.Builder.() {
                placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_error)
                scale(Scale.FILL)
                transformations(
                    RoundedCornersTransformation(roundedCornerRadius)
                )
            }).build()
        )
        Image(
            painter = painter,
            modifier = modifier,
            contentDescription = "Image"
        )
    }
}

@ExperimentalCoilApi
@Preview(showBackground = false)
@Composable
fun RestaurantCardPreview() {
    RestaurantCard(
        restaurant = YelpRestaurant(
            imageUrl = "https://en.wikipedia.org/wiki/File:Google_2015_logo.svg",
            name = "Fish and Chip",
            rating = 3.5,
            numReviews = 10,
            location = YelpLocation("Oakland", "CA"),
            categories = listOf(YelpCategories("Seafood"))
        )
    )
}
