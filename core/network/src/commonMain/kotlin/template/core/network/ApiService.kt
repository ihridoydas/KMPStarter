package template.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable

@Serializable
data class Post(val userId: Int, val id: Int, val title: String, val body: String)

class ApiService(private val client: HttpClient) {
    suspend fun getPosts(): List<Post> = client.get("https://jsonplaceholder.typicode.com/posts").body()
}
