
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 실습 02: synchronized 키워드와 ReentrantLock
 * 
 * [목표]
 * 1. 암묵적 락인 synchronized를 사용하여 경쟁 상태를 해결해 봅니다.
 * 2. 명시적 락인 ReentrantLock의 기본 동작과 try-finally 락 해제 패턴을 학습합니다.
 * 3. tryLock()을 활용하여 락 획득 대기 시간을 제한하고 대안 로직을 처리하는 방법을 실습합니다.
 */
public class Ex02_Synchronization {

    // 1. synchronized를 활용한 동기화 카운터
    static class SynchronizedCounter {
        private int count = 0;

        // public synchronized void increment() 형태로 메서드 전체에 동기화를 걸 수 있습니다.
        public synchronized void increment() {
            count++;
        }

        public int getCount() {
            return count;
        }
    }

    // 2. ReentrantLock을 활용한 동동기화 카운터
    static class LockCounter {
        private int count = 0;
        private final Lock lock = new ReentrantLock();

        public void increment() {
            // TODO: ReentrantLock을 사용하여 동기화를 처리해 보세요.
            // 힌트:
            // 1. lock.lock()을 호출하여 락을 획득합니다.
            // 2. 예외 발생 시에도 락이 정상 해제되도록 try-finally 블록을 작성해야 합니다.
            // 3. finally 블록에서 lock.unlock()을 호출하여 락을 반드시 해제하세요.

            lock.lock();
            try {
                count++;
            } finally {
                lock.unlock();
            }
        }

        public int getCount() {
            return count;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 실습 2-1: synchronized 동기화 검증 ===");
        runSynchronizedDemo();

        System.out.println("\n=== 실습 2-2: ReentrantLock 동기화 검증 ===");
        runLockDemo();

        System.out.println("\n=== 실습 2-3: tryLock()을 활용한 타임아웃 제어 ===");
        runTryLockDemo();
    }

    private static void runSynchronizedDemo() throws InterruptedException {
        SynchronizedCounter counter = new SynchronizedCounter();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 50_000; i++) counter.increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 50_000; i++) counter.increment();
        });

        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("Synchronized 카운트 결과: " + counter.getCount() + " (기대값: 100000)");
    }

    private static void runLockDemo() throws InterruptedException {
        LockCounter counter = new LockCounter();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 50_000; i++) counter.increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 50_000; i++) counter.increment();
        });

        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("ReentrantLock 카운트 결과: " + counter.getCount() + " (기대값: 100000)");
    }

    /**
     * tryLock()을 활용하여 특정 스레드가 오랜 시간 락을 점유할 때,
     * 대기 스레드가 마냥 기다리지 않고 포기하거나 대체 동작을 수행하게 만듭니다.
     */
    private static void runTryLockDemo() throws InterruptedException {
        ReentrantLock sharedLock = new ReentrantLock();

        // 락을 오래 쥐고 있는 스레드
        Thread workerHoldingLock = new Thread(() -> {
            sharedLock.lock();
            try {
                System.out.println("[스레드 1] 락 획득. 3초간 연산 작업을 수행합니다...");
                Thread.sleep(3000); // 3초 대기
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                System.out.println("[스레드 1] 작업을 마치고 락을 해제합니다.");
                sharedLock.unlock();
            }
        });

        // tryLock으로 락 획득을 시도하는 스레드
        Thread workerTryingLock = new Thread(() -> {
            try {
                // 1초 동안 대기하면서 락 획득을 시도해 봅니다.
                System.out.println("[스레드 2] 1초 안에 락 획득을 시도합니다...");
                
                // TODO: sharedLock.tryLock(1, TimeUnit.SECONDS)를 호출하여 락을 시도해 보세요.
                // 1. tryLock이 true를 반환하면 락 획득 성공 -> 임계 영역 로직 수행 후 finally에서 unlock.
                // 2. false를 반환하면 락 획득 실패 -> 포기 메시지 출력.
                
                boolean acquired = sharedLock.tryLock(1, TimeUnit.SECONDS);
                if (acquired) {
                    try {
                        System.out.println("[스레드 2] 락 획득 성공! 안전하게 공유 데이터를 수정합니다.");
                    } finally {
                        sharedLock.unlock();
                    }
                } else {
                    System.out.println("[스레드 2] 락 획득 실패 (타임아웃). 기다리지 않고 다른 백업 작업을 수행합니다.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        workerHoldingLock.start();
        Thread.sleep(100); // 스레드 1이 먼저 실행되어 락을 쥐도록 유도
        workerTryingLock.start();

        workerHoldingLock.join();
        workerTryingLock.join();
    }
}
