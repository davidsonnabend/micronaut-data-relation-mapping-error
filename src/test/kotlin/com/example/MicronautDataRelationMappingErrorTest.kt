package com.example

import com.example.entities.Bar
import com.example.entities.Foo
import com.example.repositories.BarRepository
import com.example.repositories.FooRepository
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

@MicronautTest
class MicronautDataRelationMappingErrorTest(
    private val barRepository: BarRepository,
    private val fooRepository: FooRepository,
) : ShouldSpec({

    should("map referenced relations correctly") {
        val someBar = barRepository.save(Bar())
        fooRepository.save(Foo(someBar = someBar))

        val anotherBar = barRepository.save(Bar())
        fooRepository.save(Foo(anotherBar = anotherBar))

        val childCount = 5

        repeat(childCount) {
            fooRepository.save(Foo(anotherBar = someBar))
            fooRepository.save((Foo(someBar = anotherBar)))
        }

        val fetchedSomeBar = barRepository.getById(someBar.id!!)

        fetchedSomeBar.someFoos.count() shouldBe 1
        fetchedSomeBar.otherFoos.count() shouldBe childCount
        fetchedSomeBar.otherFoos.forAll { foo ->
            withClue("SomeBar and AnotherBar should be different") {
                foo.someBar?.id shouldNotBe foo.anotherBar?.id
            }

            withClue("The id of AnotherBar entity should match the raw value of 'another_bar_id'") {
                foo.anotherBar?.id shouldBe foo.rawAnotherBarId
            }

            withClue("The id of SomeBar entity should match the raw value of 'some_bar_id'") {
                foo.someBar?.id shouldBe foo.rawSomeBarId
            }
        }

        val fetchedAnotherBar = barRepository.getById(anotherBar.id!!)

        fetchedAnotherBar.someFoos.count() shouldBe childCount
        fetchedAnotherBar.otherFoos.count() shouldBe 1
        fetchedAnotherBar.otherFoos.forAll { foo ->
            withClue("SomeBar and AnotherBar should be different") {
                foo.someBar?.id shouldNotBe foo.anotherBar?.id
            }

            withClue("The id of AnotherBar entity should match the raw value of 'another_bar_id'") {
                foo.anotherBar?.id shouldBe foo.rawAnotherBarId
            }

            withClue("The id of SomeBar entity should match the raw value of 'some_bar_id'") {
                foo.someBar?.id shouldBe foo.rawSomeBarId
            }
        }
    }
})
