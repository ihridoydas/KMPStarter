package template.feature.home.data.repository

import template.core.model.home.HomeModel
import template.feature.home.domain.repository.HomeRepository

class HomeRepositoryImpl : HomeRepository {
    override suspend fun getHomeData(): HomeModel {
        return HomeModel(
            title = "Welcome to KMP",
            description = "Production multi-module architecture",
        )
    }
}
