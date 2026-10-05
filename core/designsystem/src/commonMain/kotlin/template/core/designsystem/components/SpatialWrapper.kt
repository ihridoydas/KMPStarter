package template.core.designsystem.components

import androidx.compose.runtime.Composable

@Composable
expect fun SpatialWrapper(content: @Composable () -> Unit)
