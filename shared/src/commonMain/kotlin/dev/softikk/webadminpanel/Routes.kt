package dev.softikk.webadminpanel

sealed class Routes(open val route: String) {
    data object Auth : Routes("auth")

    data object Main : Routes("main") {
        data object Sites : Routes("${route}/sites") {
            data object Id : Routes("${route}/{id}")
        }

        data object Admins : Routes("${route}/admins") {
            data object Id : Routes("${route}/{id}")
        }
    }
}
