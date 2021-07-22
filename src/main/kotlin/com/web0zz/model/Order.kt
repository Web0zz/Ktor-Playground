package com.web0zz.model

import kotlinx.serialization.Serializable

val orderStorage = listOf(
    Order("2021-07-14-01", listOf(
        OrderItem("Ham Sandwich", 2, 5.50),
        OrderItem("Water", 1, 1.50),
        OrderItem("Beer", 3, 2.30),
        OrderItem("Cheesecake", 1, 3.75)
    )),
    Order("2021-07-14-02", listOf(
        OrderItem("Pizza", 1, 8.50),
        OrderItem("Water", 2, 1.50),
        OrderItem("Coke", 2, 1.76),
        OrderItem("Milkshake", 1, 2.35)
    ))
)

@Serializable
data class Order(
    val number: String,
    val contents: List<OrderItem>
)

@Serializable
data class OrderItem(
    val item: String,
    val amount: Int,
    val price: Double
)
