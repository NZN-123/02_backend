/**
 * 3단계: 메서드 선언, 시그니처, 가변인자, 오버로딩
 *
 * 학습 포인트
 * - 매개변수는 선언부의 변수이고 인자는 호출할 때 전달하는 값입니다.
 * - 메서드 시그니처는 메서드명과 매개변수 타입의 조합으로 구분됩니다.
 * - 반환 타입, 접근 제어자, static 여부, 매개변수 이름은 시그니처에 포함되지 않습니다.
 * - 가변인자는 마지막 매개변수로 하나만 선언하며 내부에서는 배열로 다룹니다.
 */
public class Ex03_MethodSignature {
    static void print(String message) {
        System.out.println(message);
    }

    static int add(int left, int right) {
        return left + right;
    }

    static double add(double left, double right) {
        return left + right;
    }

    static String describe(String name, int age) {
        return name + "(" + age + "세)";
    }

    static String describe(int age, String name) {
        return age + "세 " + name;
    }

    static int sum(String label, int... numbers) {
        int total = 0;
        for (int number : numbers) {
            total += number;
        }
        print(label + total);
        return total;
    }

    /*
     * 미션 1: 반환 타입만 다른 아래 메서드를 선언할 수 없는 이유를 설명하세요.
     * static double add(int left, int right) { return left + right; }
     *
     * 미션 2: 아래 선언이 잘못된 이유를 설명하세요.
     * static void invalid(int... numbers, String label) {}
     */
    public static void main(String[] args) {
        System.out.println("=== 메서드 시그니처와 오버로딩 ===");
        print("void 메서드 호출");
        System.out.println("add(int, int): " + add(10, 20));
        System.out.println("add(double, double): " + add(1.5, 2.5));
        System.out.println(describe("김민수", 20));
        System.out.println(describe(21, "이지원"));

        sum("인자 0개: ");
        sum("인자 3개: ", 10, 20, 30);
        int[] scores = {80, 90, 100};
        int result = sum("배열 전달: ", scores);
        System.out.println("반환값 저장: " + result);
    }
}
