/**
 * 실습 221-1. 6단계: 메서드 시그니처 & 가변인자(Varargs)
 * 
 * [학습 포인트]
 * 1. 메서드 시그니처(Method Signature)는 '메서드 이름'과 '매개변수 리스트(타입, 개수, 순서)'의 조합입니다.
 * 2. 반환 타입(Return Type)과 접근 제어자는 메서드 시그니처에 포함되지 않습니다. 따라서 반환 타입만 다른 오버로딩은 불가능합니다.
 * 3. 가변인자(Varargs, 'Type...')는 인자의 개수를 동적으로 지정할 수 있게 해주며, 내부적으로 '배열'로 컴파일됩니다.
 * 4. 가변인자는 매개변수 선언부의 '가장 마지막'에 위치해야 하며, 메서드당 하나만 선언할 수 있습니다.
 * 5. 가변인자가 포함된 메서드를 오버로딩할 때 컴파일러가 호출 대상을 구분하지 못해 모호성(Ambiguity) 에러가 날 수 있으니 주의해야 합니다.
 */
public class Ex06_MethodSignatureAndVarargs {

    // 기본 메서드
    public int calculate(int a, int b) {
        return a + b;
    }

    // 1. 오버로딩 성공: 매개변수 개수가 다름 (시그니처 다름)
    public int calculate(int a, int b, int c) {
        return a + b + c;
    }

    // 2. 오버로딩 성공: 매개변수 타입이 다름 (시그니처 다름)
    public double calculate(double a, double b) {
        return a + b;
    }

    // [미션 1] 아래 주석을 풀어 컴파일 에러를 직접 관찰해 보세요.
    // 반환 타입만 다르고 이름과 매개변수 리스트가 완전히 같으므로, 메서드 시그니처가 중복되어 오버로딩에 실패합니다.
    /*
    public double calculate(int a, int b) {
        return (double)(a + b); // 컴파일 에러! 이미 calculate(int, int)가 정의되어 있음.
    }
    */

    // 3. 가변인자(Varargs) 메서드
    // 가변인자는 호출 시 0개 이상의 인자를 쉼표로 나열하여 전달할 수 있습니다.
    public static int sumAll(int... numbers) {
        // 내부적으로 numbers는 'int[]' 배열로 취급됩니다.
        int total = 0;
        for (int num : numbers) {
            total += num;
        }
        return total;
    }

    // 4. 가변인자와 일반 매개변수 혼용 규칙
    // 가변인자는 반드시 매개변수의 '맨 마지막'에 와야 합니다.
    public static void printReport(String title, int... scores) {
        System.out.print(title + " -> ");
        for (int score : scores) {
            System.out.print(score + " ");
        }
        System.out.println();
    }

    // [미션 2] 아래 주석을 해제하여 가변인자의 선언 제약에 관한 컴파일 에러를 관찰해 보세요.
    /*
    public static void invalidVarargs1(int... scores, String title) {
        // 컴파일 에러: 가변인자(Varargs)는 반드시 매개변수 목록의 마지막에 위치해야 합니다.
    }

    public static void invalidVarargs2(int... a, double... b) {
        // 컴파일 에러: 가변인자는 매개변수 목록에 오직 하나만 존재해야 합니다.
    }
    */

    // 5. 가변인자 오버로딩과 모호성(Ambiguity) 에러 주의
    public static void display(String... messages) {
        System.out.println("[가변 문자열 호출]");
        for (String msg : messages) {
            System.out.print(msg + " ");
        }
        System.out.println();
    }

    public static void display(int... values) {
        System.out.println("[가변 정수 호출]");
        for (int val : values) {
            System.out.print(val + " ");
        }
        System.out.println();
    }

    // [미션 3] 아래 주석을 풀고 호출을 실행하려 할 때 발생하는 모호성 컴파일 에러를 확인해 보세요.
    /*
    public static void display(String first, String... rest) {
        System.out.println("[첫번째 고정 가변 호출]");
    }
    */

    public static void main(String[] args) {
        System.out.println("=== 6단계: 메서드 시그니처와 가변인자 실습 ===");
        Ex06_MethodSignatureAndVarargs test = new Ex06_MethodSignatureAndVarargs();

        // 1. 메서드 오버로딩 호출 확인
        System.out.println("calculate(10, 20): " + test.calculate(10, 20));
        System.out.println("calculate(10, 20, 30): " + test.calculate(10, 20, 30));
        System.out.println("calculate(10.5, 20.5): " + test.calculate(10.5, 20.5));

        // 2. 가변인자 호출 확인
        System.out.println("\n--- 가변인자 sumAll 호출 ---");
        System.out.println("sumAll() 0개 인자: " + sumAll());
        System.out.println("sumAll(1, 2, 3) 3개 인자: " + sumAll(1, 2, 3));
        System.out.println("sumAll(new int[]{10, 20, 30}) 배열 전달: " + sumAll(new int[]{10, 20, 30})); // 배열 직접 대입도 가능

        // 3. 가변인자와 일반 매개변수 혼용 호출
        System.out.println("\n--- 가변인자 혼용 호출 ---");
        printReport("기말고사 수학 점수", 95, 80, 88, 100);
        printReport("빈 성적표"); // 가변인자는 생략 가능 (0개 인자)

        // 4. 가변인자 오버로딩 모호성 테스트
        System.out.println("\n--- 가변인자 오버로딩 ---");
        display("Hello", "World", "Java");
        display(1, 2, 3, 4);
        
        // 만약 display() 형태로 인자를 아무것도 주지 않고 호출한다면?
        // display(); // 컴파일 에러! display(String...)과 display(int...) 중 어떤 것을 호출할지 컴파일러가 판단 불가능 (Ambiguous call)
        System.out.println("(아무 인자 없이 display() 호출 시 컴파일러가 대상을 판별할 수 없어 에러가 납니다.)");
    }
}
