import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 실습 222-2. 2단계: 텍스트 버퍼링 기반 입출력과 Files 클래스
 * 
 * [학습 포인트]
 * 1. 버퍼링 (Buffering)의 필요성
 *    - 디스크 장치에 1바이트 혹은 1글자씩 물리적으로 접근해서 쓰는 동작은 시스템 자원 소모와 I/O 대기 비용이 매우 큽니다.
 *    - 임시 메모리 공간(Buffer)에 데이터를 미리 차곡차곡 쌓아놓았다가, 버퍼가 가득 차거나 요청이 있을 때(Flush) 
 *      한꺼번에 디스크로 일괄 전송함으로써 입출력 성능을 극대화합니다.
 * 2. Files 유틸리티와 Path (Java NIO)
 *    - Paths.get() 혹은 Path.of()를 통해 파일 경로 객체(Path)를 획득합니다.
 *    - Files.newBufferedWriter(path)와 Files.newBufferedReader(path)는 
 *      내부적으로 최적화된 버퍼 기반 스트림 객체(BufferedWriter, BufferedReader)를 편리하게 리턴해줍니다.
 * 3. readLine()의 사용 편리성
 *    - BufferedReader의 readLine() 메서드는 텍스트 데이터를 줄(\n) 단위로 편리하게 읽어와 
 *      null이 리턴될 때까지 텍스트 한 줄씩 반복 처리할 수 있게 지원합니다.
 */
public class Ex02_BufferedIOAndFiles {

    public static void main(String[] args) {
        Path path = Paths.get("buffered_data.txt");

        System.out.println("=== 1. 버퍼 스트림을 활용한 파일 텍스트 쓰기 ===");
        writeTextWithBuffer(path);

        System.out.println("\n=== 2. 버퍼 스트림을 활용한 파일 텍스트 읽기 ===");
        readTextWithBuffer(path);
    }

    /**
     * Files.newBufferedWriter를 사용하여 버퍼링 텍스트 쓰기를 수행합니다.
     */
    private static void writeTextWithBuffer(Path path) {
        // try-with-resources 구문을 활용하여 BufferedWriter 자원 자동 close 보장
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("Java File I/O 학습 첫 번째 라인\n");
            writer.write("NIO Files 유틸리티와 버퍼의 조합\n");
            writer.write("대용량 텍스트 쓰기에는 버퍼가 효율적입니다.\n");
            
            System.out.println("버퍼 기반 파일 쓰기 완료: " + path.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("버퍼 파일 쓰기 중 에러 발생: " + e.getMessage());
        }
    }

    /**
     * Files.newBufferedReader를 사용하여 줄 단위 버퍼링 텍스트 읽기를 수행합니다.
     */
    private static void readTextWithBuffer(Path path) {
        // try-with-resources 구문을 활용하여 BufferedReader 자원 자동 close 보장
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            System.out.println("버퍼 파일 읽기 시작:");
            System.out.println("----------------------------------------");
            // readLine()은 한 줄씩 데이터를 읽으며, 파일의 끝에 도달하면 null을 반환합니다.
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("----------------------------------------");
        } catch (IOException e) {
            System.out.println("버퍼 파일 읽기 중 에러 발생: " + e.getMessage());
        }
    }
}
