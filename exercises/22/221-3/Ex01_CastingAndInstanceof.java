/**
 * 실습 221-3. 1단계: 다형성과 참조 타입 캐스팅 & 패턴 매칭 instanceof
 * 
 * [학습 포인트]
 * 1. 업캐스팅(Upcasting)은 하위 객체를 상위 타입으로 가리키는 것이며, 언제나 안전하여 묵시적(자동)으로 형변환됩니다.
 * 2. 다운캐스팅(Downcasting)은 상위 타입을 하위 타입으로 강제 변환하는 것이며, 실제 힙에 있는 객체 타입과 일치하지 않으면 런타임에 'ClassCastException'이 발생합니다.
 * 3. JDK 16+ 패턴 매칭 'instanceof'를 사용하면 타입 검증과 동시에 형변환이 완료된 바인딩 변수를 생성하여 이중 선언 코드를 완전히 제거할 수 있습니다.
 */

class Parent {
    public void parentMethod() {
        System.out.println("Parent의 메서드 호출");
    }
}

class Child extends Parent {
    public void childMethod() {
        System.out.println("Child 전용 메서드 호출");
    }
}

class AnotherChild extends Parent {
    public void anotherMethod() {
        System.out.println("AnotherChild 전용 메서드 호출");
    }
}

public class Ex01_CastingAndInstanceof {
    
    // 기존 방식의 다운캐스팅 처리 메서드
    public static void processOld(Parent p) {
        // instanceof 검증 후, 블록 내부에서 다시 명시적 형변환을 하는 보일러플레이트 코드
        if (p instanceof Child) {
            Child c = (Child) p; 
            System.out.print("[기존 방식] ");
            c.childMethod();
        }
    }

    // JDK 16+ 패턴 매칭 instanceof 처리 메서드
    public static void processNew(Parent p) {
        // [미션 1] JDK 16+ 패턴 매칭 instanceof 문법을 사용하여 타입 검사 및 변수 바인딩을 한 번에 하도록 코드를 완성해 보세요.
        // p가 Child 타입인 경우 childMethod()를 호출하고, AnotherChild 타입인 경우 anotherMethod()를 호출합니다.
        // TODO: 아래 코드를 패턴 매칭 문법(p instanceof Child c)으로 리팩토링하세요.
        if (p instanceof Child) {
            Child c = (Child) p;
            System.out.print("[패턴 매칭] ");
            c.childMethod();
        } else if (p instanceof AnotherChild) {
            AnotherChild ac = (AnotherChild) p;
            System.out.print("[패턴 매칭] ");
            ac.anotherMethod();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 1단계: 참조 타입 캐스팅 및 패턴 매칭 instanceof ===");

        // 1. 업캐스팅 (Upcasting)
        // 자식 객체 Child를 부모 타입 Parent 변수에 대입. 묵시적으로 캐스팅 완료.
        Parent p1 = new Child();
        p1.parentMethod(); // 부모 멤버만 접근 가능
        // p1.childMethod(); // 컴파일 에러! (부모 타입 선언에 존재하지 않음)

        // 2. 다운캐스팅 (Downcasting)
        // 부모 타입을 다시 원래의 자식 타입으로 강제 변환.
        System.out.println("\n--- 안전한 다운캐스팅 ---");
        Child c1 = (Child) p1; // 명시적 변환 필수
        c1.childMethod(); // 자식 멤버 접근 가능

        // 3. ClassCastException 오류 발생 시나리오
        System.out.println("\n--- 위험한 다운캐스팅 (ClassCastException) ---");
        Parent p2 = new AnotherChild(); // p2는 실제 AnotherChild의 인스턴스를 가리키고 있음
        
        // [미션 2] 아래 주석을 풀고 실행해 보세요. 
        // p2가 가리키는 실제 인스턴스는 AnotherChild인데, Child로 다운캐스팅을 강제 시도하여
        // 런타임에 ClassCastException이 발생하고 프로그램이 비정상 종료됩니다.
        // 관찰 후 다시 주석 처리하세요.
        /*
        Child c2 = (Child) p2; 
        c2.childMethod();
        */
        System.out.println("(부적절한 형변환 시 ClassCastException이 발생하여 앱이 폭사합니다.)");

        // 4. 안전한 변환을 위한 instanceof 처리 비교
        System.out.println("\n--- instanceof를 통한 안전한 변환 검증 ---");
        Parent pTest1 = new Child();
        Parent pTest2 = new AnotherChild();

        System.out.println("1) 기존 방식 호출:");
        processOld(pTest1);
        processOld(pTest2); // 동작하지 않음 (Child가 아니므로 제외됨)

        System.out.println("2) JDK 16+ 패턴 매칭 방식 호출 (미션 1 완료 후 검증):");
        processNew(pTest1);
        processNew(pTest2);
    }
}
