/**
 * 문제: 구명보트 (Programmers Lv. 2)
 * 링크: https://school.programmers.co.kr/learn/courses/30/lessons/42885
 *
 * [개념: 탐색 / 탐욕법 (Greedy) & 투 포인터 (Two Pointer)]
 * - 이 문제의 핵심은 "최소 구명보트 개수"를 사용하는 최적 배분 방식입니다.
 * - 보트에는 최대 2명밖에 탈 수 없으므로, 현재 남아있는 사람 중 가장 몸무게가 가벼운 사람(left)과
 *   가장 무거운 사람(right)을 짝지어 태우는 것이 가장 최선의 그리디한 대안입니다.
 * - 탐색을 위해 전체 사람들의 몸무게를 오름차순 정렬(Arrays.sort())합니다.
 * - 양쪽 끝을 가리키는 포인터를 설정하고, 두 사람의 몸무게 합이 제한(limit) 이하이면 둘 다 태우고(left 증가, right 감소),
 *   초과되면 무거운 사람만 탑승(right 감소)시킵니다.
 */
import java.util.Arrays;

public class Ex04_LifeBoat {

    /**
     * 모든 사람을 구출하기 위해 필요한 구명보트 개수의 최솟값을 구하는 메서드
     *
     * @param people 사람들의 몸무게 배열
     * @param limit 구명보트의 무게 제한
     * @return 필요한 구명보트 개수
     */
    public static int solution(int[] people, int limit) {
        // 1. 몸무게 오름차순 정렬
        Arrays.sort(people);

        int left = 0;
        int right = people.length - 1;
        int boats = 0;

        // 2. 투 포인터를 이용한 양 끝단 그리디 매칭
        while (left <= right) {
            // 가장 무거운 사람과 가장 가벼운 사람의 합이 제한 범위 내인지 검증
            if (people[left] + people[right] <= limit) {
                left++; // 가벼운 사람도 함께 태울 수 있으므로 가벼운 사람 포인터 안쪽 이동
            }
            right--; // 무거운 사람은 어차피 혼자 혹은 가벼운 사람과만 탈 수 있으므로 무조건 탑승 및 무거운 사람 포인터 안쪽 이동
            boats++; // 사용한 보트 수 증가
        }

        return boats;
    }

    public static void main(String[] args) {
        System.out.println("=== 구명보트 디버깅 및 테스트 ===");

        // 테스트 케이스 1
        int[] people1 = {70, 50, 80, 50};
        int limit1 = 100;
        int expected1 = 3;
        int result1 = solution(people1.clone(), limit1);
        System.out.printf("Test Case 1: %s (결과: %d, 기대값: %d)\n",
                (result1 == expected1 ? "PASS" : "FAIL"), result1, expected1);

        // 테스트 케이스 2
        int[] people2 = {70, 80, 50};
        int limit2 = 100;
        int expected2 = 3;
        int result2 = solution(people2.clone(), limit2);
        System.out.printf("Test Case 2: %s (결과: %d, 기대값: %d)\n",
                (result2 == expected2 ? "PASS" : "FAIL"), result2, expected2);
    }
}
