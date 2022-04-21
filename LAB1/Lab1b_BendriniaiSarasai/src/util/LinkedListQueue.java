package util;

import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListQueue<E> implements Queue<E>, Iterable<E>{
    private LinkedList<E> list;
    public LinkedListQueue() {
        list = new LinkedList<>();
    }

    /*public void printContent() {
        for (E e : this.list) {
            System.out.println(e.toString());
        }
    }*/
    @Override
    public void enqueue(E item) {
        list.addLast(item);
    }

    @Override
    public E dequeue() {
        E holder = list.getFirst();
        list.removeFirst();
        return holder;
    }

    @Override
    public E peak() {
        return list.getFirst();
    }

    @Override
    public boolean isEmpty() {
        if (list.isEmpty()) {
            return true;
        }
        else
            return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    private class CustomIterator implements Iterator {
        int index = 0;
        @Override
        public boolean hasNext() {
            if (index >= list.size()) {
                return false;
            }
            else
                return true;

        }

        @Override
        public Object next() {
            return list.get(index++);
        }
    }
}
