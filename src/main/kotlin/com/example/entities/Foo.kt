package com.example.entities

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import io.micronaut.data.annotation.Relation
import io.micronaut.data.annotation.Version

@MappedEntity
data class Foo(
    @field:Id
    @field:GeneratedValue
    val id: Long? = null,

    @field:Version
    val version: Long? = null,

    @Relation(value = Relation.Kind.MANY_TO_ONE)
    val someBar: Bar? = null,
    @GeneratedValue
    @MappedProperty(value = "some_bar_id")
    val rawSomeBarId: Long? = null,

    @Relation(value = Relation.Kind.MANY_TO_ONE)
    val anotherBar: Bar? = null,
    @GeneratedValue
    @MappedProperty(value = "another_bar_id")
    val rawAnotherBarId: Long? = null,
)
