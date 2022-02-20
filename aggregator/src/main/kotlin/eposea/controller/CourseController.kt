package eposea.controller

import eposea.service.AggregatorService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable

@Controller("/institutions/{institutionId}/courses", produces = [MediaType.APPLICATION_JSON])
class CourseController(private val aggregatorService: AggregatorService) {

    @Get("/{courseId}")
    fun getCourse(@PathVariable institutionId: String, @PathVariable courseId: String) =
        aggregatorService.getCourse(institutionId, courseId)

}
