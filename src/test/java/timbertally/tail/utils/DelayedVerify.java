package timbertally.tail.utils;

public class DelayedVerify extends DelayedTest<Runnable> {

    private final Runnable test;

    protected DelayedVerify(Runnable test, int retries, long waitMs) {
        super(retries, waitMs);
        this.test = test;
    }

    public void _test() {
        this.test.run();
    }
}
