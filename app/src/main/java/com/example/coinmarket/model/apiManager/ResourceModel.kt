package com.example.coinmarket.model.apiManager

sealed class ResourceModel<T>(
    val data : T? = null,
    val message: String? =null
){
    class Success<T>(data: T?):ResourceModel<T>(data)

    class Error<T>(message: String?):ResourceModel<T>(message=message)

    class Loading<T> : ResourceModel<T>()
}
