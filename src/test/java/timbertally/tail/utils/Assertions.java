package timbertally.tail.utils;

import org.assertj.core.api.Assert;

import java.util.function.Supplier;

public class Assertions {
    public static <S extends Assert<S, A>, A> DelayedAssert<S, A> delay(Supplier<Assert<S, A>> supplier) {
        return Assertions.delay(supplier, 10, 100);
    }

    public static <S extends Assert<S, A>, A> DelayedAssert<S, A> delay(
            Supplier<Assert<S, A>> assertion,
            int retries,
            long waitMs
    ) {
        return new DelayedAssert<>(assertion, retries, waitMs);
    }

    public static DelayedVerify delay(Runnable verify) {
        return Assertions.delay(verify, 10, 100);
    }

    public static DelayedVerify delay(
            Runnable verify,
            int retries,
            long waitMs
    ) {
        return new DelayedVerify(verify, retries, waitMs);
    }
}
