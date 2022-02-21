package eposea.service.command

import eposea.domain.command.CreateCourseCommand
import eposea.local.Course
import eposea.local.CreateCourseRequest
import eposea.local.Item
import eposea.local.Section
import eposea.service.AbstractLocalServiceGrpcClient

interface LocalServiceCommandGrpcClient {

    fun addCourse(createCourseCommand: CreateCourseCommand): Course

    class Base(targetUrl: String) : AbstractLocalServiceGrpcClient(targetUrl),
        LocalServiceCommandGrpcClient {

        override fun addCourse(createCourseCommand: CreateCourseCommand): Course {
            val request = CreateCourseRequest.newBuilder()
                .setTitle(createCourseCommand.title)
                .setDescription(createCourseCommand.description)
                .addAllSections(mapToSections(createCourseCommand.sections))
                .build()
            return courseServiceClient.createCourse(request)
        }

        private fun mapToSections(sections: List<CreateCourseCommand.Section>): List<Section> =
            sections.map {
                Section.newBuilder()
                    .setTitle(it.title)
                    .addAllItems(mapToitems(it.items))
                    .build()
            }

        private fun mapToitems(items: List<CreateCourseCommand.Section.Item>): List<Item> =
            items.map {
                Item.newBuilder()
                    .setTitle(it.title)
                    .setDescription(it.description)
                    .build()
            }
    }

}
