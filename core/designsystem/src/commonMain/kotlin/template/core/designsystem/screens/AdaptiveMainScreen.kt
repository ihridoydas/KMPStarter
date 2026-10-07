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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import template.core.designsystem.components.SpatialWrapper
import template.core.designsystem.screens.formfactors.AutomotiveScreen
import template.core.designsystem.screens.formfactors.TvScreen
import template.core.designsystem.screens.formfactors.WearOsScreen
import template.core.designsystem.screens.formfactors.XrScreen

enum class FormFactorMode {
    ADAPTIVE,
    TV,
    WEAR_OS,
    AUTOMOTIVE,
    XR,
}

@Composable
fun AdaptiveMainScreen(title: String = "KMPStarter Adaptive UI", onActionClicked: () -> Unit = {}) {
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
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize().padding(16.dp),
            ) {
                val screenWidth = maxWidth

                when {
                    // 1. Compact Window (Phones, Wear OS, Narrow screens < 600.dp)
                    screenWidth < 600.dp -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "$title (Compact / Phone)",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(bottom = 16.dp),
                            )
                            Button(onClick = onActionClicked, modifier = Modifier.padding(bottom = 8.dp)) {
                                Text("Get Started")
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(onClick = { selectedMode = FormFactorMode.WEAR_OS }) { Text("Watch") }
                                Button(onClick = { selectedMode = FormFactorMode.XR }) { Text("XR") }
                            }
                        }
                    }

                    // 2. Medium Window (Tablets, Foldables, 600.dp - 840.dp)
                    screenWidth in 600.dp..840.dp -> {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.spacedBy(24.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "$title (Medium / Tablet)",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            }
                            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                                Button(onClick = onActionClicked, modifier = Modifier.padding(bottom = 12.dp)) {
                                    Text("Explore Tablet View")
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(onClick = { selectedMode = FormFactorMode.TV }) { Text("TV") }
                                    Button(onClick = { selectedMode = FormFactorMode.AUTOMOTIVE }) { Text("Auto") }
                                    Button(onClick = { selectedMode = FormFactorMode.XR }) { Text("XR") }
                                }
                            }
                        }
                    }

                    // 3. Expanded Window (Desktop, Web, TV, Automotive, > 840.dp)
                    else -> {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.spacedBy(32.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            // Sidebar / Navigation Rail area with Form Factor Switcher
                            Surface(
                                modifier = Modifier.weight(0.3f).fillMaxWidth(),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = MaterialTheme.shapes.medium,
                            ) {
                                Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                    Text("Form Factors", style = MaterialTheme.typography.titleMedium)
                                    Button(onClick = {
                                        selectedMode = FormFactorMode.TV
                                    }, modifier = Modifier.fillMaxWidth()) { Text("TV Mode") }
                                    Button(onClick = {
                                        selectedMode = FormFactorMode.AUTOMOTIVE
                                    }, modifier = Modifier.fillMaxWidth()) { Text("Automotive Mode") }
                                    Button(onClick = {
                                        selectedMode = FormFactorMode.WEAR_OS
                                    }, modifier = Modifier.fillMaxWidth()) { Text("Wear OS Mode") }
                                    Button(onClick = {
                                        selectedMode = FormFactorMode.XR
                                    }, modifier = Modifier.fillMaxWidth()) { Text("XR Spatial Mode") }
                                }
                            }
                            // Main Content area
                            Column(
                                modifier = Modifier.weight(0.7f).fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start,
                            ) {
                                Text(
                                    text = "$title (Expanded / Desktop & Widescreen)",
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(bottom = 16.dp),
                                )
                                Text(
                                    text = "This layout automatically adapts to widescreen, desktop, and " +
                                        "allows previewing specialized form factors (TV, Automotive, Wear OS, XR) " +
                                        "from the left panel.",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(bottom = 24.dp),
                                )
                                Button(onClick = onActionClicked) {
                                    Text("Launch Action")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
