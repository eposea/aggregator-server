package eposea.mapper

import eposea.domain.*
import eposea.local.Course
import eposea.local.Institution
import eposea.local.Item
import org.mapstruct.Mapper

@Mapper(componentModel = "jsr330")
interface InstitutionMapper {

    fun toInstitutionDto(institution: Institution): InstitutionDto

    fun toInstitutionDataDto(institution: Institution): InstitutionDataDto

}

@Mapper(componentModel = "jsr330")
interface CourseMapper {

    fun toCourseDto(course: Course): CourseDto

    fun toCourseDataDto(course: Course): CourseDataDto

}

@Mapper(componentModel = "jsr330")
interface ItemMapper {

    fun toItemDto(item: Item): ItemDto

    fun toItemDataDto(item: Item): ItemDataDto

}
