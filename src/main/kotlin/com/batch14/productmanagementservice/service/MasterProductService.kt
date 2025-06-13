package com.batch14.productmanagementservice.service

import com.batch14.productmanagementservice.domain.dto.response.ResGetProductDto

interface MasterProductService {
    fun findAllProducts(): List<ResGetProductDto>
    fun findProductById(productId: Int): ResGetProductDto
}