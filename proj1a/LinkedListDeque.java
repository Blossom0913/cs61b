public class LinkedListDeque<T> {

    private int size;
    public Node sentinel;


    public class Node{

        Node prev;
        Node next;
        T item;
        public Node(){
            prev = null;
            next = null;
        }
        public Node(T itemp){
            item = itemp;
            prev = null;
            next = null;
        }
        public Node(Node prevp,Node nextp,T itemp){
            prev = prevp;
            next = nextp;
            item = itemp;
        }
    }
    public LinkedListDeque(){
        size = 0;
        sentinel = new Node(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    public LinkedListDeque(LinkedListDeque<T> other){
        size = other.size;
        sentinel = new Node(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;

        for(int i = 0;i < other.size();++i){
            addLast(other.get(i));
        }
    }

    public T getRecursive(int index){
        if(index < 0 || index >= size){
            return null;
        }

        Node ptr = sentinel.next;
        for(int i = 0; i < size;++i){
            ptr = ptr.next;
        }
        return ptr.item;
    }

    public void addFirst(T item){
        Node newNode = new Node(item);
        sentinel.next.prev = newNode;
        newNode.next = sentinel.next;
        newNode.prev = sentinel;
        sentinel.next = newNode;

        size++;
    }
    public void addLast(T item){
        Node newNode = new Node(item);
        sentinel.prev.next = newNode;
        newNode.prev = sentinel.prev;
        newNode.next = sentinel;
        sentinel.prev = newNode;

        size++;
    }
    public boolean isEmpty(){
        return size == 0;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        Node ptr = sentinel.next;
        while (ptr != sentinel){
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
        System.out.print("\n");
    }
    public T removeFirst(){
        if(sentinel.next == sentinel){
            return null;
        }
        Node first = sentinel.next;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return first.item;
    }
    public T removeLast(){
        if(sentinel.prev == sentinel){
            return null;
        }
        Node last = sentinel.prev;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size--;
        return last.item;
    }
    public T get(int index){
        if(index < 0 || index >= size){
            return null;
        }
        Node ptr = sentinel.next;
        for(int i = 0; i < size;++i){
            ptr = ptr.next;
        }
        return ptr.item;
    }
}
