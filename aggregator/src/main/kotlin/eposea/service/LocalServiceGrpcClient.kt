package eposea.service

import eposea.local.*
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.io.Closeable

interface LocalServiceGrpcClient {

    fun getInstitution(): Institution

    fun getCourse(id: String): Course

    fun getItem(id: String): Item

    class Base(targetUrl: String) : LocalServiceGrpcClient, Closeable {

        private var institutionServiceClient: InstitutionServiceGrpc.InstitutionServiceBlockingStub
        private var courseServiceClient: CourseServiceGrpc.CourseServiceBlockingStub
        private var itemServiceClient: ItemServiceGrpc.ItemServiceBlockingStub
        private var clientChannel: ManagedChannel

        init {
            clientChannel = ManagedChannelBuilder.forTarget(targetUrl)
                .usePlaintext()
                .build()
            institutionServiceClient = InstitutionServiceGrpc.newBlockingStub(clientChannel)
            courseServiceClient = CourseServiceGrpc.newBlockingStub(clientChannel)
            itemServiceClient = ItemServiceGrpc.newBlockingStub(clientChannel)
        }

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

        override fun close() {
            clientChannel.shutdownNow()
        }

    }

}
