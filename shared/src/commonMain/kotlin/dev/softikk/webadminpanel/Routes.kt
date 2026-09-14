package dev.softikk.webadminpanel

import kotlin.uuid.Uuid

sealed class Routes(open val route: String) {
    data object Auth : Routes("auth")

    data object Main : Routes("main") {
        data object Sites : Routes("${route}/sites") {
            data class Id(val siteId: Uuid) : Routes("${route}/$siteId") {
                companion object {
                    val pattern = "$route/{siteId}"
                }
            }
        }

        data object Admins : Routes("${route}/admins") {
            data class Id(val adminId: Uuid) : Routes("${route}/$adminId") {
                companion object {
                    val pattern = "$route/{adminId}"
                }
            }
        }
    }
}
