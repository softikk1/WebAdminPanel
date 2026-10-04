package dev.softikk.webadminpanel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.softikk.webadminpanel.api.AdminsApi
import dev.softikk.webadminpanel.api.AuthApi
import dev.softikk.webadminpanel.api.SitesApi
import dev.softikk.webadminpanel.components.WebToolbox
import dev.softikk.webadminpanel.datastore.DataStoresInit
import dev.softikk.webadminpanel.screens.AdminDetails
import dev.softikk.webadminpanel.screens.Admins
import dev.softikk.webadminpanel.screens.Auth
import dev.softikk.webadminpanel.screens.SiteDetails
import dev.softikk.webadminpanel.screens.Sites
import dev.softikk.webadminpanel.viewmodels.AdminsViewModel
import dev.softikk.webadminpanel.viewmodels.AuthViewModel
import dev.softikk.webadminpanel.viewmodels.SitesViewModel
import dev.softikk.webkit.Website
import dev.softikk.webkit.navigation.Route
import dev.softikk.webkit.navigation.WebNavigation
import dev.softikk.webkit.theme.Dimens
import dev.softikk.webkit.theme.DimensTheme
import dev.softikk.webkit.theme.Shapes
import dev.softikk.webkit.theme.WebTheme
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BasicAuthCredentials
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import org.jetbrains.compose.resources.Font
import webadminpanel.shared.generated.resources.Res
import webadminpanel.shared.generated.resources.inter
import webadminpanel.shared.generated.resources.inter_italic

@Composable
fun App(onNavHostReady: (suspend (NavController) -> Unit)) {
    val navController = rememberNavController()
    val authDataStore = remember { DataStoresInit.authDataStore }

    val client = remember {
        HttpClient {
            defaultRequest {
                host = "localhost"
                port = 8080
            }
            install(Auth) {
                basic {
                    credentials {
                        try {
                            val authDataStoreModel = authDataStore.getAuthModel()
                            if (authDataStoreModel != null) {
                                BasicAuthCredentials(
                                    username = authDataStoreModel.email,
                                    password = authDataStoreModel.password
                                )
                            } else {
                                navController.navigate(Routes.Auth.route)
                                null
                            }
                        } catch (_: Exception) {
                            null
                        }
                    }
                    realm = "CMS"
                    sendWithoutRequest { request ->
                        "/login" !in request.url.encodedPath
                    }
                }
            }
            install(ContentNegotiation) {
                json()
            }
        }
    }

    val authViewModel = viewModel {
        AuthViewModel(
            authApi = AuthApi(client), authDataStore = authDataStore
        )
    }
    val sitesViewModel = viewModel { SitesViewModel(SitesApi(client)) }
    val adminsViewModel = viewModel {
        AdminsViewModel(
            AdminsApi(
                client = client, authDataStore = authDataStore
            )
        )
    }

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
                                navController = navController,
                                sitesViewModel = sitesViewModel,
                                adminsViewModel = adminsViewModel
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
                    surfaceContainer = Color(0xFFEBEBEB),
                    error = Color.Red
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
                            Auth(navController = navController, authViewModel = authViewModel)
                        }), Route(
                        urlPath = Routes.Main.Admins.route, content = {
                            Admins(
                                navController = navController, adminsViewModel = adminsViewModel
                            )
                        }), Route(
                        urlPath = Routes.Main.Sites.route, content = {
                            Sites(
                                navController = navController, sitesViewModel = sitesViewModel
                            )
                        }), Route(
                        urlPath = Routes.Main.Admins.Details.pattern, content = {
                            AdminDetails(
                                adminsViewModel = adminsViewModel,
                                sitesViewModel = sitesViewModel,
                                navController = navController,
                                navBackStackEntry = navBackStackEntry
                            )
                        }), Route(
                        urlPath = Routes.Main.Sites.Details.pattern, content = {
                            SiteDetails(
                                sitesViewModel = sitesViewModel,
                                navController = navController,
                                navBackStackEntry = navBackStackEntry
                            )
                        })
                )
            )
        }
    }
}