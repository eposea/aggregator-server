package eposea.repository

import eposea.domain.Institution
import io.micronaut.data.jpa.repository.JpaRepository

interface InstitutionRepository : JpaRepository<Institution, String>
