package eposea.service

import jakarta.inject.Singleton

interface InstitutionService {

    fun getInstitutionsUrl(): List<String>

    fun getInstitutionUrl(id: String): String?

    @Singleton
    class Base : InstitutionService {

        override fun getInstitutionsUrl(): List<String> =
            listOf(
                "http://localhost:9990/",
                "http://localhost:9991/",
                "http://localhost:9992/"
            )

        override fun getInstitutionUrl(id: String): String? =
            "http://localhost:9990/"

    }

}
