package eposea.client

import eposea.domain.CourseDataDto
import eposea.domain.InstitutionDataDto
import eposea.domain.InstitutionsDto
import eposea.domain.ItemDataDto
import eposea.exception.NoDataAvailableException
import eposea.mapper.CourseMapper
import eposea.mapper.InstitutionMapper
import eposea.mapper.ItemMapper
import eposea.service.InstitutionsService
import eposea.service.LocalServiceGrpcClient
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton

interface InstitutionClient {

    fun getInstitutions(): InstitutionsDto

    fun getInstitutionData(institutionId: String): InstitutionDataDto

    fun getCourseData(institutionId: String, courseId: String): CourseDataDto

    fun getItemData(institutionId: String, itemId: String): ItemDataDto

    @Singleton
    @Requires(env = ["prod"])
    class Base(
        private val institutionsService: InstitutionsService,
        private val institutionMapper: InstitutionMapper,
        private val courseMapper: CourseMapper,
        private val itemMapper: ItemMapper
    ) : InstitutionClient {

        companion object {
            private const val INSTITUTION_NOT_FOUND = "institution not found"
        }

        override fun getInstitutions(): InstitutionsDto =
            InstitutionsDto(
                institutionsService.getInstitutionsUrl()
                    .map { url ->
                        LocalServiceGrpcClient.Base(url)
                            .use { it.getInstitution() }
                    }
                    .map { institutionMapper.toInstitutionDto(it) }
            )

        override fun getInstitutionData(institutionId: String): InstitutionDataDto {
            val institutionUrl = institutionsService.getInstitutionUrl(institutionId)
                ?: throw NoDataAvailableException(INSTITUTION_NOT_FOUND)
            val institution = LocalServiceGrpcClient.Base(institutionUrl)
                .use { it.getInstitution() }
            return institutionMapper.toInstitutionDataDto(institution)
        }

        override fun getCourseData(institutionId: String, courseId: String): CourseDataDto {
            val institutionUrl = institutionsService.getInstitutionUrl(institutionId)
                ?: throw IllegalArgumentException(INSTITUTION_NOT_FOUND)
            val course = LocalServiceGrpcClient.Base(institutionUrl)
                .use { it.getCourse(courseId) }
            return courseMapper.toCourseDataDto(course)
        }

        override fun getItemData(institutionId: String, itemId: String): ItemDataDto {
            val institutionUrl = institutionsService.getInstitutionUrl(institutionId)
                ?: throw IllegalArgumentException(INSTITUTION_NOT_FOUND)
            val item = LocalServiceGrpcClient.Base(institutionUrl)
                .use { it.getItem(itemId) }
            return itemMapper.toItemDataDto(item)
        }

    }

}
