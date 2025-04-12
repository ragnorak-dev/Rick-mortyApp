package com.ragnorak.ui.errormanage

import androidx.annotation.StringRes
import com.ragnorak.api.errorhandler.OwnHttpException
import com.ragnorak.ui.R


fun Throwable.mapToUiError(retryAction: () -> Unit): UiError {
    return when (this) {
        is OwnHttpException -> when (this) {
            is OwnHttpException.BadRequest -> UiError.WithAction(
                R.string.error_bad_request,
                retryAction
            )

            is OwnHttpException.Forbidden -> UiError.Resource(R.string.error_forbidden)
            is OwnHttpException.NotFound -> UiError.Resource(R.string.error_not_found)
            is OwnHttpException.RequestTimeout -> UiError.WithAction(
                R.string.error_request_timeout,
                retryAction
            )

            is OwnHttpException.TooManyRequests -> UiError.WithAction(
                R.string.error_too_many_requests,
                retryAction
            )

            is OwnHttpException.InternalServerError -> UiError.WithAction(
                R.string.error_internal_server_error,
                retryAction
            )

            is OwnHttpException.BadGateway -> UiError.WithAction(
                R.string.error_bad_gateway,
                retryAction
            )

            is OwnHttpException.ServiceUnavailable -> UiError.WithAction(
                R.string.error_service_unavailable,
                retryAction
            )

            is OwnHttpException.GatewayTimeout -> UiError.WithAction(
                R.string.error_gateway_timeout,
                retryAction
            )

            is OwnHttpException.Unknown -> {
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