package com.batch14.productmanagementservice.controller

import com.batch14.productmanagementservice.domain.dto.response.BaseResponse
import com.batch14.productmanagementservice.domain.dto.response.ResGetProductDto
import com.batch14.productmanagementservice.service.MasterProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/products")
class ProductController(
    private val masterProductService: MasterProductService
) {
    @GetMapping("/")
    fun getAllProducts(): ResponseEntity<BaseResponse<List<ResGetProductDto>>> {
        return ResponseEntity.ok(
            BaseResponse(
                data = masterProductService.findAllProducts()
            )
        )
    }

    @GetMapping("/{id}")
    fun getAProductById(
        @PathVariable(name = "id") idProduct: Int
    ): ResponseEntity<BaseResponse<ResGetProductDto>> {
        return ResponseEntity.ok(
            BaseResponse(
                data = masterProductService.findProductById(idProduct)
            )
        )
    }
}