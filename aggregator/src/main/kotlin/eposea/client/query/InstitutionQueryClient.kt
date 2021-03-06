package eposea.client.query

import eposea.domain.CourseDataDto
import eposea.domain.InstitutionDataDto
import eposea.domain.InstitutionsDto
import eposea.domain.ItemDataDto
import eposea.exception.NoDataAvailableException
import eposea.mapper.CourseMapper
import eposea.mapper.InstitutionMapper
import eposea.mapper.ItemMapper
import eposea.service.InstitutionsService
import eposea.service.query.LocalServiceQueryGrpcClient
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton

interface InstitutionQueryClient {

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
    ) : InstitutionQueryClient {

        companion object {
            private const val INSTITUTION_NOT_FOUND = "institution not found"
        }

        override fun getInstitutions(): InstitutionsDto =
            InstitutionsDto(
                institutionsService.getInstitutions()
                    .map { institution ->
                        val institutionGrpc = LocalServiceQueryGrpcClient.Base(institution.url)
                            .use { it.getInstitution() }
                        institutionMapper.toInstitutionDto(institutionGrpc, institution.id)
                    }
            )

        override fun getInstitutionData(institutionId: String): InstitutionDataDto {
            val institutionUrl = institutionsService.getInstitution(institutionId)
                ?.url ?: throw NoDataAvailableException(INSTITUTION_NOT_FOUND)
            val institution = LocalServiceQueryGrpcClient.Base(institutionUrl)
                .use { it.getInstitution() }
            return institutionMapper.toInstitutionDataDto(institution)
        }

        override fun getCourseData(institutionId: String, courseId: String): CourseDataDto {
            val institutionUrl = institutionsService.getInstitution(institutionId)
                ?.url ?: throw IllegalArgumentException(INSTITUTION_NOT_FOUND)
            val course = LocalServiceQueryGrpcClient.Base(institutionUrl)
                .use { it.getCourse(courseId) }
            return courseMapper.toCourseDataDto(course)
        }

        override fun getItemData(institutionId: String, itemId: String): ItemDataDto {
            val institutionUrl = institutionsService.getInstitution(institutionId)
                ?.url ?: throw IllegalArgumentException(INSTITUTION_NOT_FOUND)
            val item = LocalServiceQueryGrpcClient.Base(institutionUrl)
                .use { it.getItem(itemId) }
            return itemMapper.toItemDataDto(item)
        }

    }

}
