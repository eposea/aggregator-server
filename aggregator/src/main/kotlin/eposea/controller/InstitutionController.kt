package eposea.controller

import eposea.domain.InstitutionDataDto
import eposea.domain.InstitutionsDto
import eposea.service.query.AggregatorQueryService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable

@Controller("/institutions", produces = [MediaType.APPLICATION_JSON])
class InstitutionController(private val aggregatorQueryService: AggregatorQueryService) {

    @Get
    fun getInstitutions(): InstitutionsDto =
        aggregatorQueryService.getInstitutions()

    @Get("/{id}")
    fun getInstitution(@PathVariable id: String): InstitutionDataDto =
        aggregatorQueryService.getInstitution(id)

}
