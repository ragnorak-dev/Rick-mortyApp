package com.ragnorak.ui.errormanage

import androidx.annotation.StringRes
import com.ragnorak.network.api.errorhandler.OwnHttpException
import com.ragnorak.ui.R

fun Throwable.mapToUiError(retryAction: () -> Unit): UiError {
    return when (this) {
        is com.ragnorak.network.api.errorhandler.OwnHttpException -> when (this) {
            is com.ragnorak.network.api.errorhandler.OwnHttpException.BadRequest -> UiError.WithAction(
                R.string.error_bad_request,
                retryAction
            )

            is com.ragnorak.network.api.errorhandler.OwnHttpException.Forbidden -> UiError.Resource(R.string.error_forbidden)
            is com.ragnorak.network.api.errorhandler.OwnHttpException.NotFound -> UiError.Resource(R.string.error_not_found)
            is com.ragnorak.network.api.errorhandler.OwnHttpException.RequestTimeout -> UiError.WithAction(
                R.string.error_request_timeout,
                retryAction
            )

            is com.ragnorak.network.api.errorhandler.OwnHttpException.TooManyRequests -> UiError.WithAction(
                R.string.error_too_many_requests,
                retryAction
            )

            is com.ragnorak.network.api.errorhandler.OwnHttpException.InternalServerError -> UiError.WithAction(
                R.string.error_internal_server_error,
                retryAction
            )

            is com.ragnorak.network.api.errorhandler.OwnHttpException.BadGateway -> UiError.WithAction(
                R.string.error_bad_gateway,
                retryAction
            )

            is com.ragnorak.network.api.errorhandler.OwnHttpException.ServiceUnavailable -> UiError.WithAction(
                R.string.error_service_unavailable,
                retryAction
            )

            is com.ragnorak.network.api.errorhandler.OwnHttpException.GatewayTimeout -> UiError.WithAction(
                R.string.error_gateway_timeout,
                retryAction
            )

            is com.ragnorak.network.api.errorhandler.OwnHttpException.Unknown -> {
                UiError.WithAction(R.string.error_unknown, retryAction)
            }
        }

        else -> UiError.Resource(R.string.error_unknown)
    }
}

sealed class UiError {
    data class Resource(@StringRes val resId: Int) : UiError()
    data class WithAction(
        @StringRes val resId: Int,
        val onRetry: () -> Unit
    ) : UiError()
}