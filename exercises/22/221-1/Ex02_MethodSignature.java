/**
 * 실습 221-1. 2단계: 메서드 선언과 시그니처
 *
 * [학습 포인트]
 * 1. 메서드는 [접근 제어자] [static] 반환타입 메서드명(매개변수 목록) 형태로 선언합니다.
 * 2. 매개변수(Parameter)는 선언부의 변수이고, 인자(Argument)는 호출할 때 전달하는 실제 값입니다.
 * 3. Java에서 메서드 시그니처는 메서드 이름과 매개변수 타입 및 순서로 결정됩니다.
 * 4. 반환 타입, 접근 제어자, static 여부, 매개변수 이름은 메서드 시그니처에 포함되지 않습니다.
 * 5. 가변인자(Varargs)는 0개 이상의 값을 배열처럼 받으며, 매개변수 목록의 마지막에 하나만 선언할 수 있습니다.
 */
public class Ex02_MethodSignature {

    // 반환값이 없는 메서드는 반환 타입에 void를 작성합니다.
    public static void printMessage(String message) {
        System.out.println(message);
    }

    // int 두 개를 매개변수로 받고 int 값을 반환합니다.
    public static int add(int left, int right) {
        return left + right;
    }

    // 메서드 이름은 add로 같지만 매개변수 타입이 달라 별개의 시그니처가 됩니다.
    public static double add(double left, double right) {
        return left + right;
    }

    // 매개변수의 타입뿐 아니라 순서도 시그니처를 구분합니다.
    public static String formatUser(String name, int age) {
        return name + "(" + age + "세)";
    }

    public static String formatUser(int age, String name) {
        return age + "세 " + name;
    }

    // prefix는 일반 매개변수, numbers는 가변인자입니다.
    // 가변인자는 내부에서 int[] 배열과 동일하게 사용할 수 있습니다.
    public static int sum(String prefix, int... numbers) {
        int total = 0;

        for (int number : numbers) {
            total += number;
        }

        System.out.println(prefix + total);
        return total;
    }

    /*
     * [컴파일 오류 예시 1] 반환 타입만 바꾸어 오버로딩할 수 없습니다.
     * 아래 메서드는 add(int, int)의 시그니처와 같으므로 선언할 수 없습니다.
     * public static double add(int left, int right) {
     *     return left + right;
     * }
     *
     * [컴파일 오류 예시 2] 가변인자는 반드시 마지막 매개변수여야 합니다.
     * public static void invalid(int... numbers, String label) {
     * }
     */

    public static void main(String[] args) {
        System.out.println("=== 2단계: 메서드 선언과 시그니처 실습 ===");

        // "메서드 호출"은 시그니처에 맞는 인자를 전달하는 과정입니다.
        printMessage("반환값이 없는 void 메서드");

        int integerResult = add(10, 20);
        double doubleResult = add(1.5, 2.5);
        System.out.println("add(int, int) 결과: " + integerResult);
        System.out.println("add(double, double) 결과: " + doubleResult);

        System.out.println("formatUser(String, int): " + formatUser("김민수", 20));
        System.out.println("formatUser(int, String): " + formatUser(21, "이지원"));

        System.out.println("\n--- 가변인자 호출 ---");
        sum("인자 0개 합계: ");
        sum("인자 3개 합계: ", 10, 20, 30);

        // 배열을 직접 전달할 수도 있습니다.
        int[] scores = {80, 90, 100};
        int scoreTotal = sum("배열로 전달한 합계: ", scores);
        System.out.println("반환값을 저장한 결과: " + scoreTotal);
    }
}
