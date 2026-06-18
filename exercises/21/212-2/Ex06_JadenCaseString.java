/**
 * 문제: JadenCase 문자열 만들기 (Programmers Lv. 2)
 * 링크: https://school.programmers.co.kr/learn/courses/30/lessons/12951
 *
 * [개념: 문자열 처리 (String Processing)]
 * - 공백문자가 연속해서 나타나거나, 문자열 맨 앞/뒤에 공백이 존재할 수 있습니다.
 * - 이 때문에 공백문자를 단순 구분자로 사용해 단어 단위로 자르면 공백의 위치와 개수가 왜곡됩니다.
 * - 따라서 문자열의 모든 글자를 한 자씩 순회하면서, 이전 글자가 공백(" ")이었는지 여부를 나타내는 플래그(isFirst)를 통해 대소문자 처리를 정밀 제어합니다.
 * - 자바에서 루프 안의 잦은 문자열 더하기 연산은 StringBuilder 클래스를 활용해 힙 메모리 낭비와 시간 초과를 예방합니다.
 */
public class Ex06_JadenCaseString {

    /**
     * 문자열을 JadenCase로 변환하는 메서드
     *
     * @param s 원본 문자열
     * @return JadenCase로 변환된 문자열
     */
    public static String solution(String s) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true; // 첫 글자 여부 감지용 플래그

        // 문자 단위로 분할하여 순회
        for (String token : s.split("")) {
            // 첫 글자이면 대문자로, 아니면 소문자로 치환하여 추가
            sb.append(isFirst ? token.toUpperCase() : token.toLowerCase());

            // 현재 문자가 공백인 경우, 바로 다음 문자는 단어의 첫 글자가 됨
            isFirst = token.equals(" ");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("=== JadenCase 문자열 만들기 디버깅 및 테스트 ===");

        // 테스트 케이스 1
        String s1 = "3people unFollowed me";
        String expected1 = "3people Unfollowed Me";
        String result1 = solution(s1);
        System.out.printf("Test Case 1: %s (결과: \"%s\", 기대값: \"%s\")\n",
                (result1.equals(expected1) ? "PASS" : "FAIL"), result1, expected1);

        // 테스트 케이스 2
        String s2 = "for the last week";
        String expected2 = "For The Last Week";
        String result2 = solution(s2);
        System.out.printf("Test Case 2: %s (결과: \"%s\", 기대값: \"%s\")\n",
                (result2.equals(expected2) ? "PASS" : "FAIL"), result2, expected2);

        // 테스트 케이스 3 (연속된 공백 처리 검증)
        String s3 = "  hello   world  ";
        String expected3 = "  Hello   World  ";
        String result3 = solution(s3);
        System.out.printf("Test Case 3: %s (결과: \"%s\", 기대값: \"%s\")\n",
                (result3.equals(expected3) ? "PASS" : "FAIL"), result3, expected3);
    }
}
