package com.example.repositories

import com.example.entities.Bar
import io.micronaut.data.annotation.Join
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository

@JdbcRepository(dialect = Dialect.POSTGRES)
interface BarRepository : CrudRepository<Bar, Long> {

    @Join(value = "someFoos", type = Join.Type.LEFT_FETCH)
    @Join(value = "otherFoos", type = Join.Type.LEFT_FETCH)
    fun getById(id: Long): Bar
}
