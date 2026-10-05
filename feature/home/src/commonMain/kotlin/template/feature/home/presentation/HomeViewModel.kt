package template.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import template.core.model.home.HomeState
import template.feature.home.domain.usecase.GetHomeDataUseCase

class HomeViewModel(
    private val getHomeDataUseCase: GetHomeDataUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _state.value = HomeState.Loading
            try {
                val data = getHomeDataUseCase()
                _state.value = HomeState.Success(data)
            } catch (@Suppress("TooGenericExceptionCaught") e: Exception) {
                _state.value = HomeState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
