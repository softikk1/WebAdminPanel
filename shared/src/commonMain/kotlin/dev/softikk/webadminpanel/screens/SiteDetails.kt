package dev.softikk.webadminpanel.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.savedstate.read
import dev.softikk.webadminpanel.DefaultDescriptionNameSiteDetails
import dev.softikk.webadminpanel.DefaultHostNameSiteDetails
import dev.softikk.webadminpanel.DefaultSiteNameSiteDetails
import dev.softikk.webadminpanel.Site
import dev.softikk.webadminpanel.TestDatabase
import dev.softikk.webadminpanel.TextButtonSiteDetails
import dev.softikk.webadminpanel.TextFieldHostNameSiteDetails
import dev.softikk.webadminpanel.TextFieldSiteDescriptionSiteDetails
import dev.softikk.webadminpanel.TextFieldSiteNameSiteDetails
import dev.softikk.webadminpanel.components.WebButton
import dev.softikk.webadminpanel.components.WebTextField
import dev.softikk.webadminpanel.components.WebTextFieldKeyValue
import dev.softikk.webadminpanel.components.formatDateTimePlusZero
import dev.softikk.webadminpanel.models.UISiteElementModel
import dev.softikk.webadminpanel.viewmodels.SitesViewModel
import dev.softikk.webkit.theme.DimensTheme
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.vectorResource
import webadminpanel.shared.generated.resources.Res
import webadminpanel.shared.generated.resources.plus
import webadminpanel.shared.generated.resources.x
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private val MaxWidthTextFieldSiteDetails = 400.dp

@OptIn(ExperimentalUuidApi::class)
@Composable
fun SiteDetails(
    navController: NavHostController, navBackStackEntry: NavBackStackEntry?
) {
    val sitesViewModel = viewModel { SitesViewModel(TestDatabase) }

    val siteId = navBackStackEntry?.arguments?.read { getStringOrNull("siteId") }?.let { id ->
        if (id == "null") null else id
    }

    val siteName = rememberTextFieldState(initialText = DefaultSiteNameSiteDetails)
    val hostName = rememberTextFieldState(initialText = DefaultHostNameSiteDetails)
    val descriptionSite = rememberTextFieldState(initialText = DefaultDescriptionNameSiteDetails)

    val sites by TestDatabase.sites.collectAsState()

    val elements = remember { mutableStateListOf<UISiteElementModel>() }

    val findSuchSite by remember {
        mutableStateOf(siteId?.let {
            sites.singleOrNull { site -> site.id == Uuid.parse(siteId) }
        })
    }

    var site by remember(siteName.text, hostName.text, descriptionSite.text, elements.toList()) {
        var initSite = Site(
            id = Uuid.generateV4(),
            name = siteName.text.toString(),
            host = hostName.text.toString(),
            description = descriptionSite.text.toString(),
            createAt = Clock.System.now().toLocalDateTime(TimeZone.UTC),
            elements = elements
        )

        findSuchSite?.let {
            initSite = initSite.copy(id = it.id, createAt = it.createAt)
        }

        mutableStateOf(
            initSite
        )
    }

    LaunchedEffect(Unit) {
        findSuchSite?.let {
            site = site.copy(
                id = it.id,
                name = it.name,
                host = it.host,
                description = it.description,
                createAt = it.createAt,
                elements = it.elements
            )
        }

        siteName.edit { replace(0, siteName.text.length, site.name) }
        hostName.edit { replace(0, hostName.text.length, site.host) }
        descriptionSite.edit {
            replace(
                0, descriptionSite.text.length, site.description
            )
        }
        site.elements.forEach { element ->
            elements.add(element)
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.largePadding)
    ) {
        Row(
            verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f).padding(top = DimensTheme.paddings.mediumPadding),
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
                Text(
                    text = site.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                val createAt = site.createAt.toInstant(TimeZone.UTC)
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                val date = createAt.date
                val time = createAt.time
                Text(
                    text = "${time.hour.formatDateTimePlusZero()}:${time.minute.formatDateTimePlusZero()} ${date.day.formatDateTimePlusZero()}.${date.month.number.formatDateTimePlusZero()}.${date.year.formatDateTimePlusZero()}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            IconButton(
                modifier = Modifier.pointerHoverIcon(PointerIcon.Hand), onClick = {
                    navController.popBackStack()
                }) {
                Icon(
                    imageVector = vectorResource(Res.drawable.x),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        LazyColumn(
            modifier = Modifier.widthIn(max = MaxWidthTextFieldSiteDetails),
            verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.largePadding)
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.mediumPadding)
                ) {
                    WebTextField(
                        modifier = Modifier.fillMaxWidth(),
                        state = siteName,
                        labelText = TextFieldSiteNameSiteDetails
                    )
                    WebTextField(
                        modifier = Modifier.fillMaxWidth(),
                        state = hostName,
                        labelText = TextFieldHostNameSiteDetails
                    )
                    WebTextField(
                        modifier = Modifier.fillMaxWidth(),
                        state = descriptionSite,
                        labelText = TextFieldSiteDescriptionSiteDetails
                    )
                }
            }
            items(elements) { element ->
                val key = element.key
                val value = element.value
                key(element.id) {
                    val keyTextFieldState = rememberTextFieldState(
                        initialText = key
                    )
                    val valueTextFieldState = rememberTextFieldState(
                        initialText = value
                    )
                    LaunchedEffect(keyTextFieldState.text, valueTextFieldState.text) {
                        elements[elements.indexOf(element)] = UISiteElementModel(
                            id = element.id,
                            key = keyTextFieldState.text.toString(),
                            value = valueTextFieldState.text.toString()
                        )
                    }

                    WebTextFieldKeyValue(
                        modifier = Modifier.fillMaxWidth(),
                        keyTextFieldState = keyTextFieldState,
                        valueTextFieldState = valueTextFieldState
                    )
                }
            }
        }
        Row(
            modifier = Modifier.widthIn(max = MaxWidthTextFieldSiteDetails)
                .padding(bottom = DimensTheme.paddings.mediumPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(DimensTheme.paddings.smallPadding)
        ) {
            WebButton(
                modifier = Modifier.height(50.dp).weight(1f), onClick = {
                    sitesViewModel.saveSite(site)
                    navController.popBackStack()
                }) {
                Text(
                    text = TextButtonSiteDetails,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.surface
                )
            }
            WebButton(
                modifier = Modifier.size(50.dp).dropShadow(
                    shape = DimensTheme.shapes.mediumShape, shadow = Shadow(
                        radius = 8.dp,
                        offset = DpOffset(0.dp, 1.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            0.1f
                        )
                    )
                ).clip(DimensTheme.shapes.mediumShape).pointerHoverIcon(PointerIcon.Hand).border(
                    width = 1.dp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        0.1f
                    ), shape = DimensTheme.shapes.mediumShape
                ), containerColor = MaterialTheme.colorScheme.surface, onClick = {
                    elements.add(
                        UISiteElementModel(
                            id = Uuid.generateV4(), key = "", value = ""
                        )
                    )
                }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = vectorResource(Res.drawable.plus),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}