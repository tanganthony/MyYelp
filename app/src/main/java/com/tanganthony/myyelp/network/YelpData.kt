package com.tanganthony.myyelp.network

import com.google.gson.annotations.SerializedName

data class YelpData(
    @SerializedName("businesses") val restaurants : List<YelpRestaurant?>
)

data class YelpRestaurant(
    @SerializedName("image_url") val imageUrl: String?,
    val name: String?,
    val rating: Double?,
    @SerializedName("review_count") val numReviews: Int?,
    val location: YelpLocation?,
    val categories: List<YelpCategories?>,
    @SerializedName("distance") val distanceInMeters: Double = 0.0,
) {
    fun displayDistance(): String {
        val milesPerMeter = 0.000621371
        val distanceInMiles = "%.2f".format(distanceInMeters * milesPerMeter)
        return "$distanceInMiles mi"
    }
}

data class YelpLocation(
    @SerializedName("address1") val address: String?,
    val city: String?
)

data class YelpCategories(
    val title: String?
)
