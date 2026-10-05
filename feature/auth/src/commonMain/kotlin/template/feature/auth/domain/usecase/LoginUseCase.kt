package template.feature.auth.domain.usecase

import template.core.model.auth.AuthModel
import template.feature.auth.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String): AuthModel {
        return repository.login(email)
    }
}
