/*
* MIT License
*
* Copyright (c) 2026 Hridoy Chandra Das
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*
*/
package template.server.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import template.server.data.ErrorResponse
import template.server.data.ItemCreateRequest
import template.server.data.ItemRepository
import template.server.data.ItemUpdateRequest

fun Route.itemRoutes() {
    route("/api/v1/items") {
        get {
            call.respond(ItemRepository.getAll())
        }

        post {
            try {
                val request = call.receive<ItemCreateRequest>()
                if (request.title.isBlank()) {
                    call.respond(HttpStatusCode.BadRequest, ErrorResponse(400, "Title cannot be blank"))
                    return@post
                }
                val newItem = ItemRepository.create(request.title.trim(), request.description.trim())
                call.respond(HttpStatusCode.Created, newItem)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse(400, "Invalid request body: ${e.localizedMessage}"))
            }
        }

        get("{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse(400, "Invalid item ID format"))
                return@get
            }
            val item = ItemRepository.getById(id)
            if (item == null) {
                call.respond(HttpStatusCode.NotFound, ErrorResponse(404, "Item with ID $id not found"))
            } else {
                call.respond(item)
            }
        }

        put("{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse(400, "Invalid item ID format"))
                return@put
            }
            try {
                val request = call.receive<ItemUpdateRequest>()
                val updated = ItemRepository.update(id, request.title, request.description)
                if (updated == null) {
                    call.respond(HttpStatusCode.NotFound, ErrorResponse(404, "Item with ID $id not found"))
                } else {
                    call.respond(updated)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse(400, "Invalid request body: ${e.localizedMessage}"))
            }
        }

        delete("{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, ErrorResponse(400, "Invalid item ID format"))
                return@delete
            }
            val removed = ItemRepository.delete(id)
            if (removed == null) {
                call.respond(HttpStatusCode.NotFound, ErrorResponse(404, "Item with ID $id not found"))
            } else {
                call.respond(HttpStatusCode.OK, mapOf("success" to true, "deletedId" to id))
            }
        }
    }
}
