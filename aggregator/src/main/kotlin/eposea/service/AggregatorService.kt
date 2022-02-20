package eposea.service

import eposea.client.InstitutionClient
import eposea.domain.InstitutionsDto
import jakarta.inject.Singleton

interface AggregatorService {

    fun getInstitutions(): InstitutionsDto

    @Singleton
    class Base(
        private val institutionClient: InstitutionClient
    ) : AggregatorService {

        override fun getInstitutions(): InstitutionsDto =
            institutionClient.getInstitutions()

    }

}
