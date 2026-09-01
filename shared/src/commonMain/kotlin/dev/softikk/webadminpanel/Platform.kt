package dev.softikk.webadminpanel

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform