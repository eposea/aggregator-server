package eposea.service

import eposea.domain.CreateInstitutionRequest
import eposea.domain.InstitutionDto
import eposea.exception.InvalidUserInputException
import eposea.exception.NoDataAvailableException
import eposea.mapper.InstitutionMapper
import eposea.repository.InstitutionRepository
import jakarta.inject.Singleton
import org.apache.commons.validator.routines.UrlValidator
import javax.transaction.Transactional

interface InstitutionService {

    fun findAll(): List<InstitutionDto>

    fun findById(id: String): InstitutionDto

    fun save(createInstitutionRequest: CreateInstitutionRequest): InstitutionDto

    @Singleton
    @Transactional
    class Base(
        private val institutionRepository: InstitutionRepository,
        private val institutionMapper: InstitutionMapper
    ) : InstitutionService {

        private companion object {

            const val INSTITUTION_NOT_FOUND = "institution not found"
            const val INVALID_URL = "invalid url"

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
                .orElseThrow { throw NoDataAvailableException(INSTITUTION_NOT_FOUND) }

        override fun save(createInstitutionRequest: CreateInstitutionRequest): InstitutionDto =
            if (isValidUrl(createInstitutionRequest.url)) {
                val institution =
                    institutionMapper.fromCreateInstitutionRequest(createInstitutionRequest)
                institutionRepository.save(institution)
                    .let { institutionMapper.toInstitutionDto(it) }
            } else {
                throw InvalidUserInputException(INVALID_URL)
            }

    }
}
