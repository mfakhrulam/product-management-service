package com.batch14.productmanagementservice.repository

import com.batch14.productmanagementservice.domain.entity.MasterCategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MasterCategoryRepository: JpaRepository<MasterCategoryEntity, Int> {
    @Query("""
        SELECT U FROM MasterCategoryEntity U
        WHERE U.isDelete = false
        AND U.isActive = true
    """, nativeQuery = false)
    fun getAllCategory(): List<MasterCategoryEntity>

    @Query("""
        SELECT U FROM MasterCategoryEntity U
        WHERE U.isDelete = false
        AND U.isActive = true
        AND U.id = :categoryId
    """, nativeQuery = false)
    fun getCategoryById(
        @Param("categoryId") categoryId: Int
    ): MasterCategoryEntity
}