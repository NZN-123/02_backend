import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 실습 222-2. 1단계: 바이트 스트림 vs 문자 스트림
 * 
 * [학습 포인트]
 * 1. 바이트 스트림 (Byte Stream)
 *    - 데이터를 1바이트(8-bit) 단위로 입출력합니다.
 *    - 이미지, 음악, 비디오 등 모든 종류의 바이너리 데이터를 처리할 수 있습니다.
 *    - 최상위 클래스는 InputStream / OutputStream 입니다.
 * 2. 문자 스트림 (Character Stream)
 *    - 데이터를 2바이트(16-bit) 단위로 입출력하며, 문자 인코딩(UTF-8 등)을 자동으로 처리합니다.
 *    - 오직 텍스트 파일(txt, md, html 등)을 입출력하기에 가장 적합합니다.
 *    - 최상위 클래스는 Reader / Writer 입니다.
 * 3. 한글 인코딩 깨짐 현상
 *    - UTF-8 환경에서 한글 한 글자는 3바이트를 차지합니다.
 *    - 바이트 스트림으로 한글 데이터를 1바이트씩 분할하여 읽고 콘솔에 출력하면, 문자 깨짐 현상이 발생합니다.
 *    - 문자 스트림을 사용하면 글자 단위(Char)로 해석되므로 한글이 깨지지 않고 올바르게 출력됩니다.
 */
public class Ex01_ByteAndCharStreams {

    public static void main(String[] args) {
        String testData = "안녕하세요 Java! 반갑습니다.";
        String byteFilePath = "byte_test.txt";
        String charFilePath = "char_test.txt";

        System.out.println("=== 1. 바이트 스트림 테스트 (한글 깨짐 현상 관찰) ===");
        runByteStreamTest(byteFilePath, testData);

        System.out.println("\n=== 2. 문자 스트림 테스트 (한글 정상 출력 확인) ===");
        runCharStreamTest(charFilePath, testData);
    }

    /**
     * 바이트 스트림을 사용하여 텍스트 데이터를 저장하고 읽어옵니다.
     * 한글이 깨져서 나오는 현상을 확인합니다.
     */
    private static void runByteStreamTest(String filePath, String data) {
        // 1. 파일 쓰기 (FileOutputStream)
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            // String 문자열을 바이트 배열로 변환하여 씁니다.
            fos.write(data.getBytes("UTF-8"));
            System.out.println("바이트 스트림으로 파일 쓰기 완료: " + filePath);
        } catch (IOException e) {
            System.out.println("바이트 스트림 쓰기 에러: " + e.getMessage());
        }

        // 2. 파일 읽기 (FileInputStream)
        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.print("바이트 스트림으로 파일 읽기 결과: ");
            int b;
            // 1바이트씩 스트림을 통해 파일 데이터를 읽어옵니다.
            while ((b = fis.read()) != -1) {
                // 1바이트 크기(0~255)만 캐스팅하여 문자로 콘솔 출력시도
                // 영어/숫자는 1바이트라 잘 나오나, 3바이트인 한글은 깨집니다.
                System.out.print((char) b);
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("바이트 스트림 읽기 에러: " + e.getMessage());
        }
    }

    /**
     * 문자 스트림을 사용하여 텍스트 데이터를 저장하고 읽어옵니다.
     * 인코딩이 정상 반영되어 한글이 올바르게 출력됩니다.
     */
    private static void runCharStreamTest(String filePath, String data) {
        // 1. 파일 쓰기 (FileWriter)
        try (FileWriter fw = new FileWriter(filePath)) {
            // 문자열을 바이트 변환 없이 그대로 쓸 수 있습니다.
            fw.write(data);
            System.out.println("문자 스트림으로 파일 쓰기 완료: " + filePath);
        } catch (IOException e) {
            System.out.println("문자 스트림 쓰기 에러: " + e.getMessage());
        }

        // 2. 파일 읽기 (FileReader)
        try (FileReader fr = new FileReader(filePath)) {
            System.out.print("문자 스트림으로 파일 읽기 결과: ");
            int c;
            // 2바이트(글자 단위)로 데이터를 안전하게 읽어옵니다.
            while ((c = fr.read()) != -1) {
                System.out.print((char) c);
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("문자 스트림 읽기 에러: " + e.getMessage());
        }
    }
}
