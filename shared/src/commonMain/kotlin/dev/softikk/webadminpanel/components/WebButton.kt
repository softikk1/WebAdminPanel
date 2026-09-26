package dev.softikk.webadminpanel.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import dev.softikk.webkit.theme.DimensTheme

@Composable
fun WebButton(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentPadding: PaddingValues = PaddingValues(vertical = DimensTheme.paddings.smallPadding),
    cornerRadius: Shape = DimensTheme.shapes.mediumShape,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier.pointerHoverIcon(PointerIcon.Hand).clip(cornerRadius)
            .clickable(onClick = onClick).background(containerColor),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.padding(contentPadding))
        content()
    }
}