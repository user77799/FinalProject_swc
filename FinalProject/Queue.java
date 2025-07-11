import java.util.*;
class Queue {
    private LinkedList<Object> list = new LinkedList<>();
    
    public void enqueue(Object item) {
        list.addLast(item);
    }
    
    public Object dequeue() {
        if (list.isEmpty()) return null;
        return list.removeFirst();
    }
    
    public boolean empty() {
        return list.isEmpty();
    }
    
    public int size() {
        return list.size();
    }
}

class Stack {
    private LinkedList<Object> list = new LinkedList<>();
    
    public void push(Object item) {
        list.addFirst(item);
    }
    
    public Object pop() {
        if (list.isEmpty()) return null;
        return list.removeFirst();
    }
    
    public boolean empty() {
        return list.isEmpty();
    }
}
