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
package template.core.designsystem.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import template.core.designsystem.components.AppBar
import template.core.designsystem.components.SpatialWrapper
import template.core.designsystem.generated.resources.Res
import template.core.designsystem.generated.resources.auto
import template.core.designsystem.generated.resources.automotive_mode
import template.core.designsystem.generated.resources.form_factors
import template.core.designsystem.generated.resources.nice_to_meet_you
import template.core.designsystem.generated.resources.tv
import template.core.designsystem.generated.resources.tv_mode
import template.core.designsystem.generated.resources.watch
import template.core.designsystem.generated.resources.wear_os_mode
import template.core.designsystem.generated.resources.welcome
import template.core.designsystem.generated.resources.xr
import template.core.designsystem.generated.resources.xr_spatial_mode
import template.core.designsystem.model.FormFactorMode
import template.core.designsystem.screens.formfactors.AutomotiveScreen
import template.core.designsystem.screens.formfactors.TvScreen
import template.core.designsystem.screens.formfactors.WearOsScreen
import template.core.designsystem.screens.formfactors.XrScreen

@Composable
fun ViewScreen(onBackPress: () -> Unit) {
    var selectedMode by rememberSaveable { mutableStateOf(FormFactorMode.ADAPTIVE) }

    when (selectedMode) {
        FormFactorMode.TV -> {
            TvScreen(onItemSelected = { selectedMode = FormFactorMode.ADAPTIVE })
            return
        }

        FormFactorMode.WEAR_OS -> {
            WearOsScreen(onActionClicked = { selectedMode = FormFactorMode.ADAPTIVE })
            return
        }

        FormFactorMode.AUTOMOTIVE -> {
            AutomotiveScreen(
                onNavigationClick = { selectedMode = FormFactorMode.ADAPTIVE },
                onMediaClick = { selectedMode = FormFactorMode.ADAPTIVE },
            )
            return
        }

        FormFactorMode.XR -> {
            XrScreen(onSpatialAction = { selectedMode = FormFactorMode.ADAPTIVE })
            return
        }

        FormFactorMode.ADAPTIVE -> {}
    }

    SpatialWrapper {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AppBar(
                    title = stringResource(Res.string.welcome),
                    navIcon = Icons.AutoMirrored.Filled.ArrowBack,
                    onNav = onBackPress,
                )
            },
            content = { paddingValues ->
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                ) {
                    val screenWidth = maxWidth

                    when {
                        // Compact / Phone / Wear OS
                        screenWidth < 600.dp -> {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxSize(),
                            ) {
                                Text(
                                    text = stringResource(Res.string.nice_to_meet_you),
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(16.dp),
                                )
                                Row(
                                    modifier = Modifier.padding(top = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    Button(onClick = { selectedMode = FormFactorMode.TV }) {
                                        Text(stringResource(Res.string.tv))
                                    }
                                    Button(onClick = { selectedMode = FormFactorMode.WEAR_OS }) {
                                        Text(stringResource(Res.string.watch))
                                    }
                                    Button(onClick = { selectedMode = FormFactorMode.AUTOMOTIVE }) {
                                        Text(stringResource(Res.string.auto))
                                    }
                                    Button(onClick = { selectedMode = FormFactorMode.XR }) {
                                        Text(stringResource(Res.string.xr))
                                    }
                                }
                            }
                        }

                        // Medium / Tablet / Foldable
                        screenWidth in 600.dp..840.dp -> {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.spacedBy(24.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Tablet View: Details Panel",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                    )
                                }
                                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = stringResource(Res.string.nice_to_meet_you),
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.padding(bottom = 12.dp),
                                    )
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(onClick = { selectedMode = FormFactorMode.TV }) {
                                            Text(stringResource(Res.string.tv))
                                        }
                                        Button(onClick = { selectedMode = FormFactorMode.WEAR_OS }) {
                                            Text(stringResource(Res.string.watch))
                                        }
                                        Button(onClick = { selectedMode = FormFactorMode.AUTOMOTIVE }) {
                                            Text(stringResource(Res.string.auto))
                                        }
                                        Button(onClick = { selectedMode = FormFactorMode.XR }) {
                                            Text(stringResource(Res.string.xr))
                                        }
                                    }
                                }
                            }
                        }

                        // Expanded / Desktop / TV / Automotive / Widescreen
                        else -> {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.spacedBy(32.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Surface(
                                    modifier = Modifier.weight(0.3f).fillMaxWidth(),
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = MaterialTheme.shapes.medium,
                                ) {
                                    Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                        Text(
                                            stringResource(Res.string.form_factors),
                                            style = MaterialTheme.typography.titleMedium,
                                        )
                                        Button(onClick = {
                                            selectedMode = FormFactorMode.TV
                                        }, modifier = Modifier.fillMaxWidth()) {
                                            Text(stringResource(Res.string.tv_mode))
                                        }
                                        Button(onClick = {
                                            selectedMode = FormFactorMode.AUTOMOTIVE
                                        }, modifier = Modifier.fillMaxWidth()) {
                                            Text(stringResource(Res.string.automotive_mode))
                                        }
                                        Button(onClick = {
                                            selectedMode = FormFactorMode.WEAR_OS
                                        }, modifier = Modifier.fillMaxWidth()) {
                                            Text(stringResource(Res.string.wear_os_mode))
                                        }
                                        Button(onClick = {
                                            selectedMode = FormFactorMode.XR
                                        }, modifier = Modifier.fillMaxWidth()) {
                                            Text(stringResource(Res.string.xr_spatial_mode))
                                        }
                                    }
                                }
                                Column(
                                    modifier = Modifier.weight(0.7f).fillMaxWidth(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.Start,
                                ) {
                                    Text(
                                        text = stringResource(Res.string.nice_to_meet_you),
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(bottom = 16.dp),
                                    )
                                    Text(
                                        text = "Optimized for widescreen, desktop, Android TV, and Automotive " +
                                            "dashboards. Use the sidebar to preview dedicated form factor UIs.",
                                        style = MaterialTheme.typography.bodyLarge,
                                    )
                                }
                            }
                        }
                    }
                }
            },
        )
    }
}
