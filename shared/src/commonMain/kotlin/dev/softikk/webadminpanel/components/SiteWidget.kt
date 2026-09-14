package dev.softikk.webadminpanel.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import dev.softikk.webkit.theme.DimensTheme
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number

@Composable
fun SiteWidget(name: String, host: String, description: String, createAt: LocalDateTime) {
    Box(
        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand).fillMaxWidth().dropShadow(
            shape = DimensTheme.shapes.mediumShape, shadow = Shadow(
                radius = 6.dp,
                offset = DpOffset(0.dp, 1.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(0.1f)
            )
        ).clip(DimensTheme.shapes.mediumShape).border(
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
                text = name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = host,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            val date = createAt.date
            val time = createAt.time
            Text(
                text = "${time.hour.formatDateTimePlusZero()}:${time.minute.formatDateTimePlusZero()} ${date.day.formatDateTimePlusZero()}.${date.month.number.formatDateTimePlusZero()}.${date.year.formatDateTimePlusZero()}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

fun Int.formatDateTimePlusZero(): String = if (this < 10) "0$this" else this.toString()