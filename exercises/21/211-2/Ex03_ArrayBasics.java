/**
 * Java 배열의 물리적 제약사항(단일 타입, 고정 크기), 선언/초기화 방식 및 향상된 for문을 다룹니다.
 */
public class Ex03_ArrayBasics {
    public static void main(String[] args) {
        // 1. 배열의 선언, 할당, 리터럴 초기화
        System.out.println("--- 1. 배열의 생성과 초기화 ---");
        
        // 방법 A: 선언 후 메모리 공간 할당 (기본값인 0으로 채워짐)
        int[] scoreArray = new int[3]; 
        scoreArray[0] = 90;
        scoreArray[1] = 80;
        scoreArray[2] = 100;
        
        // 방법 B: 중괄호{} 리터럴을 이용한 선언과 동시에 초기화
        String[] languages = {"Java", "JavaScript", "Python"};

        // 2. 배열의 제약사항과 .length 속성
        System.out.println("\n--- 2. 배열의 제약 및 조회 ---");
        // 제약 1: 단일 타입 보존 - languages 배열에는 오직 String만 저장할 수 있습니다.
        // languages[0] = 123; // 컴파일 에러 발생
        
        // 제약 2: 고정 크기 제한 - scoreArray는 영구적으로 크기 3이며, 런타임에 동적으로 크기를 늘릴 수 없습니다.
        System.out.println("scoreArray 배열의 길이: " + scoreArray.length);
        System.out.println("languages 배열의 길이: " + languages.length);

        // 예외 상황: 할당된 크기를 초과한 인덱스 접근 시 ArrayIndexOutOfBoundsException 발생
        try {
            int outOfBounds = scoreArray[3]; // 인덱스는 0, 1, 2까지만 존재
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("예외 발생: 배열 인덱스 범위를 초과했습니다!");
        }

        // 3. 향상된 for문 (for-each)의 특징
        System.out.println("\n--- 3. 향상된 for문 (for-each) ---");
        // 인덱스를 별도로 제어하지 않고 모든 요소를 순회합니다.
        for (String lang : languages) {
            System.out.println("언어: " + lang);
        }

        // for-each 루프의 Read-only(읽기 전용) 특성 실습
        int[] originalNumbers = {1, 2, 3};
        for (int num : originalNumbers) {
            num = num * 10; // 복사본을 변경하는 것이므로 원본 배열에 영향 없음
        }
        
        System.out.print("for-each 내부에서 값 변경 시도 후 원본 배열 상태: ");
        for (int i = 0; i < originalNumbers.length; i++) {
            System.out.print(originalNumbers[i] + " "); // 여전히 1, 2, 3 출력
        }
        System.out.println();
    }
}
