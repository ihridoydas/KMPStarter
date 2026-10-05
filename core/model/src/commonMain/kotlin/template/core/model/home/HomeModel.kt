package template.core.model.home

import kotlinx.serialization.Serializable

@Serializable
data class HomeModel(
    val title: String,
    val description: String,
)
