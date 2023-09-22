package com.koolya.weathertest.feature.map.ui

import com.google.android.gms.maps.model.LatLng
import com.koolya.weathertest.core.ui.viewmodel.BaseViewModel
import com.koolya.weathertest.feature.map.domain.GetWeatherInfoUseCase
import com.koolya.weathertest.feature.map.domain.SaveWeatherInfoUseCase
import com.koolya.weathertest.feature.map.ui.contract.MapContract
import com.koolya.weathertest.feature.map.ui.mapper.toModel
import com.koolya.weathertest.feature.map.ui.mapper.toUi
import kotlinx.coroutines.Job

class MapViewModel(
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase,
    private val saveWeatherInfoUseCase: SaveWeatherInfoUseCase,
) :
    BaseViewModel<MapContract.Event, MapContract.State, MapContract.SideEffect>() {

    private var weatherInfoRequestJob: Job? = null
    override fun createInitialState(): MapContract.State = MapContract.State()

    override fun handleEvent(event: MapContract.Event) = when (event) {
        is MapContract.Event.OnMapClick -> handleOnMapClick(event.latLng)
        MapContract.Event.OnSaveWeatherClick -> saveWeatherInfo()
        MapContract.Event.HideBottomSheet -> hideBottomSheet()
    }

    private fun saveWeatherInfo() {
        launch {
            setState { copy(weatherBottomSheet = weatherBottomSheet.copy(isLoading = true)) }
            saveWeatherInfoUseCase.invoke(currentState.weatherBottomSheet.weatherInfo!!.toModel())
                .onSuccess { handleEvent(MapContract.Event.HideBottomSheet) }
                .onFailure {
                    setState {
                        copy(
                            weatherBottomSheet = weatherBottomSheet.copy(
                                isLoading = false,
                                error = it.message
                            )
                        )
                    }
                }
        }
    }

    private fun hideBottomSheet() {
        runCatching {
            setState {
                copy(
                    weatherBottomSheet = MapContract.State.WeatherBottomSheet()
                )
            }
            weatherInfoRequestJob?.cancel()
        }
    }

    private fun handleOnMapClick(latLng: LatLng) {
        runCatching {
            setState {
                copy(
                    marker = latLng,
                    weatherBottomSheet = weatherBottomSheet.copy(
                        isVisible = true,
                        isLoading = true
                    )
                )
            }
            weatherInfoRequestJob?.cancel()
        }
        weatherInfoRequestJob = launch {
            getWeatherInfoUseCase.invoke(lat = latLng.latitude, lng = latLng.longitude)
                .onSuccess {
                    setState {
                        copy(
                            weatherBottomSheet = weatherBottomSheet.copy(
                                isLoading = false,
                                weatherInfo = it.toUi()
                            )
                        )
                    }
                }
                .onFailure {
                    setState {
                        copy(
                            weatherBottomSheet = weatherBottomSheet.copy(
                                isLoading = false,
                                error = it.message
                            )
                        )
                    }
                }

        }
    }

}