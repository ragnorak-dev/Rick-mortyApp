package com.ragnorak.api.errorhandler

import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class HttpExceptionHandlerTest {

    private fun createHttpException(code: Int): HttpException {
        val response = Response.error<Any>(code, "".toResponseBody(null))
        return HttpException(response)
    }

    @Test
    fun `returns NotFound for 404`() {
        val exception = createHttpException(404)
        try {
            exception.httpExceptionHandler()
        } catch (e: OwnHttpException) {
            assertTrue(e is OwnHttpException.NotFound)
        }
    }

    @Test
    fun `returns InternalServerError for 500`() {
        val exception = createHttpException(500)
        try {
            exception.httpExceptionHandler()
        } catch (e: OwnHttpException) {
            assertTrue(e is OwnHttpException.InternalServerError)
        }
    }

    @Test
    fun `returns Unknown for unhandled code`() {
        val exception = createHttpException(999)
        try {
            exception.httpExceptionHandler()
        } catch (e: OwnHttpException) {
            assertTrue(e is OwnHttpException.Unknown)
        }
    }

    @Test
    fun `returns Unknown for non-HttpException`() {
        val exception = IllegalStateException("Something went wrong")
        val result = exception.httpExceptionHandler()
        assertTrue(result is OwnHttpException.Unknown)
    }
}