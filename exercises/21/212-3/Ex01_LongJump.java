/**
 * 문제: 멀리 뛰기 (Programmers Lv. 2)
 * 링크: https://school.programmers.co.kr/learn/courses/30/lessons/12914
 *
 * [개념: 동적계획법 (Dynamic Programming)]
 * - 큰 문제를 작은 문제들로 분할하여 해결한 뒤, 그 결과를 기록(Memoization)하여 재사용하는 기법입니다.
 * - N번째 칸에 도달하는 최후의 방법은 2가지입니다:
 *   1) N-1번째 칸에서 1칸을 뛰기
 *   2) N-2번째 칸에서 2칸을 뛰기
 * - 즉, dp[N] = dp[N-1] + dp[N-2] 라는 피보나치 수열과 같은 점화식을 이끌어낼 수 있습니다.
 * - 문제의 요구사항대로 계산 값이 커지는 것을 방지하기 위해 매 덧셈 과정마다 1234567의 나머지 연산을 취해 오버플로우를 차단합니다.
 */
public class Ex01_LongJump {

    /**
     * n번째 칸까지 도달하는 방법의 수를 1234567로 나눈 나머지를 구하는 메서드
     *
     * @param n 멀리뛰기 칸 수 (1 <= n <= 2000)
     * @return 방법의 수를 1234567로 나눈 나머지
     */
    public static long solution(int n) {
        // n이 1인 경우도 안전하게 커버하기 위해 n + 2 크기로 선언
        long[] dp = new long[n + 2];
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++) {
            // 점화식 대입 및 매 단계마다 나머지 연산 처리
            dp[i] = (dp[i - 1] + dp[i - 2]) % 1234567;
        }

        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println("=== 멀리 뛰기 디버깅 및 테스트 ===");

        // 테스트 케이스 1
        int n1 = 4;
        long expected1 = 5;
        long result1 = solution(n1);
        System.out.printf("Test Case 1: %s (결과: %d, 기대값: %d)\n",
                (result1 == expected1 ? "PASS" : "FAIL"), result1, expected1);

        // 테스트 케이스 2
        int n2 = 3;
        long expected2 = 3;
        long result2 = solution(n2);
        System.out.printf("Test Case 2: %s (결과: %d, 기대값: %d)\n",
                (result2 == expected2 ? "PASS" : "FAIL"), result2, expected2);
    }
}
