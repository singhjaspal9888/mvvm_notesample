package com.cheezycode.notesample.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cheezycode.notesample.models.users.GetUsers
import com.cheezycode.notesample.repository.FakeUserRepository
import com.cheezycode.notesample.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val fakeUserRepository: FakeUserRepository) :
    ViewModel() {

    val getUsersLiveData get() = fakeUserRepository.getUsersList

    fun getUsersList() {
        viewModelScope.launch {
            fakeUserRepository.getUsersList()
        }
    }
}