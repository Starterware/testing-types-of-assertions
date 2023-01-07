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
        val p = Person.build { lastName = "Smith"; firstName = "John"; age=18 }

        // Example using hamcrest
        assertThat(p.firstName, equalTo("John"))
        assertThat(p.lastName, startsWith("S"))
        assertThat(p.age, greaterThanOrEqualTo(18))

        // Example using hamcrest - Custom Super Matcher
        assertThat(p, isAPerson()
            .withFirstName(equalTo("John"))
            .withLastName(startsWith("S"))
            .withAge(greaterThanOrEqualTo(18)))

        // Example using assertj
        assertThat(p.firstName).isEqualTo("John")
        assertThat(p.lastName).startsWith("S")

        // Example using assertj - Custom Assertion
        assertThat(p)
            .hasFirstName("John")
            .hasLastNameStartingWith("S")
            .hasAgeGreaterOrEqualTo(18)
    }

    @Test
    fun testSuperMatcherWithContainers() {
        val p = Person.build { firstName = "Summer" }

        p.pets.add("Snowball")
        p.pets.add("Beethoven")

        // Example using hamcrest
        assertThat(p.pets, hasItem("Snowball"))

        // Example using hamcrest - Custom Super Matcher
        assertThat(p, isAPerson().withPets(hasItem("Snowball")))

        // Example using assertj
        assertThat(p.pets).contains("Snowball")

        // Example using assertj - Custom Assertion
        assertThat(p).hasPet("Snowball")
    }

    @Test
    fun testNestedSuperMatchers() {
        val adr = Address(city="Dublin", eir="D04K2V4")
        val p = Person.build { firstName = "Marc"; address = adr}

        // Example using hamcrest
        assertThat(p.address?.city, equalTo("Dublin"))
        assertThat(p.address?.eir, equalTo("D04K2V4"))

        // Example using hamcrest - Custom Super Matcher
        assertThat(p, isAPerson().withAddress(anAddress().withCity("Dublin").withEir(startsWith("D04"))))
        assertThat(p, isAPerson().withCity("Dublin")) // Simplified?

        // Example using assertj
        assertThat(p.address?.city).isEqualTo("Dublin")
        assertThat(p.address?.eir).isEqualTo("D04K2V4")

        // Example using assertj - Custom Assertion
        assertThat(p).livesIn("Dublin").livesAtEir("D04K2V4")
    }

    @Test
    fun testRecursively() {
        val p = Person.build { firstName = "Marc"; address = Address(city="Dublin", eir="D04K2V4")}
        val p2 = Person.build { firstName = "Marc"; address = Address(city="Dublin", eir="D04K2V4")}

        Assertions.assertThat(p).isEqualToComparingFieldByFieldRecursively(p2)
        // some frameworks allow field exclusions
    }
}
