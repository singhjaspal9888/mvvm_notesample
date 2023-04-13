package com.cheezycode.notesample.api

import com.cheezycode.notesample.models.UserRequest
import com.cheezycode.notesample.models.users.GetUsers
import com.cheezycode.notesample.models.users.UserRequestRoot
import com.cheezycode.notesample.models.users.UserResponseRoot
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FakeUsers {

    @POST("api/users")
    suspend fun createUser(@Body userRequestRoot: UserRequestRoot) : Response<UserResponseRoot>

    @GET("api/users?page=2")
    suspend fun getListOfUsers() : Response<GetUsers>
}