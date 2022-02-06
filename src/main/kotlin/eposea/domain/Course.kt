package eposea.domain

import java.net.URL

data class Course(val id: String, val url: URL)

data class Subject(val id: String, val title: String, val description: String)
