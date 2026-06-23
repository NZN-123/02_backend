# 실습 221-3. Java 추상화와 다형성 II

본 실습은 Java의 업캐스팅과 다운캐스팅, JDK 16+ 패턴 매칭 `instanceof`, 추상 클래스와 인터페이스의 비교, 실무형 추상 골격 구현 패턴(Skeleton Implementation), Wrapper 클래스의 오토 박싱 성능 문제, 그리고 제네릭 타입 이레이저(Type Erasure)의 제약 사항을 코드를 통해 분석하고 구현해보기 위한 자료입니다.
총 5개의 단계별 실습 예제로 구성되어 있으며, 2시간 동안 순차적으로 진행합니다.

## 실습 환경 및 실행 방법
- JDK 17 이상 권장 (패턴 매칭 instanceof 및 private interface 메서드 등이 포함되어 있습니다)
- 터미널에서 컴파일 및 실행:
  ```bash
  # 컴파일 (221-3 폴더에서 실행 시)
  javac *.java
  
  # 개별 클래스 실행
  java Ex01_CastingAndInstanceof
  java Ex02_AbstractClass
  java Ex03_InterfaceAndLooseCoupling
  java Ex04_SkeletonImplementation
  java Ex05_WrapperAndGenerics
  ```

---

## 단계별 실습 주제

### 1단계: 다형성과 캐스팅 (`Ex01_CastingAndInstanceof.java`)
- **목표**: 업캐스팅(Upcasting)과 다운캐스팅(Downcasting)의 동작 원리를 이해하고, JDK 16+ 패턴 매칭 `instanceof`로 안전하게 형변환하는 방법을 학습합니다.
- **실습 내용**:
  - 묵시적 업캐스팅과 명시적 다운캐스팅 작성
  - 잘못된 형변환 시 발생하는 `ClassCastException` 관찰
  - JDK 16+ 패턴 매칭 `instanceof`를 활용해 보일러플레이트 코드를 줄인 형변환

### 2단계: 추상 클래스 설계 (`Ex02_AbstractClass.java`)
- **목표**: 추상 클래스(`abstract class`)와 추상 메서드의 선언 방식 및 제약 조건을 확인하고 자식 클래스에서의 오버라이딩 책임을 이해합니다.
- **실습 내용**:
  - 추상 클래스의 단독 인스턴스화 금지 제약 관찰
  - 추상 메서드 선언 및 구체 클래스에서의 필수 오버라이딩
  - 공통 멤버 변수 및 일반 메서드를 지닌 추상 클래스 구조 구현

### 3단계: 인터페이스와 느슨한 결합 (`Ex03_InterfaceAndLooseCoupling.java`)
- **목표**: 인터페이스의 묵시적 상수/추상 메서드 규칙을 학습하고, 디폴트/정적/비공개 메서드(JDK 9+)의 구현과 인터페이스를 통한 느슨한 결합(Loose Coupling)을 실습합니다.
- **실습 내용**:
  - 인터페이스 내의 상수(`public static final`)와 추상 메서드(`public abstract`) 생략 규칙 검증
  - `default` 메서드와 `private` 캡슐화 메서드 작성
  - 인터페이스 기반의 호출 구조 설계를 통한 의존성 주입과 결합도 완화

### 4단계: 실무 패턴 - 추상 골격 구현 (`Ex04_SkeletonImplementation.java`)
- **목표**: 인터페이스 규격과 추상 클래스를 결합하여 다중 상속 제약을 극복하고 중복 보일러플레이트 코드를 제거하는 **추상 골격 구현 패턴(Skeleton Implementation)**을 구현합니다.
- **실습 내용**:
  - 스펙 정의용 `Character` 인터페이스 선언
  - 공통 로직 및 템플릿 메서드를 구현한 `AbstractCharacter` 골격 추상 클래스 선언
  - 구체적인 로직만 구현한 `Warrior`, `Mage` 구현 클래스 완성

### 5단계: Wrapper와 제네릭 제약 (`Ex05_WrapperAndGenerics.java`)
- **목표**: Wrapper 클래스의 오토 박싱 성능 주의점과 제네릭 타입 이레이저(Type Erasure)에 의한 런타임 제약을 코드로 검증합니다.
- **실습 내용**:
  - 기본 타입(`long`) 루프 연산 vs Wrapper(`Long`) 오토 박싱 연산 간의 실행 속도 비교 측정
  - 타입 소거로 인해 런타임에 제네릭 타입 `<T>` 정보를 활용하지 못해 발생하는 조작 금지 제약(instanceof T 불가, new T 불가, new T[] 배열 생성 불가) 직접 확인
