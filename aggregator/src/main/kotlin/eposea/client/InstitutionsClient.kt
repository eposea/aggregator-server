package eposea.client

import eposea.domain.client.InstitutionClientDto
import io.micronaut.context.annotation.Requires
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client

@Requires(env = ["prod"])
@Client("\${app.institutions-server-url}")
interface InstitutionsClient {

    @Get("/institutions")
    fun getInstitutions(): List<InstitutionClientDto>

    @Get("/institution/{id}")
    fun getInstitution(@PathVariable id: String): InstitutionClientDto?

}
