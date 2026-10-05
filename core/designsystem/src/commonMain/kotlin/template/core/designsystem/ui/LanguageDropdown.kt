package template.core.designsystem.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import template.core.common.LanguageManager
import template.core.model.language.Language

@Composable
fun LanguageDropdown() {
    val currentLanguage by LanguageManager.currentLanguage.collectAsState()

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center,
    ) {
        IconButton(
            onClick = { expanded = true },
            modifier = Modifier.align(Alignment.Center),
        ) {
            Icon(
                imageVector = Icons.Default.Language,
                contentDescription = "Language Menu",
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                text = {
                    Row {
                        Text("System Default")
                        if (currentLanguage == Language.SYSTEM) {
                            Spacer(Modifier.width(5.dp))
                            Text("✓")
                        }
                    }
                },
                onClick = {
                    expanded = false
                    LanguageManager.setLanguage(Language.SYSTEM)
                },
            )
            DropdownMenuItem(
                text = {
                    Row {
                        Text("English")
                        if (currentLanguage == Language.ENGLISH) {
                            Spacer(Modifier.width(5.dp))
                            Text("✓")
                        }
                    }
                },
                onClick = {
                    expanded = false
                    LanguageManager.setLanguage(Language.ENGLISH)
                },
            )
            DropdownMenuItem(
                text = {
                    Row {
                        Text("日本語")
                        if (currentLanguage == Language.JAPANESE) {
                            Spacer(Modifier.width(5.dp))
                            Text("✓")
                        }
                    }
                },
                onClick = {
                    expanded = false
                    LanguageManager.setLanguage(Language.JAPANESE)
                },
            )
            DropdownMenuItem(
                text = {
                    Row {
                        Text(if (currentLanguage == Language.BENGALI) "বাংলা" else "Bangla")
                        if (currentLanguage == Language.BENGALI) {
                            Spacer(Modifier.width(5.dp))
                            Text("✓")
                        }
                    }
                },
                onClick = {
                    expanded = false
                    LanguageManager.setLanguage(Language.BENGALI)
                },
            )
        }
    }
}
