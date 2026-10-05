package template.core.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthModel(
    val token: String,
    val email: String,
)
