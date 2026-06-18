/**
 * 문제: 숫자 문자열과 영단어 (Programmers Lv. 1)
 * 링크: https://school.programmers.co.kr/learn/courses/30/lessons/81301
 *
 * [개념: 문자열 처리 (String Processing)]
 * - 영단어("zero", "one", ...)로 이루어진 일부가 숫자로 혼합된 문자열을 정수형 숫자로 변경합니다.
 * - Java의 String은 불변(Immutable) 객체이므로, replace() 호출 결과 변경된 새로운 String 인스턴스를 계속해서 덮어씌워야(s = s.replace(...)) 합니다.
 * - 이 문제와 같이 치환 규칙이 고정적인 경우에는 1:1 매핑 테이블(배열의 인덱스와 영단어)을 설계하여 단순 루프로 해결하는 것이 매우 효율적입니다.
 */
public class Ex05_NumericStringAndEnglishWord {

    /**
     * 영단어로 섞여있는 문자열을 정수로 변환하는 메서드
     *
     * @param s 영단어가 포함된 문자열
     * @return 변환된 정수
     */
    public static int solution(String s) {
        // 0부터 9까지의 영단어 배열 (인덱스와 1:1 매핑)
        String[] words = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

        for (int i = 0; i < words.length; i++) {
            // 영단어를 매핑된 정수(i)의 문자열 형태로 일괄 치환
            s = s.replace(words[i], Integer.toString(i));
        }

        // 가공 완료된 최종 문자열을 정수형으로 변환하여 반환
        return Integer.parseInt(s);
    }

    public static void main(String[] args) {
        System.out.println("=== 숫자 문자열과 영단어 디버깅 및 테스트 ===");

        // 테스트 케이스 1
        String s1 = "one4seveneight";
        int expected1 = 1478;
        int result1 = solution(s1);
        System.out.printf("Test Case 1: %s (결과: %d, 기대값: %d)\n",
                (result1 == expected1 ? "PASS" : "FAIL"), result1, expected1);

        // 테스트 케이스 2
        String s2 = "23three45sixseven";
        int expected2 = 2334567;
        int result2 = solution(s2);
        System.out.printf("Test Case 2: %s (결과: %d, 기대값: %d)\n",
                (result2 == expected2 ? "PASS" : "FAIL"), result2, expected2);

        // 테스트 케이스 3
        String s3 = "2three45sixseven";
        int expected3 = 234567;
        int result3 = solution(s3);
        System.out.printf("Test Case 3: %s (결과: %d, 기대값: %d)\n",
                (result3 == expected3 ? "PASS" : "FAIL"), result3, expected3);

        // 테스트 케이스 4
        String s4 = "123";
        int expected4 = 123;
        int result4 = solution(s4);
        System.out.printf("Test Case 4: %s (결과: %d, 기대값: %d)\n",
                (result4 == expected4 ? "PASS" : "FAIL"), result4, expected4);
    }
}
