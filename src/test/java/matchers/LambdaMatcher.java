package matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.function.Function;

class LambdaMatcher<I, T> extends BaseMatcher<I> {
    public static <I, T> LambdaMatcher<I, T> of(String name, Function<I, T> lambda, Matcher<? super T> matcher) {
        return new LambdaMatcher<>(name, lambda, matcher);
    }

    private final String name;
    private final Function<I, T> lambda;
    private final Matcher<? super T> matcher;

    private LambdaMatcher(String name, Function<I, T> lambda, Matcher<? super T> matcher) {
        this.name = name;
        this.lambda = lambda;
        this.matcher = matcher;
    }

    @Override
    public boolean matches(Object actual) {
        return matcher.matches(lambda.apply((I)actual));
    }

    @Override
    public void describeTo(Description description) {
        matcher.describeTo(description);
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        matcher.describeMismatch(lambda.apply((I)item), description);
    }

    public String name() {
        return name;
    }
}
