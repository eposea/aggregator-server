package eposea.controller

import eposea.domain.InstitutionDataDto
import eposea.domain.InstitutionsDto
import eposea.service.AggregatorService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable

@Controller("/institutions", produces = [MediaType.APPLICATION_JSON])
class InstitutionController(private val aggregatorService: AggregatorService) {

    @Get
    fun getInstitutions(): InstitutionsDto =
        aggregatorService.getInstitutions()

    @Get("/{id}")
    fun getInstitution(@PathVariable id: String): InstitutionDataDto =
        aggregatorService.getInstitution(id)

}
