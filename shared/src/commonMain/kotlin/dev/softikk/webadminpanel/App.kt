package dev.softikk.webadminpanel

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.savedstate.read
import dev.softikk.webadminpanel.components.AdminWidget
import dev.softikk.webadminpanel.components.SiteWidget
import dev.softikk.webadminpanel.components.WebButton
import dev.softikk.webadminpanel.components.WebTextField
import dev.softikk.webadminpanel.components.WebToolbox
import dev.softikk.webadminpanel.components.formatDateTimePlusZero
import dev.softikk.webkit.Website
import dev.softikk.webkit.components.buttons.Button
import dev.softikk.webkit.navigation.Route
import dev.softikk.webkit.navigation.WebNavigation
import dev.softikk.webkit.theme.Dimens
import dev.softikk.webkit.theme.DimensTheme
import dev.softikk.webkit.theme.Shapes
import dev.softikk.webkit.theme.WebTheme
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.vectorResource
import webadminpanel.shared.generated.resources.Res
import webadminpanel.shared.generated.resources.inter
import webadminpanel.shared.generated.resources.inter_italic
import webadminpanel.shared.generated.resources.plus
import webadminpanel.shared.generated.resources.x
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class Site(
    val id: Uuid,
    val name: String,
    val host: String,
    val description: String,
    val createAt: LocalDateTime
)

@Serializable
data class Admin(
    val id: Uuid, val name: String, val email: String, val description: String
)

@OptIn(ExperimentalUuidApi::class)
val admins = listOf(
    Admin(
        id = Uuid.generateV4(), name = "Softikk", email = "softikk31@gmail.com", description = "GAY"
    ), Admin(
        id = Uuid.generateV4(), name = "Softikk", email = "softikk31@gmail.com", description = "GAY"
    ), Admin(
        id = Uuid.generateV4(), name = "Softikk", email = "softikk31@gmail.com", description = "GAY"
    ), Admin(
        id = Uuid.generateV4(), name = "Softikk", email = "softikk31@gmail.com", description = "GAY"
    ), Admin(
        id = Uuid.generateV4(), name = "Softikk", email = "softikk31@gmail.com", description = "GAY"
    ), Admin(
        id = Uuid.generateV4(), name = "Softikk", email = "softikk31@gmail.com", description = "GAY"
    ), Admin(
        id = Uuid.generateV4(), name = "Softikk", email = "softikk31@gmail.com", description = "GAY"
    ), Admin(
        id = Uuid.generateV4(), name = "Softikk", email = "softikk31@gmail.com", description = "GAY"
    ), Admin(
        id = Uuid.generateV4(), name = "Softikk", email = "softikk31@gmail.com", description = "GAY"
    ), Admin(
        id = Uuid.generateV4(), name = "Softikk", email = "softikk31@gmail.com", description = "GAY"
    )
)

@OptIn(ExperimentalUuidApi::class)
val sites = listOf(
    Site(
        id = Uuid.generateV4(),
        name = "My site",
        host = "https://site.ru",
        description = "Это какой то сайт крч",
        createAt = Clock.System.now().plus(DateTimePeriod(days = 2), TimeZone.UTC).toLocalDateTime(
            TimeZone.currentSystemDefault()
        )
    ), Site(
        id = Uuid.generateV4(),
        name = "My site",
        host = "https://site.ru",
        description = "Это какой то сайт крч",
        createAt = Clock.System.now().plus(DateTimePeriod(days = 2), TimeZone.UTC).toLocalDateTime(
            TimeZone.currentSystemDefault()
        )
    ), Site(
        id = Uuid.generateV4(),
        name = "My site",
        host = "https://site.ru",
        description = "Это какой то сайт крч",
        createAt = Clock.System.now().plus(DateTimePeriod(days = 2), TimeZone.UTC).toLocalDateTime(
            TimeZone.currentSystemDefault()
        )
    ), Site(
        id = Uuid.generateV4(),
        name = "My site",
        host = "https://site.ru",
        description = "Это какой то сайт крч",
        createAt = Clock.System.now().plus(DateTimePeriod(days = 2), TimeZone.UTC).toLocalDateTime(
            TimeZone.currentSystemDefault()
        )
    ), Site(
        id = Uuid.generateV4(),
        name = "My site",
        host = "https://site.ru",
        description = "Это какой то сайт крч",
        createAt = Clock.System.now().plus(DateTimePeriod(days = 2), TimeZone.UTC).toLocalDateTime(
            TimeZone.currentSystemDefault()
        )
    ), Site(
        id = Uuid.generateV4(),
        name = "My site",
        host = "https://site.ru",
        description = "Это какой то сайт крч",
        createAt = Clock.System.now().plus(DateTimePeriod(days = 2), TimeZone.UTC).toLocalDateTime(
            TimeZone.currentSystemDefault()
        )
    ), Site(
        id = Uuid.generateV4(),
        name = "My site",
        host = "https://site.ru",
        description = "Это какой то сайт крч",
        createAt = Clock.System.now().plus(DateTimePeriod(days = 2), TimeZone.UTC).toLocalDateTime(
            TimeZone.currentSystemDefault()
        )
    ), Site(
        id = Uuid.generateV4(),
        name = "My site",
        host = "https://site.ru",
        description = "Это какой то сайт крч",
        createAt = Clock.System.now().plus(DateTimePeriod(days = 2), TimeZone.UTC).toLocalDateTime(
            TimeZone.currentSystemDefault()
        )
    ), Site(
        id = Uuid.generateV4(),
        name = "My site",
        host = "https://site.ru",
        description = "Это какой то сайт крч",
        createAt = Clock.System.now().plus(DateTimePeriod(days = 2), TimeZone.UTC).toLocalDateTime(
            TimeZone.currentSystemDefault()
        )
    )
)

