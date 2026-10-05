package template.core.model.home

sealed interface HomeState {
    data object Loading : HomeState
    data class Success(val data: HomeModel) : HomeState
    data class Error(val message: String) : HomeState
}
