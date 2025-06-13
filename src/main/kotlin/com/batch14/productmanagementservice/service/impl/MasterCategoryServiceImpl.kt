package com.batch14.productmanagementservice.service.impl

import com.batch14.productmanagementservice.domain.dto.response.ResGetCategoryDto
import com.batch14.productmanagementservice.domain.dto.response.ResGetProductDto
import com.batch14.productmanagementservice.repository.MasterCategoryRepository
import com.batch14.productmanagementservice.service.MasterCategoryService
import org.springframework.stereotype.Service

@Service
class MasterCategoryServiceImpl(
    private val masterCategoryRepository: MasterCategoryRepository
): MasterCategoryService {
    override fun getAllCategories(): List<ResGetCategoryDto> {
        val rawCategories = masterCategoryRepository.findAll()
        val result = mutableListOf<ResGetCategoryDto>()
        rawCategories.forEach { category ->
            result.add(
                ResGetCategoryDto(
                    id = category.id,
                    name = category.name
                )
            )
        }
        return result
    }

    override fun findCategoryById(categoryId: Int): ResGetCategoryDto {
        val rawData = masterCategoryRepository.getCategoryById(categoryId)
        val result = ResGetCategoryDto(
            id = rawData.id,
            name = rawData.name,
        )
        return result
    }

}