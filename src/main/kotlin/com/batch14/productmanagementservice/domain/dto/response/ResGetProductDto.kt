package com.batch14.productmanagementservice.domain.dto.response

data class ResGetProductDto(
    val id: Int,
    val name: String,
    val price: Int,
    val description: String? = null,
    val isForSale: Boolean,
    var categoryId: Int? = null,
    var categoryName: String? = null
)
