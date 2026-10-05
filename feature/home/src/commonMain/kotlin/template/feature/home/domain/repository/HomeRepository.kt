package template.feature.home.domain.repository

import template.core.model.home.HomeModel

interface HomeRepository {
    suspend fun getHomeData(): HomeModel
}
