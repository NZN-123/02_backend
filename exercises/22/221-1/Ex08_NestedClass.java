/**
 * 8단계: 중첩 클래스와 바깥 인스턴스 참조
 *
 * 학습 포인트
 * - 중첩 클래스에는 인스턴스 내부, 정적 중첩, 지역, 익명 클래스가 있습니다.
 * - 비정적 내부 클래스 객체는 바깥 인스턴스의 암묵적 참조를 가집니다.
 * - 정적 중첩 클래스는 바깥 인스턴스 없이 생성하며 암묵적 바깥 참조가 없습니다.
 * - 바깥 인스턴스가 필요 없다면 정적 중첩 클래스를 우선 고려합니다.
 */
class Outer {
    private String instanceMessage = "바깥 인스턴스 메시지";
    private static String staticMessage = "바깥 static 메시지";

    class Inner {
        void print() {
            System.out.println(instanceMessage);
        }
    }

    static class StaticNested {
        void print() {
            System.out.println(staticMessage);
        }
    }

    void runLocalAndAnonymous() {
        class Local {
            void print() {
                System.out.println("지역 클래스");
            }
        }

        Runnable anonymous = new Runnable() {
            @Override
            public void run() {
                System.out.println("익명 클래스");
            }
        };

        new Local().print();
        anonymous.run();
    }
}

public class Ex08_NestedClass {
    public static void main(String[] args) {
        System.out.println("=== 네 가지 중첩 클래스 ===");

        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();
        inner.print();

        Outer.StaticNested staticNested = new Outer.StaticNested();
        staticNested.print();

        outer.runLocalAndAnonymous();

        // 미션: inner를 오래 보관하면 outer도 GC 대상이 되기 어려운 이유를 설명하세요.
        // 정답: inner가 암묵적인 Outer.this 참조를 유지하기 때문입니다.
    }
}
