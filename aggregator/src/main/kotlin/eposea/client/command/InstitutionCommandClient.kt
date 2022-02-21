package eposea.client.command

import eposea.domain.CourseDataDto
import eposea.domain.command.CreateCourseCommand
import eposea.exception.NoDataAvailableException
import eposea.mapper.CourseMapper
import eposea.service.InstitutionsService
import eposea.service.command.LocalServiceCommandGrpcClient
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton

interface InstitutionCommandClient {

    fun addCourse(institutionId: String, createCourseCommand: CreateCourseCommand): CourseDataDto

    @Singleton
    @Requires(env = ["prod"])
    class Base(
        private val institutionsService: InstitutionsService,
        private val courseMapper: CourseMapper,
    ) : InstitutionCommandClient {

        companion object {
            private const val INSTITUTION_NOT_FOUND = "institution not found"
        }

        override fun addCourse(
            institutionId: String,
            createCourseCommand: CreateCourseCommand
        ): CourseDataDto {
            val institutionUrl = institutionsService.getInstitutionUrl(institutionId)
                ?: throw NoDataAvailableException(INSTITUTION_NOT_FOUND)
            val course = LocalServiceCommandGrpcClient.Base(institutionUrl)
                .use { it.addCourse(createCourseCommand) }
            return courseMapper.toCourseDataDto(course)
        }

    }

}
