package dev.softikk.webadminpanel.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.softikk.webadminpanel.ButtonContinueText
import dev.softikk.webadminpanel.DescriptionAuth
import dev.softikk.webadminpanel.PrivacyPolicyText1
import dev.softikk.webadminpanel.PrivacyPolicyText2
import dev.softikk.webadminpanel.PrivacyPolicyText3
import dev.softikk.webadminpanel.Routes
import dev.softikk.webadminpanel.TextFieldPlaceEmailAuth
import dev.softikk.webadminpanel.TextFieldPlacePasswordAuth
import dev.softikk.webadminpanel.TitleAuth
import dev.softikk.webadminpanel.components.WebTextField
import dev.softikk.webadminpanel.viewmodels.AuthViewModel
import dev.softikk.webkit.components.buttons.Button
import dev.softikk.webkit.theme.DimensTheme

private val MaxWidthBlankAuth = 400.dp

@Composable
fun Auth(navController: NavHostController, authViewModel: AuthViewModel) {
    val stateTextFieldEmail = rememberTextFieldState()
    val stateTextFieldPassword = rememberTextFieldState()
    var checked by remember { mutableStateOf(false) }

    val isLogin by authViewModel.isLogin.collectAsState()

    LaunchedEffect(isLogin) {
        if (isLogin) {
            navController.navigate(Routes.Main.Sites.route)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
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
                    authViewModel.login(
                        email = stateTextFieldEmail.text.toString(),
                        password = stateTextFieldPassword.text.toString()
                    )
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
}