/**
 * Java의 조건문(if, switch)과 Java 12+ 도입된 switch 표현식(Switch Expressions)을 다룹니다.
 */
public class Ex01_ConditionAndSwitch {
    public static void main(String[] args) {
        // 1. if문과 Strict Boolean 제약
        System.out.println("--- 1. if문 조건식 제약 ---");
        int score = 85;
        
        // 자바에서는 조건식 결과가 반드시 boolean 타입이어야 합니다.
        // JS처럼 if (score) 나 if (1) 같은 Truthy/Falsy 평가는 컴파일 에러를 유발합니다.
        if (score >= 80) {
            System.out.println("합격입니다.");
        } else {
            System.out.println("불합격입니다.");
        }

        // 2. 전통적인 switch문과 Fall-through 현상
        System.out.println("\n--- 2. 전통적인 switch문 (Fall-through) ---");
        int month = 3;
        String season = "";
        
        switch (month) {
            case 3:
            case 4:
            case 5:
                season = "봄";
                break; // break를 생략하면 다음 case까지 연속 실행되는 Fall-through가 발생합니다.
            case 6: case 7: case 8:
                season = "여름";
                break;
            default:
                season = "기타 계절";
        }
        System.out.println(month + "월은 " + season + "입니다.");

        // 3. 모던 switch 표현식 (Switch Expressions) - Java 12+
        System.out.println("\n--- 3. 모던 switch 표현식 ---");
        String grade = "B";
        
        // switch 블록 전체가 하나의 '값'으로 평가되어 변수에 대입할 수 있습니다.
        // break 키워드 대신 화살표 연산자(->)를 사용하여 Fall-through가 자동으로 방지됩니다.
        // 여러 케이스를 쉼표(,)로 나열할 수 있습니다.
        String evaluation = switch (grade) {
            case "A", "B" -> "우수한 성적";
            case "C"      -> "보통의 성적";
            case "D", "F" -> "재수강 대상";
            default       -> "잘못된 학점";
        };
        System.out.println("학점: " + grade + " -> 평가 결과: " + evaluation);
    }
}
