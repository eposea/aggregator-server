package eposea.service

import eposea.domain.InstitutionDto
import eposea.domain.InstitutionsResponse
import io.micronaut.http.client.HttpClient
import jakarta.inject.Singleton
import java.net.ConnectException

interface AggregatorService {

    fun getInstitutions(): InstitutionsResponse

    @Singleton
    class Base(
        private val httpClient: HttpClient,
        private val institutionService: InstitutionService
    ) : AggregatorService {

        override fun getInstitutions(): InstitutionsResponse {
            val institutionsUrl = institutionService.getInstitutionsUrl()
            val institutionDtos = institutionsUrl.map { getInstitution(it) }
                .toList()

            return InstitutionsResponse(institutionDtos)
        }

        private fun getInstitution(url: String): InstitutionDto =
            httpClient.toBlocking()
                .exchange(url, InstitutionDto::class.java)
                .body() ?: throw ConnectException("something wrong")

    }

}
