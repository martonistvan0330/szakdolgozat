package hu.bme.aut.android.homeworkmanager.navigation

const val ROOT_GRAPH_ROUTE = "root"
const val AUTH_GRAPH_ROUTE = "auth"
const val GROUP_GRAPH_ROUTE = "groups"

sealed class Screen(val route: String) {
    object Login : Screen(route = "login")
    object Register : Screen(route = "register")
    object GroupList : Screen(route = "list")
}