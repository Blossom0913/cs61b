public class ArrayDeque<T> {
    public int size;
    public int nextFirst;
    public int nextLast;
    public T[] items;


    public ArrayDeque(){
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 4;
        nextLast = 5;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void add(T item){
        if(size == items.length){
            resize(size * 2);
            nextFirst = size*2 - 1;
            nextLast = size;
        }
        items[nextFirst] = item;
        nextFirst = (nextFirst - 1)%items.length;
        size++;
    }

    public T remove(int i){
        if(isEmpty()){
            return null;
        }
        T item = items[(nextFirst + i)%items.length];
        items[(nextFirst + i)%items.length] = null;
        for(int j = nextFirst+i+1;j < nextLast;++j){
            items[j - 1] = items[j];
        }
        return item;
    }

    public ArrayDeque(ArrayDeque<T> other){
        size = other.size;
        items = (T[]) new Object[size + 1];
        System.arraycopy(items,0,other,0,size);
    }

    private void resize(int cap){
        T[] a = (T[]) new Object[cap];
        System.arraycopy(items,0,a,0,size);
        items = a;
    }

    public T get(int i){
        return items[i];
    }

    public int size(){
        return size;
    }





}
