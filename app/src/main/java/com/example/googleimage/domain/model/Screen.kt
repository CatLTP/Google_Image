package com.example.googleimage.domain.model

sealed class Screen(val route: String) {
    data object ImageListScreen : Screen("list_screen")
    data object ImageDetailScreen : Screen("detail_screen")

    fun withArgs(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
