package com.batch14.productmanagementservice.domain.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ReqCreateProductDto(
    @field:NotBlank(message = "Nama produk wajib diisi")
    val name: String,
    @field:NotNull(message = "Harga produk wajib diisi")
    val price: Int,
    val description: String? = null,
    val isForSale: Boolean? = false,
    val categoryId: Int?,
)
