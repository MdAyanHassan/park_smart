package com.example.firebasesigninwithmanualdi.presentation.users

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasesigninwithmanualdi.components.ProgressBar
import com.example.firebasesigninwithmanualdi.core.Utils
import com.example.firebasesigninwithmanualdi.domain.model.Response
import com.example.firebasesigninwithmanualdi.presentation.users.components.UserDetailScreen
import com.example.firebasesigninwithmanualdi.presentation.users.components.UsersList
import com.example.firebasesigninwithmanualdi.presentation.users.components.UsersTopBar

@Composable
fun UsersScreen(
    usersViewModel: UsersViewModel = viewModel(factory = UsersViewModel.Factory),
    onBackClick: () -> Unit
) {

    val getAllUsersInfoResponse by usersViewModel.getAllUsersInfoResponse.collectAsState()

    val currentUserResponse by usersViewModel.currentUserResponse.collectAsState()

    var detailsPageOpen by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            UsersTopBar(
                detailsPageOpen = detailsPageOpen,
                onDetailsPageOpenChange = {
                    detailsPageOpen = it
                    usersViewModel.resetCurrentUser()
                },
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->

        if (!detailsPageOpen) {
            when (getAllUsersInfoResponse) {
                is Response.Failure -> {
                    LaunchedEffect(key1 = Unit) {
                        Utils.printError((getAllUsersInfoResponse as Response.Failure).e)
                    }
                }
                is Response.Loading -> {
                    ProgressBar()
                }
                is Response.Success -> {
                    (getAllUsersInfoResponse as Response.Success).data?.let {
                        UsersList(
                            users = it,
                            onUserClicked = { user ->
                                detailsPageOpen = true
                                usersViewModel.setClickedUser(user)
                            },
                            paddingValues = paddingValues
                        )
                    }
                }
            }
        }
        else {
            when (currentUserResponse) {
                is Response.Failure -> {
                    LaunchedEffect(key1 = Unit) {
                       Utils.printError((currentUserResponse as Response.Failure).e)
                    }
                }
                is Response.Loading -> {
                    ProgressBar()
                }
                is Response.Success -> {
                    (currentUserResponse as Response.Success).data?.let { user ->
                        UserDetailScreen(
                            user = user,
                            onBackClick = {
                                detailsPageOpen = false
                                usersViewModel.resetCurrentUser()
                            },
                            paddingValues = paddingValues
                        )
                    }
                }
            }
        }

    }
}