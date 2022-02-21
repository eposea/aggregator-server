package eposea.mapper

import eposea.domain.*
import eposea.local.Course
import eposea.local.Institution
import eposea.local.Item
import eposea.local.Section
import jakarta.inject.Inject
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "jsr330")
abstract class InstitutionMapper {

    @Inject
    lateinit var courseMapper: CourseMapper

    @Inject
    lateinit var sectionMapper: SectionMapper

    @Mapping(source = "id", target = "id")
    abstract fun toInstitutionDto(source: Institution, id: String): InstitutionDto

    @Mapping(
        target = "courses",
        expression = "java(courseMapper.toCourseDto(source.getCoursesList()))"
    )
    @Mapping(
        target = "sections",
        expression = "java(sectionMapper.toSectionDto(source.getSectionsList()))"
    )
    abstract fun toInstitutionDataDto(source: Institution): InstitutionDataDto

}

@Mapper(componentModel = "jsr330")
abstract class CourseMapper {

    @Inject
    lateinit var sectionMapper: SectionMapper

    abstract fun toCourseDto(source: Course): CourseDto

    abstract fun toCourseDto(source: List<Course>): List<CourseDto>

    @Mapping(
        target = "sections",
        expression = "java(sectionMapper.toSectionDto(source.getSectionsList()))"
    )
    abstract fun toCourseDataDto(source: Course): CourseDataDto

}

@Mapper(componentModel = "jsr330")
abstract class SectionMapper {

    @Inject
    lateinit var itemMapper: ItemMapper

    @Mapping(
        target = "items",
        expression = "java(itemMapper.toItemDto(source.getItemsList()))"
    )
    abstract fun toSectionDto(source: Section): SectionDto

    abstract fun toSectionDto(source: List<Section>): List<SectionDto>

}

@Mapper(componentModel = "jsr330")
interface ItemMapper {

    fun toItemDto(source: Item): ItemDto

    fun toItemDto(source: List<Item>): List<ItemDto>

    fun toItemDataDto(source: Item): ItemDataDto

}
