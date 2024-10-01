package com.lunchmenu.home.presentation.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.lunchmenu.R
import com.lunchmenu.home.presentation.viewmodel.HomeState
import com.lunchmenu.home.presentation.viewmodel.HomeViewModel

//sealed interface State {
//    data object Loading : State
//    data object Error : State
//    data object ShowLegalAgreements : State
//    data object ShowRadarTooltip : State
//    data object ShowPopups : State
//    data class ShowTapsAndViewedMeNotification(val isBoosting: Boolean, val viewedMeCount: Int, val tapsCount: Int) : State
//    sealed interface PopupUi : State {
//        data object ShowGenderUpdates : PopupUi
//        data object ShowRoamOnboarding : PopupUi
//        data object ShowBraze : PopupUi
//        data object ShowInstagramAuthentication : PopupUi
//        data object None : PopupUi
//    }
//}

private const val TAG = "HomeViewModel"

@Composable
fun HomeScreen(modifier: Modifier, viewModel: HomeViewModel = viewModel()) = Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
) {
    val uiState = viewModel.mapOfWeeksAndMenu.collectAsState()
    when (uiState.value) {
        HomeState.Error -> Text("An error occurred. Please try again.")
        HomeState.Loading -> CircularProgressIndicator()
        is HomeState.ShowMenu -> {
            Log.i(TAG, "HomeScreen: UI Loaded")
            CalendarGridView((uiState.value as HomeState.ShowMenu).list)
        }
    }

}

@Composable
fun CalendarGridView(mealCalendarMap: List<Pair<String, String>>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        items(mealCalendarMap.size) { index ->
            val (date, meal) = mealCalendarMap[index]
            CalendarDayItem(date = date, meal = meal)
        }
    }
}

/**
 *  https://s3-media2.fl.yelpcdn.com/bphoto/DMc3Bgf8NahyzLFuTSrA5Q/o.jpg
 *  https://s3-media2.fl.yelpcdn.com/bphoto/GrMIfmWngDxdnPfZMdoKpw/o.jpg
 *  https://s3-media2.fl.yelpcdn.com/bphoto/jx0z_0DtZ0b5zPu-6zADFQ/o.jpg
 *  https://s3-media2.fl.yelpcdn.com/bphoto/FHHj6L0eN1mOBnzmhy6ZrA/o.jpg
 *  https://s3-media1.fl.yelpcdn.com/bphoto/Vhf1v9FKU-n-PH4GgkVAVg/o.jpg
 *  https://s3-media1.fl.yelpcdn.com/bphoto/YmsB0Prxhi4h6SVI2FEVbQ/o.jpg
 *  https://s3-media2.fl.yelpcdn.com/bphoto/fy8WOGJM_PLWP7oY9C11tQ/o.jpg
 *  https://s3-media4.fl.yelpcdn.com/bphoto/KcBFGl5QOMJwuRE2NBnamg/o.jpg
 */

@Composable
fun CalendarDayItem(date: String, meal: String) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp) // Add elevation for card-like look
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (LocalInspectionMode.current) {
                Image(
                    painterResource(R.drawable.placeholder_image),
                    contentDescription = "test",
                )
            } else {
                AsyncImage(
                    model = "https://s3-media2.fl.yelpcdn.com/bphoto/DMc3Bgf8NahyzLFuTSrA5Q/o.jpg",
                    contentDescription = "test",
                    modifier = Modifier.size(64.dp) // Optional: adjust size as needed
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally // Center text horizontally
            ) {
                Text(
                    text = date,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    textAlign = TextAlign.Left,
                    maxLines = 1, // Ensure single line
                    overflow = TextOverflow.Ellipsis // Add ellipsis if text overflows
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = meal,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Left
                )
            }
        }
    }
}

@Preview
@Composable
fun CalendarGridPreview() {
    val list = listOf(
        Pair("Mon, Feb 30", "Pizza"),
        Pair("Tue, Feb 31", "Pizza2"),
    )
    CalendarGridView(list)
}
