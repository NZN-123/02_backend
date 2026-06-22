import java.lang.ref.WeakReference;

/**
 * 7단계: 가비지 컬렉션과 도달 가능성
 *
 * 학습 포인트
 * - GC Root에서 참조 경로가 있는 객체는 Reachable 상태입니다.
 * - 참조 경로가 끊어진 객체는 Unreachable 상태가 되어 GC 대상이 될 수 있습니다.
 * - System.gc()는 GC를 요청할 뿐 실행 시점이나 수거 여부를 보장하지 않습니다.
 * - WeakReference를 사용하면 객체의 생존을 강제로 유지하지 않고 상태를 관찰할 수 있습니다.
 */
public class Ex07_GarbageCollection {
    static class Node {
        String name;
        Node next;

        Node(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== GC와 Reachability ===");

        Node root = new Node("A");
        root.next = new Node("B");
        System.out.println("root -> " + root.name + " -> " + root.next.name);

        Node temporary = new Node("수거 후보");
        WeakReference<Node> observer = new WeakReference<>(temporary);
        System.out.println("참조 해제 전: " + observer.get().name);

        temporary = null;
        System.gc();

        Node observed = observer.get();
        System.out.println("GC 요청 후: "
                + (observed == null ? "수거됨" : "아직 수거되지 않음"));
        System.out.println("주의: 어느 결과든 JVM 명세상 정상입니다.");
    }
}
