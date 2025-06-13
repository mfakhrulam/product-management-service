package com.batch14.productmanagementservice.repository

import com.batch14.productmanagementservice.domain.entity.MasterProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MasterProductRepository: JpaRepository<MasterProductEntity, Int> {
    @Query("""
        SELECT U FROM MasterProductEntity U
        WHERE U.isDelete = false
        AND U.isActive = true
    """, nativeQuery = false)
    fun getAllProducts(): List<MasterProductEntity>

    @Query("""
        SELECT U FROM MasterProductEntity U
        WHERE U.isDelete = false
        AND U.isActive = true
        AND U.id = :productId
    """, nativeQuery = false)
    fun getProductById(
        @Param("productId") productId: Int
    ): MasterProductEntity
}