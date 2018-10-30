import java.util.ArrayList;
import java.util.Comparator;

public class PriorityQueue<T> {

    final private ArrayList<T> queue;
    final private Comparator<T> comparator;

    public PriorityQueue(Comparator<T> comparable) {
        this.queue = new ArrayList<>();
        this.comparator = comparable;
    }
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    public T pop() {
        int index = queue.size() - 1;
        T lastItem = queue.get(index);
        queue.remove(index);
        return lastItem;
    }
    public ArrayList<T> insert(T e) {
        queue.add(e);
        queue.sort(comparator);
        return queue;
    }

}
