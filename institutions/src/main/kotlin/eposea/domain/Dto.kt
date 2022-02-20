package eposea.domain

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class CreateInstitutionRequest(@NotBlank val title: String, @NotBlank val url: String)

data class InstitutionDto(val id: String, val title: String, val url: String)
