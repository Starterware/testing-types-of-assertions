package data

import Assertions.PersonAssertions.assertThat
import matchers.AddressMatcher.anAddress
import matchers.PersonMatcher.isAPerson
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test

class PersonTest {
    @Test
    fun testSuperMatcherLiteralProperties() {
        val person = Person.build { lastName = "Smith"; firstName = "John"; age=18 }

        // Example using hamcrest
        assertThat(person.firstName, equalTo("John"))
        assertThat(person.lastName, startsWith("S"))
        assertThat(person.age, greaterThanOrEqualTo(18))

        // Example using hamcrest - Custom Super Matcher
        assertThat(person, isAPerson()
            .withFirstName(equalTo("John"))
            .withLastName(startsWith("S"))
            .withAge(greaterThanOrEqualTo(18)))

        // Example using assertj
        assertThat(person.firstName).isEqualTo("John")
        assertThat(person.lastName).startsWith("S")

        // Example using assertj - Custom Assertion
        assertThat(person)
            .hasFirstName("John")
            .hasLastNameStartingWith("S")
            .hasAgeGreaterOrEqualTo(18)
    }

    @Test
    fun testSuperMatcherWithContainers() {
        val person = Person.build { firstName = "Summer" }

        person.pets.add("Snowball")
        person.pets.add("Beethoven")

        // Example using hamcrest
        assertThat(person.pets, hasItem("Snowball"))

        // Example using hamcrest - Custom Super Matcher
        assertThat(person, isAPerson().withPets(hasItem("Snowball")))

        // Example using assertj
        assertThat(person.pets).contains("Snowball")

        // Example using assertj - Custom Assertion
        assertThat(person).hasPet("Snowball")
    }

    @Test
    fun testNestedSuperMatchers() {
        val adr = Address(city="Dublin", eir="D04K2V4")
        val person = Person.build { firstName = "Marc"; address = adr}

        // Example using hamcrest
        assertThat(person.address?.city, equalTo("Dublin"))
        assertThat(person.address?.eir, equalTo("D04K2V4"))

        // Example using hamcrest - Custom Super Matcher
        assertThat(person, isAPerson().withAddress(anAddress().withCity("Dublin").withEir(startsWith("D04"))))
        assertThat(person, isAPerson().withCity("Dublin")) // Simplified?

        // Example using assertj
        assertThat(person.address?.city).isEqualTo("Dublin")
        assertThat(person.address?.eir).isEqualTo("D04K2V4")

        // Example using assertj - Custom Assertion
        assertThat(person).livesIn("Dublin").livesAtEir("D04K2V4")
    }

    @Test
    fun testRecursively() {
        val person = Person.build { firstName = "Marc"; age = 18; address = Address(city="Dublin", eir="D04K2V4")}
        val other_person = Person.build { firstName = "Marc"; age = 22; address = Address(city="Dublin", eir="D04K2V4")}

        // nice way to compare complex types
        Assertions.assertThat(person)
                .usingRecursiveComparison()
                .ignoringFields("age")
                .isEqualTo(other_person)
    }
}
