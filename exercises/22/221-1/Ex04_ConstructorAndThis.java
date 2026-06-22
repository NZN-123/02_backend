/**
 * 4단계: 생성자, this, this()
 *
 * 학습 포인트
 * - 생성자는 반환 타입이 없고 클래스명과 이름이 같습니다.
 * - 생성자를 하나도 선언하지 않았을 때만 컴파일러가 기본 생성자를 제공합니다.
 * - this는 현재 인스턴스를 가리키고, this()는 같은 클래스의 다른 생성자를 호출합니다.
 * - this()는 생성자의 첫 문장이어야 합니다.
 */
class Member {
    String name;
    int age;

    Member() {
        this("이름 없음", 0);
    }

    Member(String name) {
        this(name, 20);
    }

    Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    void printInfo() {
        System.out.println(name + ", " + age + "세");
    }
}

public class Ex04_ConstructorAndThis {
    public static void main(String[] args) {
        System.out.println("=== 생성자와 this 체이닝 ===");

        Member guest = new Member();
        Member defaultAgeMember = new Member("김민수");
        Member member = new Member("이지원", 25);

        guest.printInfo();
        defaultAgeMember.printInfo();
        member.printInfo();

        // 미션: Member() 생성자를 제거하면 new Member()가 컴파일되지 않는 이유를 설명하세요.
        // 이미 다른 생성자를 선언했으므로 기본 생성자가 자동으로 추가되지 않습니다.
    }
}
