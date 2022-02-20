package eposea.domain

data class InstitutionsDto(val institutions: List<InstitutionDto>)

data class InstitutionDto(val id: String, val name: String, val imageUrl: String?)

data class InstitutionDataDto(
    val imageUrl: String?,
    val title: String,
    val description: String,
    val sections: List<SectionDto>,
    val courses: List<CourseDto>
)

data class CourseDto(
    val id: String,
    val title: String
)

data class SectionDto(val title: String, val items: List<ItemDto>)

data class ItemDto(val id: String, val title: String)

data class ItemDataDto(val title: String, val description: String)

data class CourseDataDto(val title: String, val description: String, val sections: List<SectionDto>)
