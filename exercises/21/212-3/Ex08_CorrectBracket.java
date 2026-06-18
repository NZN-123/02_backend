/**
 * 문제: 올바른 괄호 (Programmers Lv. 2)
 * 링크: https://school.programmers.co.kr/learn/courses/30/lessons/12909
 *
 * [개념: 스택 (Stack)]
 * - 괄호 매칭은 스택의 가장 전형적인 응용 사례 중 하나입니다.
 * - 문자열을 순차적으로 탐색하면서:
 *   1) 열린 괄호 '('를 만나면 스택에 추가(push)합니다.
 *   2) 닫힌 괄호 ')'를 만나면 스택에서 열린 괄호 하나를 꺼내(pop) 짝을 지어 제거합니다.
 *   3) ')'를 만났는데 스택이 비어 있다면 매칭될 '('가 없으므로 즉시 false를 반환합니다.
 * - 문자열 순회가 끝났을 때 스택이 온전히 비어 있어야 올바른 괄호쌍으로 최종 인정(stack.isEmpty())됩니다.
 */
import java.util.ArrayDeque;
import java.util.Deque;

public class Ex08_CorrectBracket {

    /**
     * 괄호가 올바르게 짝지어져 있는지 확인하는 메서드
     *
     * @param s 괄호 문자열
     * @return 올바른 괄호이면 true, 아니면 false
     */
    public static boolean solution(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(c); // 열린 괄호는 스택에 임시 적재
            } else {
                // 닫힌 괄호 출현 시 매칭할 열린 괄호가 스택에 없다면 비정상 구조
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop(); // 정상 매칭 시 열린 괄호 소거
            }
        }

        // 모든 문자 검사 후 스택이 온전히 비어 있어야 올바른 쌍으로 판정
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println("=== 올바른 괄호 디버깅 및 테스트 ===");

        // 테스트 케이스 1
        String s1 = "()()";
        boolean expected1 = true;
        boolean result1 = solution(s1);
        System.out.printf("Test Case 1: %s (결과: %b, 기대값: %b)\n",
                (result1 == expected1 ? "PASS" : "FAIL"), result1, expected1);

        // 테스트 케이스 2
        String s2 = "(())()";
        boolean expected2 = true;
        boolean result2 = solution(s2);
        System.out.printf("Test Case 2: %s (결과: %b, 기대값: %b)\n",
                (result2 == expected2 ? "PASS" : "FAIL"), result2, expected2);

        // 테스트 케이스 3
        String s3 = ")()(";
        boolean expected3 = false;
        boolean result3 = solution(s3);
        System.out.printf("Test Case 3: %s (결과: %b, 기대값: %b)\n",
                (result3 == expected3 ? "PASS" : "FAIL"), result3, expected3);

        // 테스트 케이스 4
        String s4 = "(()(";
        boolean expected4 = false;
        boolean result4 = solution(s4);
        System.out.printf("Test Case 4: %s (결과: %b, 기대값: %b)\n",
                (result4 == expected4 ? "PASS" : "FAIL"), result4, expected4);
    }
}
