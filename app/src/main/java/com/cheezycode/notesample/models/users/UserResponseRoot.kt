package com.cheezycode.notesample.models.users

data class UserResponseRoot(
    val createdAt: String,
    val id: String,
    val job: String,
    val name: String
)