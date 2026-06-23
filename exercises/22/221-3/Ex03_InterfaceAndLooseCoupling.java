/**
 * 실습 221-3. 3단계: 인터페이스 묵시적 규칙, default/private 메서드 & 느슨한 결합 (Loose Coupling)
 * 
 * [학습 포인트]
 * 1. 인터페이스 내의 필드는 자동으로 'public static final' 상수가 되며, 메서드는 'public abstract' 추상 메서드가 됩니다.
 * 2. default 메서드(JDK 8+)는 인터페이스 내부에서 완성된 구현 바디를 갖는 메서드로 하위 호환성을 제공합니다.
 * 3. private 메서드(JDK 9+)는 default나 static 메서드에서 공통으로 호출되는 세부 로직을 내부로 감추기 위해 사용됩니다.
 * 4. 인터페이스를 매개로 설계하면 결합도가 완화되어 호출 측(Service) 코드 변경 없이 하부 구현체(Repository)를 자유롭게 갈아끼울 수 있습니다. (DIP 의존성 역전 원칙)
 */

// 1. 데이터 저장소 인터페이스 정의
interface MemberRepository {
    // 묵시적 규칙: 인터페이스 내부 상수는 public static final이 자동으로 붙습니다.
    int MAX_CONNECTIONS = 50; 

    // 묵시적 규칙: 일반 메서드는 public abstract가 생략되어도 추상 메서드로 지정됩니다.
    void save(String name);
    String findById(int id);

    // default 메서드 (JDK 8+)
    default void checkStatus() {
        printLog("상태 확인 시도..."); // private 캡슐화 메서드 호출
        System.out.println("[Default] 저장소 접속 상태 양호 (최대 커넥션: " + MAX_CONNECTIONS + ")");
    }

    // private 메서드 (JDK 9+ 인터페이스 내 캡슐화)
    private void printLog(String message) {
        System.out.println("[Log] " + message);
    }
}

// 2. 구체 구현체 A: 메모리 저장소
class MemoryMemberRepository implements MemberRepository {
    @Override
    public void save(String name) {
        System.out.println("MemoryRepository에 회원 저장: " + name);
    }

    @Override
    public String findById(int id) {
        return "MemoryUser_" + id;
    }
}

// 3. 구체 구현체 B: 데이터베이스 저장소
class DatabaseMemberRepository implements MemberRepository {
    @Override
    public void save(String name) {
        System.out.println("DatabaseRepository(DB)에 회원 저장: " + name);
    }

    @Override
    public String findById(int id) {
        return "DBUser_" + id;
    }
}

// 4. 호출부 (Service) - 인터페이스에만 의존하여 느슨하게 결합됨
class MemberService {
    // 구체 클래스가 아닌 인터페이스 타입의 참조 변수를 선언합니다.
    private final MemberRepository repository;

    // 생성자를 통해 외부에서 실제 구현 객체를 주입받습니다. (DIP / 의존성 주입)
    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public void register(String name) {
        System.out.println("[Service] 회원 등록 비즈니스 로직 처리 중...");
        repository.save(name); // 인터페이스 규격을 호출
    }

    public void query(int id) {
        String result = repository.findById(id);
        System.out.println("[Service] 조회 결과: " + result);
    }

    public void healthCheck() {
        repository.checkStatus(); // 디폴트 메서드 호출
    }
}

public class Ex03_InterfaceAndLooseCoupling {
    public static void main(String[] args) {
        System.out.println("=== 3단계: 인터페이스 진화 및 느슨한 결합(Loose Coupling) 실습 ===");

        // 1. 메모리 저장소로 동작하는 서비스 생성 및 실행
        System.out.println("--- 1) 메모리 저장소 연동 ---");
        MemberRepository memoryRepo = new MemoryMemberRepository();
        MemberService service1 = new MemberService(memoryRepo);
        service1.register("이효리");
        service1.query(101);
        service1.healthCheck();

        // 2. DB 저장소로 서비스 교체 작동 검증 (DIP에 의해 서비스 핵심 로직 코드는 1자도 바뀌지 않음)
        System.out.println("\n--- 2) 데이터베이스 저장소 연동 (구현체 교체) ---");
        
        // [미션 1] DatabaseMemberRepository를 생성하여 MemberService에 주입한 뒤 작동을 확인해 보세요.
        // TODO: 아래 TODO 부분에 DatabaseMemberRepository 객체를 주입하여 service2를 구동해 보세요.
        MemberRepository dbRepo = new DatabaseMemberRepository(); // 여기에 구현체 대입
        MemberService service2 = new MemberService(dbRepo); 
        
        service2.register("유재석");
        service2.query(202);
        service2.healthCheck();

        // 3. 인터페이스 상수 접근
        System.out.println("\n--- 3) 인터페이스 상수 출력 ---");
        System.out.println("MemberRepository 최대 커넥션 수: " + MemberRepository.MAX_CONNECTIONS);
    }
}
