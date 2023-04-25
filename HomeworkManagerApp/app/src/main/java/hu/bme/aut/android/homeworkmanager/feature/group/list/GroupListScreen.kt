package hu.bme.aut.android.homeworkmanager.feature.auth.group

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.homeworkmanager.R
import hu.bme.aut.android.homeworkmanager.feature.group.list.GroupListState
import hu.bme.aut.android.homeworkmanager.feature.group.list.GroupListViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun GroupListScreen(
    onListItemClick: (Int) -> Unit,
    onCreateClick: () -> Unit,
    viewModel: GroupListViewModel = viewModel(factory = GroupListViewModel.factory(LocalContext.current)),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = onCreateClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(
                    color = if (state is GroupListState.Loading || state is GroupListState.Error) {
                        MaterialTheme.colorScheme.secondaryContainer
                    } else {
                        MaterialTheme.colorScheme.background
                    },
                ),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                is GroupListState.Loading -> CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
                is GroupListState.Error -> Text(
                    text = state.error.toString(),
                )
                is GroupListState.Result -> {
                    if (state.groupList.isEmpty()) {
                        Text(text = stringResource(id = R.string.text_empty_group_list))
                    } else {
                        Column {
                            Text(
                                text = stringResource(id = R.string.text_your_group_list),
                                fontSize = 24.sp,
                            )
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize(),
                            ) {
                                items(state.groupList, key = { group -> group.id }) { group ->
                                    ListItem(
                                        headlineText = {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = Icons.Default.Circle,
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .size(40.dp)
                                                        .padding(
                                                            end = 8.dp,
                                                            top = 8.dp,
                                                            bottom = 8.dp,
                                                        ),
                                                )
                                                Text(text = group.name)
                                            }
                                        },
                                        supportingText = {
                                            Text(
                                                text = group.description,
                                            )
                                        },
                                        modifier = Modifier
                                            .clickable(onClick = {
                                                onListItemClick(
                                                    group.id,
                                                )
                                            })
                                            .animateItemPlacement(),
                                    )
                                    if (state.groupList.last() != group) {
                                        Divider(
                                            thickness = 2.dp,
                                            color = MaterialTheme.colorScheme.secondaryContainer,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
