package dev.softikk.webadminpanel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.savedstate.read
import dev.softikk.webadminpanel.components.AdminWidget
import dev.softikk.webadminpanel.components.SiteWidget
import dev.softikk.webadminpanel.components.WebTextField
import dev.softikk.webadminpanel.components.WebToolbox
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
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.Font
import webadminpanel.shared.generated.resources.Res
import webadminpanel.shared.generated.resources.inter
import webadminpanel.shared.generated.resources.inter_italic
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

    val admins = listOf(
        Admin(
            id = Uuid.generateV4(),
            name = "Softikk",
            email = "softikk31@gmail.com",
            description = "GAY"
        ), Admin(
            id = Uuid.generateV4(),
            name = "Softikk",
            email = "softikk31@gmail.com",
            description = "GAY"
        ), Admin(
            id = Uuid.generateV4(),
            name = "Softikk",
            email = "softikk31@gmail.com",
            description = "GAY"
        ), Admin(
            id = Uuid.generateV4(),
            name = "Softikk",
            email = "softikk31@gmail.com",
            description = "GAY"
        ), Admin(
            id = Uuid.generateV4(),
            name = "Softikk",
            email = "softikk31@gmail.com",
            description = "GAY"
        ), Admin(
            id = Uuid.generateV4(),
            name = "Softikk",
            email = "softikk31@gmail.com",
            description = "GAY"
        ), Admin(
            id = Uuid.generateV4(),
            name = "Softikk",
            email = "softikk31@gmail.com",
            description = "GAY"
        ), Admin(
            id = Uuid.generateV4(),
            name = "Softikk",
            email = "softikk31@gmail.com",
            description = "GAY"
        ), Admin(
            id = Uuid.generateV4(),
            name = "Softikk",
            email = "softikk31@gmail.com",
            description = "GAY"
        ), Admin(
            id = Uuid.generateV4(),
            name = "Softikk",
            email = "softikk31@gmail.com",
            description = "GAY"
        )
    )

    val sites = listOf(
        Site(
            id = Uuid.generateV4(),
            name = "My site",
            host = "https://site.ru",
            description = "Это какой то сайт крч",
            createAt = Clock.System.now().plus(DateTimePeriod(days = 2), TimeZone.UTC)
                .toLocalDateTime(
                    TimeZone.currentSystemDefault()
                )
        ), Site(
            id = Uuid.generateV4(),
            name = "My site",
            host = "https://site.ru",
            description = "Это какой то сайт крч",
            createAt = Clock.System.now().plus(DateTimePeriod(days = 2), TimeZone.UTC)
                .toLocalDateTime(
                    TimeZone.currentSystemDefault()
                )
        ), Site(
            id = Uuid.generateV4(),
            name = "My site",
            host = "https://site.ru",
            description = "Это какой то сайт крч",
            createAt = Clock.System.now().plus(DateTimePeriod(days = 2), TimeZone.UTC)
                .toLocalDateTime(
                    TimeZone.currentSystemDefault()
                )
        ), Site(
            id = Uuid.generateV4(),
            name = "My site",
            host = "https://site.ru",
            description = "Это какой то сайт крч",
            createAt = Clock.System.now().plus(DateTimePeriod(days = 2), TimeZone.UTC)
                .toLocalDateTime(
                    TimeZone.currentSystemDefault()
                )
        ), Site(
            id = Uuid.generateV4(),
            name = "My site",
            host = "https://site.ru",
            description = "Это какой то сайт крч",
            createAt = Clock.System.now().plus(DateTimePeriod(days = 2), TimeZone.UTC)
                .toLocalDateTime(
                    TimeZone.currentSystemDefault()
                )
        ), Site(
            id = Uuid.generateV4(),
            name = "My site",
            host = "https://site.ru",
            description = "Это какой то сайт крч",
            createAt = Clock.System.now().plus(DateTimePeriod(days = 2), TimeZone.UTC)
                .toLocalDateTime(
                    TimeZone.currentSystemDefault()
                )
        ), Site(
            id = Uuid.generateV4(),
            name = "My site",
            host = "https://site.ru",
            description = "Это какой то сайт крч",
            createAt = Clock.System.now().plus(DateTimePeriod(days = 2), TimeZone.UTC)
                .toLocalDateTime(
                    TimeZone.currentSystemDefault()
                )
        ), Site(
            id = Uuid.generateV4(),
            name = "My site",
            host = "https://site.ru",
            description = "Это какой то сайт крч",
            createAt = Clock.System.now().plus(DateTimePeriod(days = 2), TimeZone.UTC)
                .toLocalDateTime(
                    TimeZone.currentSystemDefault()
                )
        ), Site(
            id = Uuid.generateV4(),
            name = "My site",
            host = "https://site.ru",
            description = "Это какой то сайт крч",
            createAt = Clock.System.now().plus(DateTimePeriod(days = 2), TimeZone.UTC)
                .toLocalDateTime(
                    TimeZone.currentSystemDefault()
                )
        )
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
                                    modifier = Modifier.widthIn(max = 400.dp),
                                    verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.mediumPadding),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.smallPadding)
                                    ) {
                                        Text(
                                            text = "Добро пожаловать!",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "Админ панель вашего сайта.",
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
                                            labelText = "Email"
                                        )
                                        WebTextField(
                                            modifier = Modifier.fillMaxWidth(),
                                            state = stateTextFieldPassword,
                                            labelText = "Password",
                                            keyboardType = KeyboardType.Password
                                        )
                                    }
                                    Button(
                                        modifier = Modifier.fillMaxWidth(), onClick = {
                                            navController.navigate(Routes.Main.Sites.route)
                                        }, containerColor = MaterialTheme.colorScheme.primary
                                    ) {
                                        Text(
                                            text = "Далее",
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
                                                    append("Соглашение с ")
                                                }
                                                withStyle(
                                                    style = SpanStyle(
                                                        color = MaterialTheme.colorScheme.primary
                                                    )
                                                ) {
                                                    append("Политикой конфиденциальности")
                                                }
                                                withStyle(
                                                    style = SpanStyle(
                                                        color = MaterialTheme.colorScheme.onSurface
                                                    )
                                                ) {
                                                    append(".")
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
                                columns = GridCells.Adaptive(150.dp),
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
                                columns = GridCells.Adaptive(150.dp),
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
                            var error by remember { mutableStateOf(false) }
                            val adminId =
                                navBackStackEntry?.arguments?.read { getStringOrNull("adminId") }

                            LaunchedEffect(Unit) {
                                if (adminId == null) {
                                    error = true
                                }
                            }

                            if (error) {
                                Text(
                                    text = "Error",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            } else {
                                Text(
                                    text = adminId ?: "",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                        }), Route(
                        urlPath = Routes.Main.Sites.Id.pattern, content = {
                            var error by remember { mutableStateOf(false) }
                            val siteId =
                                navBackStackEntry?.arguments?.read { getStringOrNull("siteId") }

                            LaunchedEffect(Unit) {
                                if (siteId == null) {
                                    error = true
                                }
                            }

                            if (error) {
                                Text(
                                    text = "Error",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            } else {
                                Text(
                                    text = siteId ?: "",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
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