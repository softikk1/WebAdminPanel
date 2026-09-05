package dev.softikk.webadminpanel.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import dev.softikk.webkit.theme.DimensTheme
import org.jetbrains.compose.resources.vectorResource
import webadminpanel.shared.generated.resources.Res
import webadminpanel.shared.generated.resources.eye
import webadminpanel.shared.generated.resources.eye_off

private val WebTextFieldHeight = 56.dp
private val WebTextFieldBorderWidth = 1.dp
private val WebTextFieldContentPadding = 3.dp
private val WebTextFieldTrailingIconSize = 24.dp
private val WebTextFieldShadowRadius = 6.dp

@Composable
fun WebTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    labelText: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Box(
        modifier = Modifier.height(WebTextFieldHeight).dropShadow(
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
        ), contentAlignment = Alignment.Center
    ) {
        val isPassword = keyboardType == KeyboardType.Password
        if (isPassword) {
            var showPassword by remember { mutableStateOf(true) }
            BasicSecureTextField(
                modifier = modifier.padding(
                    horizontal = DimensTheme.paddings.mediumPadding
                ),
                state = state,
                textObfuscationMode = if (showPassword) {
                    TextObfuscationMode.Visible
                } else {
                    TextObfuscationMode.RevealLastTyped
                },
                textStyle = MaterialTheme.typography.bodyMedium,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                decorator = TextFieldDefaults.decorator(
                    state = state,
                    enabled = true,
                    lineLimits = TextFieldLineLimits.SingleLine,
                    outputTransformation = null,
                    interactionSource = remember { MutableInteractionSource() },
                    contentPadding = PaddingValues(vertical = WebTextFieldContentPadding),
                    placeholder = {
                        Text(
                            text = labelText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand), onClick = {
                                showPassword = !showPassword
                            }) {
                            Icon(
                                modifier = Modifier.size(WebTextFieldTrailingIconSize),
                                imageVector = vectorResource(
                                    if (showPassword) Res.drawable.eye_off else Res.drawable.eye
                                ),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    container = {})
            )
        } else {
            BasicTextField(
                modifier = modifier.padding(
                    horizontal = DimensTheme.paddings.mediumPadding
                ),
                state = state,
                textStyle = MaterialTheme.typography.bodyMedium,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                decorator = TextFieldDefaults.decorator(
                    state = state,
                    enabled = true,
                    lineLimits = TextFieldLineLimits.SingleLine,
                    outputTransformation = null,
                    interactionSource = remember { MutableInteractionSource() },
                    contentPadding = PaddingValues(vertical = WebTextFieldContentPadding),
                    placeholder = {
                        Text(
                            text = labelText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    container = {})
            )
        }
    }
}