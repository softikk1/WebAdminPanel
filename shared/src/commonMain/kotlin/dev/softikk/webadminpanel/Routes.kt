package dev.softikk.webadminpanel

sealed class Routes(open val route: String) {
    data object Auth : Routes("auth")
    data object Sites : Routes("sites") {
        data object Id : Routes("${route}/{id}")
    }

    data object Admins : Routes("admins") {
        data object Id : Routes("${route}/{id}")
    }
}
