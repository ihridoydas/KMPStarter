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
package template.core.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import template.core.datastore.local.language.LanguageDataStore
import template.core.model.language.Language

object LanguageManager {
    private val _currentLanguage = MutableStateFlow(Language.SYSTEM)
    val currentLanguage: StateFlow<Language> = _currentLanguage.asStateFlow()

    private var dataStore: LanguageDataStore? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var isInitialized = false

    fun init(languageDataStore: LanguageDataStore) {
        if (isInitialized) return
        isInitialized = true
        dataStore = languageDataStore

        scope.launch {
            languageDataStore.getLanguage.collect { savedLanguage ->
                val finalLanguage = if (savedLanguage == Language.UNKNOWN) Language.SYSTEM else savedLanguage
                _currentLanguage.value = finalLanguage
            }
        }
    }

    fun resetForTesting() {
        isInitialized = false
        dataStore = null
        _currentLanguage.value = Language.SYSTEM
    }

    fun setLanguage(language: Language) {
        _currentLanguage.value = language
        scope.launch {
            dataStore?.setLanguage(language)
        }
    }
}
