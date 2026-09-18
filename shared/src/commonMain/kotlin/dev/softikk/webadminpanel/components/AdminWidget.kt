package dev.softikk.webadminpanel.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.softikk.webadminpanel.Admin
import dev.softikk.webadminpanel.Routes
import dev.softikk.webkit.theme.DimensTheme

private val AdminWidgetShadowRadius = 6.dp

@Composable
fun AdminWidget(admin: Admin, navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxWidth().dropShadow(
            shape = DimensTheme.shapes.mediumShape, shadow = Shadow(
                radius = AdminWidgetShadowRadius,
                offset = DpOffset(0.dp, 1.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(0.1f)
            )
        ).clip(DimensTheme.shapes.mediumShape).pointerHoverIcon(PointerIcon.Hand).clickable {
            navController.navigate(
                Routes.Main.Admins.Details(
                    adminId = admin.id
                ).route
            )
        }.border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            shape = DimensTheme.shapes.mediumShape
        ).background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(DimensTheme.paddings.mediumPadding),
            verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.smallPadding)
        ) {
            Text(
                text = admin.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = admin.email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = admin.password,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
