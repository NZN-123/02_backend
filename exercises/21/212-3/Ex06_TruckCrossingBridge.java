/**
 * 문제: 다리를 지나는 트럭 (Programmers Lv. 2)
 * 링크: https://school.programmers.co.kr/learn/courses/30/lessons/42583
 *
 * [개념: 큐 (Queue) 시뮬레이션]
 * - 다리라는 한정된 크기의 공간을 고정 크기(bridge_length)의 큐로 시뮬레이션합니다.
 * - 다리 위에 트럭이 없거나 움직이는 빈 공간은 0으로 채워 넣어 큐의 전체 길이를 고정으로 유지시킵니다.
 * - 매 초마다 큐에서 맨 앞 원소(트럭 무게 혹은 0)를 탈출시켜 다리 위 누적 무게(totalWeight)를 차감합니다.
 * - 대기 중인 다음 트럭이 다리 위에 올라왔을 때 제한 무게(weight)를 만족하면 트럭을 진입(offer(truck_weight))시키고,
 *   그렇지 않으면 0을 진입(offer(0))시켜 이미 다리 위에 올라온 트럭들을 앞으로 전진시킵니다.
 * - 대기 트럭이 모두 다리에 올라가면, 마지막 트럭이 다리를 통과하는 데 걸리는 시간(bridge_length)을 더하여 최종 시간을 리턴합니다.
 */
import java.util.ArrayDeque;
import java.util.Queue;

public class Ex06_TruckCrossingBridge {

    /**
     * 모든 트럭이 다리를 건너기 위해 필요한 최소 시간을 구하는 메서드
     *
     * @param bridge_length 다리 길이
     * @param weight 다리가 견딜 수 있는 무게
     * @param truck_weights 트럭들의 무게 배열
     * @return 모든 트럭이 다리를 건너는 데 필요한 시간
     */
    public static int solution(int bridge_length, int weight, int[] truck_weights) {
        Queue<Integer> bridge = new ArrayDeque<>();
        for (int i = 0; i < bridge_length; i++) {
            bridge.offer(0); // 최초 다리 위를 0(빈 공간)으로 채워 고정 크기 유지
        }

        int time = 0;
        int totalWeight = 0; // 다리 위의 현재 누적 무게
        int index = 0; // 대기 중인 트럭 배열 인덱스

        while (index < truck_weights.length) {
            time++;
            // 1초가 지나 다리 끝에 도달한 트럭(혹은 빈 공간 0) 탈출
            totalWeight -= bridge.poll();

            // 대기 중인 다음 트럭이 올라올 수 있는지 여부 검사
            if (totalWeight + truck_weights[index] <= weight) {
                bridge.offer(truck_weights[index]);
                totalWeight += truck_weights[index];
                index++; // 다음 대기 트럭으로 타겟 지정
            } else {
                bridge.offer(0); // 무게 초과로 트럭 진입 불가 시 빈 공간 0 추가
            }
        }

        return time + bridge_length; // 대기 차량 소진 시간 + 마지막 차량의 다리 통과 소요 시간
    }

    public static void main(String[] args) {
        System.out.println("=== 다리를 지나는 트럭 디버깅 및 테스트 ===");

        // 테스트 케이스 1
        int bridge_length1 = 2;
        int weight1 = 10;
        int[] truck_weights1 = {7, 4, 5, 6};
        int expected1 = 8;
        int result1 = solution(bridge_length1, weight1, truck_weights1);
        System.out.printf("Test Case 1: %s (결과: %d, 기대값: %d)\n",
                (result1 == expected1 ? "PASS" : "FAIL"), result1, expected1);

        // 테스트 케이스 2
        int bridge_length2 = 100;
        int weight2 = 100;
        int[] truck_weights2 = {10};
        int expected2 = 101;
        int result2 = solution(bridge_length2, weight2, truck_weights2);
        System.out.printf("Test Case 2: %s (결과: %d, 기대값: %d)\n",
                (result2 == expected2 ? "PASS" : "FAIL"), result2, expected2);

        // 테스트 케이스 3
        int bridge_length3 = 100;
        int weight3 = 100;
        int[] truck_weights3 = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
        int expected3 = 110;
        int result3 = solution(bridge_length3, weight3, truck_weights3);
        System.out.printf("Test Case 3: %s (결과: %d, 기대값: %d)\n",
                (result3 == expected3 ? "PASS" : "FAIL"), result3, expected3);
    }
}
