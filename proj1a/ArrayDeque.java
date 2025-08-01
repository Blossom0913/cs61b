public class ArrayDeque<T> {
    private int size;
    private  T[] items;
// array   [0 0 0 0 0 0 0 0]
// index   [0 1 2 3 4 5 6 7]
// size    [1 2 3 4 5 6 7 8]
// length  [1 2 3 4 5 6 7 8]

    public ArrayDeque() {
        items = (T[] ) new Object[8];
        size -= 0;

    }
    private void resize(int oldLength) {
        T[] newArray = (T[] ) new Object[oldLength*2];
        System.arraycopy(items, 0, newArray, 0, oldLength);
        items = newArray;
    }
    //    在有限条件下（可以确定总元素个数不大于数组总长度。（
//    也就是向后移动元素后，元素顺序不变，元素没有移动位置，元素个数没有少，
//    这个操作的关键思路是,从最后一个(如果设置为空)索引，从后往前替换。
    private  void MoveArrayItemBackward() {

        for (int i = items.length - 1; i >0; i--) {
            items[i] = items[i - 1]; // 向后移动元素
//            注意 原始的第 items[0] 没变化
        }

    }
    //    类似操作，把元素向前移动。
//    这里也要保证 第一个元素为空，数组总长足够。
    private  void moveArrayItemsForward() {

        for (int i = 0; i < items.length-1; i++) {

            items[i] = items[i + 1]; // 向前移动元素

        }
        items[items.length-1] = null;


    }
    //    addFirst 就是把所有元素后移，在空出来的第一个位置加一个
    public void addFirst(T item) {
        if(size== items.length) {
            resize(items.length);
        }
        if(size==0) {
            items[0] = item;
            size += 1;
            return;
        }
        else {
//            这里可以确定 增加一个元素后的元素总个数也没有超过数组总长，因为最开始做了  size和items.length的检查
            MoveArrayItemBackward();
            items[0] = item;
            size += 1;
            return;
        }

    }

    public int size() {
        return size;
    }
    public void addLast(T item) {
        if(size== items.length) {
            resize(items.length);
        }
        items[size] = item;
        size += 1;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public void printDeque() {
        for(int i = 0;i < size; i++){
            System.out.print(items[i]+" ");
        }
    }
    public T removeFirst() {
        int currentLength = items.length;
        double compareResult = (double)size / currentLength;
        if(compareResult < 0.25){
            resize((int)Math.floor(items.length/4));
        }

        T FirstItem = items[0];
        items[0] = null;
        moveArrayItemsForward();
        size -= 1;
        return FirstItem;
    }

    public T removeLast() {
        int currentLength = items.length;
        double compareResult = (double)size / currentLength;
        if(compareResult < 0.25) {
            resize((int)Math.floor(items.length / 4));
        }
        T lastItem = items[size - 1];
        items[size-1] = null;
        size -= 1;
        return lastItem;
    }
    public T get(int index) {
        if(index<0||index>size-1){
            return null;
        }
        return items[index];
    }

}
