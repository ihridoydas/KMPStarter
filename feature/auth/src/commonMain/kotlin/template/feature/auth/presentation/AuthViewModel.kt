package template.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import template.feature.auth.domain.usecase.LoginUseCase

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state.asStateFlow()

    fun login(email: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            try {
                val result = loginUseCase(email)
                _state.value = AuthState.Success(result)
            } catch (@Suppress("TooGenericExceptionCaught") e: Exception) {
                _state.value = AuthState.Error(e.message ?: "Login failed")
            }
        }
    }
}
