package com.web0zz.model

import kotlinx.serialization.Serializable
import org.kodein.db.model.orm.Metadata


@Serializable
data class User (
    override val id: String,
    val username: String,
    val password: String
) : Metadata