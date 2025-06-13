package com.batch14.productmanagementservice.service.impl

import com.batch14.productmanagementservice.domain.dto.response.ResGetProductDto
import com.batch14.productmanagementservice.repository.MasterProductRepository
import com.batch14.productmanagementservice.service.MasterProductService
import org.springframework.stereotype.Service

@Service
class MasterProductServiceImpl(
    private val masterProductRepository: MasterProductRepository
): MasterProductService {
    override fun findAllProducts(): List<ResGetProductDto> {
        val rawData = masterProductRepository.getAllProducts()
        val result = mutableListOf<ResGetProductDto>()
        rawData.forEach { product ->
            result.add(
                ResGetProductDto(
                    id = product.id,
                    name = product.name,
                    price = product.price,
                    description = product.description,
                    isForSale = product.isForSale,
                    categoryId = product.category?.id,
                    categoryName = product.category?.name
                )
            )
        }
        return result
    }

    override fun findProductById(productId: Int): ResGetProductDto {
        val rawData = masterProductRepository.getProductById(productId)
        val result = ResGetProductDto(
            id = rawData.id,
            name = rawData.name,
            price = rawData.price,
            description = rawData.description,
            isForSale = rawData.isForSale,
            categoryId = rawData.category?.id,
            categoryName = rawData.category?.name
        )
        return result
    }

}