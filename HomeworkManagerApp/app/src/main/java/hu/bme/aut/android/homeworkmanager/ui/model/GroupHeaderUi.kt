package hu.bme.aut.android.homeworkmanager.ui.model

import hu.bme.aut.android.homeworkmanager.model.group.GroupHeader

data class GroupHeaderUi(
    val id: Int = 0,
    val name: String = "",
    val description: String,
    val coverUrl: String?
)

fun GroupHeader.asGroupHeaderUi(): GroupHeaderUi = GroupHeaderUi(
    id = id,
    name = name,
    description = description,
    coverUrl = coverUrl
)

fun GroupHeaderUi.asGroupHeader(): GroupHeader = GroupHeader(
    id = id,
    name = name,
    description = description,
    coverUrl = coverUrl
)