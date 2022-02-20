package eposea.service

import eposea.client.InstitutionClient
import eposea.domain.CourseDataDto
import eposea.domain.InstitutionDataDto
import eposea.domain.InstitutionsDto
import eposea.domain.ItemDataDto
import jakarta.inject.Singleton

interface AggregatorService {

    fun getInstitutions(): InstitutionsDto

    fun getInstitution(id: String): InstitutionDataDto

    fun getCourse(institutionId: String, courseId: String): CourseDataDto

    fun getItem(institutionId: String, itemId: String): ItemDataDto

    @Singleton
    class Base(
        private val institutionClient: InstitutionClient
    ) : AggregatorService {

        override fun getInstitutions(): InstitutionsDto =
            institutionClient.getInstitutions()

        override fun getInstitution(id: String): InstitutionDataDto =
            institutionClient.getInstitutionData(id)

        override fun getCourse(institutionId: String, courseId: String): CourseDataDto =
            institutionClient.getCourseData(institutionId, courseId)

        override fun getItem(institutionId: String, itemId: String): ItemDataDto =
            institutionClient.getItemData(institutionId, itemId)

    }

}
