/**
 * 문제: 체육복 (Programmers Lv. 1)
 * 링크: https://school.programmers.co.kr/learn/courses/30/lessons/42862
 *
 * [개념: 탐색 / 탐욕법 (Greedy)]
 * - 탐욕법(Greedy)은 매 순간 가장 이득이 되는 최선의 국소적 선택을 함으로써 최적해에 도달하려는 기법입니다.
 * - 이 문제에서 그리디한 선택이란, 체육복이 없는 학생이 "자신의 앞번호 학생"에게 먼저 빌려보고,
 *   그 다음 "뒷번호 학생"에게 빌려보는 순차적인 양보적 선택을 취하는 것입니다.
 * - 그리디 분배가 성립하려면 체육복이 필요한 도난 학생 배열(lost)과 여벌이 있는 학생 배열(reserve)이 반드시
 *   오름차순 정렬(Arrays.sort()) 되어 있어야 인접한 번호끼리 교차 없이 순차 배분이 가능합니다.
 * - 또한, 여벌 체육복을 가져온 학생이 체육복을 도난당했을 때(중복 상황)는 남에게 빌려줄 수 없고
 *   자신만 체육복을 입을 수 있으므로 대여 대행 로직 실행 전에 필터링을 통해 소거시켜 주어야 합니다.
 */
import java.util.Arrays;

public class Ex03_GymSuit {

    /**
     * 체육수업을 들을 수 있는 학생의 최댓값을 구하는 메서드
     *
     * @param n 전체 학생 수
     * @param lost 체육복을 도난당한 학생들의 번호 배열
     * @param reserve 여벌의 체육복을 가져온 학생들의 번호 배열
     * @return 체육수업을 들을 수 있는 학생 수의 최댓값
     */
    public static int solution(int n, int[] lost, int[] reserve) {
        // 1. 도난 학생과 여벌 학생 배열을 오름차순 정렬 (그리디 방향성 유지 및 인접 매칭을 위함)
        Arrays.sort(lost);
        Arrays.sort(reserve);

        // 2. 여벌 소유자 중 도난당한 학생 제외 처리 (lost 및 reserve 상태를 특수값 -1로 갱신)
        int lostCount = lost.length;
        for (int i = 0; i < lost.length; i++) {
            for (int j = 0; j < reserve.length; j++) {
                if (lost[i] == reserve[j]) {
                    lost[i] = -1;
                    reserve[j] = -1;
                    lostCount--; // 체육복 필요 학생 수 차감 (본인 여벌로 해결)
                    break;
                }
            }
        }

        // 3. 앞번호 학생에게 먼저 빌려보는 그리디 탐색
        for (int i = 0; i < lost.length; i++) {
            if (lost[i] == -1) continue; // 이미 본인 여벌로 해결한 경우는 패스
            for (int j = 0; j < reserve.length; j++) {
                if (reserve[j] == -1) continue; // 이미 남에게 빌려줬거나 본인이 입은 경우 패스

                // 앞번호(lost-1) 혹은 뒷번호(lost+1) 일치 시 대여
                if (reserve[j] == lost[i] - 1 || reserve[j] == lost[i] + 1) {
                    reserve[j] = -1; // 대여 완료 마킹
                    lostCount--;     // 필요한 학생 수 차감
                    break;
                }
            }
        }

        return n - lostCount;
    }

    public static void main(String[] args) {
        System.out.println("=== 체육복 디버깅 및 테스트 ===");

        // 테스트 케이스 1
        int n1 = 5;
        int[] lost1 = {2, 4};
        int[] reserve1 = {1, 3, 5};
        int expected1 = 5;
        int result1 = solution(n1, lost1.clone(), reserve1.clone());
        System.out.printf("Test Case 1: %s (결과: %d, 기대값: %d)\n",
                (result1 == expected1 ? "PASS" : "FAIL"), result1, expected1);

        // 테스트 케이스 2
        int n2 = 5;
        int[] lost2 = {2, 4};
        int[] reserve2 = {3};
        int expected2 = 4;
        int result2 = solution(n2, lost2.clone(), reserve2.clone());
        System.out.printf("Test Case 2: %s (결과: %d, 기대값: %d)\n",
                (result2 == expected2 ? "PASS" : "FAIL"), result2, expected2);

        // 테스트 케이스 3
        int n3 = 3;
        int[] lost3 = {3};
        int[] reserve3 = {1};
        int expected3 = 2;
        int result3 = solution(n3, lost3.clone(), reserve3.clone());
        System.out.printf("Test Case 3: %s (결과: %d, 기대값: %d)\n",
                (result3 == expected3 ? "PASS" : "FAIL"), result3, expected3);
    }
}
