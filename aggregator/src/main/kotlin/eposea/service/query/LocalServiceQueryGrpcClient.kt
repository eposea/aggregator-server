package eposea.service.query

import eposea.local.*
import eposea.service.AbstractLocalServiceGrpcClient

interface LocalServiceQueryGrpcClient {

    fun getInstitution(): Institution

    fun getCourse(id: String): Course

    fun getItem(id: String): Item

    class Base(targetUrl: String) : AbstractLocalServiceGrpcClient(targetUrl),
        LocalServiceQueryGrpcClient {

        override fun getInstitution(): Institution {
            val request = GetInstitutionRequest.newBuilder()
                .build()
            return institutionServiceClient.getInstitution(request)
        }

        override fun getCourse(id: String): Course {
            val request = GetCourseRequest.newBuilder()
                .setId(id)
                .build()
            return courseServiceClient.getCourse(request)
        }

        override fun getItem(id: String): Item {
            val request = GetItemRequest.newBuilder()
                .setId(id)
                .build()
            return itemServiceClient.getItem(request)
        }

    }

}
