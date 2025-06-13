package com.batch14.productmanagementservice.controller

import com.batch14.productmanagementservice.domain.dto.response.BaseResponse
import com.batch14.productmanagementservice.domain.dto.response.ResGetCategoryDto
import com.batch14.productmanagementservice.domain.dto.response.ResGetProductDto
import com.batch14.productmanagementservice.service.MasterCategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/categories")
class CategoryController(
    private val masterCategoryService: MasterCategoryService
) {
    @GetMapping("/")
    fun getAllCategories(): ResponseEntity<BaseResponse<List<ResGetCategoryDto>>> {
        return ResponseEntity.ok(
            BaseResponse(
                data = masterCategoryService.getAllCategories()
            )
        )
    }

    @GetMapping("/{id}")
    fun getAProductById(
        @PathVariable(name = "id") idCategory: Int
    ): ResponseEntity<ResGetCategoryDto> {
        return ResponseEntity.ok(
            masterCategoryService.findCategoryById(idCategory)
        )
    }

}