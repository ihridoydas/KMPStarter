package template.core.designsystem.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import template.core.designsystem.components.AppBar
import template.core.designsystem.generated.resources.Res
import template.core.designsystem.generated.resources.nice_to_meet_you
import template.core.designsystem.generated.resources.welcome

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewScreen(onBackPress: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(
                title = stringResource(Res.string.welcome),
                navIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNav = onBackPress,
            )
        },
        content = {
            Box(modifier = Modifier.padding(it)) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    Text(
                        text = stringResource(Res.string.nice_to_meet_you),
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }
        },
    )
}
