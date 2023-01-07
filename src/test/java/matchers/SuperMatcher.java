package matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SuperMatcher<T> extends BaseMatcher<T> {
    private final List<LambdaMatcher<T, ?>> matchers = new ArrayList<>();
    private final List<LambdaMatcher<T, ?>> failed = new ArrayList<>();

    public <X> void addMatcher(String property, Function<T, X> callable, final Matcher<? super X> matcher) {
        matchers.add(LambdaMatcher.of(property, callable, matcher));
    }

    @Override
    public boolean matches(final Object item) {
        failed.clear();
        for (final LambdaMatcher<T, ?> lambdaMatcher : matchers) {
            if (!lambdaMatcher.matches(item)) {
                failed.add(lambdaMatcher);
            }
        }
        return failed.isEmpty();
    }

    @Override
    public void describeTo(final Description description) {
        for (int i = 0; i < matchers.size(); i++) {
            description.appendText(matchers.get(i).name() + " to be ").appendDescriptionOf(matchers.get(i));
            if (i != matchers.size() - 1) {
                description.appendText(" and ");
            }
        }
    }

    @Override
    public void describeMismatch(final Object item, final Description description) {
        description.appendText("{ ");
        for (int i = 0; i < failed.size(); i++) {
            description.appendDescriptionOf(failed.get(i)).appendText(" ");
            failed.get(i).describeMismatch(item, description);
            if (i != failed.size()-1)
                description.appendText(", ");
        }
        description.appendText(" }");
    }
}
