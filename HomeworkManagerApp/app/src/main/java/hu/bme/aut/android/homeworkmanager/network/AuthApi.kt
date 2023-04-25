package hu.bme.aut.android.homeworkmanager.network

import hu.bme.aut.android.homeworkmanager.model.auth.AuthenticationRequest
import hu.bme.aut.android.homeworkmanager.model.auth.AuthenticationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/Auth/BearerToken")
    fun login(
        @Body authRequest: AuthenticationRequest,
    ): Call<AuthenticationResponse>?
}
