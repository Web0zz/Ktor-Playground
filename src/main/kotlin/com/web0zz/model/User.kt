package com.web0zz.model

import kotlinx.serialization.Serializable

/**
 *  Using an in-memory storage.
 *
 *  In real application, data will be stored in database
 */
val userStorage = mutableListOf<User>()

@Serializable
data class User (
    val id: String,
    val username: String,
    val email: String
)