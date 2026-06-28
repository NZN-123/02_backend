
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 실습 04: ExecutorService를 이용한 스레드 자원 관리
 * 
 * [목표]
 * 1. 매번 스레드를 생성하는 오버헤드 대신 스레드 풀(Thread Pool)을 사용하는 이유를 이해합니다.
 * 2. ExecutorService를 선언하고 작업을 제출하여 병렬 처리해 봅니다.
 * 3. 작업을 완료한 스레드 풀을 안전하게 종료(Graceful Shutdown)하는 방법을 배웁니다.
 */
public class Ex04_ExecutorService {

    public static void main(String[] args) {
        System.out.println("=== 실습 4: 스레드 풀을 활용한 태스크 병렬 처리 ===");

        // TODO: 3개의 고정된 스레드를 가지는 스레드 풀을 생성하세요.
        // 힌트: Executors.newFixedThreadPool(int nThreads) 메소드를 사용합니다.
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        // 스레드 풀에 제출할 10개의 작업
        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            threadPool.execute(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("[" + threadName + "] 작업 " + taskId + " 시작");
                try {
                    // 비즈니스 로직 수행 시뮬레이션
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("[" + threadName + "] 작업 " + taskId + " 중단됨");
                }
                System.out.println("[" + threadName + "] 작업 " + taskId + " 완료");
            });
        }

        // TODO: 스레드 풀을 종료하는 코드를 작성하세요.
        // 힌트:
        // 1. threadPool.shutdown()을 호출하여 새로운 작업 수락을 중단하고 기존 작업 완료 처리를 시작합니다.
        // 2. threadPool.awaitTermination(...)을 사용하여 작업이 다 끝날 때까지 대기합니다. (예: 최대 5초 대기)
        // 3. 만약 대기 시간 내에 종료되지 못했다면 강제 종료(shutdownNow())를 시도하는 안전 장치를 구성해 보세요.

        threadPool.shutdown(); // 새로운 작업을 받지 않고 현재 큐에 대기 중인 작업들까지 완료 처리 시작

        try {
            // 모든 작업이 종료될 때까지 대기 (최대 5초)
            if (!threadPool.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("시간 초과로 인해 아직 완료되지 않은 작업들이 있어 강제 종료를 시작합니다.");
                threadPool.shutdownNow();
            } else {
                System.out.println("모든 작업이 성공적으로 종료되어 스레드 풀을 안전하게 해제했습니다.");
            }
        } catch (InterruptedException e) {
            System.err.println("종료 대기 중 인터럽트 발생, 강제 종료를 처리합니다.");
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
