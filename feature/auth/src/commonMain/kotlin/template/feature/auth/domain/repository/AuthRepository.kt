package template.feature.auth.domain.repository

import template.core.model.auth.AuthModel

interface AuthRepository {
    suspend fun login(email: String): AuthModel
}
