package eposea.client.stub

import eposea.client.InstitutionsClient
import eposea.domain.client.InstitutionClientDto
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton

@Singleton
@Requires(env = ["dev"])
class StubInstitutionsClient(private val stubStorage: StubStorage) : InstitutionsClient {

    override fun getInstitutions(): List<InstitutionClientDto> =
        stubStorage.institutionList
            .map { InstitutionClientDto(it.id, it.title, it.title) }

    override fun getInstitution(id: String): InstitutionClientDto? =
        stubStorage.institutionList
            .filter { it.id == id }
            .map { InstitutionClientDto(it.id, it.title, it.title) }
            .firstOrNull()

}
