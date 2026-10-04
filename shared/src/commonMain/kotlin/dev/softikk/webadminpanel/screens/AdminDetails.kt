package dev.softikk.webadminpanel.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.savedstate.read
import dev.softikk.webadminpanel.Routes
import dev.softikk.webadminpanel.TextButtonSiteDetailsDeleteSite
import dev.softikk.webadminpanel.TextButtonSiteDetailsSave
import dev.softikk.webadminpanel.TextFieldAdminNameAdminDetails
import dev.softikk.webadminpanel.TextFieldEmailAdminDetails
import dev.softikk.webadminpanel.TextFieldPasswordAdminDetails
import dev.softikk.webadminpanel.components.WebButton
import dev.softikk.webadminpanel.components.WebTextField
import dev.softikk.webadminpanel.viewmodels.AdminsViewModel
import dev.softikk.webadminpanel.viewmodels.SitesViewModel
import dev.softikk.webkit.theme.DimensTheme
import org.jetbrains.compose.resources.vectorResource
import webadminpanel.shared.generated.resources.Res
import webadminpanel.shared.generated.resources.plus
import webadminpanel.shared.generated.resources.x
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private val MaxWidthAdminDetails = 400.dp
private val HeightSaveButton = 50.dp
private val SiteWidgetShadowRadius = 6.dp
private val SitesHeight = 90.dp
private val IconDeleteSiteSize = 24.dp
private val HeightElements = 50.dp

@OptIn(ExperimentalUuidApi::class)
@Composable
fun AdminDetails(
    adminsViewModel: AdminsViewModel,
    sitesViewModel: SitesViewModel,
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry?
) {
    val adminId = navBackStackEntry?.arguments?.read { getStringOrNull("adminId") }?.let { id ->
        if (id == "null") null else id
    }

    val stateAdmin by adminsViewModel.stateAdmin.collectAsState()

    val adminName = rememberTextFieldState()
    val email = rememberTextFieldState()
    val password = rememberTextFieldState()

    LaunchedEffect(adminId) {
        adminsViewModel.initStateAdmin(adminId)
    }

    LaunchedEffect(stateAdmin) {
        adminName.edit { replace(0, length, stateAdmin.name) }
        email.edit { replace(0, length, stateAdmin.email) }
        password.edit { replace(0, length, stateAdmin.password) }
    }

    LaunchedEffect(adminName.text, email.text, password.text) {
        adminsViewModel.setStateAdmin(
            stateAdmin.copy(
                name = adminName.text.toString(),
                email = email.text.toString(),
                password = password.text.toString()
            )
        )
    }

    var isSelectedSites by remember { mutableStateOf(false) }

    if (isSelectedSites) {
        SelectSites(
            adminsViewModel = adminsViewModel, sitesViewModel = sitesViewModel, onClose = {
                isSelectedSites = false
            })
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.largePadding)
        ) {
            Row(
                verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                        .padding(top = DimensTheme.paddings.mediumPadding),
                    verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.smallPadding)
                ) {
                    Text(
                        text = stateAdmin.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = stateAdmin.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(
                    modifier = Modifier.pointerHoverIcon(PointerIcon.Hand), onClick = {
                        adminsViewModel.clearStateAdmin()
                        navController.popBackStack()
                    }) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.x),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            LazyRow(
                modifier = Modifier.height(SitesHeight),
                horizontalArrangement = Arrangement.spacedBy(DimensTheme.paddings.mediumPadding)
            ) {
                items(stateAdmin.sites) { site ->
                    Box(
                        modifier = Modifier.weight(1f).dropShadow(
                            shape = DimensTheme.shapes.mediumShape, shadow = Shadow(
                                radius = SiteWidgetShadowRadius,
                                offset = DpOffset(0.dp, 1.dp),
                                color = MaterialTheme.colorScheme.onSurface.copy(0.1f)
                            )
                        ).clip(DimensTheme.shapes.mediumShape).border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            shape = DimensTheme.shapes.mediumShape
                        ).pointerHoverIcon(PointerIcon.Hand).clickable {
                            navController.navigate(
                                Routes.Main.Sites.Details(
                                    siteId = site.id
                                ).route
                            )
                        }.background(MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(DimensTheme.paddings.mediumPadding)
                        ) {
                            Column(
                                modifier = Modifier.padding(DimensTheme.paddings.mediumPadding),
                                verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.smallPadding)
                            ) {
                                Text(
                                    text = site.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = site.host,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            IconButton({
                                adminsViewModel.setStateAdmin(
                                    stateAdmin.copy(
                                        sites = stateAdmin.sites - site
                                    )
                                )
                            }) {
                                Icon(
                                    modifier = Modifier.size(IconDeleteSiteSize),
                                    imageVector = vectorResource(Res.drawable.x),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
                item {
                    Box(
                        modifier = Modifier.aspectRatio(1f).fillMaxSize().dropShadow(
                            shape = DimensTheme.shapes.mediumShape, shadow = Shadow(
                                radius = SiteWidgetShadowRadius,
                                offset = DpOffset(0.dp, 1.dp),
                                color = MaterialTheme.colorScheme.onSurface.copy(0.1f)
                            )
                        ).clip(DimensTheme.shapes.mediumShape).border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            shape = DimensTheme.shapes.mediumShape
                        ).pointerHoverIcon(PointerIcon.Hand).clickable {
                            isSelectedSites = true
                        }.background(MaterialTheme.colorScheme.surface),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = vectorResource(Res.drawable.plus),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.widthIn(max = MaxWidthAdminDetails),
                verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.largePadding)
            ) {
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.mediumPadding)
                    ) {
                        WebTextField(
                            modifier = Modifier.fillMaxWidth(),
                            state = adminName,
                            labelText = TextFieldAdminNameAdminDetails
                        )
                        WebTextField(
                            modifier = Modifier.fillMaxWidth(),
                            state = email,
                            labelText = TextFieldEmailAdminDetails
                        )
                        WebTextField(
                            modifier = Modifier.fillMaxWidth(),
                            state = password,
                            labelText = TextFieldPasswordAdminDetails,
                            keyboardType = KeyboardType.Password
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.widthIn(max = MaxWidthAdminDetails)
                    .padding(bottom = DimensTheme.paddings.mediumPadding),
                verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.smallPadding)
            ) {
                WebButton(
                    modifier = Modifier.height(HeightSaveButton).fillMaxWidth(), onClick = {
                        adminsViewModel.saveAdmin(adminId)
                        adminsViewModel.clearStateAdmin()
                        navController.popBackStack()
                    }) {
                    Text(
                        text = TextButtonSiteDetailsSave,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
                adminId?.let {
                    WebButton(
                        modifier = Modifier.height(HeightElements).fillMaxWidth(),
                        containerColor = MaterialTheme.colorScheme.error,
                        onClick = {
                            adminsViewModel.deleteAdmin(Uuid.parse(adminId))
                            adminsViewModel.clearStateAdmin()
                            navController.popBackStack()
                        }) {
                        Text(
                            text = TextButtonSiteDetailsDeleteSite,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            }
        }
    }
}