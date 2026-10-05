package template.feature.home.domain.usecase

import template.core.model.home.HomeModel
import template.feature.home.domain.repository.HomeRepository

class GetHomeDataUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke(): HomeModel {
        return repository.getHomeData()
    }
}
