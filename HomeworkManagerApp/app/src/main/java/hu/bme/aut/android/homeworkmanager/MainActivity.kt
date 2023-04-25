package hu.bme.aut.android.homeworkmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.android.homeworkmanager.navigation.NavGraph
import hu.bme.aut.android.homeworkmanager.network.AuthNetworkManager
import hu.bme.aut.android.homeworkmanager.ui.theme.HomeworkManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeworkManagerTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
