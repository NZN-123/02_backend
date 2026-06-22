/**
 * 1단계: JVM 메모리 영역과 변수의 수명
 *
 * 학습 포인트
 * - 클래스 메타데이터와 메서드 코드는 메서드 영역에 적재됩니다.
 * - new로 생성한 인스턴스와 배열은 힙에 적재됩니다.
 * - 메서드 호출마다 스택 프레임이 생기고, 종료되면 제거됩니다.
 * - 멤버 변수는 기본값으로 초기화되지만 지역 변수는 직접 초기화해야 합니다.
 */
public class Ex01_JvmMemoryAndScope {
    int count;
    boolean active;
    String name;

    void printStackFrame(int parameter) {
        int localValue = parameter + 10;
        System.out.println("매개변수: " + parameter);
        System.out.println("지역 변수: " + localValue);

        // 미션: 아래 주석을 해제하고 지역 변수 초기화 규칙을 확인하세요.
        // int uninitialized;
        // System.out.println(uninitialized);
    }

    public static void main(String[] args) {
        System.out.println("=== JVM 메모리 영역과 변수의 수명 ===");

        // stack: example 참조 변수
        // heap: new로 생성한 Ex01_JvmMemoryAndScope 인스턴스
        Ex01_JvmMemoryAndScope example = new Ex01_JvmMemoryAndScope();

        System.out.println("멤버 변수 기본값: "
                + example.count + ", " + example.active + ", " + example.name);

        System.out.println("\n메서드 호출 전");
        example.printStackFrame(20);
        System.out.println("메서드 종료: printStackFrame의 스택 프레임 제거");
    }
}
