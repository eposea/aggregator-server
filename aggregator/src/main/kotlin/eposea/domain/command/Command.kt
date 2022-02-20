package eposea.domain.command

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class CreateCourseCommand(
    @NotBlank val title: String,
    val description: String,
    val sections: List<Section> = emptyList()
) {

    @Introspected
    data class Section(
        @NotBlank val title: String,
        val items: List<Item> = emptyList()
    ) {

        @Introspected
        data class Item(
            @NotBlank val title: String,
            val description: String
        )

    }

}
