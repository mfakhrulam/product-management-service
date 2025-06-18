package com.batch14.productmanagementservice.exception

class CustomException (
    val exceptionMessage: String,
    val statusCode: Int,
    val data: Any? = null
): RuntimeException()
