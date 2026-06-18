/**
 * 문제: 퍼즐 게임 챌린지 (Programmers Lv. 2)
 * 링크: https://school.programmers.co.kr/learn/courses/30/lessons/340212
 *
 * [개념: 이분탐색 / 매개 변수 탐색 (Parametric Search)]
 * - 특정 조건을 만족하는 '최적의 값'을 직접 구하기 어려울 때,
 *   "특정 값(level)으로 조건(제한 시간 limit 내 해결)을 만족하는가?"라는 결정 문제(YES/NO)로 바꾸어
 *   이분탐색을 통해 최적의 경계를 찾아나가는 기법입니다.
 * - 숙련도(level)의 범위는 1부터 난이도의 상한값(100,000)까지 설정할 수 있습니다.
 * - 매 턴마다 중간값(mid)을 임시 숙련도로 가정하고, 해당 숙련도로 전체 퍼즐을 푸는 데 걸리는 시간을 계산합니다.
 *   계산된 시간이 limit 이하이면 숙련도를 더 줄여서 최소 숙련도를 찾기 위해 왼쪽 범위를 탐색하고,
 *   limit을 초과하면 숙련도가 더 높아야 하므로 오른쪽 범위를 탐색합니다.
 */
public class Ex03_PuzzleGameChallenge {

    /**
     * 제한 시간 내에 모든 퍼즐을 해결하기 위한 최소 숙련도를 구하는 메서드
     *
     * @param diffs 각 퍼즐의 난이도 배열
     * @param times 각 퍼즐의 소요 시간 배열
     * @param limit 전체 제한 시간
     * @return 최소 숙련도(level)
     */
    public static int solution(int[] diffs, int[] times, long limit) {
        long left = 1;
        long right = 100000; // 난이도의 상한값 설정 (문제 조건 상 diffs[i] <= 100,000)
        long answer = right;

        while (left <= right) {
            long mid = left + (right - left) / 2; // 테스트해 볼 숙련도(level)

            if (canSolve(diffs, times, limit, mid)) {
                answer = mid;       // 가능한 숙련도 기록
                right = mid - 1;    // 최솟값을 찾기 위해 숙련도를 줄여 봄
            } else {
                left = mid + 1;     // 숙련도가 낮아 제한 시간을 초과하므로 숙련도 높임
            }
        }

        return (int) answer;
    }

    /**
     * 임의의 숙련도(level)로 전체 퍼즐을 제한 시간 내에 풀 수 있는지 검증하는 메서드
     *
     * - 난이도 diffs[i], 소요 시간 times[i], 숙련도 level 일 때
     *   1) diffs[i] <= level 이면: times[i]만큼 시간 소요
     *   2) diffs[i] > level 이면: (diffs[i] - level) 만큼 퍼즐을 틀리게 됨.
     *      이전 퍼즐을 풀기 위해 재도전할 때마다 times[i] + times[i-1] 만큼 시간이 추가 소요되고,
     *      마지막에 성공 시 times[i] 만큼 추가 소요됨.
     *      즉, 소요시간 = (diffs[i] - level) * (times[i] + times[i-1]) + times[i]
     */
    private static boolean canSolve(int[] diffs, int[] times, long limit, long level) {
        long totalTime = 0;
        for (int i = 0; i < diffs.length; i++) {
            if (diffs[i] <= level) {
                totalTime += times[i];
            } else {
                long count = diffs[i] - level;
                // 이전 퍼즐의 인덱스가 0보다 작을 수 없으므로 i > 0 검사
                long prevTime = i > 0 ? times[i - 1] : 0;
                totalTime += count * (times[i] + prevTime) + times[i];
            }
            // 계산 도중 이미 제한 시간을 넘어가면 더 검사할 필요 없이 false 반환
            if (totalTime > limit) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("=== 퍼즐 게임 챌린지 디버깅 및 테스트 ===");

        // 테스트 케이스 1
        int[] diffs1 = {1, 5, 3};
        int[] times1 = {2, 4, 7};
        long limit1 = 30;
        int expected1 = 3;
        int result1 = solution(diffs1, times1, limit1);
        System.out.printf("Test Case 1: %s (결과: %d, 기대값: %d)\n",
                (result1 == expected1 ? "PASS" : "FAIL"), result1, expected1);

        // 테스트 케이스 2
        int[] diffs2 = {1, 4, 4, 2};
        int[] times2 = {6, 3, 8, 2};
        long limit2 = 59;
        int expected2 = 2;
        int result2 = solution(diffs2, times2, limit2);
        System.out.printf("Test Case 2: %s (결과: %d, 기대값: %d)\n",
                (result2 == expected2 ? "PASS" : "FAIL"), result2, expected2);

        // 테스트 케이스 3
        int[] diffs3 = {1, 328, 467, 209, 54};
        int[] times3 = {2, 7, 1, 4, 3};
        long limit3 = 1723;
        int expected3 = 294;
        int result3 = solution(diffs3, times3, limit3);
        System.out.printf("Test Case 3: %s (결과: %d, 기대값: %d)\n",
                (result3 == expected3 ? "PASS" : "FAIL"), result3, expected3);
    }
}
