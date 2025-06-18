package com.batch14.productmanagementservice.service.impl

import com.batch14.productmanagementservice.domain.dto.response.ResGetProductDto
import com.batch14.productmanagementservice.exception.CustomException
import com.batch14.productmanagementservice.repository.MasterProductRepository
import com.batch14.productmanagementservice.rest.UserManagementClient
import com.batch14.productmanagementservice.service.MasterProductService
import org.springframework.stereotype.Service

@Service
class MasterProductServiceImpl(
    private val masterProductRepository: MasterProductRepository,
    private val userManagementClient: UserManagementClient
): MasterProductService {
    override fun findAllProducts(): List<ResGetProductDto> {
        val rawData = masterProductRepository.getAllProducts()
        val result = mutableListOf<ResGetProductDto>()
        rawData.forEach { product ->
            // TODO: jadikan lebih efisien supaya enggak n+1
            var createdBy = product.createdBy
            if(createdBy != null) {
                val user = userManagementClient.getUserById(
                    createdBy.toInt()
                ).body!!.data!!
                createdBy = user.username
            }
            result.add(
                ResGetProductDto(
                    id = product.id,
                    name = product.name,
                    price = product.price,
                    description = product.description,
                    isForSale = product.isForSale,
                    categoryId = product.category?.id,
                    categoryName = product.category?.name,
                    createdBy = createdBy
                )
            )
        }


        return result
    }

    override fun findProductById(productId: Int): ResGetProductDto {
        val productEntity = masterProductRepository.getProductById(productId).orElseThrow {
            throw CustomException(
                "Produk tidak ditemukan",
                400
            )
        }

        var createdBy = productEntity.createdBy
        if(createdBy != null) {
            val user = userManagementClient.getUserById(
                createdBy.toInt()
            ).body!!.data!!
            createdBy = user.username
        }
        val result = ResGetProductDto(
            id = productEntity.id,
            name = productEntity.name,
            price = productEntity.price,
            description = productEntity.description,
            isForSale = productEntity.isForSale,
            categoryId = productEntity.category?.id,
            categoryName = productEntity.category?.name,
            createdBy = createdBy
        )
        return result
    }

}