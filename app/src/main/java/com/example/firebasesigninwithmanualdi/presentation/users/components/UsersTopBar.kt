package com.example.firebasesigninwithmanualdi.presentation.users.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.firebasesigninwithmanualdi.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersTopBar(
    detailsPageOpen: Boolean,
    onDetailsPageOpenChange: (Boolean) -> Unit,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = if (detailsPageOpen) R.string.details_title else R.string.users_title)
            )
        },
        navigationIcon = {
            IconButton(onClick = { if (!detailsPageOpen) onBackClick() else onDetailsPageOpenChange(false) }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
        },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    )
}