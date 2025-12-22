package otherPractice.ManyThread;

public class ThreeThreadPrint {

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
            // 每个线程循环打印10次
            for (int i = 0; i < 10; i++) {
                // 2. 自旋检查：一直循环，直到轮到自己
                // while 循环检查共享变量 currentThread
                // 如果不是自己，就继续空转等待
                while (currentThread != thisLetter) {
                    // 空循环体，让出CPU时间片是一种优化，但不是必须的
                     Thread.yield();
                }

//                if(currentThread == thisLetter)
                // 3. 轮到自己，执行打印
                System.out.print(thisLetter);

                // 4. 更新状态，将“信号灯”交给下一个线程
                // 因为 currentThread 是 volatile 的，这个写入操作会立刻对所有线程可见
                currentThread = nextLetter;
            }
        }
    }
}
