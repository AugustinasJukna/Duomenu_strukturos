package utils;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class LinkList<T>  implements Iterable<T> {


    @Override
    public Iterator iterator() {
        return new CustomIterator();
    }

    private class CustomIterator implements Iterator {
        Node current = Head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T data = current.Value;
                current = current.Link;
                return data;
            }
            else {
                return null;
            }
        }
    }

    class Node{
        T Value;
        Node Link;

        public Node(T value, Node link) {
            this.Value = value;
            this.Link = link;
        }
    }

    Node Head;
    Node Tail;

    public LinkList() {
        Head = null;
        Tail = null;
    }


    public void add(T item) {
        Node node = new Node(item, null);
        if (Head == null) {
            Head = node;
            Tail = node;
        }

        else {
            Tail.Link = node;
            Tail = node;
        }
    }

    public void remove(T item) {
        for (Node w = Head; w != null; w = w.Link) {
            if (w.Value == item && w == Head) {
                Head = w.Link;
            }

            else if (w.Value == item && w != Head) {
                Node charge;
                for (charge = Head; charge.Link != w; charge = charge.Link) ;
                if (w == Tail) {
                    Tail = charge;
                    charge.Link = null;
                }
                else {
                    charge.Link = w.Link;
                }
            }
        }
    }


}

