package com.example.firebasesigninwithmanualdi.di

import android.content.Context
import com.example.firebasesigninwithmanualdi.BuildConfig
import com.example.firebasesigninwithmanualdi.R
import com.example.firebasesigninwithmanualdi.data.repository.AuthenticationRepositoryImpl
import com.example.firebasesigninwithmanualdi.data.repository.PlacesRepositoryImpl
import com.example.firebasesigninwithmanualdi.data.repository.ProfileRepositoryImpl
import com.example.firebasesigninwithmanualdi.data.repository.UsersRepositoryImpl
import com.example.firebasesigninwithmanualdi.domain.repository.AuthenticationRepository
import com.example.firebasesigninwithmanualdi.domain.repository.PlacesRepository
import com.example.firebasesigninwithmanualdi.domain.repository.ProfileRepository
import com.example.firebasesigninwithmanualdi.domain.repository.UsersRepository
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

interface AppContainer {
    val authenticationRepository: AuthenticationRepository
    val profileRepository: ProfileRepository
    val usersRepository: UsersRepository
    val oneTapClient: SignInClient
    val placesRepository: PlacesRepository
}

class DefaultAppContainer(
    applicationContext: Context
): AppContainer {

    private val auth = Firebase.auth

    private val beginSignInRequest = BeginSignInRequest.Builder()
        .setGoogleIdTokenRequestOptions(
            GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(applicationContext.getString(R.string.default_web_client_id))
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    private val apiKey = BuildConfig.PLACES_API_KEY

    private val firestoreDb = Firebase.firestore

    private val googleSignInOptions = GoogleSignInOptions
        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(applicationContext.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    private val signInClient = GoogleSignIn.getClient(applicationContext, googleSignInOptions)

    override val oneTapClient = Identity.getSignInClient(applicationContext)

    override val placesRepository: PlacesRepository by lazy {
        Places.initializeWithNewPlacesApiEnabled(applicationContext, apiKey)
        PlacesRepositoryImpl(
            placesClient = Places.createClient(applicationContext),
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override val authenticationRepository: AuthenticationRepository by lazy {
        AuthenticationRepositoryImpl(
            auth = auth,
            oneTapClient = oneTapClient,
            beginSignInRequest = beginSignInRequest,
            db = firestoreDb
        )
    }
    override val profileRepository: ProfileRepository by lazy {
        ProfileRepositoryImpl(
            auth = auth,
            oneTapClient = oneTapClient,
            signInClient = signInClient,
            db = firestoreDb
        )
    }
    override val usersRepository: UsersRepository by lazy {
        UsersRepositoryImpl(
            db = firestoreDb
        )
    }
}