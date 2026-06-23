/**
 * 실습 221-3. 2단계: 추상 클래스 & 추상 메서드 선언 및 상속 규칙
 * 
 * [학습 포인트]
 * 1. 추상 클래스(Abstract Class)는 'abstract' 제어자가 선언되어 객체로 단독 인스턴스화할 수 없습니다. (new Animal() 불가)
 * 2. 추상 메서드는 바디 { } 구현부 없이 시그니처만 선언한 것이며, 상속받는 구체 클래스는 이를 반드시 오버라이딩해야 합니다.
 * 3. 추상 클래스는 멤버 변수(상태 정보)와 완성된 일반 메서드, 생성자를 소유할 수 있습니다.
 * 4. 자식 클래스가 부모의 추상 메서드를 오버라이딩하지 않는다면, 자식 클래스도 반드시 'abstract' 클래스여야 합니다.
 */

// 추상 클래스 선언
abstract class Animal {
    protected String name; // 멤버 변수 (상태 정보 필드 가질 수 있음)

    public Animal(String name) {
        this.name = name;
        System.out.println("Animal 생성자 호출 -> " + name);
    }

    // 일반 구현 메서드
    public void breathe() {
        System.out.println(name + "이(가) 숨을 쉽니다.");
    }

    // 추상 메서드 선언 (바디 없이 세미콜론으로 마침)
    public abstract void sound(); 
}

// 구체 클래스 1: Dog
class Dog extends Animal {
    // 부모 생성자 호출 필수
    public Dog(String name) {
        super(name);
    }

    // [미션 1] 부모의 추상 메서드 sound()를 오버라이딩하여 구현을 완성해 보세요.
    // 만약 아래 sound() 메서드를 주석 처리하면 컴파일 에러가 발생합니다.
    @Override
    public void sound() {
        System.out.println(name + "이(가) '멍멍!' 하고 짖습니다.");
    }
}

// 구체 클래스 2: Cat
class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }

    @Override
    public void sound() {
        System.out.println(name + "이(가) '야옹~' 하고 웁니다.");
    }
}

// 추상 상속 클래스 3: Dolphin
// [미션 2] 아래 Dolphin 클래스는 추상 클래스 Animal을 상속받았으나 sound() 추상 메서드를 구현하지 않았습니다.
// 이로 인해 발생하는 컴파일 에러를 관찰하기 위해 주석을 해제해 보거나, 클래스 선언 앞에 'abstract'를 붙여 Dolphin도 추상 클래스로 선언해 보세요.
/*
class Dolphin extends Animal {
    public Dolphin(String name) {
        super(name);
    }
    
    // sound()를 구현하지 않고 상속을 유지하기 위해 Dolphin 자체를 추상 클래스로 설정
}
*/

public class Ex02_AbstractClass {
    public static void main(String[] args) {
        System.out.println("=== 2단계: 추상 클래스와 추상 메서드 실습 ===");

        // 1. 추상 클래스의 인스턴스화 제한 테스트
        // [주의] 아래 코드는 컴파일 에러를 유발합니다. 추상 클래스는 단독 객체화가 절대 불가합니다.
        // Animal a = new Animal("동물"); // 컴파일 에러!

        // 2. 다형성을 이용한 상속 계통 설계 호출
        System.out.println("--- 1. Dog 객체 생성 및 작동 ---");
        Animal dog = new Dog("바둑이");
        dog.breathe(); // 부모 일반 메서드 재사용
        dog.sound();   // 자식이 오버라이딩한 구체적 추상 메서드 호출

        System.out.println("\n--- 2. Cat 객체 생성 및 작동 ---");
        Animal cat = new Cat("나비");
        cat.breathe();
        cat.sound();

        System.out.println("\n--- 3. 추상 상속 클래스(Dolphin) 검증 ---");
        // Dolphin도 추상 클래스이므로 단독 객체 생성이 불가합니다.
        // Animal dolphin = new Dolphin("돌돌이"); // 컴파일 에러!
        System.out.println("(추상 메서드를 온전히 오버라이딩해야만 실체 구체 클래스로서 new를 통해 객체화가 가능합니다.)");
    }
}
