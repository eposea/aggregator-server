package eposea.mapper

import eposea.domain.CreateInstitutionRequest
import eposea.domain.Institution
import eposea.domain.InstitutionDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named
import java.time.LocalDateTime
import java.time.ZoneOffset

@Mapper(componentModel = "jsr330")
interface InstitutionMapper {

    companion object {

        @JvmStatic
        @Named("titleToId")
        fun generateId(title: String): String =
            title.trim()
                .split(" ")
                .joinToString(
                    separator = "_",
                    postfix = "_${LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)}"
                ) { it.uppercase() }

    }

    fun toInstitutionDto(source: Institution): InstitutionDto

    @Mapping(source = "title", target = "id", qualifiedByName = ["titleToId"])
    fun fromCreateInstitutionRequest(source: CreateInstitutionRequest): Institution

}
