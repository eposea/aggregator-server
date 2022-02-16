package eposea.service

import eposea.domain.InstitutionsResponseDto
import jakarta.inject.Singleton

interface AggregatorService {

    fun getInstitutions(): InstitutionsResponseDto

    @Singleton
    class Base(
        private val institutionClientService: InstitutionClientService
    ) : AggregatorService {

        override fun getInstitutions(): InstitutionsResponseDto =
            institutionClientService.getInstitutions()

    }

}
