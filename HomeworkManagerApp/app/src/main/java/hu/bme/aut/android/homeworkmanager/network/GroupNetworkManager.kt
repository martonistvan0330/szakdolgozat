package hu.bme.aut.android.homeworkmanager.network

import android.content.Context
import hu.bme.aut.android.homeworkmanager.model.auth.AuthenticationRequest
import hu.bme.aut.android.homeworkmanager.model.auth.AuthenticationResponse
import hu.bme.aut.android.homeworkmanager.model.group.GroupHeader
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GroupNetworkManager(context: Context) {
    private val retrofit: Retrofit
    private val groupApi: GroupApi

    private val SERVICE_URL = "http://10.0.2.2:5024"

    init {
        val client = OkHttpClient.Builder().addInterceptor(AuthInterceptor(context)).build()
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        groupApi = retrofit.create(GroupApi::class.java)
    }

    fun getGroups(): Call<Array<GroupHeader>>? {
        return groupApi.getGroups()
    }
}