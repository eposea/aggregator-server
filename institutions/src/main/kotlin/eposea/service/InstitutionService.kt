package eposea.service

import eposea.domain.InsitutionDto
import eposea.domain.Institution
import eposea.repository.InsitutionRepository
import jakarta.inject.Singleton
import org.apache.commons.validator.routines.UrlValidator

interface InstitutionService {

    @Singleton
    class Base(private val institutionRepository: InsitutionRepository) : InstitutionService {

        private companion object {
            private val urlValidator: UrlValidator = UrlValidator()
            fun isValidUrl(url: String): Boolean =
                urlValidator.isValid(url)

        }

        fun findAll(): List<Institution> =
            institutionRepository.findAll()

        fun save(institutionDto: InsitutionDto): Institution? =
            if (isValidUrl(institutionDto.url)) {
                val institution = Institution(institutionDto.id, institutionDto.url)
                institutionRepository.save(institution)
            } else {
                throw IllegalArgumentException("url is invalid")
            }

    }
}
