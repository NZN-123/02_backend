/**
 * 문제: 카펫 (Programmers Lv. 2)
 * 링크: https://school.programmers.co.kr/learn/courses/30/lessons/42842
 *
 * [개념: 완전탐색 (Exhaustive Search)]
 * - 이 문제 또한 가능한 가로(w)와 세로(h)의 후보들을 차례대로 탐색하며 수학적 조건을 만족하는 해를 도출합니다.
 * - 카펫의 가로 길이를 w, 세로 길이를 h라 할 때:
 *   1) 갈색 격자 수: 2w + 2h - 4 = brown
 *   2) 노란색 격자 수: (w - 2) * (h - 2) = yellow
 *   3) 전체 격자 수: total = brown + yellow = w * h
 * - 세로 길이 h는 최소 3 이상이어야 하며(내부에 노란색이 있으려면 가로/세로 모두 3 이상),
 *   가로(w)가 세로(h)보다 크거나 같으므로 h는 total의 제곱근(Math.sqrt(total)) 이하까지만 순회하면 충분합니다.
 */
import java.util.Arrays;

public class Ex02_Carpet {

    /**
     * 카펫의 가로, 세로 크기를 구하는 메서드
     *
     * @param brown 갈색 격자의 수
     * @param yellow 노란색 격자의 수
     * @return [가로, 세로] 크기 배열
     */
    public static int[] solution(int brown, int yellow) {
        int total = brown + yellow;
        int[] answer = new int[2];

        // 세로는 노란색이 한 칸 이상 둘러싸여야 하므로 최소 3부터 시작
        // 가로 >= 세로이므로 h는 제곱근 이하까지만 순회
        for (int h = 3; h <= (int) Math.sqrt(total); h++) {
            if (total % h == 0) {
                int w = total / h; // 가로 크기 도출

                // 안쪽의 노란색 면적 식과 일치하는지 최종 검증
                if ((w - 2) * (h - 2) == yellow) {
                    answer[0] = w;
                    answer[1] = h;
                    break; // 정답을 찾았으므로 즉시 중단
                }
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        System.out.println("=== 카펫 디버깅 및 테스트 ===");

        // 테스트 케이스 1
        int brown1 = 10, yellow1 = 2;
        int[] expected1 = {4, 3};
        int[] result1 = solution(brown1, yellow1);
        System.out.printf("Test Case 1: %s (결과: %s, 기대값: %s)\n",
                (Arrays.equals(result1, expected1) ? "PASS" : "FAIL"),
                Arrays.toString(result1), Arrays.toString(expected1));

        // 테스트 케이스 2
        int brown2 = 8, yellow2 = 1;
        int[] expected2 = {3, 3};
        int[] result2 = solution(brown2, yellow2);
        System.out.printf("Test Case 2: %s (결과: %s, 기대값: %s)\n",
                (Arrays.equals(result2, expected2) ? "PASS" : "FAIL"),
                Arrays.toString(result2), Arrays.toString(expected2));

        // 테스트 케이스 3
        int brown3 = 24, yellow3 = 24;
        int[] expected3 = {8, 6};
        int[] result3 = solution(brown3, yellow3);
        System.out.printf("Test Case 3: %s (결과: %s, 기대값: %s)\n",
                (Arrays.equals(result3, expected3) ? "PASS" : "FAIL"),
                Arrays.toString(result3), Arrays.toString(expected3));
    }
}
