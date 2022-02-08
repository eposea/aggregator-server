package eposea.service

import jakarta.inject.Singleton

interface InstitutionService {

    fun getInstitutionsUrl(): List<String>

    @Singleton
    class Base : InstitutionService {

        override fun getInstitutionsUrl(): List<String> =
            listOf(
                "http://localhost:9990/savva",
                "http://localhost:9991/savva",
                "http://localhost:9992/savva"
            )

    }

}
