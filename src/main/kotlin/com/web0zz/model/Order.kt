package com.web0zz.model

import kotlinx.serialization.Serializable
import org.kodein.db.IndexValues
import org.kodein.db.model.orm.Metadata

@Serializable
data class Order(
    override val id: String,
    val item: String,
    val amount: String,
    val price: String
) : Metadata