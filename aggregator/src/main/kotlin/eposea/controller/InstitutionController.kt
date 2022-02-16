package eposea.controller

import eposea.domain.InstitutionsResponseDto
import eposea.service.AggregatorService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/institutions", produces = [MediaType.APPLICATION_JSON])
class InstitutionController(private val aggregatorService: AggregatorService) {

    @Get
    fun getInstitutions(): InstitutionsResponseDto =
        aggregatorService.getInstitutions()

}
