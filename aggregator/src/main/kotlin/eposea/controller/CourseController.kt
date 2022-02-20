package eposea.controller

import eposea.domain.command.CreateCourseCommand
import eposea.service.command.AggregatorCommandService
import eposea.service.query.AggregatorQueryService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import javax.validation.Valid

@Controller("/institutions/{institutionId}/courses", produces = [MediaType.APPLICATION_JSON])
open class CourseController(
    private val aggregatorQueryService: AggregatorQueryService,
    private val aggregatorCommandService: AggregatorCommandService
) {

    @Get("/{courseId}")
    fun getCourse(@PathVariable institutionId: String, @PathVariable courseId: String) =
        aggregatorQueryService.getCourse(institutionId, courseId)

    @Post
    open fun addCourse(
        @PathVariable institutionId: String,
        @Body @Valid createCourseCommand: CreateCourseCommand
    ) =
        aggregatorCommandService.addCourse(institutionId, createCourseCommand)


}
