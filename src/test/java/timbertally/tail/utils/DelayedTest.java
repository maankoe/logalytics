package timbertally.tail.utils;

public abstract class DelayedTest<T> {

    private final int retries;
    private final long waitMs;

    protected DelayedTest(int retries, long waitMs) {
        this.retries = retries;
        this.waitMs = waitMs;
    }

    abstract void _test();

    public void test() throws InterruptedException {
        int tries = 0;
        while (tries < retries) {
            try {
                this._test();
                return;
            } catch (AssertionError e) {
                Thread.sleep(waitMs);
                tries++;
            }
        }
        this._test();
    }
}
