package eposea.client.stub.command

import eposea.client.command.InstitutionCommandClient
import eposea.client.stub.StubStorage
import eposea.domain.*
import eposea.domain.command.CreateCourseCommand
import eposea.exception.NoDataAvailableException
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton

@Singleton
@Requires(env = ["dev"])
class StubInstitutionCommandClient(private val stubStorage: StubStorage) :
    InstitutionCommandClient {

    companion object {
        const val INSTITUTION_NOT_FOUND = "institution not found"
    }

    override fun addCourse(
        institutionId: String,
        createCourseCommand: CreateCourseCommand
    ): CourseDataDto {
        val institution = stubStorage.institutionDataMap[institutionId]
            ?: throw NoDataAvailableException(INSTITUTION_NOT_FOUND)
        val (imageUrl, title, description, sections, courses) = institution
        val saveCourse = saveCourse(createCourseCommand)
        val newInstitution = InstitutionDataDto(
            imageUrl, title, description, sections, courses + saveCourse.first
        )
        stubStorage.institutionDataMap[institutionId] = newInstitution
        return saveCourse.second
    }

    private fun saveCourse(createCourseCommand: CreateCourseCommand): Pair<CourseDto, CourseDataDto> {
        val sections = createCourseCommand.sections
            .map {
                SectionDto(
                    it.title,
                    saveItems(it.items)
                )
            }
        val courseId = stubStorage.generateCourseId()
        val courseDataDto = CourseDataDto(
            createCourseCommand.title,
            createCourseCommand.description,
            sections
        )
        stubStorage.courseDataMap[courseId] = courseDataDto
        val courseDto = CourseDto(
            courseId,
            courseDataDto.title
        )
        return courseDto to courseDataDto
    }

    private fun saveItems(items: List<CreateCourseCommand.Section.Item>): List<ItemDto> {
        fun saveItem(itemDataDto: ItemDataDto): ItemDto {
            val itemId = stubStorage.generateItemId()
            stubStorage.itemDataMap[itemId] = itemDataDto
            return ItemDto(
                itemId,
                itemDataDto.title
            )
        }

        return items.map { ItemDataDto(it.title, it.description) }
            .map { saveItem(it) }
    }

}
