package template.core.designsystem.components

import androidx.compose.runtime.Composable
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.MovePolicy
import androidx.xr.compose.subspace.ResizePolicy
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.layout.SubspaceModifier

@Composable
actual fun SpatialWrapper(content: @Composable () -> Unit) {
    if (LocalSpatialCapabilities.current.isSpatialUiEnabled) {
        Subspace {
            SpatialPanel(
                modifier = SubspaceModifier,
                dragPolicy = MovePolicy(),
                resizePolicy = ResizePolicy(),
            ) {
                content()
            }
        }
    } else {
        content()
    }
}
