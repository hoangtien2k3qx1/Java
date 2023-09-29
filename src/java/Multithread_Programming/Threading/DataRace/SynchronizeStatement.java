package src.java.Multithread_Programming.Threading.DataRace;

import java.util.stream.IntStream;

/*
    Xử lý hiện tượng Data Race bằng Mutual Exclusion
        + ReentrantLock: là lock và unlock khi truy cập vào resource chung.
        + Synchronize statement hoặc synchronize method.
        + Sử dụng các Atomic variable với cơ chế CAS (atomic operation)
*/
public class SynchronizeStatement {
    private static int COUNTER = 0;
    private static final Object lock = new Object();

    public static void main(String... args) throws Exception {
        final Runnable increaseCounterFunc = () -> IntStream
                .range(0, 100)
                .forEach(SynchronizeStatement::increaseCounter);

        final var first = new Thread(increaseCounterFunc);
        final var second = new Thread(increaseCounterFunc);

        first.start();
        second.start();

        first.join();
        second.join();

        System.out.println(COUNTER);
    }

    private static void increaseCounter(int i) {
        synchronized (lock) {
            ++COUNTER;
        }
    }
}