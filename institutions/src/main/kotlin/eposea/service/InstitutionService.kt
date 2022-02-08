package eposea.service

import eposea.domain.Institution
import eposea.domain.InstitutionDto
import eposea.repository.InstitutionRepository
import jakarta.inject.Singleton
import org.apache.commons.validator.routines.UrlValidator

interface InstitutionService {

    @Singleton
    class Base(private val institutionRepository: InstitutionRepository) : InstitutionService {

        private companion object {

            private val urlValidator: UrlValidator = UrlValidator()

            fun isValidUrl(url: String): Boolean =
                urlValidator.isValid(url)

        }

        fun findAll(): List<Institution> =
            institutionRepository.findAll()

        fun save(institutionDto: InstitutionDto): Institution? =
            if (isValidUrl(institutionDto.url)) {
                val institution = Institution(institutionDto.id, institutionDto.url)
                institutionRepository.save(institution)
            } else {
                throw IllegalArgumentException("url is invalid")
            }

    }
}
