package eposea.mapper

import eposea.domain.*
import eposea.local.Course
import eposea.local.Institution
import eposea.local.Item
import org.mapstruct.Mapper

@Mapper(componentModel = "jsr330")
interface InstitutionMapper {

    fun toInstitutionDto(source: Institution): InstitutionDto

    fun toInstitutionDataDto(source: Institution): InstitutionDataDto

}

@Mapper(componentModel = "jsr330")
interface CourseMapper {

    fun toCourseDto(source: Course): CourseDto

    fun toCourseDataDto(source: Course): CourseDataDto

}

@Mapper(componentModel = "jsr330")
interface ItemMapper {

    fun toItemDto(source: Item): ItemDto

    fun toItemDataDto(source: Item): ItemDataDto

}
