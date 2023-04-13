package com.cheezycode.notesample.ui.login

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheezycode.notesample.models.UserRequest
import com.cheezycode.notesample.models.UserResponse
import com.cheezycode.notesample.models.users.UserRequestRoot
import com.cheezycode.notesample.models.users.UserResponseRoot
import com.cheezycode.notesample.repository.FakeUserRepository
import com.cheezycode.notesample.repository.UserRepository
import com.cheezycode.notesample.utils.Helper
import com.cheezycode.notesample.utils.LiveDataInternetConnections
import com.cheezycode.notesample.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository,
private val fakeUserRepository: FakeUserRepository) : ViewModel() {



//    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
//    get() = userRepository.userResponseLiveData
//
//    fun registerUser(userRequest: UserRequest){
//        viewModelScope.launch {
//            userRepository.registerUser(userRequest)
//        }
//    }

    fun loginUser(userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }
    }

    fun validateCredentials(emailAddress: String, userName: String, password: String,
     isLogin: Boolean) : Pair<Boolean, String> {

        var result = Pair(true, "")
        if(TextUtils.isEmpty(emailAddress) || (!isLogin && TextUtils.isEmpty(userName)) || TextUtils.isEmpty(password)){
            result = Pair(false, "Please provide the credentials")
        }
        else if(!Helper.isValidEmail(emailAddress)){
            result = Pair(false, "Email is invalid")
        }
        else if(!TextUtils.isEmpty(password) && password.length <= 5){
            result = Pair(false, "Password length should be greater than 5")
        }
        return result
    }

    //fake api call
    val userResponseLiveData : LiveData<NetworkResult<UserResponseRoot>>
    get() = fakeUserRepository.createUsers

    fun createUser(userRequestRoot: UserRequestRoot){
        viewModelScope.launch {
            fakeUserRepository.createUser(userRequestRoot)
        }
    }

}