package com.ragnorak.network.api.errorhandler

sealed class OwnHttpException : Throwable() {
    data object BadRequest : OwnHttpException() {
        override val message: String = "Bad request"
    }

    data object Forbidden : OwnHttpException() {
        override val message: String = "Access forbidden"
    }

    data object NotFound : OwnHttpException() {
        override val message: String = "Resource not found"
    }

    data object RequestTimeout : OwnHttpException() {
        override val message: String = "Request timed out"
    }

    data object TooManyRequests : OwnHttpException() {
        override val message: String = "Too many requests"
    }

    data object InternalServerError : OwnHttpException() {
        override val message: String = "Internal server error"
    }

    data object BadGateway : OwnHttpException() {
        override val message: String = "Bad gateway"
    }

    data object ServiceUnavailable : OwnHttpException() {
        override val message: String = "Service unavailable"
    }

    data object GatewayTimeout : OwnHttpException() {
        override val message: String = "Gateway timeout"
    }

    data class Unknown(val code: Int, override val message: String?) : OwnHttpException()
}