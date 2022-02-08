package eposea.domain

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity
data class EmptyEntity(@Id @GeneratedValue(GeneratedValue.Type.AUTO) val id: Long)
