package eposea.repository

import eposea.domain.Institution
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface InstitutionRepository : JpaRepository<Institution, String>
