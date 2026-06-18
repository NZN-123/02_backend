/**
 * 문제: 정수 삼각형 (Programmers Lv. 3)
 * 링크: https://school.programmers.co.kr/learn/courses/30/lessons/43105
 *
 * [개념: 동적계획법 (Dynamic Programming)]
 * - 위에서부터 순차적으로 내려오면서 경로의 최대 합을 구하는 상향식(Bottom-up) 격자 DP 문제입니다.
 * - (i, j) 칸으로 들어올 수 있는 경로는 대각선 위 (i-1, j-1) 또는 바로 위 (i-1, j) 두 군데입니다.
 * - 최댓값을 누적하기 위해 dp[i][j] = val + Max(dp[i-1][j-1], dp[i-1][j]) 점화식을 적용합니다.
 * - 메모리 공간을 아끼기 위해 입력받은 triangle 배열을 그대로 복사한 dp 배열 위에서 연산을 덮어씌우는(In-place) 방식으로 진행합니다.
 */
public class Ex02_IntegerTriangle {

    /**
     * 삼각형 꼭대기에서 바닥까지 이어지는 경로 중 거쳐간 숫자의 합의 최댓값을 구하는 메서드
     *
     * @param triangle 삼각형 정보가 담긴 2차원 배열
     * @return 최댓값
     */
    public static int solution(int[][] triangle) {
        // 테스트 케이스 디버깅 시 원본 triangle 배열 훼손을 방지하기 위해 깊은 복사(Deep Copy)를 수행합니다.
        int[][] dp = new int[triangle.length][];
        for (int i = 0; i < triangle.length; i++) {
            dp[i] = triangle[i].clone();
        }

        // 위에서 아래로 누적합을 갱신하는 상향식 DP
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                if (j == 0) {
                    // 왼쪽 맨 끝 열인 경우: 바로 위의 값만 누적 가능
                    dp[i][j] += dp[i - 1][j];
                } else if (j == dp[i].length - 1) {
                    // 오른쪽 맨 끝 열인 경우: 대각선 왼쪽 위의 값만 누적 가능
                    dp[i][j] += dp[i - 1][j - 1];
                } else {
                    // 중간 열인 경우: 대각선 왼쪽 위와 바로 위 중 최댓값을 선택하여 합산
                    dp[i][j] += Math.max(dp[i - 1][j - 1], dp[i - 1][j]);
                }
            }
        }

        // 바닥 행(마지막 row)에서 가장 큰 누적합을 탐색하여 반환
        int answer = 0;
        for (int value : dp[dp.length - 1]) {
            answer = Math.max(answer, value);
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println("=== 정수 삼각형 디버깅 및 테스트 ===");

        // 테스트 케이스 1
        int[][] triangle1 = {
                {7},
                {3, 8},
                {8, 1, 0},
                {2, 7, 4, 4},
                {4, 5, 2, 6, 5}
        };
        int expected1 = 30;
        int result1 = solution(triangle1);
        System.out.printf("Test Case 1: %s (결과: %d, 기대값: %d)\n",
                (result1 == expected1 ? "PASS" : "FAIL"), result1, expected1);
    }
}
