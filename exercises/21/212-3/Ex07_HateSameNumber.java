/**
 * 문제: 같은 숫자는 싫어 (Programmers Lv. 1)
 * 링크: https://school.programmers.co.kr/learn/courses/30/lessons/12906
 *
 * [개념: 스택 (Stack)]
 * - 스택(Stack)은 마지막에 입력된 데이터가 먼저 방출되는 후입선출(LIFO) 형태의 선형 자료구조입니다.
 * - 본 문제에서는 중복된 숫자를 필터링하기 위해 스택의 최상단(최신 삽입 데이터)을 비교해 중복을 제거합니다.
 * - 스택이 비어있거나, 스택 최상단에 있는 숫자와 방금 읽은 숫자가 다를 때에만 스택에 적재(push)합니다.
 * - 자바에서는 전통적인 Stack 클래스보다 성능이 좋고 스택 연산을 대체할 수 있는 ArrayDeque 클래스를 사용합니다.
 */
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Ex07_HateSameNumber {

    /**
     * 연속적으로 나타나는 숫자는 제거하고 남은 수들을 반환하는 메서드
     *
     * @param arr 숫자 배열
     * @return 연속 중복이 제거된 배열
     */
    public static int[] solution(int[] arr) {
        Deque<Integer> stack = new ArrayDeque<>();

        for (int num : arr) {
            // 스택이 비어 있거나 최상단(마지막 삽입값)에 있는 숫자와 다를 때만 스택에 적재
            if (stack.isEmpty() || stack.peekLast() != num) {
                stack.addLast(num);
            }
        }

        // 저장된 스택을 순서대로 정수형 배열로 변환
        int[] answer = new int[stack.size()];
        int idx = 0;
        for (int num : stack) {
            answer[idx++] = num;
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println("=== 같은 숫자는 싫어 디버깅 및 테스트 ===");

        // 테스트 케이스 1
        int[] arr1 = {1, 1, 3, 3, 0, 1, 1};
        int[] expected1 = {1, 3, 0, 1};
        int[] result1 = solution(arr1);
        System.out.printf("Test Case 1: %s (결과: %s, 기대값: %s)\n",
                (Arrays.equals(result1, expected1) ? "PASS" : "FAIL"),
                Arrays.toString(result1), Arrays.toString(expected1));

        // 테스트 케이스 2
        int[] arr2 = {4, 4, 4, 3, 3};
        int[] expected2 = {4, 3};
        int[] result2 = solution(arr2);
        System.out.printf("Test Case 2: %s (결과: %s, 기대값: %s)\n",
                (Arrays.equals(result2, expected2) ? "PASS" : "FAIL"),
                Arrays.toString(result2), Arrays.toString(expected2));
    }
}
