package hu.bme.aut.android.homeworkmanager.network

import hu.bme.aut.android.homeworkmanager.model.group.GroupHeader
import retrofit2.Call
import retrofit2.http.GET

interface GroupApi {
    @GET("/api/Group")
    fun getGroups(): Call<Array<GroupHeader>>?
}