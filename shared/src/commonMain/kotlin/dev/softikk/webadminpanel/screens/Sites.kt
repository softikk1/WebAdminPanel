package dev.softikk.webadminpanel.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.softikk.webadminpanel.TestDatabase
import dev.softikk.webadminpanel.components.SiteSelectWidget
import dev.softikk.webadminpanel.components.SiteWidget
import dev.softikk.webadminpanel.components.WebButton
import dev.softikk.webadminpanel.viewmodels.AdminsViewModel
import dev.softikk.webkit.theme.DimensTheme

private val MinSizeColumnsSitesAndAdmins = 150.dp
private val MaxWidthWebButtonAdminDetails = 400.dp
private val HeightSelectButton = 50.dp

@Composable
fun Sites(
    navController: NavHostController
) {
    val sites by TestDatabase.sites.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.mediumPadding)
    ) {
        LazyVerticalGrid(
            modifier = Modifier.weight(1f),
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
    }
}

@Composable
fun SelectSites(adminsViewModel: AdminsViewModel, onClose: () -> Unit) {
    val sites by TestDatabase.sites.collectAsState()
    val stateAdmin by adminsViewModel.stateAdmin.collectAsState()
    val selectedSites = remember { stateAdmin.sites.toMutableStateList() }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.mediumPadding)
    ) {
        LazyVerticalGrid(
            modifier = Modifier.weight(1f),
            columns = GridCells.Adaptive(MinSizeColumnsSitesAndAdmins),
            contentPadding = PaddingValues(top = DimensTheme.paddings.mediumPadding),
            verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.mediumPadding),
            horizontalArrangement = Arrangement.spacedBy(DimensTheme.paddings.mediumPadding),
            content = {
                items(sites) { site ->
                    key(site.id) {
                        SiteSelectWidget(
                            site = site, isSelect = site in selectedSites
                        ) { site ->
                            if (site in selectedSites) {
                                selectedSites.remove(site)
                            } else {
                                selectedSites.add(site)
                            }
                        }
                    }
                }
            })
        Box(
            modifier = Modifier.widthIn(max = MaxWidthWebButtonAdminDetails)
                .padding(bottom = DimensTheme.paddings.mediumPadding),
            contentAlignment = Alignment.Center
        ) {
            WebButton(
                modifier = Modifier.height(HeightSelectButton).fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    adminsViewModel.setStateAdmin(
                        stateAdmin.copy(
                            sites = selectedSites.toList()
                        )
                    )
                    onClose()
                }) {
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}