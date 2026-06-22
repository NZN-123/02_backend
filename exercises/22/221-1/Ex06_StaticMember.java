/**
 * 6단계: static 멤버
 *
 * 학습 포인트
 * - static 멤버는 인스턴스가 아니라 클래스에 속하며 모든 인스턴스가 공유합니다.
 * - 정적 멤버는 클래스명으로 접근하는 것이 권장됩니다.
 * - static 메서드에는 특정 인스턴스가 없으므로 this를 사용할 수 없습니다.
 * - 인스턴스 멤버가 필요하면 객체 참조를 매개변수로 받아 접근할 수 있습니다.
 */
class UserAccount {
    static int accountCount;
    String username;

    UserAccount(String username) {
        this.username = username;
        accountCount++;
    }

    void printAccount() {
        System.out.println(username + " / 전체 계정: " + accountCount);
    }

    static int getAccountCount() {
        return accountCount;
    }

    static void printUsername(UserAccount account) {
        System.out.println("전달받은 인스턴스: " + account.username);
    }

    /*
     * 미션: 아래 코드가 static 메서드에서 컴파일되지 않는 이유를 설명하세요.
     * System.out.println(username);
     * System.out.println(this.username);
     */
}

public class Ex06_StaticMember {
    public static void main(String[] args) {
        System.out.println("=== static 멤버와 공유 구조 ===");
        System.out.println("생성 전: " + UserAccount.getAccountCount());

        UserAccount first = new UserAccount("김민수");
        UserAccount second = new UserAccount("이지원");

        first.printAccount();
        second.printAccount();
        System.out.println("클래스명으로 조회: " + UserAccount.accountCount);
        UserAccount.printUsername(first);
    }
}
