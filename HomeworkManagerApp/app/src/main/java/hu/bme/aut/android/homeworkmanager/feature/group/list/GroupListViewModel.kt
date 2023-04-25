package hu.bme.aut.android.homeworkmanager.feature.group.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.homeworkmanager.feature.group.GroupHandler
import hu.bme.aut.android.homeworkmanager.ui.model.GroupHeaderUi
import hu.bme.aut.android.homeworkmanager.ui.model.asGroupHeaderUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class GroupListState {
    object Loading : GroupListState()
    data class Error(val error: Throwable) : GroupListState()
    data class Result(val groupList: List<GroupHeaderUi>) : GroupListState()
}

class GroupListViewModel(private val groupHandler: GroupHandler) : ViewModel() {
    private val _state = MutableStateFlow<GroupListState>(GroupListState.Loading)
    val state = _state.asStateFlow()

    init {
        loadGroups()
    }

    fun loadGroups() {
        groupHandler.getGroups(
            { result ->
                _state.value = GroupListState.Result(
                    groupList = result.map { it.asGroupHeaderUi() },
                )
            },
            { _state.value = GroupListState.Error(Exception("ERROR")) },
        )
    }

    companion object {
        fun factory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                GroupListViewModel(GroupHandler(context))
            }
        }
    }
}
