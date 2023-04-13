package com.cheezycode.notesample.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cheezycode.notesample.api.FakeUsers
import com.cheezycode.notesample.models.UserRequest
import com.cheezycode.notesample.models.UserResponse
import com.cheezycode.notesample.models.users.GetUsers
import com.cheezycode.notesample.models.users.UserRequestRoot
import com.cheezycode.notesample.models.users.UserResponseRoot
import com.cheezycode.notesample.utils.LiveDataInternetConnections
import com.cheezycode.notesample.utils.NetworkResult
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class FakeUserRepository @Inject constructor(private val fakeUsers: FakeUsers) {

    val _createUsersLiveData = MutableLiveData<NetworkResult<UserResponseRoot>>()
    val createUsers: LiveData<NetworkResult<UserResponseRoot>>
        get() = _createUsersLiveData

    suspend fun createUser(userRequestRoot: UserRequestRoot) {
        _createUsersLiveData.postValue(NetworkResult.Loading())
        val response = fakeUsers.createUser(userRequestRoot)
        handleResponse(response)
    }


    val _getUsersList = MutableLiveData<NetworkResult<GetUsers>>()
    val getUsersList: LiveData<NetworkResult<GetUsers>>
        get() = _getUsersList

    suspend fun getUsersList() {
//        if (LiveDataInternetConnections.checkForInternet(context)) {
            _getUsersList.postValue(NetworkResult.Loading())
            val response = fakeUsers.getListOfUsers()

            if (response.isSuccessful && response.body() != null) {
                _getUsersList.postValue(NetworkResult.Success(response.body()!!))
            } else if (response.errorBody() != null) {
                val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                _getUsersList.postValue(NetworkResult.Error(errorObj.getString("message")))
            } else {
                _getUsersList.postValue(NetworkResult.Error("Something went wrong"))
            }
//        }else{
//            Toast.makeText(context,"Connect to internet",Toast.LENGTH_LONG).show()
//        }
    }


    private fun handleResponse(response: Response<UserResponseRoot>) {
        if (response.isSuccessful && response.body() != null) {
            _createUsersLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _createUsersLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _createUsersLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

}