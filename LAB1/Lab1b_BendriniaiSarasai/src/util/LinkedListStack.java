package util;
import java.util.*;

public class LinkedListStack<E extends Comparable<E>> implements Stack<E>, Iterable<E> {

    java.util.LinkedList<E> list;

    public LinkedListStack() {
        list = new java.util.LinkedList<>();
    }

    /*public void printContent() {
        for (E item : list) {
            System.out.println(item.toString());
        }
    }*/

    @Override
    public E pop() {
        if (!list.isEmpty()) {
            E holder = list.getFirst();
            list.removeFirst();
            return holder;
        }
        else
            return null;
    }

    @Override
    public void push(E item) {
        list.addFirst(item);
    }

    @Override
    public E peak() {
        if (!list.isEmpty()) {
            return list.getFirst();
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        if (list.isEmpty())
            return true;
        else return false;
    }

    @Override
    public Iterator iterator() {
        return new CustomIterator();
    }

    public class CustomIterator implements Iterator {
        int index = 0;
        @Override
        public boolean hasNext() {
            if (index >= list.size()) {
                return false;
            }
            else return true;
        }

        @Override
        public E next() {
            return list.get(index++);
        }
    }
}
