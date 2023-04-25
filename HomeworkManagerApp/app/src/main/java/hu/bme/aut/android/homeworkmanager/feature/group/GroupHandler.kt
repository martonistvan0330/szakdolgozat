package hu.bme.aut.android.homeworkmanager.feature.group

import android.content.Context
import hu.bme.aut.android.homeworkmanager.model.group.GroupHeader
import hu.bme.aut.android.homeworkmanager.network.GroupNetworkManager
import hu.bme.aut.android.homeworkmanager.network.handleAuthorize

class GroupHandler(private val context: Context) {
    fun getGroups(onSuccess: (Array<GroupHeader>) -> Unit, onError: () -> Unit) {
        GroupNetworkManager(context).getGroups().handleAuthorize(
            { response -> onSuccess(response) },
            { onError() },
        )
    }
}