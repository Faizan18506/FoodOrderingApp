package com.example.foodorderingapp.domain.useCases

import com.example.foodorderingapp.common.ResultState
import com.example.foodorderingapp.data.models.UserData
import com.example.foodorderingapp.domain.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class loginUserUseCase @Inject constructor(val repo: Repo) {

    fun loginUser(userData: UserData): Flow<ResultState<String>> {
        return repo.loginWithEmailAndPassword(userData)
    }
}