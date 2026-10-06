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

import io.ktor.http.ContentType
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.swaggerRoutes() {
    get("/openapi.json") {
        val openApiJson = """
        {
          "openapi": "3.0.3",
          "info": {
            "title": "KMPStarter REST API",
            "version": "1.0.0",
            "description": "Production-ready Ktor backend REST API designed for multiplatform consumption across Android, iOS, Desktop, and Web."
          },
          "servers": [
            {
              "url": "http://localhost:8080",
              "description": "Local Development Server"
            }
          ],
          "paths": {
            "/health": {
              "get": {
                "summary": "Health check",
                "responses": {
                  "200": { "description": "Server is healthy" }
                }
              }
            },
            "/api/v1/items": {
              "get": {
                "summary": "Get all items",
                "responses": {
                  "200": { "description": "List of items retrieved successfully" }
                }
              },
              "post": {
                "summary": "Create a new item",
                "requestBody": {
                  "required": true,
                  "content": {
                    "application/json": {
                      "schema": {
                        "type": "object",
                        "properties": {
                          "title": { "type": "string" },
                          "description": { "type": "string" }
                        },
                        "required": ["title", "description"]
                      }
                    }
                  }
                },
                "responses": {
                  "201": { "description": "Item created successfully" },
                  "400": { "description": "Bad request" }
                }
              }
            },
            "/api/v1/items/{id}": {
              "get": {
                "summary": "Get item by ID",
                "parameters": [
                  {
                    "name": "id",
                    "in": "path",
                    "required": true,
                    "schema": { "type": "integer" }
                  }
                ],
                "responses": {
                  "200": { "description": "Item found" },
                  "404": { "description": "Item not found" }
                }
              },
              "put": {
                "summary": "Update an item",
                "parameters": [
                  {
                    "name": "id",
                    "in": "path",
                    "required": true,
                    "schema": { "type": "integer" }
                  }
                ],
                "requestBody": {
                  "required": true,
                  "content": {
                    "application/json": {
                      "schema": {
                        "type": "object",
                        "properties": {
                          "title": { "type": "string" },
                          "description": { "type": "string" }
                        }
                      }
                    }
                  }
                },
                "responses": {
                  "200": { "description": "Item updated successfully" },
                  "404": { "description": "Item not found" }
                }
              },
              "delete": {
                "summary": "Delete an item",
                "parameters": [
                  {
                    "name": "id",
                    "in": "path",
                    "required": true,
                    "schema": { "type": "integer" }
                  }
                ],
                "responses": {
                  "200": { "description": "Item deleted successfully" },
                  "404": { "description": "Item not found" }
                }
              }
            }
          }
        }
        """.trimIndent()
        call.respondText(openApiJson, ContentType.Application.Json)
    }

    get("/docs") {
        val swaggerHtml = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <title>KMPStarter API - Swagger UI</title>
            <link rel="stylesheet" href="https://unpkg.com/swagger-ui-dist@5.11.0/swagger-ui.css" />
            <style>
                body { margin: 0; background: #fafafa; }
            </style>
        </head>
        <body>
            <div id="swagger-ui"></div>
            <script src="https://unpkg.com/swagger-ui-dist@5.11.0/swagger-ui-bundle.js"></script>
            <script>
                window.onload = () => {
                    window.ui = SwaggerUIBundle({
                        url: '/openapi.json',
                        dom_id: '#swagger-ui',
                        presets: [
                            SwaggerUIBundle.presets.apis,
                            SwaggerUIBundle.StandalonePreset
                        ]
                    });
                };
            </script>
        </body>
        </html>
        """.trimIndent()
        call.respondText(swaggerHtml, ContentType.Text.Html)
    }
}
