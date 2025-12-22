package otherPractice.ManyThread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreeThreadPrint4 {

    // 1. 共享的锁对象
    private static final Lock lock = new ReentrantLock();

    // 2. 为每个线程创建独立的 Condition
    private static final Condition conditionA = lock.newCondition();
    private static final Condition conditionB = lock.newCondition();
    private static final Condition conditionC = lock.newCondition();

    // 3. 共享的状态变量，依然需要 volatile 保证可见性
    private static volatile char currentThread = 'A';

    public static void main(String[] args) {
        Thread threadA = new Thread(new PrintTask('A', conditionA, conditionB), "Thread-A");
        Thread threadB = new Thread(new PrintTask('B', conditionB, conditionC), "Thread-B");
        Thread threadC = new Thread(new PrintTask('C', conditionC, conditionA), "Thread-C");

        threadA.start();
        threadB.start();
        threadC.start();
    }

    static class PrintTask implements Runnable {
        private final char thisLetter;
        private final Condition currentCondition; // 自己的等待条件
        private final Condition nextCondition;    // 下一个线程的等待条件

        public PrintTask(char thisLetter, Condition currentCondition, Condition nextCondition) {
            this.thisLetter = thisLetter;
            this.currentCondition = currentCondition;
            this.nextCondition = nextCondition;
        }

        @Override
        public void run() {
            for (int i = 0; i < 50; i++) {
                // 4. 获取锁
                lock.lock();
                try {
                    // 5. 循环检查，防止虚假唤醒
                    while (currentThread != thisLetter) {
                        try {
                            // 如果不是自己，就在自己的 Condition 上等待
                            currentCondition.await();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    // 6. 轮到自己，执行打印
                    System.out.print(thisLetter);

                    // 7. 更新状态
                    currentThread = (char) (thisLetter == 'C' ? 'A' : thisLetter + 1);

                    // 8. 精确唤醒下一个线程
                    nextCondition.signal();

                } finally {
                    // 9. 在 finally 块中释放锁，确保锁一定会被释放
                    lock.unlock();
                }
            }
        }
    }
}
