
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 실습 05: CompletableFuture를 이용한 비동기 작업 파이프라인
 * 
 * [목표]
 * 1. CompletableFuture를 사용하여 논블로킹(Non-blocking) 비동기 작업을 실행합니다.
 * 2. thenApply, thenAccept 등의 메서드를 이용해 비동기 가공 체이닝 파이프라인을 구축합니다.
 * 3. exceptionally를 사용하여 비동기 작업 중 발생한 예외를 안전하게 처리합니다.
 * 4. 여러 비동기 작업의 결과를 결합(Combine)하여 병렬 연산을 완료하는 방법을 익힙니다.
 */
public class Ex05_CompletableFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("=== 실습 5-1: 비동기 작업 체이닝 ===");
        runChainingDemo();

        System.out.println("\n=== 실습 5-2: 비동기 작업 예외 처리 ===");
        runExceptionHandlingDemo();

        System.out.println("\n=== 실습 5-3: 두 비동기 작업 결과 결합 (Combine) ===");
        runCombineDemo();
    }

    /**
     * 비동기로 데이터를 가져와 가공하고 소비하는 파이프라인 실습
     */
    private static void runChainingDemo() throws InterruptedException, ExecutionException {
        // TODO: CompletableFuture.supplyAsync를 활용하여 다음 비동기 작업을 작성하세요.
        // 1. 비동기로 "Hello" 문자열을 반환합니다. (중간에 100ms 대기 시뮬레이션 포함)
        // 2. thenApply를 통해 반환된 문자열 뒤에 " World"를 붙입니다.
        // 3. thenAccept를 통해 최종 결합된 문자열을 출력합니다.
        
        CompletableFuture<Void> pipeline = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Hello";
        })
        .thenApply(text -> text + " World")
        .thenAccept(result -> System.out.println("[파이프라인 결과] " + result));

        // 비동기 작업이 끝날 때까지 대기 (데모 실행 보장을 위해 join 사용)
        pipeline.join();
    }

    /**
     * 비동기 체인 흐름 중에 예외가 발생했을 때 처리하는 실습
     */
    private static void runExceptionHandlingDemo() {
        boolean triggerError = true;

        CompletableFuture<String> exceptionPipeline = CompletableFuture.supplyAsync(() -> {
            if (triggerError) {
                throw new RuntimeException("DB 조회 오류 발생!");
            }
            return "정상 데이터";
        })
        // TODO: exceptionally를 추가하여 예외 발생 시 "대체 데이터(Default Value)"를 반환하도록 작성하세요.
        .exceptionally(throwable -> {
            System.out.println("[예외 감지] " + throwable.getMessage());
            return "대체 데이터(Default Value)";
        });

        System.out.println("[결과 값] " + exceptionPipeline.join());
    }

    /**
     * 각각 독립된 비동기 태스크 2개를 실행하고, 둘 다 완료되면 결과를 합치는 실습
     */
    private static void runCombineDemo() {
        CompletableFuture<Integer> priceFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("[스레드 1] 상품 가격 정보를 조회 중...");
            try { Thread.sleep(200); } catch (InterruptedException e) { }
            return 30000; // 3만원
        });

        CompletableFuture<Double> discountFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("[스레드 2] 할인율 정보를 조회 중...");
            try { Thread.sleep(100); } catch (InterruptedException e) { }
            return 0.1; // 10% 할인
        });

        // TODO: priceFuture와 discountFuture를 결합하여 최종 할인가를 계산하세요.
        // 힌트: priceFuture.thenCombine(discountFuture, (price, discount) -> 계산로직)을 사용합니다.
        
        CompletableFuture<Double> finalPriceFuture = priceFuture.thenCombine(discountFuture, (price, discount) -> {
            System.out.println("[결합 스레드] 두 정보를 취합하여 계산을 완료합니다.");
            return price * (1 - discount);
        });

        System.out.println("최종 결제 금액: " + finalPriceFuture.join() + "원");
    }
}
