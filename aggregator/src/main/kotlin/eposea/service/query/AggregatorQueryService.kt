package eposea.service.query

import eposea.client.query.InstitutionQueryClient
import eposea.domain.CourseDataDto
import eposea.domain.InstitutionDataDto
import eposea.domain.InstitutionsDto
import eposea.domain.ItemDataDto
import jakarta.inject.Singleton

interface AggregatorQueryService {

    fun getInstitutions(): InstitutionsDto

    fun getInstitution(id: String): InstitutionDataDto

    fun getCourse(institutionId: String, courseId: String): CourseDataDto

    fun getItem(institutionId: String, itemId: String): ItemDataDto

    @Singleton
    class Base(
        private val institutionQueryClient: InstitutionQueryClient
    ) : AggregatorQueryService {

        override fun getInstitutions(): InstitutionsDto =
            institutionQueryClient.getInstitutions()

        override fun getInstitution(id: String): InstitutionDataDto =
            institutionQueryClient.getInstitutionData(id)

        override fun getCourse(institutionId: String, courseId: String): CourseDataDto =
            institutionQueryClient.getCourseData(institutionId, courseId)

        override fun getItem(institutionId: String, itemId: String): ItemDataDto =
            institutionQueryClient.getItemData(institutionId, itemId)

    }

}
