/**
 * 실습 221-3. 4단계: 실무 아키텍처 패턴 - 추상 골격 구현 (Skeleton Implementation)
 * 
 * [학습 포인트]
 * 1. 자바는 클래스 다중 상속을 금지하므로, 다중 구현 시 공통 메서드의 반복 구현(보일러플레이트)이 발생하기 쉽습니다.
 * 2. 이를 보완하기 위해 '인터페이스(스펙)'와 이를 구현한 '추상 클래스(공통 골격)'를 결합한 '추상 골격 구현 패턴'을 사용합니다.
 * 3. 3단계 구조:
 *    - 1단계 (인터페이스): 순수 스펙 API 정의 (예: Character)
 *    - 2단계 (추상 골격 클래스): 공통 필드 및 중복되기 쉬운 기본 기능 메서드 구현 (예: AbstractCharacter)
 *    - 3단계 (구체 클래스): 골격을 상속받아 특화된 핵심 행위만 오버라이딩하여 완성 (예: Warrior, Mage)
 */

// 1단계: 인터페이스 (순수 기능 스펙 규정)
interface GameCharacter {
    void attack();
    void takeDamage(int damage);
    void showStatus();
    String getName();
}

// 2단계: 추상 골격 구현 클래스 (Abstract Class)
// 인터페이스를 implements하여 공통 멤버 변수 및 중복되는 기본 동작을 미리 구현해 둡니다.
abstract class AbstractCharacter implements GameCharacter {
    protected String name;
    protected int hp;
    protected int mp;

    public AbstractCharacter(String name, int hp, int mp) {
        this.name = name;
        this.hp = hp;
        this.mp = mp;
    }

    // getName()은 모든 자식 클래스에서 거의 동일하게 사용되므로 골격 클래스에서 공통 구현 완료
    @Override
    public String getName() {
        return name;
    }

    // takeDamage() 역시 공통적인 라이프 사이클 로직이므로 여기에 일괄 작성하여 코드 중복 제거
    @Override
    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) this.hp = 0;
        System.out.println(name + "이(가) " + damage + "의 피해를 입었습니다. (남은 HP: " + hp + ")");
    }

    // showStatus() 공통 정보 출력 템플릿 구현
    @Override
    public void showStatus() {
        System.out.println("[" + name + "] HP: " + hp + " | MP: " + mp);
    }
}

// 3단계: 구체 클래스 1 (전사)
class Warrior extends AbstractCharacter {
    public Warrior(String name) {
        // 부모 골격 클래스의 생성자를 호출하여 초기 체력(200) 및 마나(50) 구성
        super(name, 200, 50);
    }

    // 전사만의 특화 공격 행위만 명시적으로 오버라이딩하여 간단하게 완성
    @Override
    public void attack() {
        System.out.println(name + "이(가) 묵직한 대검으로 강타 공격을 수행합니다! (MP -5)");
        this.mp -= 5;
    }
}

// 3단계: 구체 클래스 2 (마법사)
class Mage extends AbstractCharacter {
    public Mage(String name) {
        // 초기 체력(100) 및 마나(250) 구성
        super(name, 100, 250);
    }

    // [미션 1] 마법사(Mage)의 공격 행위를 오버라이딩하여 완성해 보세요.
    // 마법사는 공격 시 마나(mp)를 20 소비하며, 파이어볼 마법을 시전한다는 메시지를 출력합니다.
    // TODO: 아래 attack() 메서드를 완성해 보세요.
    @Override
    public void attack() {
        System.out.println(name + "이(가) 파이어볼 마법을 시전합니다! (MP -20)");
        this.mp -= 20;
    }
}

public class Ex04_SkeletonImplementation {
    public static void main(String[] args) {
        System.out.println("=== 4단계: 추상 골격 구현 아키텍처 패턴 실습 ===");

        // 1. 전사 객체 생성 및 플레이
        System.out.println("--- 1. 전사(Warrior) 시뮬레이션 ---");
        GameCharacter warrior = new Warrior("아라곤");
        warrior.showStatus();
        warrior.attack();
        warrior.takeDamage(40);
        warrior.showStatus();

        // 2. 마법사 객체 생성 및 플레이
        System.out.println("\n--- 2. 마법사(Mage) 시뮬레이션 (미션 1 완료 후 테스트) ---");
        GameCharacter mage = new Mage("간달프");
        mage.showStatus();
        mage.attack();
        mage.takeDamage(60);
        mage.showStatus();
        
        System.out.println("\n(골격 구현 패턴을 통해 Warrior와 Mage 클래스의 핵심 코드가 얼마나 짧아졌는지 구조를 살펴보세요.)");
    }
}
