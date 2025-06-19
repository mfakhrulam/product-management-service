package com.batch14.productmanagementservice.service

import com.batch14.productmanagementservice.domain.dto.request.ReqCreateProductDto
import com.batch14.productmanagementservice.domain.dto.response.ResGetProductDto

interface MasterProductService {
    fun findAllProducts(): List<ResGetProductDto>
    fun findProductById(productId: Int): ResGetProductDto
    fun createProduct(req: ReqCreateProductDto): ResGetProductDto
    fun findProductsByIds(ids: List<Int>): List<ResGetProductDto>
}