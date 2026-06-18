/**
 * 문제: 입국심사 (Programmers Lv. 3)
 * 링크: https://school.programmers.co.kr/learn/courses/30/lessons/43238
 *
 * [개념: 이분탐색 / 매개 변수 탐색 (Parametric Search)]
 * - "모든 사람이 심사를 마치는 데 걸리는 최솟값"을 구하는 문제로,
 *   시간의 흐름에 따른 결정론적 문제("특정 시간 Time 내에 n명 이상을 심사할 수 있는가?")로 변경하여 해결합니다.
 * - 최악의 경우(가장 느린 심사관에게 n명 모두 심사받는 경우)를 right로 설정하고, 1분을 left로 설정합니다.
 * - mid 시간 동안 각 심사관이 심사할 수 있는 사람의 수 (mid / 심사 소요시간)를 모두 더해, 그 합이 n 이상인지를 판단합니다.
 * - 자료형 오버플로우 주의: 대기자 수 n과 심사 시간의 곱이 32비트 정수 범위를 아득히 초과하므로
 *   시간 관련 모든 변수(left, right, mid, sum)는 64비트 정수형(long)으로 처리해야 합니다.
 */
import java.util.Arrays;

public class Ex04_Immigration {

    /**
     * 모든 사람이 심사를 받는 데 걸리는 최소 시간을 구하는 메서드
     *
     * @param n 입국심사를 대기하는 사람 수
     * @param times 각 심사관의 심사 시간 배열
     * @return 모든 사람이 심사를 마치는 데 걸리는 최소 시간
     */
    public static long solution(int n, int[] times) {
        // 심사 시간 정렬
        Arrays.sort(times);

        long left = 1;
        // 가장 느린 심사관이 모든 대기자를 혼자 처리할 때 걸리는 최악의 시간을 상한값으로 설정
        long right = (long) times[times.length - 1] * n;
        long answer = right;

        while (left <= right) {
            long mid = left + (right - left) / 2; // 검사할 시간
            long sum = 0; // mid 시간 동안 모든 심사관이 처리할 수 있는 최대 인원 수

            for (int time : times) {
                sum += mid / time;
                // n을 초과하여 세는 것은 불필요하므로 오버플로우 방지 및 최적화를 위해 탈출 가능
                if (sum >= n) {
                    break;
                }
            }

            if (sum >= n) {
                answer = mid;      // n명 이상 처리 가능하므로 정답 후보로 기록
                right = mid - 1;   // 더 짧은 시간 내에 처리 가능한지 알아보기 위해 범위 축소
            } else {
                left = mid + 1;    // 시간이 부족하므로 하한값을 늘려 탐색
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println("=== 입국심사 디버깅 및 테스트 ===");

        // 테스트 케이스 1
        int n1 = 6;
        int[] times1 = {7, 10};
        long expected1 = 28;
        long result1 = solution(n1, times1);
        System.out.printf("Test Case 1: %s (결과: %d, 기대값: %d)\n",
                (result1 == expected1 ? "PASS" : "FAIL"), result1, expected1);
    }
}
