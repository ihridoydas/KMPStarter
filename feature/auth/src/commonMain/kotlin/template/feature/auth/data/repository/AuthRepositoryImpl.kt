package template.feature.auth.data.repository

import template.core.model.auth.AuthModel
import template.feature.auth.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(email: String): AuthModel {
        return AuthModel(
            token = "dummy_token_12345",
            email = email,
        )
    }
}
