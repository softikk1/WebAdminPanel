package dev.softikk.webadminpanel.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.softikk.webadminpanel.Routes
import dev.softikk.webkit.theme.DimensTheme
import org.jetbrains.compose.resources.vectorResource
import webadminpanel.shared.generated.resources.Res
import webadminpanel.shared.generated.resources.plus
import webadminpanel.shared.generated.resources.search
import webadminpanel.shared.generated.resources.settings
import webadminpanel.shared.generated.resources.x

private val WebToolboxShadowRadius = 6.dp
private val WebToolboxIconSize = 24.dp
private val WebToolboxItemVerticalPadding = 120.dp
private val WidthSearchTextField = 400.dp

@Composable
fun WebToolbox(
    modifier: Modifier = Modifier, navController: NavHostController
) {
    var isSearch by remember { mutableStateOf(false) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(
        modifier = modifier.dropShadow(
            shape = DimensTheme.shapes.mediumShape, shadow = Shadow(
                radius = WebToolboxShadowRadius,
                offset = DpOffset(0.dp, 1.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(0.1f)
            )
        ).clip(DimensTheme.shapes.mediumShape).background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        if (isSearch) {
            val searchTextFieldState = rememberTextFieldState()

            WebTextField(
                modifier = Modifier.widthIn(min = WidthSearchTextField),
                state = searchTextFieldState,
                labelText = "search",
                leadingIcon = {
                    Icon(
                        imageVector = vectorResource(Res.drawable.search), contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(modifier = Modifier.pointerHoverIcon(PointerIcon.Hand), onClick = {
                        if (searchTextFieldState.text.isNotEmpty()) {
                            searchTextFieldState.edit {
                                replace(0, searchTextFieldState.text.length, "")
                            }
                        } else {
                            isSearch = false
                        }
                    }) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.x), contentDescription = null
                        )
                    }
                })
        } else {
            Row(
                modifier = Modifier.padding(end = DimensTheme.paddings.smallPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(DimensTheme.paddings.smallPadding)
            ) {
                IconButton(
                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand), onClick = {
                        isSearch = true
                    }) {
                    Icon(
                        modifier = Modifier.size(WebToolboxIconSize),
                        imageVector = vectorResource(
                            Res.drawable.search
                        ),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(
                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand), onClick = {

                    }) {
                    Icon(
                        modifier = Modifier.size(WebToolboxIconSize),
                        imageVector = vectorResource(
                            Res.drawable.plus
                        ),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(
                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand), onClick = {

                    }) {
                    Icon(
                        modifier = Modifier.size(WebToolboxIconSize),
                        imageVector = vectorResource(
                            Res.drawable.settings
                        ),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                WebButton(
                    modifier = Modifier.width(WebToolboxItemVerticalPadding),
                    containerColor = if (currentRoute == Routes.Main.Sites.route) MaterialTheme.colorScheme.primary else Color.Transparent,
                    onClick = {
                        navController.navigate(Routes.Main.Sites.route)
                    }) {
                    Text(
                        modifier = Modifier.padding(vertical = DimensTheme.paddings.smallPadding),
                        text = "Сайты",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (currentRoute == Routes.Main.Sites.route) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface
                    )
                }

                WebButton(
                    modifier = Modifier.width(WebToolboxItemVerticalPadding),
                    containerColor = if (currentRoute == Routes.Main.Admins.route) MaterialTheme.colorScheme.primary else Color.Transparent,
                    onClick = {
                        navController.navigate(Routes.Main.Admins.route)
                    }) {
                    Text(
                        modifier = Modifier.padding(vertical = DimensTheme.paddings.smallPadding),
                        text = "Админы",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (currentRoute == Routes.Main.Admins.route) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}