package dev.softikk.webadminpanel.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.savedstate.read
import dev.softikk.webadminpanel.Admin
import dev.softikk.webadminpanel.DefaultAdminNameSiteDetails
import dev.softikk.webadminpanel.DefaultEmailSiteDetails
import dev.softikk.webadminpanel.DefaultPasswordNameSiteDetails
import dev.softikk.webadminpanel.TestDatabase
import dev.softikk.webadminpanel.TextButtonSiteDetails
import dev.softikk.webadminpanel.TextFieldHostNameSiteDetails
import dev.softikk.webadminpanel.TextFieldSiteDescriptionSiteDetails
import dev.softikk.webadminpanel.TextFieldSiteNameSiteDetails
import dev.softikk.webadminpanel.components.WebButton
import dev.softikk.webadminpanel.components.WebTextField
import dev.softikk.webadminpanel.viewmodels.AdminsViewModel
import dev.softikk.webkit.theme.DimensTheme
import org.jetbrains.compose.resources.vectorResource
import webadminpanel.shared.generated.resources.Res
import webadminpanel.shared.generated.resources.x
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private val MaxWidthTextFieldAdminDetails = 400.dp

@OptIn(ExperimentalUuidApi::class)
@Composable
fun AdminDetails(navController: NavHostController, navBackStackEntry: NavBackStackEntry?) {

    val adminsViewModel = viewModel { AdminsViewModel(TestDatabase) }

    val adminName = rememberTextFieldState(initialText = DefaultAdminNameSiteDetails)
    val email = rememberTextFieldState(initialText = DefaultEmailSiteDetails)
    val password = rememberTextFieldState(initialText = DefaultPasswordNameSiteDetails)

    val admins by TestDatabase.admins.collectAsState()

    val adminId = navBackStackEntry?.arguments?.read { getStringOrNull("adminId") }?.let { id ->
        if (id == "null") null else id
    }

    val findSuchAdmin by remember {
        mutableStateOf(adminId?.let {
            admins.singleOrNull { admin -> admin.id == Uuid.parse(adminId) }
        })
    }

    var admin by remember(adminName.text, email.text, password.text) {
        var initAdmin = Admin(
            id = Uuid.generateV4(),
            name = adminName.text.toString(),
            email = email.text.toString(),
            password = password.text.toString()
        )

        findSuchAdmin?.let {
            initAdmin = initAdmin.copy(id = it.id)
        }

        mutableStateOf(
            initAdmin
        )
    }

    LaunchedEffect(Unit) {
        findSuchAdmin?.let {
            admin = admin.copy(
                id = it.id, name = it.name, email = it.email, password = it.password
            )
        }

        adminName.edit {
            replace(0, adminName.text.length, admin.name)
        }
        email.edit {
            replace(0, email.text.length, admin.email)
        }
        password.edit {
            replace(0, password.text.length, admin.password)
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
                    text = admin.password,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
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
            modifier = Modifier.widthIn(max = MaxWidthTextFieldAdminDetails),
            verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.largePadding)
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(DimensTheme.paddings.mediumPadding)
                ) {
                    WebTextField(
                        modifier = Modifier.fillMaxWidth(),
                        state = adminName,
                        labelText = TextFieldSiteNameSiteDetails
                    )
                    WebTextField(
                        modifier = Modifier.fillMaxWidth(),
                        state = email,
                        labelText = TextFieldHostNameSiteDetails
                    )
                    WebTextField(
                        modifier = Modifier.fillMaxWidth(),
                        state = password,
                        labelText = TextFieldSiteDescriptionSiteDetails,
                        keyboardType = KeyboardType.Password
                    )
                }
            }
        }

        Box(
            modifier = Modifier.widthIn(max = MaxWidthTextFieldAdminDetails)
                .padding(bottom = DimensTheme.paddings.mediumPadding),
            contentAlignment = Alignment.Center
        ) {
            WebButton(
                modifier = Modifier.height(50.dp).fillMaxWidth(), onClick = {
                    adminsViewModel.saveAdmin(admin)
                    navController.popBackStack()
                }) {
                Text(
                    text = TextButtonSiteDetails,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}