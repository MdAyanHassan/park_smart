package com.example.firebasesigninwithmanualdi.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.firebasesigninwithmanualdi.FirebaseSignInApplication
import com.example.firebasesigninwithmanualdi.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.firebasesigninwithmanualdi.domain.model.Response
import com.example.firebasesigninwithmanualdi.domain.model.User
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository
): ViewModel() {

    val displayName = profileRepository.displayName
    val photoUrl = profileRepository.photoUrl

    private val _signOutResponse = MutableStateFlow<Response<Boolean>>(Response.Success(false))
    val signOutResponse: StateFlow<Response<Boolean>> = _signOutResponse.asStateFlow()

    private val _revokeAccessResponse = MutableStateFlow<Response<Boolean>>(Response.Success(false))
    val revokeAccessResponse: StateFlow<Response<Boolean>> = _revokeAccessResponse.asStateFlow()

    fun signOut(): Job {
        return viewModelScope.launch {
            _signOutResponse.value = Response.Loading
            _signOutResponse.value = profileRepository.signOut()
        }
    }

    fun revokeAccess(): Job {
        return viewModelScope.launch {
            _revokeAccessResponse.value = Response.Loading
            _revokeAccessResponse.value = profileRepository.revokeAccess()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as FirebaseSignInApplication
                val appContainer = application.appContainer
                ProfileViewModel(appContainer.profileRepository)
            }
        }
    }
}