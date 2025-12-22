package otherPractice.ManyThread;

public class ThreeThreadPrint3 {

    static final Object lock = new Object();
    // 1. 定义一个共享的状态变量，并用 volatile 修饰
    // 它的作用是作为线程间的“信号灯”，告诉其他线程现在轮到谁了。
    private static volatile char currentThread = 'A';

    public static void main(String[] args) {
        // 创建三个线程，分别负责打印 A, B, C
        Thread threadA = new Thread(new PrintTask('A', 'B'), "Thread-A");
        Thread threadB = new Thread(new PrintTask('B', 'C'), "Thread-B");
        Thread threadC = new Thread(new PrintTask('C', 'A'), "Thread-C");

        threadA.start();
        threadB.start();
        threadC.start();
    }

    // 打印任务
    static class PrintTask implements Runnable {
        private final char thisLetter;   // 当前线程负责打印的字母
        private final char nextLetter;   // 下一个应该打印的字母

        public PrintTask(char thisLetter, char nextLetter) {
            this.thisLetter = thisLetter;
            this.nextLetter = nextLetter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                // 3. 在 synchronized 块内操作锁和共享变量
                synchronized (lock) {
                    // 4. 循环检查，防止被错误唤醒（spurious wakeup）
                    while (currentThread != thisLetter) {
                        try {
                            // 如果不是自己，就等待并释放锁
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return; // 如果被中断，就退出
                        }
                    }

                    // 5. 轮到自己，执行打印
                    System.out.print(thisLetter);

                    // 6. 更新状态，准备唤醒下一个线程
                    currentThread = nextLetter;

                    // 7. 唤醒所有在 lock 上等待的线程
                    // 不能用 notify()，因为我们不确定哪个线程是下一个
                    // notifyAll() 是最安全的选择
                    lock.notifyAll();
                }
            }
        }
    }
}
