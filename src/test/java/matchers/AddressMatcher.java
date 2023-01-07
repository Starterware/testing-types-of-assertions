package matchers;

import data.Address;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;

public class AddressMatcher extends SuperMatcher<Address> {

    public static AddressMatcher anAddress() {
        return new AddressMatcher();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("[An address ");
        super.describeTo(description);
        description.appendText("]");
    }

    public AddressMatcher withCity(String name) {
        addMatcher("city", Address::getCity, equalTo(name));
        return this;
    }

    public AddressMatcher withEir(final Matcher<String> matcher) {
        addMatcher("eir", Address::getEir, matcher);
        return this;
    }
}
