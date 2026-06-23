/**
 * 실습 221-3. 5단계: Wrapper 박싱 성능 경고 & 제네릭 타입 이레이저 제약
 * 
 * [학습 포인트]
 * 1. Wrapper 클래스는 기본 타입을 객체처럼 다룰 수 있게 해주며, 제네릭이나 컬렉션 프레임워크에 필수적입니다.
 * 2. 오토 박싱(Auto-boxing)은 기본 타입을 Wrapper 객체로 자동 변환해주지만, 루프 내의 무분별한 박싱 연산은 대량의 단명 객체를 생성하여 GC 부하와 실행 지연을 초래합니다.
 * 3. 제네릭(Generics)은 컴파일 시점에 강력한 타입 안정성을 검사하여 형변환의 수고를 덜어줍니다.
 * 4. 타입 이레이저(Type Erasure)로 인해, 컴파일 완료 후 바이트코드에서는 제네릭 타입 정보가 완전히 소거됩니다.
 *    이 때문에 런타임에는 'instanceof T', 'new T()', 'new T[10]' 같은 동적 생성 조작이 원천적으로 불가능해집니다.
 */

// 간단한 제네릭 클래스 정의
class Box<T> {
    private T val;

    public void set(T val) {
        this.val = val;
    }

    public T get() {
        return val;
    }

    // [미션 1] 아래 주석을 풀어 타입 이레이저(Type Erasure)의 제약 사항을 관찰해 보세요.
    // 런타임에는 T가 Object 또는 경계 클래스로 치환되어 정보가 사라지므로 아래 행위들은 컴파일 에러를 유발합니다.
    /*
    public void invalidOperations() {
        // 1. T를 사용한 직접 인스턴스화 불가
        T newObj = new T(); // 컴파일 에러!

        // 2. instanceof T 타입 검사 불가
        if (this.val instanceof T) { // 컴파일 에러!
            System.out.println("T의 인스턴스입니다.");
        }

        // 3. 제네릭 배열 직접 생성 불가
        T[] arr = new T[10]; // 컴파일 에러!
    }
    */
}

public class Ex05_WrapperAndGenerics {

    public static void runPrimitivePerformance() {
        long startTime = System.currentTimeMillis();
        
        // 기본 타입 연산 (스택 영역에서 초고속 처리)
        long sum = 0L;
        for (int i = 0; i < 10_000_000; i++) {
            sum += i;
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("[기본 타입 long 연산] 결과: " + sum + " | 소요 시간: " + (endTime - startTime) + "ms");
    }

    public static void runWrapperPerformance() {
        long startTime = System.currentTimeMillis();
        
        // [미션 2] 아래 누적 합계 변수를 Wrapper 클래스인 'Long' 객체로 선언하여 실행 성능을 비교해 보세요.
        // Long sum = 0L; 로 선언하고 루프 내에서 sum += i; 를 할 때마다
        // 내부적으로 오토 박싱(Long.valueOf)이 천만 번 발생하며 힙 영역에 엄청난 임시 객체가 생성/GC됩니다.
        // TODO: 아래 누적 변수를 'Long' 타입으로 변경하여 속도 저하를 실측해 보세요.
        long sum = 0L; 
        
        for (int i = 0; i < 10_000_000; i++) {
            sum += i; // 오토 박싱 & 언박싱 천만 번 발생
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("[Wrapper 타입 Long 연산] 결과: " + sum + " | 소요 시간: " + (endTime - startTime) + "ms");
    }

    public static void main(String[] args) {
        System.out.println("=== 5단계: Wrapper 박싱 성능 비교 및 제네릭 타입 이레이저 실습 ===");

        // 1. 제네릭을 사용한 타입 안정성 확인
        System.out.println("--- 1. 제네릭 타입 안정성 및 형변환 생략 ---");
        Box<String> stringBox = new Box<>();
        stringBox.set("Hello Java Generic");
        
        // 제네릭 덕분에 String 캐스팅 '(String)' 코드가 생략되어 안전하고 간결해집니다.
        String str = stringBox.get(); 
        System.out.println("String Box 값: " + str);

        // 2. 기본 타입 vs Wrapper 클래스 오토 박싱 성능 실측
        System.out.println("\n--- 2. 오토 박싱 성능 비교 (천만 번 루프 연산) ---");
        runPrimitivePerformance();
        
        // 미션 2에서 Long 타입으로 변경한 후 실행해 보면, 기본 타입 대비 수 배~수십 배 느린 현상을 직접 목격하게 됩니다.
        runWrapperPerformance();
        System.out.println("\n(연산 집약적인 대규모 루프 내에서는 반드시 기본 타입을 사용하여 메모리 낭비와 GC 부하를 막아야 합니다.)");
        System.out.println("(타입 이레이저 미션을 통해 컴파일 시와 런타임 시의 제네릭 차이점도 소스코드로 확인해 보세요.)");
    }
}
