package ch.ak

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform