package dev.softikk.webadminpanel.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import dev.softikk.webkit.theme.DimensTheme

private val WebTextFieldHeight = 56.dp
private val WebTextFieldBorderWidth = 1.dp
private val WebTextFieldContentPadding = 3.dp
private val WebTextFieldShadowRadius = 6.dp

@Composable
fun WebTextFieldKeyValue(
    modifier: Modifier = Modifier,
    keyTextFieldState: TextFieldState,
    valueTextFieldState: TextFieldState
) {
    Box(
        modifier = modifier.wrapContentSize().dropShadow(
            shape = DimensTheme.shapes.mediumShape, shadow = Shadow(
                radius = WebTextFieldShadowRadius,
                offset = DpOffset(0.dp, 1.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(0.1f)
            )
        ).clip(DimensTheme.shapes.mediumShape).border(
            width = WebTextFieldBorderWidth,
            color = MaterialTheme.colorScheme.surfaceContainer,
            shape = DimensTheme.shapes.mediumShape
        ).background(
            color = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.height(WebTextFieldHeight),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                modifier = Modifier.weight(1f),
                state = keyTextFieldState,
                textStyle = MaterialTheme.typography.bodyMedium,
                decorator = TextFieldDefaults.decorator(
                    state = keyTextFieldState,
                    enabled = true,
                    lineLimits = TextFieldLineLimits.SingleLine,
                    outputTransformation = null,
                    interactionSource = remember { MutableInteractionSource() },
                    contentPadding = PaddingValues(
                        vertical = WebTextFieldContentPadding,
                        horizontal = DimensTheme.paddings.mediumPadding
                    ),
                    placeholder = {
                        Text(
                            text = "Key",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    container = {})
            )
            Spacer(
                modifier = Modifier.width(1.dp).fillMaxHeight()
                    .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(0.2f))
            )
            BasicTextField(
                modifier = Modifier.weight(1f),
                state = valueTextFieldState,
                textStyle = MaterialTheme.typography.bodyMedium,
                decorator = TextFieldDefaults.decorator(
                    state = valueTextFieldState,
                    enabled = true,
                    lineLimits = TextFieldLineLimits.SingleLine,
                    outputTransformation = null,
                    interactionSource = remember { MutableInteractionSource() },
                    contentPadding = PaddingValues(
                        vertical = WebTextFieldContentPadding,
                        horizontal = DimensTheme.paddings.mediumPadding
                    ),
                    placeholder = {
                        Text(
                            text = "Value",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    container = {})
            )
        }
    }
}