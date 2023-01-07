package Assertions;

import data.Person;
import org.assertj.core.api.AbstractAssert;

import java.util.Objects;
import java.util.function.Function;

public class PersonAssertions extends AbstractAssert<PersonAssertions, Person> {
    public PersonAssertions(Person person) {
        super(person, PersonAssertions.class);
    }

    public static PersonAssertions assertThat(Person actual) {
        return new PersonAssertions(actual);
    }

    public PersonAssertions hasFirstName(String name) {
        isEqual(Person::getFirstName, name);
        return this;
    }

    public PersonAssertions hasLastNameStartingWith(String s) {
        isNotNull();
        if (!actual.getLastName().startsWith(s)) {
            failWithMessage("Expected lastName to start with <%s> but was <%s>", s, actual.getLastName());
        }
        return this;
    }

    public PersonAssertions hasAgeGreaterOrEqualTo(int age) {
        isNotNull();
        if (actual.getAge() < age) {
            failWithMessage("Expected age to be greater or equal than <%d> but was <%d>", age, actual.getAge());
        }
        return this;
    }

    public PersonAssertions hasPet(String pet) {
        isNotNull();
        if (!actual.getPets().contains(pet)) {
            failWithMessage("Expected pets to contain <%s> but was <%s>", pet, actual.getPets());
        }
        return this;
    }

    public PersonAssertions livesIn(String city) {
        isNotNull();
        if (!Objects.requireNonNull(actual.getAddress()).getCity().equals(city)) {
            failWithMessage("Expected person to live in city <%s> but was <%s>", city, actual.getAddress().getCity());
        }
        return this;
    }

    public PersonAssertions livesAtEir(String eir) {
        isNotNull();
        if (!Objects.requireNonNull(actual.getAddress()).getEir().equals(eir)) {
            failWithMessage("Expected person to live at eir <%s> but was <%s>", eir, actual.getAddress().getEir());
        }
        return this;
    }

    private void isEqual(Function<Person, Object> f, String expected) {
        isNotNull();
        if (!Objects.equals(f.apply(actual), expected)) {
            failWithMessage("Expected firstName to be <%s> but was <%s>", expected, f.apply(actual));
        }
    }
}
