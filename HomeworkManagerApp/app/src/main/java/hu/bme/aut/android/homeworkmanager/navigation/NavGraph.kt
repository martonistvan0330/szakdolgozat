package hu.bme.aut.android.homeworkmanager.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import hu.bme.aut.android.homeworkmanager.feature.auth.LoginScreen
import hu.bme.aut.android.homeworkmanager.feature.auth.RegisterScreen
import hu.bme.aut.android.homeworkmanager.feature.auth.group.GroupListScreen

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = AUTH_GRAPH_ROUTE,
        route = ROOT_GRAPH_ROUTE,
    ) {
        authNavGraph(navController = navController)
        groupNavGraph(navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = Screen.Login.route,
        route = AUTH_GRAPH_ROUTE,
    ) {
        composable(
            route = Screen.Login.route,
        ) {
            LoginScreen(
                onLogin = {
                    navController.navigate(Screen.GroupList.route)
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                },
            )
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(
                onRegisterClick = { /* navController.navigate(Screen.Home.passUsername(it)) */ },
                onLoginClick = { navController.navigate(Screen.Login.route) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.groupNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = Screen.Login.route,
        route = GROUP_GRAPH_ROUTE,
    ) {
        composable(
            route = Screen.GroupList.route,
        ) {
            GroupListScreen(
                {},
                {},
            )
        }
    }
}
