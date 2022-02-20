package eposea.controller

import eposea.domain.CreateInstitutionRequest
import eposea.domain.InstitutionDto
import eposea.service.InstitutionService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import javax.validation.Valid

@Controller("/institutions", produces = [MediaType.APPLICATION_JSON])
open class InstitutionController(private val institutionService: InstitutionService) {

    @Get
    fun getInstitutions(): List<InstitutionDto> =
        institutionService.findAll()

    @Get("/{id}")
    fun getInstitution(@PathVariable id: String): InstitutionDto =
        institutionService.findById(id)

    @Post
    open fun addInstitution(@Valid createInstitutionRequest: CreateInstitutionRequest): InstitutionDto =
        institutionService.save(createInstitutionRequest)

}
