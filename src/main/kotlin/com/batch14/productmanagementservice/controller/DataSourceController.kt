package com.batch14.productmanagementservice.controller

import com.batch14.productmanagementservice.domain.dto.response.BaseResponse
import com.batch14.productmanagementservice.domain.dto.response.ResGetProductDto
import com.batch14.productmanagementservice.service.MasterProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/data-source")
class DataSourceController(
    private val masterProductService: MasterProductService
) {
    @GetMapping("/products/by-ids")
    fun getProductsByIds(
        @RequestParam(value = "ids", required = true) productsIds: List<Int>
    ): ResponseEntity<BaseResponse<List<ResGetProductDto>>>{
        return ResponseEntity.ok(
            BaseResponse(
                data = masterProductService.findProductsByIds(productsIds)
            )
        )
    }
}