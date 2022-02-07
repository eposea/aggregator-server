package eposea.domain

import java.net.URL

data class Institution(val id: String, val url: URL)

data class Course(val id: String, val title: String, val description: String)
