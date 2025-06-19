package com.batch14.productmanagementservice.rest

import com.batch14.productmanagementservice.domain.dto.response.BaseResponse
import com.batch14.productmanagementservice.domain.dto.response.ResGetUsersDto
import feign.Headers
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "user-service",
    path = "/user-service"
)
interface UserManagementClient {
    @GetMapping("/v1/users/{id}")
    fun getUserById(
        @PathVariable(name = "id") idUser: Int
    ): ResponseEntity<BaseResponse<ResGetUsersDto>>

    @GetMapping("/v1/data-source/users/by-ids")
    fun getUsersByIds(
        @RequestParam(name = "ids", required = true) userIds: List<Int>
    ): ResponseEntity<BaseResponse<List<ResGetUsersDto>>>
}