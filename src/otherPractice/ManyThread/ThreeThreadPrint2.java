package otherPractice.ManyThread;

import java.util.concurrent.*;

public class ThreeThreadPrint2 {

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(9, 16, 1L,
                TimeUnit.MINUTES, new LinkedBlockingQueue<>(100),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardOldestPolicy());

        Semaphore semaphore1 = new Semaphore(1);
        Semaphore semaphore2 = new Semaphore(0);
        Semaphore semaphore3 = new Semaphore(0);


        executorService.execute(() -> {
            try {
                print("A", semaphore1, semaphore2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        executorService.execute(() -> {
            try {
                print("B", semaphore2, semaphore3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        executorService.execute(() -> {
            try {
                print("C", semaphore3, semaphore1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        executorService.shutdown();
    }

    static void print(String letter, Semaphore current, Semaphore next) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            current.acquire();
            System.out.print(letter);
            next.release();
        }
    }
}