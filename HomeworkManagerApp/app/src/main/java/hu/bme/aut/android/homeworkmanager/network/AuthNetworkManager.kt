package hu.bme.aut.android.homeworkmanager.network

import android.content.Context
import hu.bme.aut.android.homeworkmanager.model.auth.AuthenticationRequest
import hu.bme.aut.android.homeworkmanager.model.auth.AuthenticationResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthNetworkManager(context: Context) {
    private val retrofit: Retrofit
    private val authApi: AuthApi

    private val SERVICE_URL = "http://10.0.2.2:5024"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        authApi = retrofit.create(AuthApi::class.java)
    }

    fun login(authRequest: AuthenticationRequest): Call<AuthenticationResponse>? {
        return authApi.login(authRequest)
    }
}
