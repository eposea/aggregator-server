package eposea.client.stub.query

import eposea.client.query.InstitutionQueryClient
import eposea.client.stub.StubStorage
import eposea.domain.CourseDataDto
import eposea.domain.InstitutionDataDto
import eposea.domain.InstitutionsDto
import eposea.domain.ItemDataDto
import eposea.exception.NoDataAvailableException
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton
import java.util.stream.Collectors

@Singleton
@Requires(env = ["dev"])
class StubInstitutionQueryClient(private val stubStorage: StubStorage) : InstitutionQueryClient {

    companion object {
        const val INSTITUTION_NOT_FOUND = "institution not found"
        const val COURSE_NOT_FOUND = "course not found"
        const val ITEM_NOT_FOUND = "item not found"
    }

    override fun getInstitutions(): InstitutionsDto = InstitutionsDto(
        stubStorage.institutionList
    )

    override fun getInstitutionData(institutionId: String): InstitutionDataDto =
        stubStorage.institutionDataMap[institutionId]
            ?: throw NoDataAvailableException(INSTITUTION_NOT_FOUND)

    override fun getCourseData(institutionId: String, courseId: String): CourseDataDto {
        val institution =
            stubStorage.institutionDataMap[institutionId]
                ?: throw NoDataAvailableException(INSTITUTION_NOT_FOUND)

        val contains = institution.courses.stream()
            .map { it.id }
            .anyMatch { it == courseId }

        if (contains) {
            return stubStorage.courseDataMap[courseId]
                ?: throw NoDataAvailableException(COURSE_NOT_FOUND)
        } else {
            throw NoDataAvailableException(COURSE_NOT_FOUND)
        }
    }

    override fun getItemData(institutionId: String, itemId: String): ItemDataDto {
        val institution =
            stubStorage.institutionDataMap[institutionId]
                ?: throw NoDataAvailableException(INSTITUTION_NOT_FOUND)

        val coursesId = institution.courses.stream()
            .map { it.id }
        val items = coursesId.map { stubStorage.courseDataMap[it] }
            .filter { it != null }
            .map { it!!.sections }
            .flatMap { it.stream() }
            .map { it.items }
            .flatMap { it.stream() }
            .collect(Collectors.toSet())
        institution.sections.stream()
            .map { it.items }
            .flatMap { it.stream() }
            .collect(Collectors.toCollection { items })

        val contains = items
            .map { it.id }
            .contains(itemId)

        if (contains) {
            return stubStorage.itemDataMap[itemId]
                ?: throw NoDataAvailableException(ITEM_NOT_FOUND)
        } else {
            throw NoDataAvailableException(ITEM_NOT_FOUND)
        }
    }

}
