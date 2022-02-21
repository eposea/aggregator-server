package eposea.config

import eposea.exception.NoDataAvailableException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Produces
@Singleton
class RuntimeExceptionHandler :
    ExceptionHandler<RuntimeException, HttpResponse<String>> {

    override fun handle(
        request: HttpRequest<*>?,
        exception: RuntimeException?
    ): HttpResponse<String> =
        defineHttpResponse(exception)

    private fun defineHttpResponse(exception: RuntimeException?): HttpResponse<String> =
        when (exception) {
            is NoDataAvailableException -> HttpResponse.notFound(exception.message)
            else -> HttpResponse.serverError(exception?.message)
        }

}
