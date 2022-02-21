package eposea.service

import eposea.client.InstitutionsClient
import eposea.domain.client.InstitutionClientDto
import jakarta.inject.Singleton

interface InstitutionsService {

    fun getInstitutions(): List<InstitutionClientDto>

    fun getInstitution(id: String): InstitutionClientDto?

    @Singleton
    class Base(private val institutionsClient: InstitutionsClient) : InstitutionsService {

        override fun getInstitutions(): List<InstitutionClientDto> =
            institutionsClient.getInstitutions()

        override fun getInstitution(id: String): InstitutionClientDto? =
            institutionsClient.getInstitution(id)

    }

}
