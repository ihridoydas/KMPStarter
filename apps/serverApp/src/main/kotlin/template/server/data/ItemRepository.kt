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
package template.server.data

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

object ItemRepository {
    private val itemStore = ConcurrentHashMap<Long, Item>()
    private val idCounter = AtomicLong(0)

    init {
        // Seed initial demo data
        val id1 = idCounter.incrementAndGet()
        itemStore[id1] =
            Item(id1, "KMPStarter Architecture", "Explore multiplatform clean architecture across Android, iOS, Desktop, and Web.")
        val id2 = idCounter.incrementAndGet()
        itemStore[id2] = Item(id2, "Ktor REST API", "Build production-ready backend services with Kotlin and Ktor.")
    }

    fun getAll(): List<Item> = itemStore.values.sortedByDescending { it.createdAt }

    fun getById(id: Long): Item? = itemStore[id]

    fun create(title: String, description: String): Item {
        val newId = idCounter.incrementAndGet()
        val newItem = Item(id = newId, title = title, description = description)
        itemStore[newId] = newItem
        return newItem
    }

    fun update(id: Long, title: String?, description: String?): Item? {
        val existing = itemStore[id] ?: return null
        val updated = existing.copy(
            title = title?.trim() ?: existing.title,
            description = description?.trim() ?: existing.description,
        )
        itemStore[id] = updated
        return updated
    }

    fun delete(id: Long): Item? = itemStore.remove(id)
}
