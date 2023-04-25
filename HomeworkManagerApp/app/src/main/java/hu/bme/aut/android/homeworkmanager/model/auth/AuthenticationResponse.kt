package hu.bme.aut.android.homeworkmanager.model.auth

import java.util.Date

data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiration: Date,
)
