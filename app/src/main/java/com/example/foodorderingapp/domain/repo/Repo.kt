package com.example.foodorderingapp.domain.repo

import com.example.foodorderingapp.common.ResultState
import com.example.foodorderingapp.data.models.UserData
import kotlinx.coroutines.flow.Flow

interface Repo {
    fun loginWithEmailAndPassword(userData: UserData): Flow<ResultState<String>>
    fun registerWithEmailAndPassword(userData: UserData): Flow<ResultState<String>>
}