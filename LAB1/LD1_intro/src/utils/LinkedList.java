package utils;

import java.util.Iterator;

/*
Realizuokite visus interfeiso metodus susietojo sarašo pagrindu.
Nesinaudokite java klase LinkedList<E>, visa logika meginkite parasyti patys.
Jeigu reikia, galima kurti papildomus metodus ir kintamuosius.
*/
public class LinkedList<T> implements List<T> {
    private class Node {
        T Data;
        Node Link;

        public Node(T data, Node link) {
            this.Data = data;
            this.Link = link;
        }
    }

    Node Head;
    Node Tail;

    public LinkedList() {
        this.Head = null;
        Tail = null;
    }

    @Override
    public void add(T item) {
        Node newNode = new Node(item, null);
        if (this.Head == null) {
            this.Head = newNode;
            this.Tail = newNode;
        } else {
            this.Tail.Link = newNode;
            this.Tail = newNode;
        }
    }

    @Override
    public T get(int index) {
        int count = 0;
        for (Node w = Head; w != null; w = w.Link) {
            if (count == index) {
                return w.Data;
            }
            count++;
        }
        return null;
    }

    @Override
    public boolean remove(T item) {
        for (Node w = Head; w != null; w = w.Link) {
            if (w.Data.equals(item) && w != Head) {
                Node charge;
                for (charge = Head; charge.Link != w; charge = charge.Link) ;
                if (w == Tail) {
                    Tail = charge;
                    Tail.Link = null;
                } else {
                    charge.Link = w.Link;
                }
                return true;
            } else if (w.Data.equals(item) && w == Head) {
                Head = w.Link;
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node current = Head;

            @Override
            public boolean hasNext() {
                if (current == null) {
                    return false;
                }
                return true;
            }

            @Override
            public T next() {
                Node temp;
                if (current != null) {
                    temp = current;
                } else {
                    temp = null;
                }
                current = current.Link;
                return temp.Data;
            }
        };
    }
}
