package eposea.domain

data class InstitutionsResponse(val institutions: List<InstitutionDto>)

data class InstitutionDto(val id: String, val name: String, val courses: List<CourseDto>)

data class CourseDto(
    val id: String,
    val title: String,
    val description: String,
    val items: List<CourseItemDto>
)

data class CourseItemDto(val id: String, val title: String, val description: String)
