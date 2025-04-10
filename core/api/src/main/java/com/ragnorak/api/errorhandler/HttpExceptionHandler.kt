package com.ragnorak.api.errorhandler

import retrofit2.HttpException

fun Exception.httpExceptionHandler(): OwnHttpException {
    if (this is HttpException) {
        when (code()) {
            400 -> throw OwnHttpException.BadRequest
            403 -> throw OwnHttpException.Forbidden
            404 -> throw OwnHttpException.NotFound
            408 -> throw OwnHttpException.RequestTimeout
            429 -> throw OwnHttpException.TooManyRequests
            500 -> throw OwnHttpException.InternalServerError
            502 -> throw OwnHttpException.BadGateway
            503 -> throw OwnHttpException.ServiceUnavailable
            504 -> throw OwnHttpException.GatewayTimeout
            else -> throw OwnHttpException.Unknown(code(), message())
        }
    }
    return OwnHttpException.Unknown(0, message)
}