// Auth
private val MaxWidthBlankAuth = 400.dp

// Sites and Admins
private val MinSizeColumnsSitesAndAdmins = 150.dp

// SiteDetails
private val MaxWidthTextFieldSitesDetails = 400.dp

@OptIn(ExperimentalUuidApi::class)
@Composable
fun App(onNavHostReady: (suspend (NavController) -> Unit)) {
//    val siteHost = "localhost:8081"
//    val client = HttpClient {
//        defaultRequest {
//            host = "localhost"
//            port = 8080
//        }
//        install(ContentNegotiation) {
//            json()
//        }
//    }
//
//    var schemas by remember { mutableStateOf<MutableMap<String, String>?>(null) }
//
//    val coroutine = rememberCoroutineScope()
//
//    LaunchedEffect(Unit) {
//        val receiveSchema = getSchema(
//            client = client, host = siteHost
//        )
//        receiveSchema?.let {
//            schemas = receiveSchema.jsonObject.map { (key, value) ->
//                key to value.jsonPrimitive.content
//            }.toMutableStateMap()
//        }
//
//    }

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val interFamily = FontFamily(
        Font(Res.font.inter, style = FontStyle.Italic),
        Font(Res.font.inter_italic, style = FontStyle.Italic)
    )

    Box(modifier = Modifier.padding(horizontal = DimensTheme.paddings.mediumPadding)) {
        Website(
            header = {
                currentRoute?.let {
                    if ((Routes.Main.route in currentRoute) and (currentRoute.split('/').size == Routes.Main.Sites.route.split(
                            '/'
                        ).size) and (currentRoute.split('/').size == Routes.Main.Admins.route.split(
                            '/'
                        ).size)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                        ) {
                            WebToolbox(
                                modifier = Modifier.padding(top = DimensTheme.paddings.smallPadding),
                                navController = navController
                            )
                        }
                    }
                }
            }, theme = WebTheme(
                colorScheme = lightColorScheme(
                    background = Color(0xFFFFFFFF),
                    primary = Color(0xFF9340FF),
                    surface = Color(0xFFFFFFFF),
                    onSurface = Color(0xFF000000),
                    onSurfaceVariant = Color(0xFF8B8B8B),
                    surfaceContainer = Color(0xFFEBEBEB)
                ), dimens = Dimens(
                    shapes = Shapes(
                        mediumShape = RoundedCornerShape(10.dp),
                        largeShape = RoundedCornerShape(20.dp)
                    )
                ), typography = Typography(interFamily)
            )
        ) {
            WebNavigation(
                navController = navController,
                onNavHostReady = onNavHostReady,
                startDestination = Routes.Auth.route,
                routes = listOf(
                    Route(
                        urlPath = Routes.Auth.route, content = {
                            val stateTextFieldEmail = rememberTextFieldState()
                            val stateTextFieldPassword = rememberTextFieldState()
                            var checked by remember { mutableStateOf(false) }

                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    modifier = Modifier.widthIn(max = MaxWidthBlankAuth),
                                    verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.mediumPadding),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.smallPadding)
                                    ) {
                                        Text(
                                            text = TitleAuth,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = DescriptionAuth,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.smallPadding)
                                    ) {
                                        WebTextField(
                                            modifier = Modifier.fillMaxWidth(),
                                            state = stateTextFieldEmail,
                                            labelText = TextFieldPlaceEmailAuth
                                        )
                                        WebTextField(
                                            modifier = Modifier.fillMaxWidth(),
                                            state = stateTextFieldPassword,
                                            labelText = TextFieldPlacePasswordAuth,
                                            keyboardType = KeyboardType.Password
                                        )
                                    }
                                    Button(
                                        modifier = Modifier.fillMaxWidth(), onClick = {
                                            navController.navigate(Routes.Main.Sites.route)
                                        }, containerColor = MaterialTheme.colorScheme.primary
                                    ) {
                                        Text(
                                            text = ButtonContinueText,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.surface
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(DimensTheme.paddings.mediumPadding),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            modifier = Modifier.size(18.dp),
                                            checked = checked,
                                            onCheckedChange = { checked = it },
                                            colors = CheckboxDefaults.colors(
                                                checkedColor = MaterialTheme.colorScheme.primary,
                                                checkmarkColor = MaterialTheme.colorScheme.surface,
                                                disabledCheckedColor = MaterialTheme.colorScheme.primary,
                                                disabledIndeterminateColor = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        )
                                        Text(
                                            text = buildAnnotatedString {
                                                withStyle(
                                                    style = SpanStyle(
                                                        color = MaterialTheme.colorScheme.onSurface
                                                    )
                                                ) {
                                                    append(PrivacyPolicyText1)
                                                }
                                                withStyle(
                                                    style = SpanStyle(
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                ) {
                                                    append(PrivacyPolicyText2)
                                                }
                                                withStyle(
                                                    style = SpanStyle(
                                                        color = MaterialTheme.colorScheme.onSurface
                                                    )
                                                ) {
                                                    append(PrivacyPolicyText3)
                                                }
                                            }, style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }), Route(
                        urlPath = Routes.Main.Admins.route, content = {
                            LazyVerticalGrid(
                                modifier = Modifier.fillMaxSize(),
                                columns = GridCells.Adaptive(MinSizeColumnsSitesAndAdmins),
                                contentPadding = PaddingValues(top = DimensTheme.paddings.mediumPadding),
                                verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.mediumPadding),
                                horizontalArrangement = Arrangement.spacedBy(DimensTheme.paddings.mediumPadding),
                                content = {
                                    items(admins) { admin ->
                                        key(admin.id) {
                                            AdminWidget(
                                                admin = admin, navController = navController
                                            )
                                        }
                                    }
                                })
                        }), Route(
                        urlPath = Routes.Main.Sites.route, content = {
                            LazyVerticalGrid(
                                modifier = Modifier.fillMaxSize(),
                                columns = GridCells.Adaptive(MinSizeColumnsSitesAndAdmins),
                                contentPadding = PaddingValues(top = DimensTheme.paddings.mediumPadding),
                                verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.mediumPadding),
                                horizontalArrangement = Arrangement.spacedBy(DimensTheme.paddings.mediumPadding),
                                content = {
                                    items(sites) { site ->
                                        key(site.id) {
                                            SiteWidget(
                                                site = site, navController = navController
                                            )
                                        }
                                    }
                                })
                        }), Route(
                        urlPath = Routes.Main.Admins.Id.pattern, content = {
                            val adminId =
                                navBackStackEntry?.arguments?.read { getStringOrNull("adminId") }

                            adminId?.let {
                                val admin =
                                    admins.single { admin -> admin.id == Uuid.parse(adminId) }
                                Row(
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(
                                        modifier = Modifier.weight(1f)
                                            .padding(top = DimensTheme.paddings.mediumPadding),
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
                                            text = admin.description,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    IconButton(
                                        modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                                        onClick = {
                                            navController.popBackStack()
                                        }) {
                                        Icon(
                                            imageVector = vectorResource(Res.drawable.x),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }), Route(
                        urlPath = Routes.Main.Sites.Id.pattern, content = {
                            val siteId =
                                navBackStackEntry?.arguments?.read { getStringOrNull("siteId") }

                            siteId?.let {
                                val site = sites.single { site -> site.id == Uuid.parse(siteId) }
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.largePadding)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.Top,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(1f)
                                                .padding(top = DimensTheme.paddings.mediumPadding),
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
                                            val date = site.createAt.date
                                            val time = site.createAt.time
                                            Text(
                                                text = "${time.hour.formatDateTimePlusZero()}:${time.minute.formatDateTimePlusZero()} ${date.day.formatDateTimePlusZero()}.${date.month.number.formatDateTimePlusZero()}.${date.year.formatDateTimePlusZero()}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                        IconButton(
                                            modifier = Modifier.pointerHoverIcon(PointerIcon.Hand),
                                            onClick = {
                                                navController.popBackStack()
                                            }) {
                                            Icon(
                                                imageVector = vectorResource(Res.drawable.x),
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
                                    Column(
                                        modifier = Modifier.widthIn(max = MaxWidthTextFieldSitesDetails),
                                        verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.mediumPadding)
                                    ) {
                                        val siteName = rememberTextFieldState()
                                        WebTextField(
                                            modifier = Modifier.fillMaxWidth(),
                                            state = siteName,
                                            labelText = TextFieldSiteNameSiteDetails
                                        )
                                        val hostName = rememberTextFieldState()
                                        WebTextField(
                                            modifier = Modifier.fillMaxWidth(),
                                            state = hostName,
                                            labelText = TextFieldHostNameSiteDetails
                                        )
                                        val descriptionSite = rememberTextFieldState()
                                        WebTextField(
                                            modifier = Modifier.fillMaxWidth(),
                                            state = descriptionSite,
                                            labelText = TextFieldSiteDescriptionSiteDetails
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.widthIn(max = MaxWidthTextFieldSitesDetails)
                                            .padding(bottom = DimensTheme.paddings.mediumPadding),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(DimensTheme.paddings.smallPadding)
                                    ) {
                                        WebButton(
                                            modifier = Modifier.height(50.dp).weight(1f),
                                            onClick = {}) {
                                            Text(
                                                text = TextButtonSiteDetails,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.surface
                                            )
                                        }
                                        WebButton(
                                            modifier = Modifier.size(50.dp).dropShadow(
                                                shape = DimensTheme.shapes.mediumShape,
                                                shadow = Shadow(
                                                    radius = 8.dp,
                                                    offset = DpOffset(0.dp, 1.dp),
                                                    color = MaterialTheme.colorScheme.onSurface.copy(
                                                        0.1f
                                                    )
                                                )
                                            ).clip(DimensTheme.shapes.mediumShape)
                                                .pointerHoverIcon(PointerIcon.Hand).border(
                                                    width = 1.dp,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                                        0.1f
                                                    ),
                                                    shape = DimensTheme.shapes.mediumShape
                                                ),
                                            containerColor = MaterialTheme.colorScheme.surface,
                                            onClick = {}) {
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
                        })
                )
            )
        }
    }
}


//Column {
//    schemas?.let {
//        LazyColumn {
//            items(
//                it.toList().sortedBy { value -> value.first },
//                key = { value -> value.first }) { value ->
//                val stateKey = rememberTextFieldState(initialText = value.first)
//                val stateValue = rememberTextFieldState(initialText = value.second)
//                var oldStateKey by remember { mutableStateOf(stateKey.text) }
//                LaunchedEffect(stateKey.text, stateValue.text) {
//                    if (stateKey.text !in it.keys) {
//                        it.remove(oldStateKey)
//                    }
//                    it[stateKey.text.toString()] = stateValue.text.toString()
//                    oldStateKey = stateKey.text
//                }
//
//                Row {
//                    TextField(
//                        state = stateKey, label = {
//                            Text(
//                                text = "Key"
//                            )
//                        })
//                    TextField(
//                        state = stateValue, label = {
//                            Text(
//                                text = "Value"
//                            )
//                        })
//                }
//            }
//        }
//    }
//    Row {
//        Button({
//            coroutine.launch {
//                if (getSchema(client = client, host = siteHost) != null) {
//                    updateSchema(
//                        client = client,
//                        host = siteHost,
//                        model = Json.encodeToJsonElement(schemas)
//                    )
//                } else {
//                    schemas = addSchema(
//                        client = client,
//                        host = siteHost,
//                        model = Json.encodeToJsonElement(schemas)
//                    ).jsonObject.map { (key, value) ->
//                        key to value.jsonPrimitive.content
//                    }.toMutableStateMap()
//                }
//            }
//        }) {
//            Text("Save")
//        }
//        Button({
//            if (schemas != null) {
//                schemas!![(schemas!!.keys.size + 1).toString()] = ""
//            } else {
//                schemas = mutableMapOf("" to "")
//            }
//        }) {
//            Text("Plus")
//        }
//    }
//}