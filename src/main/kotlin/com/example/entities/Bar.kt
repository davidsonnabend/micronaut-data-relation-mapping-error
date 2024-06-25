package com.example.entities

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Relation
import io.micronaut.data.annotation.Version

@MappedEntity
data class Bar(
    @field:Id
    @field:GeneratedValue
    val id: Long? = null,

    @field:Version
    val version: Long? = null,

    @Relation(value = Relation.Kind.ONE_TO_MANY, mappedBy = "someBar")
    val someFoos: List<Foo> = emptyList(),
    @Relation(value = Relation.Kind.ONE_TO_MANY, mappedBy = "anotherBar")
    val otherFoos: List<Foo> = emptyList(),
)
