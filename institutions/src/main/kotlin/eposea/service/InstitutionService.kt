package eposea.service

import eposea.domain.CreateInstitutionRequest
import eposea.domain.InstitutionDto
import eposea.mapper.InstitutionMapper
import eposea.repository.InstitutionRepository
import jakarta.inject.Singleton
import org.apache.commons.validator.routines.UrlValidator

interface InstitutionService {

    fun findAll(): List<InstitutionDto>

    fun findById(id: String): InstitutionDto

    fun save(createInstitutionRequest: CreateInstitutionRequest): InstitutionDto

    @Singleton
    class Base(
        private val institutionRepository: InstitutionRepository,
        private val institutionMapper: InstitutionMapper
    ) : InstitutionService {

        private companion object {

            private val urlValidator: UrlValidator = UrlValidator()

            fun isValidUrl(url: String): Boolean =
                urlValidator.isValid(url)

        }

        override fun findAll(): List<InstitutionDto> =
            institutionRepository.findAll()
                .map { institutionMapper.toInstitutionDto(it) }

        override fun findById(id: String): InstitutionDto =
            institutionRepository.findById(id)
                .map { institutionMapper.toInstitutionDto(it) }
                .orElseThrow { throw IllegalArgumentException("aboba") }

        override fun save(createInstitutionRequest: CreateInstitutionRequest): InstitutionDto =
            if (isValidUrl(createInstitutionRequest.url)) {
                val institution =
                    institutionMapper.fromCreateInstitutionRequest(createInstitutionRequest)
                institutionRepository.save(institution)
                    .let { institutionMapper.toInstitutionDto(it) }
            } else {
                throw IllegalArgumentException("aboba")
            }

    }
}
