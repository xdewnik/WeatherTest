package com.koolya.weathertest.ui.feature.map.ui

import com.koolya.weathertest.core.ui.viewmodel.BaseViewModel
import com.koolya.weathertest.ui.feature.map.ui.contract.MapContract

class MapViewModel : BaseViewModel<MapContract.Event, MapContract.State, MapContract.SideEffect>() {
    override fun createInitialState(): MapContract.State = MapContract.State()

    override fun handleEvent(event: MapContract.Event) = when (event) {
        is MapContract.Event.OnMapClick -> setState { copy(marker = event.latLng) }
        MapContract.Event.OnSaveWeatherClick -> TODO()
        MapContract.Event.HideBottomSheet -> TODO()
    }

}