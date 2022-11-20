package logalytics.tail.utils;

import org.assertj.core.api.Assert;

import java.util.function.Supplier;

public class DelayedAssert<S extends Assert<S, A>, A> extends DelayedTest<Supplier<Assert<S, A>>> {

    protected final Supplier<Assert<S, A>> test;

    protected DelayedAssert(Supplier<Assert<S, A>> test, int retries, long waitMs) {
        super(retries, waitMs);
        this.test = test;
    }

    public void _test() {
        this.test.get();
    }
}
