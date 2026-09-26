package dev.softikk.webadminpanel.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.softikk.webadminpanel.components.AdminWidget
import dev.softikk.webadminpanel.viewmodels.AdminsViewModel
import dev.softikk.webkit.theme.DimensTheme

private val MinSizeColumnsSitesAndAdmins = 150.dp

@Composable
fun Admins(navController: NavHostController, adminsViewModel: AdminsViewModel) {
    val admins by adminsViewModel.admins.collectAsState()

    LaunchedEffect(Unit) {
        adminsViewModel.getAdmins()
    }

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
}