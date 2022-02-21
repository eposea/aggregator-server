package eposea.service

import eposea.client.InstitutionsClient
import jakarta.inject.Singleton

interface InstitutionsService {

    fun getInstitutionsUrl(): List<String>

    fun getInstitutionUrl(id: String): String?

    @Singleton
    class Base(private val institutionsClient: InstitutionsClient) : InstitutionsService {

        override fun getInstitutionsUrl(): List<String> =
            institutionsClient.getInstitutions()
                .map { it.url }

        override fun getInstitutionUrl(id: String): String? =
            institutionsClient.getInstitution(id)?.url

    }

}
