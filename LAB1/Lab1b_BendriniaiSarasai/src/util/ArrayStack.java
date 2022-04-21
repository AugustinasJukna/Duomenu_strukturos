package util;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayStack<E> implements Stack<E>, Iterable<E> {
    private ArrayList<E> stack;
    private int lastIndex;

    public ArrayStack() {
        stack = new ArrayList<>();
        lastIndex = -1;
    }

    /*public void printContent() {
        for (int i = lastIndex; i >= 0; i--) {
            System.out.println(stack.get(i).toString());
        }
    }*/

    @Override
    public E pop() {
        if (lastIndex >= 0) {
            E holder = this.stack.get(lastIndex);
            this.stack.remove(lastIndex);
            lastIndex--;
            return holder;
        }
        else return null;
    }

    @Override
    public void push(E item) {
        stack.add(item);
        lastIndex++;
    }

    @Override
    public E peak() {
        if (lastIndex >= 0) {
            return this.stack.get(lastIndex);
        }
        else return null;
    }

    @Override
    public boolean isEmpty() {
        if (stack.size() == 0)
            return true;
        else return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomIterator();
    }
    public class CustomIterator implements Iterator {
        int index = 0;
        @Override
        public boolean hasNext() {
            if (index >= stack.size()) {
                return false;
            }
            else return true;
        }

        @Override
        public E next() {
            return stack.get(index++);
        }
    }


}
