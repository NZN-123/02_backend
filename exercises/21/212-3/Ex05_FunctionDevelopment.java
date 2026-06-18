/**
 * 문제: 기능개발 (Programmers Lv. 2)
 * 링크: https://school.programmers.co.kr/learn/courses/30/lessons/42586
 *
 * [개념: 큐 (Queue)]
 * - 큐(Queue)는 먼저 삽입된 데이터가 먼저 나가는 선입선출(FIFO, First-In First-Out) 구조의 선형 자료구조입니다.
 * - 본 문제에서는 각 기능의 남은 작업 일수를 계산하여 순서대로 큐에 담아 배포 스케줄링을 진행합니다.
 * - 큐의 맨 첫 번째 작업 완료일을 기준일(first)로 설정하고, 그 다음 작업들 중 기준일 이전에 완료될 수 있는
 *   작업들은 큐에서 동시 배포 처리(count 증가)하여 순차적으로 그룹 배포를 구현합니다.
 * - 자바에서는 Queue 인터페이스의 구현체로 ArrayDeque를 사용하여 빠르게 단방향 입출력을 처리합니다.
 */
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class Ex05_FunctionDevelopment {

    /**
     * 각 배포마다 몇 개의 기능이 배포되는지 구하는 메서드
     *
     * @param progresses 각 기능의 개발 진행도 배열
     * @param speeds 각 기능의 개발 속도 배열
     * @return 각 배포마다 배포되는 기능의 개수 배열
     */
    public static int[] solution(int[] progresses, int[] speeds) {
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < progresses.length; i++) {
            // 남은 진척도를 속도로 나누어 소요되는 일수 올림 처리
            int remain = 100 - progresses[i];
            int day = (remain % speeds[i] == 0) ? (remain / speeds[i]) : (remain / speeds[i] + 1);
            queue.offer(day); // 완료 일수를 큐에 차례로 적재
        }

        List<Integer> list = new ArrayList<>();
        while (!queue.isEmpty()) {
            int first = queue.poll(); // 기준이 되는 첫 작업 완료일
            int count = 1;

            // 기준일보다 작거나 같은 완료 일수를 가진 후속 작업은 묶어서 함께 배포
            while (!queue.isEmpty() && queue.peek() <= first) {
                queue.poll();
                count++;
            }
            list.add(count);
        }

        // 리스트를 기본 int 배열로 형변환
        return list.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        System.out.println("=== 기능개발 디버깅 및 테스트 ===");

        // 테스트 케이스 1
        int[] progresses1 = {93, 30, 55};
        int[] speeds1 = {1, 30, 5};
        int[] expected1 = {2, 1};
        int[] result1 = solution(progresses1, speeds1);
        System.out.printf("Test Case 1: %s (결과: %s, 기대값: %s)\n",
                (Arrays.equals(result1, expected1) ? "PASS" : "FAIL"),
                Arrays.toString(result1), Arrays.toString(expected1));

        // 테스트 케이스 2
        int[] progresses2 = {95, 90, 99, 99, 80, 99};
        int[] speeds2 = {1, 1, 1, 1, 1, 1};
        int[] expected2 = {1, 3, 2};
        int[] result2 = solution(progresses2, speeds2);
        System.out.printf("Test Case 2: %s (결과: %s, 기대값: %s)\n",
                (Arrays.equals(result2, expected2) ? "PASS" : "FAIL"),
                Arrays.toString(result2), Arrays.toString(expected2));
    }
}
