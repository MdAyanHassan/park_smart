package com.example.firebasesigninwithmanualdi.presentation.users.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.firebasesigninwithmanualdi.domain.model.User

@Composable
fun UsersList(
    users: List<User>,
    onUserClicked: (User) -> Unit,
    paddingValues: PaddingValues
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(users) { user ->
            UserListItem(
                user = user,
                onItemClick = {
                    onUserClicked(user)
                }
            )
        }
    }
}