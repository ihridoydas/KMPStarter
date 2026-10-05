package template.core.designsystem.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import template.core.designsystem.generated.resources.Res
import template.core.designsystem.generated.resources.app_name
import template.core.designsystem.generated.resources.hello_developer
import template.core.designsystem.ui.LanguageDropdown
import template.core.designsystem.ui.ThemeToggleButton
import template.navigation.api.Navigator
import template.navigation.api.ScreenDestinations
import template.core.datastore.local.theme.ThemeLocalDataStore
import template.core.model.theme.ThemeMode

@Composable
fun HomeScreen(navigator: Navigator, themeDataStore: ThemeLocalDataStore = koinInject()) {
    val themeMode by themeDataStore.themeMode
        .collectAsState(initial = ThemeMode.SYSTEM)

    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            LanguageDropdown()
            ThemeToggleButton(
                themeMode = themeMode,
                onToggle = { newMode ->
                    scope.launch {
                        themeDataStore.setThemeMode(newMode)
                    }
                },
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(
                    Res.string.hello_developer,
                    stringResource(Res.string.app_name),
                ),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Button(
                modifier = Modifier.size(120.dp, 40.dp),
                onClick = {
                    navigator.navigate(ScreenDestinations.ViewScreen)
                },
            ) {
                Text(
                    text = "Lets Start!",
                    color = MaterialTheme.colorScheme.background,
                )
            }
        }
    }
}
