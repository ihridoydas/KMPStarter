package template.feature.auth.presentation

import template.core.model.auth.AuthModel

sealed interface AuthState {
    data object Idle : AuthState
    data object Loading : AuthState
    data class Success(val auth: AuthModel) : AuthState
    data class Error(val message: String) : AuthState
}
