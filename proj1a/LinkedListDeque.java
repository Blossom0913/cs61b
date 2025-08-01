public class LinkedListDeque<T> {

    private int size;
    private Node sentinel;


    private class Node {

        Node prev;
        Node next;
        T item;
        public Node() {
            prev = null;
            next = null;
        }
        public Node(T itemp) {
            item = itemp;
            prev = null;
            next = null;
        }
        public Node(Node prevp,Node nextp,T itemp) {
            prev = prevp;
            next = nextp;
            item = itemp;
        }
    }
    public LinkedListDeque() {
        size = 0;
        sentinel = new Node(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    public LinkedListDeque(LinkedListDeque<T> other) {
        size = other.size;
        sentinel = new Node(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;

        for(int i = 0;i < other.size();++i) {
            addLast(other.get(i));
        }
    }

    public T get(int index) {
        Node ptr = sentinel;
        int tmp = 0;
        while (ptr.next != sentinel) {
            if (tmp == index) {
                return ptr.next.item;
            }
            tmp++;
            ptr = ptr.next;
        }
        return null;
    }


    public T getRecursive(int index) {
        return getRecursive(sentinel, index, 0);
    }

    private T getRecursive(Node x, int index, int tmp) {
        Node ptr = x;
        if (ptr.next == sentinel) {
            return null;
        } else if (tmp == index) {
            return ptr.next.item;
        }
        return getRecursive(ptr.next, index, tmp + 1);
    }

    public void addFirst(T item) {
        Node newNode = new Node(item);
        sentinel.next.prev = newNode;
        newNode.next = sentinel.next;
        newNode.prev = sentinel;
        sentinel.next = newNode;

        size++;
    }
    public void addLast(T item) {
        Node newNode = new Node(item);
        sentinel.prev.next = newNode;
        newNode.prev = sentinel.prev;
        newNode.next = sentinel;
        sentinel.prev = newNode;

        size++;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        Node ptr = sentinel.next;
        while (ptr != sentinel){
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
        System.out.print("\n");
    }
    public T removeFirst() {
        if(sentinel.next == sentinel) {
            return null;
        }
        Node first = sentinel.next;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return first.item;
    }
    public T removeLast() {
        if(sentinel.prev == sentinel) {
            return null;
        }
        Node last = sentinel.prev;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size--;
        return last.item;
    }
    
}
