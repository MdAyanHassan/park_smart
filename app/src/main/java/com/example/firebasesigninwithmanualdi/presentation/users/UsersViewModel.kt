package com.example.firebasesigninwithmanualdi.presentation.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.firebasesigninwithmanualdi.FirebaseSignInApplication
import com.example.firebasesigninwithmanualdi.domain.model.Response
import com.example.firebasesigninwithmanualdi.domain.model.User
import com.example.firebasesigninwithmanualdi.domain.repository.UsersRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersViewModel(
    private val usersRepository: UsersRepository
): ViewModel() {

    init {
        getAllUsersInfo()
    }

    private val _currentUserResponse = MutableStateFlow<Response<User>>(Response.Loading)
    val currentUserResponse: StateFlow<Response<User>> = _currentUserResponse.asStateFlow()

    private val _getAllUsersInfoResponse = MutableStateFlow<Response<List<User>>>(Response.Loading)
    val getAllUsersInfoResponse: StateFlow<Response<List<User>>> = _getAllUsersInfoResponse.asStateFlow()

    private fun getAllUsersInfo(): Job {
        return viewModelScope.launch {
            usersRepository.getAllUsersInfo().collect {
                _getAllUsersInfoResponse.value = it
            }
        }
    }

    fun setClickedUser(user: User) = viewModelScope.launch {
        _currentUserResponse.value = Response.Success(user)
    }

    fun resetCurrentUser() = viewModelScope.launch {
        _currentUserResponse.value = Response.Loading
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as FirebaseSignInApplication
                val appContainer = application.appContainer
                UsersViewModel(appContainer.usersRepository)
            }
        }
    }
}
