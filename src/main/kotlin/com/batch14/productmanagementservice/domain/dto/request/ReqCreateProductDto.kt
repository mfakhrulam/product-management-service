package com.batch14.productmanagementservice.domain.dto.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero

data class ReqCreateProductDto(
    @field:NotBlank(message = "Nama produk wajib diisi")
    val name: String,
    @field:NotNull(message = "Harga produk wajib diisi")
    @field:PositiveOrZero( message = "Harga produk tidak boleh kurang dari 0")
    val price: Int,
    val description: String? = null,
    val isForSale: Boolean? = false,
    val categoryId: Int?,
)
