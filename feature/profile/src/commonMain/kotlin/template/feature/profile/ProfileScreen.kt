package template.feature.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import template.core.designsystem.components.AppBar
import template.navigation.api.Navigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navigator: Navigator) {
    Scaffold(
        topBar = {
            AppBar(
                title = "Profile",
                navIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNav = { navigator.goBack() },
            )
        },
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
            Text("Profile Screen")
        }
    }
}
