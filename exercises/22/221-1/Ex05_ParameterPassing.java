/**
 * 5단계: Java Parameter Passing
 *
 * 학습 포인트
 * - Java는 기본 타입과 참조 타입 모두 값을 복사해 전달합니다.
 * - 기본 타입은 실제 값, 참조 타입은 객체를 가리키는 참조값이 복사됩니다.
 * - 복사된 참조로 같은 객체의 상태는 변경할 수 있습니다.
 * - 매개변수에 새 객체를 대입해도 호출자의 참조 변수는 바뀌지 않습니다.
 */
class Score {
    int value;

    Score(int value) {
        this.value = value;
    }
}

public class Ex05_ParameterPassing {
    static void changeNumber(int number) {
        number = 999;
        System.out.println("메서드 내부 number: " + number);
    }

    static void changeState(Score score) {
        score.value = 999;
        System.out.println("메서드 내부 score.value: " + score.value);
    }

    static void changeReference(Score score) {
        score = new Score(777);
        System.out.println("메서드 내부 새 객체 값: " + score.value);
    }

    public static void main(String[] args) {
        System.out.println("=== Java는 항상 Call by Value ===");

        int number = 100;
        changeNumber(number);
        System.out.println("호출 후 number: " + number);

        Score first = new Score(100);
        changeState(first);
        System.out.println("상태 변경 후 first.value: " + first.value);

        Score second = new Score(100);
        changeReference(second);
        System.out.println("참조 재대입 후 second.value: " + second.value);
    }
}
