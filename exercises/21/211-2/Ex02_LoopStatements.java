/**
 * Java의 반복문(while, for)과 break/continue 제어자 및 중첩 루프를 실습합니다.
 */
public class Ex02_LoopStatements {
    public static void main(String[] args) {
        // 1. while문과 break / continue 제어자
        System.out.println("--- 1. while문과 제어자 ---");
        int num = 0;
        while (num < 10) {
            num++;
            
            // 3의 배수이면 이번 루프의 남은 코드를 건너뛰고 다음 조건 검사로 진행
            if (num % 3 == 0) {
                System.out.println("[continue] 3의 배수 " + num + " 건너뜜");
                continue;
            }
            
            System.out.println("현재 값: " + num);
            
            // 8에 도달하면 루프 전체를 즉시 탈출
            if (num >= 8) {
                System.out.println("[break] 8에 도달하여 루프 종료");
                break;
            }
        }

        // 2. 일반 for문과 루프 변수 타입 지정
        System.out.println("\n--- 2. 일반 for문 ---");
        // JS의 let i 대신 int i와 같은 명시적인 변수 타입 지정이 필수적입니다.
        // 이 루프 변수 i는 for문 블록 내부 스코프에서만 생존합니다.
        for (int i = 1; i <= 5; i++) {
            System.out.println("for문 카운트: " + i);
        }

        // 3. 중첩 for문 (다차원 루프)
        System.out.println("\n--- 3. 중첩 for문 (구구단 2단) ---");
        for (int dan = 2; dan <= 2; dan++) {
            System.out.println("== " + dan + "단 ==");
            for (int i = 1; i <= 9; i++) {
                System.out.println(dan + " * " + i + " = " + (dan * i));
            }
        }
    }
}
