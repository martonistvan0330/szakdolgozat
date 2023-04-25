package hu.bme.aut.android.homeworkmanager.feature.auth

import android.content.Context
import hu.bme.aut.android.homeworkmanager.model.auth.AuthenticationRequest
import hu.bme.aut.android.homeworkmanager.network.AuthNetworkManager
import hu.bme.aut.android.homeworkmanager.network.handle

class AuthHandler(private val context: Context) {
    fun login(userName: String, password: String, onSuccess: () -> Unit, onError: () -> Unit) {
        AuthNetworkManager(context).login(AuthenticationRequest(userName, password))?.handle(
            { authResponse ->
                val sharedPref = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("access-token", authResponse.accessToken)
                    putString("refresh-token", authResponse.refreshToken)
                    apply()
                }
                onSuccess()
            },
            { onError() },
        )
    }
}
