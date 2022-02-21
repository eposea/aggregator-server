package eposea.service

import eposea.local.CourseServiceGrpc
import eposea.local.InstitutionServiceGrpc
import eposea.local.ItemServiceGrpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.io.Closeable

abstract class AbstractLocalServiceGrpcClient(targetUrl: String) : Closeable {

    var institutionServiceClient: InstitutionServiceGrpc.InstitutionServiceBlockingStub
    var courseServiceClient: CourseServiceGrpc.CourseServiceBlockingStub
    var itemServiceClient: ItemServiceGrpc.ItemServiceBlockingStub
    private var clientChannel: ManagedChannel

    init {
        clientChannel = ManagedChannelBuilder.forTarget(targetUrl)
            .usePlaintext()
            .build()
        institutionServiceClient = InstitutionServiceGrpc.newBlockingStub(clientChannel)
        courseServiceClient = CourseServiceGrpc.newBlockingStub(clientChannel)
        itemServiceClient = ItemServiceGrpc.newBlockingStub(clientChannel)
    }

    override fun close() {
        clientChannel.shutdownNow()
    }

}
