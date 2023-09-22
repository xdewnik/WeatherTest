package com.koolya.weathertest.feature.weather.ui

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.koolya.weathertest.R
import com.koolya.weathertest.core.model.Weather
import com.koolya.weathertest.feature.weather.ui.contract.WeatherList
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.getViewModel

@Composable
fun WeatherListScreen(
    viewModel: WeatherListViewModel = getViewModel(),
    toMapNavigation: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(key1 = Unit) {
        viewModel.sideEffect.collectLatest {
            when (it) {
                WeatherList.SideEffect.NavigateToMap -> toMapNavigation()
            }
        }
    }
    WeatherListScreen(state = state, onEvent = viewModel::setEvent)
}

@Composable
fun WeatherListScreen(
    state: WeatherList.State,
    onEvent: (WeatherList.Event) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            if (!state.isEmpty) {
                FloatingActionButton(onClick = { onEvent(WeatherList.Event.OnAddNewLocationClick) }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        },
    ) { paddingValues ->
        WeatherList(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onEvent = onEvent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherList(
    modifier: Modifier = Modifier,
    state: WeatherList.State,
    onEvent: (WeatherList.Event) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (state.isEmpty) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.empty_list_please_add_location),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(16.dp))

                Button(onClick = { onEvent(WeatherList.Event.OnAddNewLocationClick) }) {
                    Text(text = stringResource(id = R.string.add_new_location))
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = spacedBy(8.dp)
            ) {
                items(items = state.weatherList, key = { weather -> weather.id }) { weather ->
                    WeatherSwipeableItem(weather = weather, onEvent = onEvent)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherSwipeableItem(
    weather: Weather,
    onEvent: (WeatherList.Event) -> Unit,
    dismissState: DismissState = rememberDismissState(
        DismissValue.Default,
        confirmValueChange = {
            if (it == DismissValue.DismissedToStart) {
                onEvent(WeatherList.Event.OnRemoveWeatherClicked(weather))
                true
            } else {
                false
            }
        }
    ),
) {
    SwipeToDismiss(
        state = dismissState,
        dismissContent = {
            WeatherItem(weather = weather)
        },
        background = {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .padding(16.dp)
                    .align(CenterVertically),
                imageVector = Icons.Default.Delete,
                tint = Color.Companion.Red,
                contentDescription = "delete"
            )
        },
        directions = setOf(DismissDirection.EndToStart)
    )
}

@Composable
fun WeatherItem(weather: Weather) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = weather.location.ifEmpty { stringResource(id = R.string.unknown_location) })

            Spacer(modifier = Modifier.width(16.dp))

            Text(text = weather.weather)

            Spacer(modifier = Modifier.width(16.dp))

            Text(text = weather.temp)
        }
    }
}
