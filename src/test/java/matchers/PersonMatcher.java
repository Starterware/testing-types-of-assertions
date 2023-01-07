package matchers;

import data.Address;
import data.Person;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.Objects;

import static org.hamcrest.Matchers.equalTo;

public class PersonMatcher extends SuperMatcher<Person> {

    public static PersonMatcher isAPerson() {
        return new PersonMatcher();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("[A Person's ");
        super.describeTo(description);
        description.appendText("]");
    }

    public PersonMatcher withFirstName(final Matcher<String> matcher) {
        addMatcher("firstname", Person::getFirstName, matcher);
        return this;
    }

    public PersonMatcher withFirstName(String name) {
        addMatcher("firstname", Person::getFirstName, equalTo(name));
        return this;
    }
    public PersonMatcher withLastName(final Matcher<String> matcher) {
        addMatcher("lastname", Person::getLastName, matcher);
        return this;
    }

    public PersonMatcher withAge(final Matcher<Integer> matcher) {
        addMatcher("age", Person::getAge, matcher);
        return this;
    }

    public PersonMatcher withAddress(final Matcher<Address> matcher) {
        addMatcher("address", Person::getAddress, matcher);
        return this;
    }

    public PersonMatcher withPets(final Matcher<Iterable<? super String>> matcher) {
        addMatcher("address", Person::getPets, matcher);
        return this;
    }

    public PersonMatcher withCity(final String city) {
        addMatcher("address.city", (p) -> Objects.requireNonNull(p.getAddress()).getCity(), equalTo(city));
        return this;
    }
}
