/**
 * 2단계: 클래스, 객체, 인스턴스
 *
 * 학습 포인트
 * - 클래스는 객체의 상태와 동작을 정의하는 설계도입니다.
 * - 인스턴스는 클래스를 바탕으로 힙에 생성된 구체적인 실체입니다.
 * - 참조 변수에는 객체 자체가 아니라 객체를 가리키는 참조값이 저장됩니다.
 * - public 클래스명과 .java 파일명은 일치해야 합니다.
 */
class Car {
    String model;
    int speed;

    void accelerate() {
        speed += 10;
    }
}

public class Ex02_ClassObjectInstance {
    public static void main(String[] args) {
        System.out.println("=== 클래스, 객체, 인스턴스 ===");

        Car firstCar = new Car();
        firstCar.model = "전기차";

        Car secondCar = new Car();
        secondCar.model = "하이브리드차";

        firstCar.accelerate();
        firstCar.accelerate();
        secondCar.accelerate();

        System.out.println(firstCar.model + " 속도: " + firstCar.speed);
        System.out.println(secondCar.model + " 속도: " + secondCar.speed);
        System.out.println("서로 다른 인스턴스인가? " + (firstCar != secondCar));

        Car sameCar = firstCar;
        sameCar.accelerate();
        System.out.println("같은 객체를 참조하는가? " + (firstCar == sameCar));
        System.out.println("firstCar 속도: " + firstCar.speed);
    }
}
