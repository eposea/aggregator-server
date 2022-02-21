package eposea.service.command

import eposea.client.command.InstitutionCommandClient
import eposea.domain.CourseDataDto
import eposea.domain.command.CreateCourseCommand
import jakarta.inject.Singleton

interface AggregatorCommandService {

    fun addCourse(institutionId: String, createCourseCommand: CreateCourseCommand): CourseDataDto

    @Singleton
    class Base(
        private val institutionCommandClient: InstitutionCommandClient
    ) : AggregatorCommandService {

        override fun addCourse(institutionId: String, createCourseCommand: CreateCourseCommand) =
            institutionCommandClient.addCourse(institutionId, createCourseCommand)

    }

}
