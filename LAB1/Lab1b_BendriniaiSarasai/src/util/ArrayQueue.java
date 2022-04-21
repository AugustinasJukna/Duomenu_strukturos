package util;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayQueue<E> implements Queue<E>, Iterable<E> {
    private ArrayList<E> list;

    public ArrayQueue() {
        list = new ArrayList<>();
    }

    /*public void printContent() {
        for (E e : this.list) {
            System.out.println(e.toString());
        }
    }*/
    @Override
    public void enqueue(E item) {
        list.add(item);
    }

    @Override
    public E dequeue() {
        E holder = this.list.get(0);
        this.list.remove(0);
        return holder;
    }

    @Override
    public E peak() {
        return this.list.get(0);
    }

    @Override
    public boolean isEmpty() {
        if (this.list.isEmpty()) {
            return true;
        }

        else
            return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomIterator();
    }

    private class CustomIterator implements Iterator {
        int index = 0;
        @Override
        public boolean hasNext() {
            if (list.size() <= index) {
                return false;
            }
            else
                return true;
        }

        @Override
        public E next() {
            return list.get(index++);
        }
    }
}
