package com.example.foodorderingapp.data.repoimpl

import com.example.foodorderingapp.common.ResultState
import com.example.foodorderingapp.data.models.UserData
import com.example.foodorderingapp.domain.repo.Repo
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepoImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : Repo {

    override fun loginWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> = flow {
        emit(ResultState.Loading)

        try {
            // Perform login and await the result
            val authResult = firebaseAuth.signInWithEmailAndPassword(userData.email, userData.password).await()
            val userId = authResult.user?.uid ?: throw IllegalStateException("User is null after login")
            emit(ResultState.Success(userId))  // Emit the UID as meaningful data
        } catch (e: Exception) {
            val errorMsg = when (e) {
                is FirebaseAuthInvalidUserException -> "Invalid email or password"
                is FirebaseAuthWeakPasswordException -> "Password is too weak"
                is FirebaseNetworkException -> "Network error - check your connection"
                else -> e.message ?: "Login failed"
            }
            emit(ResultState.Error(errorMsg))
        }
    }.flowOn(Dispatchers.IO)

    override fun registerWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> = flow {
        emit(ResultState.Loading)

        try {
            // Perform registration and await the result
            val authResult = firebaseAuth.createUserWithEmailAndPassword(userData.email, userData.password).await()
            val userId = authResult.user?.uid ?: throw IllegalStateException("User is null after registration")

            // Store user data in Firestore and await the result
            firebaseFirestore.collection("users")
                .document(userId)
                .set(userData)
                .await()

            emit(ResultState.Success(userId))  // Emit UID after successful registration and Firestore save
        } catch (e: Exception) {
            val errorMsg = when (e) {
                is FirebaseAuthInvalidUserException -> "Invalid email or password"
                is FirebaseAuthWeakPasswordException -> "Password is too weak"
                is FirebaseNetworkException -> "Network error - check your connection"
                is FirebaseFirestoreException -> "Failed to save user data: ${e.message}"
                else -> e.message ?: "Registration failed"
            }
            emit(ResultState.Error(errorMsg))
        }
    }.flowOn(Dispatchers.IO)
}