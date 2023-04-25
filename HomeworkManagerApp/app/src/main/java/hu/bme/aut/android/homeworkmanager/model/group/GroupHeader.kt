package hu.bme.aut.android.homeworkmanager.model.group

data class GroupHeader(
    val id: Int,
    val name: String,
    val description: String,
    val coverUrl: String?
)