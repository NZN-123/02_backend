
/**
 * 실습 01: 경쟁 상태 (Race Condition)와 JVM 메모리 구조
 * 
 * [목표]
 * 1. 자바에서 스레드를 생성하고 실행하는 기본 방법을 이해합니다.
 * 2. JVM의 Stack 영역(스레드 안전)과 Heap 영역(동시성 노출)의 차이를 이해합니다.
 * 3. 여러 스레드가 공유 자원을 동시에 변경할 때 발생하는 경쟁 상태(Race Condition)를 눈으로 확인합니다.
 */
public class Ex01_RaceCondition {

    // 공유 자원이 저장되는 Heap 영역의 객체
    static class SharedCounter {
        private int count = 0;

        public void increment() {
            // count++ 은 사실 3단계 연산입니다:
            // 1. 현재 count 값을 읽기 (Read)
            // 2. 값에 1을 더하기 (Modify)
            // 3. 더한 값을 다시 count에 저장하기 (Write)
            // 여러 스레드가 이 연산을 동시에 수행하면 중간에 CPU 제어권이 넘어가면서 연산 결과가 유실됩니다.
            count++;
        }

        public int getCount() {
            return count;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== 실습 1-1: 독립된 Stack 영역 확인 ===");
        runStackIsolationDemo();

        System.out.println("\n=== 실습 1-2: 공유 Heap 영역과 경쟁 상태 (Race Condition) ===");
        runRaceConditionDemo();
    }

    /**
     * Stack 영역 격리성 실습
     * 각 스레드 내의 지역 변수는 독립된 Stack 프레임에 저장되어 동시성 영향이 없습니다.
     */
    private static void runStackIsolationDemo() throws InterruptedException {
        Runnable localVariableTask = () -> {
            // 지역 변수는 각 스레드의 전용 Stack 영역에 생성됩니다.
            int localCounter = 0;
            for (int i = 0; i < 5; i++) {
                localCounter++;
                System.out.println(Thread.currentThread().getName() + " - Stack 지역 변수 값: " + localCounter);
                try {
                    Thread.sleep(10); // 가시적인 교대 실행을 위한 대기
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread threadA = new Thread(localVariableTask, "Thread-A");
        Thread threadB = new Thread(localVariableTask, "Thread-B");

        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();
        
        System.out.println("-> 결과: Thread-A와 Thread-B는 서로의 지역 변수 값에 영향을 주지 않았습니다.");
    }

    /**
     * Heap 영역 공유 변수 경합 실습 (경쟁 상태 발생)
     */
    private static void runRaceConditionDemo() throws InterruptedException {
        SharedCounter sharedCounter = new SharedCounter(); // Heap 영역에 생성되어 공유됨
        int numberOfIncrements = 50_000;

        // SharedCounter 인스턴스를 공유하여 count를 증가시키는 Task
        Runnable incrementTask = () -> {
            for (int i = 0; i < numberOfIncrements; i++) {
                sharedCounter.increment();
            }
        };

        // TODO: 아래 순서에 맞게 스레드를 생성하고 시작(start)한 뒤 완료를 기다려(join) 보세요.
        // 1. incrementTask를 수행할 스레드 2개(t1, t2)를 생성하세요.
        // 2. 두 스레드를 시작하세요.
        // 3. 메인 스레드가 t1과 t2의 작업이 완전히 끝날 때까지 대기(join)하게 하세요.

        Thread t1 = new Thread(incrementTask);
        Thread t2 = new Thread(incrementTask);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        // 이론적 기대값: 50,000 * 2 = 100,000
        int expected = numberOfIncrements * 2;
        int actual = sharedCounter.getCount();

        System.out.println("기대하는 최종 카운트: " + expected);
        System.out.println("실제 기록된 최종 카운트: " + actual);
        System.out.println("정합성 일치 여부: " + (expected == actual));
        
        // TODO-질문: 실제 최종 카운트가 기대치보다 작게 나온 이유는 무엇일까요?
        // JVM 메모리 구조(Heap)와 멀티스레드의 Context Switching 관점에서 머릿속으로 정리해 봅시다.
    }
}
