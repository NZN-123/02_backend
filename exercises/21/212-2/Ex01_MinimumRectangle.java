/**
 * 문제: 최소직사각형 (Programmers Lv. 1)
 * 링크: https://school.programmers.co.kr/learn/courses/30/lessons/86491
 *
 * [개념: 완전탐색 (Exhaustive Search)]
 * - 가능한 모든 경우를 다 탐색하여 답을 내는 직관적인 기법입니다.
 * - 본 문제에서는 지갑에 명함을 수납할 수 있는 최소 크기를 구하기 위해,
 *   명함의 방향을 회전할 수 있다는 조건과 전체 최대값을 수용해야 하는 조건을 결합하여 단순 O(N)으로 해결합니다.
 */
public class Ex01_MinimumRectangle {

    /**
     * 모든 명함을 수용할 수 있는 가장 작은 지갑 크기를 구하는 메서드
     *
     * @param sizes 명함들의 [가로, 세로] 크기 배열
     * @return 지갑의 최소 크기 (가로 x 세로)
     */
    public static int solution(int[][] sizes) {
        int maxW = 0; // 가로와 세로 중 더 긴 변들의 최댓값
        int maxH = 0; // 가로와 세로 중 더 짧은 변들의 최댓값

        for (int[] size : sizes) {
            // 가로/세로 중 긴 쪽을 w, 짧은 쪽을 h로 모으기 (회전 조건 고려)
            int w = Math.max(size[0], size[1]);
            int h = Math.min(size[0], size[1]);

            // 전체 명함을 모두 수용할 수 있는 각 변의 최댓값 갱신
            maxW = Math.max(maxW, w);
            maxH = Math.max(maxH, h);
        }

        return maxW * maxH;
    }

    public static void main(String[] args) {
        System.out.println("=== 최소직사각형 디버깅 및 테스트 ===");

        // 테스트 케이스 1
        int[][] sizes1 = {{60, 50}, {30, 70}, {60, 30}, {80, 40}};
        int expected1 = 4000;
        int result1 = solution(sizes1);
        System.out.printf("Test Case 1: %s (결과: %d, 기대값: %d)\n",
                (result1 == expected1 ? "PASS" : "FAIL"), result1, expected1);

        // 테스트 케이스 2
        int[][] sizes2 = {{10, 7}, {12, 3}, {8, 15}, {14, 7}, {5, 15}};
        int expected2 = 120;
        int result2 = solution(sizes2);
        System.out.printf("Test Case 2: %s (결과: %d, 기대값: %d)\n",
                (result2 == expected2 ? "PASS" : "FAIL"), result2, expected2);

        // 테스트 케이스 3
        int[][] sizes3 = {{14, 4}, {19, 6}, {6, 16}, {18, 7}, {7, 11}};
        int expected3 = 133;
        int result3 = solution(sizes3);
        System.out.printf("Test Case 3: %s (결과: %d, 기대값: %d)\n",
                (result3 == expected3 ? "PASS" : "FAIL"), result3, expected3);
    }
}
