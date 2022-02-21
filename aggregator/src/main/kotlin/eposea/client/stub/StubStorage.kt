package eposea.client.stub

import com.github.javafaker.Faker
import eposea.domain.*
import io.micronaut.context.annotation.Requires
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import jakarta.inject.Singleton

@Singleton
@Requires(env = ["dev"])
class StubStorage {

    val institutionList: MutableList<InstitutionDto> = mutableListOf()
    val institutionDataMap: MutableMap<String, InstitutionDataDto> = mutableMapOf()
    val courseDataMap: MutableMap<String, CourseDataDto> = mutableMapOf()
    val itemDataMap: MutableMap<String, ItemDataDto> = mutableMapOf()

    companion object {
        private val faker: Faker = Faker.instance()

        fun generateItemId(): String =
            faker.idNumber().ssnValid()

        fun generateCourseId(): String =
            faker.aquaTeenHungerForce().character()
    }

    @EventListener
    fun onStartup(event: StartupEvent) {
        generateInstitutions()
    }

    private fun generateInstitutions(): List<InstitutionDto> {
        fun generateInstitution(): InstitutionDto {
            val institutionId = faker.bothify("??????_????????_#####")
            val photo =
                "https://picsum.photos/${
                    faker.random().nextInt(200, 1000)
                }/${
                    faker.random().nextInt(200, 1000)
                }"
            val institutionDataDto = InstitutionDataDto(
                photo,
                faker.cat().breed(),
                faker.lorem().paragraph(15),
                generateSections(),
                generateCourses()
            )
            institutionDataMap[institutionId] = institutionDataDto
            val institutionDto = InstitutionDto(
                institutionId, institutionDataDto.title,
                institutionDataDto.imageUrl
            )
            institutionList += institutionDto
            return institutionDto
        }

        return (1..faker.random().nextInt(4, 10))
            .map { generateInstitution() }
    }

    private fun generateCourses(): List<CourseDto> {
        fun generateCourse(): CourseDto {
            val courseId = generateCourseId()
            val courseDataDto = CourseDataDto(
                faker.ancient().god(),
                faker.lorem().paragraph(4),
                generateSections()
            )
            courseDataMap[courseId] = courseDataDto
            return CourseDto(
                courseId, courseDataDto.title
            )
        }

        return (1..faker.random().nextInt(4, 10))
            .map { generateCourse() }
    }

    private fun generateSections(): List<SectionDto> {
        fun generateSection() = SectionDto(
            faker.lordOfTheRings().character(),
            generateItems()
        )

        return (1..faker.random().nextInt(4, 10))
            .map { generateSection() }
    }

    private fun generateItems(): List<ItemDto> {
        fun generateItem(): ItemDto {
            val itemId = generateItemId()
            val itemDataDto = ItemDataDto(
                faker.dragonBall().character(),
                faker.lorem().paragraph(3)
            )
            itemDataMap[itemId] = itemDataDto
            return ItemDto(
                itemId, itemDataDto.title
            )
        }

        return (1..faker.random().nextInt(4, 10))
            .map { generateItem() }
    }

}
