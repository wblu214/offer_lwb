package otherPractice.ManyThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ThreeThreadPrint5 {

    private static final int TIMES = 10;

    public static void main(String[] args) {

        char[] letters = {'A', 'B', 'C', 'D', 'E'};
        int n = letters.length;

        ExecutorService executor = Executors.newFixedThreadPool(n);

        Semaphore[] semaphores = new Semaphore[n];
        for (int i = 0; i < n; i++) {
            semaphores[i] = new Semaphore(i == 0 ? 1 : 0);
        }

        for (int i = 0; i < n; i++) {
            final int index = i;
            executor.execute(() ->
                    print(letters[index],
                            semaphores[index],
                            semaphores[(index + 1) % n])
            );
        }

        executor.shutdown();
    }

    private static void print(char letter,
                              Semaphore current,
                              Semaphore next) {
        try {
            for (int i = 0; i < TIMES; i++) {
                current.acquire();
                System.out.print(letter);
                next.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
