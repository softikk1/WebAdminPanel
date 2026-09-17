package dev.softikk.webadminpanel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.softikk.webadminpanel.components.WebToolbox
import dev.softikk.webadminpanel.models.UIElementModel
import dev.softikk.webadminpanel.screens.AdminDetails
import dev.softikk.webadminpanel.screens.Admins
import dev.softikk.webadminpanel.screens.Auth
import dev.softikk.webadminpanel.screens.SiteDetails
import dev.softikk.webadminpanel.screens.Sites
import dev.softikk.webkit.Website
import dev.softikk.webkit.navigation.Route
import dev.softikk.webkit.navigation.WebNavigation
import dev.softikk.webkit.theme.Dimens
import dev.softikk.webkit.theme.DimensTheme
import dev.softikk.webkit.theme.Shapes
import dev.softikk.webkit.theme.WebTheme
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.Font
import webadminpanel.shared.generated.resources.Res
import webadminpanel.shared.generated.resources.inter
import webadminpanel.shared.generated.resources.inter_italic
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class Site(
    val id: Uuid,
    val name: String,
    val host: String,
    val description: String,
    val createAt: LocalDateTime,
    val elements: List<UIElementModel> = emptyList()
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
                            Auth(navController)
                        }), Route(
                        urlPath = Routes.Main.Admins.route, content = {
                            Admins(navController)
                        }), Route(
                        urlPath = Routes.Main.Sites.route, content = {
                            Sites(navController)
                        }), Route(
                        urlPath = Routes.Main.Admins.Details.pattern, content = {
                            AdminDetails(
                                navController = navController, navBackStackEntry = navBackStackEntry
                            )
                        }), Route(
                        urlPath = Routes.Main.Sites.Details.pattern, content = {
                            SiteDetails(
                                navController = navController, navBackStackEntry = navBackStackEntry
                            )
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