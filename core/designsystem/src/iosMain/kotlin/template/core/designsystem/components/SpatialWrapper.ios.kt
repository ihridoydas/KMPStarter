package template.core.designsystem.components

import androidx.compose.runtime.Composable

@Composable
actual fun SpatialWrapper(content: @Composable () -> Unit) {
    content()
}
