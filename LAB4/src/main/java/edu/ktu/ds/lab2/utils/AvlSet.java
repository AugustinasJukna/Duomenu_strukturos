package edu.ktu.ds.lab2.utils;

import java.util.Comparator;

/**
 * Rikiuojamos objektų kolekcijos - aibės realizacija AVL-medžiu.
 *
 * @param <E> Aibės elemento tipas. Turi tenkinti interfeisą Comparable<E>, arba
 *            per klasės konstruktorių turi būti paduodamas Comparator<E>
 *            interfeisą tenkinantis objektas.
 * @author darius.matulis@ktu.lt
 * @užduotis Peržiūrėkite ir išsiaiškinkite pateiktus metodus.
 */
public class AvlSet<E extends Comparable<E>> extends BstSet<E> implements SortedSet<E> {

    public AvlSet() {
    }

    public AvlSet(Comparator<? super E> c) {
        super(c);
    }

    /**
     * Aibė papildoma nauju elementu.
     *
     * @param element
     */
    @Override
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in add(E element)");
        }
        root = addRecursive(element, (AVLNode<E>) root);

    }
    int getBalance(AVLNode<E> N)
    {
        if (N == null)
            return 0;
        return height((AVLNode<E>) N.left) - height((AVLNode<E>)N.right);
    }


    /**
     * Privatus rekursinis metodas naudojamas add metode;
     *
     * @param element
     * @param node
     * @return
     */
    private AVLNode<E> addRecursive(E element, AVLNode<E> node) {
        if (node == null) {
            size++;
            return new AVLNode<>(element);
        }
        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.setLeft(addRecursive(element, node.getLeft()));
            if ((height(node.getLeft()) - height(node.getRight())) == 2) {
                int cmp2 = c.compare(element, node.getLeft().element);
                node = (cmp2 < 0) ? rightRotation(node) : doubleRightRotation(node);
            }
        } else if (cmp > 0) {
            node.setRight(addRecursive(element, node.getRight()));
            if ((height(node.getRight()) - height(node.getLeft())) == 2) {
                int cmp2 = c.compare(node.getRight().element, element);
                node = (cmp2 < 0) ? leftRotation(node) : doubleLeftRotation(node);
            }
        }
        node.height = Math.max(height(node.getLeft()), height(node.getRight())) + 1;
        return node;
    }

    /**
     * Pašalinamas elementas iš aibės.
     *
     * @param element
     */
    @Override
    public void remove(E element) {
        if (element == null) return;
        root = deleteNode((AVLNode<E>) root, element);
    }

    /*private AVLNode<E> removeRecursive(E element, AVLNode<E> n) {
        if (n == null) {
            return n;
        }

        int cmp = c.compare(element, n.element);
        if (cmp < 0) {
            n.setLeft(removeRecursive(element, n.getLeft()));
        }
        else if (cmp > 0) {
            n.setRight(removeRecursive(element, n.getRight()));
        }

        else  {
            if (n.getLeft() == null)
                return n.getRight();
            else if (n.getRight() == null)
                return n.getLeft();

            n.element = minimumValue(n.getRight());
            n.setRight(removeRecursive(n.element, n.getRight()));
            size--;
        }

        n.height = Math.max(height(n.getLeft()), height(n.getRight())) + 1;

        if ((height(n.getLeft()) - height(n.getRight())) == -2) {
            if (n.getLeft() == null && n.getRight() != null) {
                n = leftRotation(n);
                return n;
            }
            int cmp2 = c.compare(n.element, n.getLeft().element);
            n = (cmp2 < 0) ? rightRotation(n) : doubleRightRotation(n);
        }

        if ((height(n.getLeft()) - height(n.getRight())) == 2) {
            if (n.getLeft() != null && n.getRight() == null) {
                n = rightRotation(n);
                return n;
            }
            int cmp2 = c.compare(n.getRight().element, n.element);
            n = (cmp2 < 0) ? leftRotation(n) : doubleLeftRotation(n);
        }

        return n;
    }*/

    AVLNode<E> deleteNode(AVLNode<E> root, E key)
    {
        if (root == null)
            return root;
        int cmp = c.compare(key, root.element);
        if (cmp < 0)
            root.setLeft(deleteNode(root.getLeft(), key));
        else if (cmp > 0)
            root.setRight(deleteNode(root.getRight(), key));
        else
        {
            if ((root.left == null) || (root.right == null))
            {
                AVLNode<E> y = null;
                if (y == root.left)
                    y = root.getRight();
                else
                    y = root.getLeft();
                if (y == null)
                {
                    y = root;
                    root = null;
                }
                else
                    root = y;
            }
            else
            {

                AVLNode<E> y = minValueNode(root.getRight());

                root.element = y.element;

                root.right = deleteNode(root.getRight(), y.element);
            }
        }

        if (root == null)
            return root;

        root.height = Math.max(height(root.getLeft()), height(root.getRight())) + 1;

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.getLeft()) >= 0)
            return rightRotation(root);

        if (balance > 1 && getBalance(root.getLeft()) < 0)
        {
            root.left = leftRotation(root.getLeft());
            return rightRotation(root);
        }

        if (balance < -1 && getBalance(root.getRight()) <= 0)
            return leftRotation(root);

        if (balance < -1 && getBalance(root.getRight()) > 0)
        {
            root.right = rightRotation(root.getRight());
            return leftRotation(root);
        }

        return root;
    }

    private E minimumValue(AVLNode<E> node)
    {
        E minimum = node.element;
        while (node.getLeft() != null)
        {
            minimum = node.left.element;
            node = node.getLeft();
        }
        return minimum;
    }

    AVLNode<E> minValueNode(AVLNode<E> node)
    {
        AVLNode<E> current = node;
        while (current.left != null)
            current = current.getLeft();
        return current;
    }

    // Papildomi privatūs metodai, naudojami operacijų su aibe realizacijai
    // AVL-medžiu;

    //           n2
    //          /                n1
    //         n1      ==>      /  \
    //        /                n3  n2
    //       n3

    private AVLNode<E> rightRotation(AVLNode<E> n2) {
        AVLNode<E> n1 = n2.getLeft();
        n2.setLeft(n1.getRight());
        n1.setRight(n2);
        n2.height = Math.max(height(n2.getLeft()), height(n2.getRight())) + 1;
        n1.height = Math.max(height(n1.getLeft()), height(n2)) + 1;
        return n1;
    }

    private AVLNode<E> leftRotation(AVLNode<E> n1) {
        AVLNode<E> n2 = n1.getRight();
        n1.setRight(n2.getLeft());
        n2.setLeft(n1);
        n1.height = Math.max(height(n1.getLeft()), height(n1.getRight())) + 1;
        n2.height = Math.max(height(n2.getRight()), height(n1)) + 1;
        return n2;
    }

    //            n3               n3
    //           /                /                n2
    //          n1      ==>      n2      ==>      /  \
    //           \              /                n1  n3
    //            n2           n1
    //
    private AVLNode<E> doubleRightRotation(AVLNode<E> n3) {
        n3.left = leftRotation(n3.getLeft());
        return rightRotation(n3);
    }

    private AVLNode<E> doubleLeftRotation(AVLNode<E> n1) {
         n1.right = rightRotation(n1.getRight());
        return leftRotation(n1);
    }

    private int height(AVLNode<E> n) {
        return (n == null) ? -1 : n.height;
    }

    /**
     * Vidinė kolekcijos mazgo klasė
     *
     * @param <N> mazgo elemento duomenų tipas
     */
    protected class AVLNode<N> extends BstNode<N> {

        protected int height;

        protected AVLNode(N element) {
            super(element);
            this.height = 0;
        }

        protected void setLeft(AVLNode<N> left) {
            this.left = left;
        }

        protected AVLNode<N> getLeft() {
            return (AVLNode<N>) left;
        }

        protected void setRight(AVLNode<N> right) {
            this.right = right;
        }

        protected AVLNode<N> getRight() {
            return (AVLNode<N>) right;
        }
    }
}
