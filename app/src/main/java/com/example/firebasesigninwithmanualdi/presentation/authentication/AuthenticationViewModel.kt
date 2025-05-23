package com.example.firebasesigninwithmanualdi.presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.firebasesigninwithmanualdi.FirebaseSignInApplication
import com.example.firebasesigninwithmanualdi.domain.model.Response
import com.example.firebasesigninwithmanualdi.domain.repository.AuthenticationRepository
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val authenticationRepository: AuthenticationRepository,
    val oneTapClient: SignInClient
): ViewModel() {

    val isUserAuthenticated = authenticationRepository.isUserSignedIn

    private val _oneTapSignInResponse = MutableStateFlow<Response<BeginSignInResult>>(Response.Success(null))
    val oneTapSignInResponse: StateFlow<Response<BeginSignInResult>> = _oneTapSignInResponse.asStateFlow()

    private val _googleSignInResponse = MutableStateFlow<Response<Boolean>>(Response.Success(false))
    val googleSignInResponse: StateFlow<Response<Boolean>> = _googleSignInResponse.asStateFlow()

    fun oneTapSignIn(): Job {
        return viewModelScope.launch {
            _oneTapSignInResponse.value = Response.Loading
            _oneTapSignInResponse.value = authenticationRepository.oneTapSignInWithGoogle()
        }
    }

    fun signInWithGoogle(googleAuthCredentials: AuthCredential): Job {
        return viewModelScope.launch {
            _googleSignInResponse.value = Response.Loading
            _googleSignInResponse.value = authenticationRepository.signInWithGoogle(googleAuthCredentials)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as FirebaseSignInApplication

                val appContainer = application.appContainer

                AuthenticationViewModel(
                    appContainer.authenticationRepository,
                    appContainer.oneTapClient
                )
            }
        }
    }
}