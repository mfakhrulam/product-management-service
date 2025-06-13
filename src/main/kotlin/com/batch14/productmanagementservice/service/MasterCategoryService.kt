package com.batch14.productmanagementservice.service

import com.batch14.productmanagementservice.domain.dto.response.ResGetCategoryDto

interface MasterCategoryService {
    fun getAllCategories(): List<ResGetCategoryDto>
    fun findCategoryById(categoryId: Int): ResGetCategoryDto

}