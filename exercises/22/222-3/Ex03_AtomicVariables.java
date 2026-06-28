
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 실습 03: 원자적 변수 (Atomic Variables)와 CAS
 * 
 * [목표]
 * 1. CAS(Compare-And-Swap) 기반으로 설계된 AtomicInteger를 학습합니다.
 * 2. 락(Lock) 없이도 멀티스레드 환경에서 안전하게 값을 수정하는 방법을 실습합니다.
 * 3. compareAndSet 메서드를 활용하여 CAS 알고리즘의 동작 메커니즘을 직접 구현해 봅니다.
 */
public class Ex03_AtomicVariables {

    static class AtomicCounter {
        // 내부적으로 volatile 키워드와 CAS 기법을 활용하여 원자성을 보장합니다.
        private final AtomicInteger count = new AtomicInteger(0);

        public void increment() {
            // TODO: count의 값을 원자적으로 1 증가시켜 보세요.
            // 힌트: count.incrementAndGet() 또는 count.getAndIncrement() 메소드를 활용합니다.
            count.incrementAndGet();
        }

        public int getCount() {
            return count.get();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 실습 3-1: AtomicInteger를 이용한 동기화 제거 ===");
        runAtomicCounterDemo();

        System.out.println("\n=== 실습 3-2: CAS(Compare-And-Swap) 동작 방식 직접 실습 ===");
        runCasDemo();
    }

    private static void runAtomicCounterDemo() throws InterruptedException {
        AtomicCounter counter = new AtomicCounter();
        int numberOfIncrements = 50_000;

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < numberOfIncrements; i++) counter.increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < numberOfIncrements; i++) counter.increment();
        });

        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("AtomicInteger 최종 결과: " + counter.getCount() + " (기대값: 100000)");
        System.out.println("-> 설명: 락을 사용하지 않고 CPU 레벨의 CAS 명령을 활용하여 오버헤드 없이 안전하게 동기화했습니다.");
    }

    /**
     * CAS 연산 시뮬레이션
     * 낙관적 락(Optimistic Lock) 기법이나 비선점형 알고리즘에서 자주 쓰이는 재시도(Retry) 패턴 구현
     */
    private static void runCasDemo() {
        AtomicInteger balance = new AtomicInteger(100);

        // 여러 스레드가 동시에 돈을 인출(출금)하려고 시도하는 시나리오
        Runnable withdrawTask = () -> {
            boolean success = false;
            while (!success) {
                int currentBalance = balance.get();
                if (currentBalance < 50) {
                    System.out.println(Thread.currentThread().getName() + " - 잔액 부족으로 출금 실패 (잔액: " + currentBalance + ")");
                    break;
                }
                
                int nextBalance = currentBalance - 50;

                // TODO: balance.compareAndSet(expectedValue, newValue)를 사용해 보세요.
                // 1. 읽어온 시점의 잔액(currentBalance)이 현재 메모리 상의 잔액과 여전히 일치하면, 안전하게 차감액(nextBalance)으로 갱신합니다.
                // 2. 만약 다른 스레드가 그 사이에 값을 바꿨다면 compareAndSet은 false를 반환하고, 루프를 돌며 현재 값을 다시 읽어 재시도하게 됩니다.
                
                success = balance.compareAndSet(currentBalance, nextBalance);
                
                if (success) {
                    System.out.println(Thread.currentThread().getName() + " - 50원 출금 성공! (남은 잔액: " + nextBalance + ")");
                } else {
                    System.out.println(Thread.currentThread().getName() + " - 다른 스레드가 값을 변경함. CAS 실패! 재시도합니다...");
                }
            }
        };

        Thread userA = new Thread(withdrawTask, "User-A");
        Thread userB = new Thread(withdrawTask, "User-B");
        Thread userC = new Thread(withdrawTask, "User-C");

        userA.start();
        userB.start();
        userC.start();
        
        try {
            userA.join();
            userB.join();
            userC.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("최종 잔액: " + balance.get());
    }
}
