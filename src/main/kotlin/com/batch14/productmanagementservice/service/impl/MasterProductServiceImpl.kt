package com.batch14.productmanagementservice.service.impl

import com.batch14.productmanagementservice.domain.constant.Constant
import com.batch14.productmanagementservice.domain.dto.request.ReqCreateProductDto
import com.batch14.productmanagementservice.domain.dto.response.ResGetProductDto
import com.batch14.productmanagementservice.domain.entity.MasterProductEntity
import com.batch14.productmanagementservice.exception.CustomException
import com.batch14.productmanagementservice.repository.MasterCategoryRepository
import com.batch14.productmanagementservice.repository.MasterProductRepository
import com.batch14.productmanagementservice.rest.UserManagementClient
import com.batch14.productmanagementservice.service.MasterProductService
import feign.FeignException
import jakarta.servlet.http.HttpServletRequest
import jakarta.ws.rs.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.Optional
import kotlin.text.toInt

@Service
class MasterProductServiceImpl(
    private val masterProductRepository: MasterProductRepository,
    private val masterCategoryRepository: MasterCategoryRepository,
    private val userManagementClient: UserManagementClient,
    private val httpServletRequest: HttpServletRequest

): MasterProductService {
    override fun findAllProducts(): List<ResGetProductDto> {
        // val rawData = masterProductRepository.getAllProducts()
        val productsEntity = masterProductRepository.findAll()
        // solusi mengatasi n+1 problem
        val createdByIds = productsEntity.mapNotNull { it.createdBy?.toInt() }.distinct()
        val users = userManagementClient.getUsersByIds(createdByIds).body!!.data!!
        val result = productsEntity.map { prod ->
            ResGetProductDto(
                id = prod.id,
                name = prod.name,
                price = prod.price,
                description = prod.description,
                isForSale = prod.isForSale,
                categoryId = prod.category?.id,
                categoryName = prod.category?.name,
                createdBy = users.find { it.id == prod.createdBy?.toInt() }?.username
            )
        }
        return result
    }

    override fun findProductById(productId: Int): ResGetProductDto {
        val productEntity = masterProductRepository.findProductById(productId).orElseThrow {
            throw CustomException(
                "Produk dengan id $productId tidak ditemukan",
                400
            )
        }


        var createdBy = productEntity.createdBy

        try {
            if(createdBy != null) {
                val user = userManagementClient.getUserById(
                    createdBy.toInt()
                ).body!!.data!!
                createdBy = user.username
            }
        } catch (e : FeignException) {
            createdBy = null
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

    override fun createProduct(req: ReqCreateProductDto): ResGetProductDto {
        val roleUser = httpServletRequest.getHeader(Constant.HEADER_USER_ROLE)
        val idUser = httpServletRequest.getHeader(Constant.HEADER_USER_ID)
        var createdBy = ""

        // cek apakah role admin
        if(!roleUser.equals("admin")) {
            throw CustomException(
                "Unauthorized user",
                HttpStatus.UNAUTHORIZED.value()
            )
        }
        // cek apakah user ada
        try {
            val userResponse = userManagementClient.getUserById(idUser.toInt())
            createdBy = userResponse.body!!.data!!.username
        } catch (e : FeignException) {
            if(e.status() == 400) {
                throw CustomException("User tidak ditemukan", 400)
            }
        }

        val isForSale = req.isForSale ?: false
        val category = if(req.categoryId == null) {
            Optional.empty()
        } else {
            masterCategoryRepository.findById(req.categoryId)
        }

        val productRaw = MasterProductEntity(
            name = req.name,
            price = req.price,
            description = req.description,
            isForSale = isForSale,
            category = if(category.isPresent){ // ini ga pake role != null
                category.get()
            } else {
                null
            },
            createdBy = idUser
        )

        val product = masterProductRepository.save(productRaw)

        return ResGetProductDto(
            id = product.id,
            name = product.name,
            price = product.price,
            description = product.description,
            isForSale = product.isForSale,
            categoryId = product.category?.id,
            categoryName = product.category?.name,
            createdBy = createdBy
        )

    }

    override fun findProductsByIds(ids: List<Int>): List<ResGetProductDto> {
        val rawData = masterProductRepository.findAllByIds(ids)
        return rawData.map {
            ResGetProductDto(
                id = it.id,
                name = it.name,
                price = it.price,
                isForSale = it.isForSale
            )
        }
    }

}