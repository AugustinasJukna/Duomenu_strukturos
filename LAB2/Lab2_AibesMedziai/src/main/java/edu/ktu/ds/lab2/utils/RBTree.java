package edu.ktu.ds.lab2.utils;

import java.util.Comparator;

public class RBTree<E extends Comparable<E>> extends BstSet<E> implements SortedSet<E> {
    protected Node root;
    private final Color RED = new Color("RED");
    private final Color BLACK =  new Color("BLACK");

    public RBTree() {
        super(Comparator.naturalOrder());
    }


    public void insert(E item) {
        if (root == null) {
            root = new Node(item, BLACK);
            size++;
        }
        else {
            Node parentNode = null;
            Node currentNode = root;
            int compareInt = 0;
            while (currentNode != null) {
                parentNode = currentNode;
                compareInt = c.compare(currentNode.element, item);
                if (compareInt < 0) {
                    currentNode = currentNode.right;
                }
                else currentNode = currentNode.left;
            }
            Node toInsert = new Node(item, RED, parentNode);
            if (compareInt < 0) {
                parentNode.right = toInsert;
                size++;
            }
            else {
                parentNode.left = toInsert;
                size++;
            }
            fixBalanceAfterInsert(toInsert);
        }
    }

    public void leftRotation(Node toRotate) {
        Node parent = toRotate.right;
        toRotate.right = parent.left;
        if (parent.left != null) {
            parent.left.parent = toRotate;
        }
        parent.parent = toRotate.parent;
        if (toRotate.parent == null) {
            root = parent;
        }
        else if (toRotate == toRotate.parent.left) {
            toRotate.parent.left = parent;
        }
        else {
            toRotate.parent.right = parent;
        }
        parent.left = toRotate;
        toRotate.parent = parent;
    }

    public void rightRotation(Node toRotate) {
        Node parent = toRotate.left;
        toRotate.left = parent.right;
        if (parent.right != null) {
            parent.right.parent = toRotate;
        }
        parent.parent = toRotate.parent;
        if (toRotate.parent == null) {
            root = parent;
        }
        else if (toRotate == toRotate.parent.right) {
            toRotate.parent.right = parent;
        }
        else {
            toRotate.parent.left = parent;
        }
        parent.right = toRotate;
        toRotate.parent = parent;
    }

    public void fixBalanceAfterInsert(Node inserted) {
        while (inserted.parent.color.equals(RED)) {
            if (inserted.parent.equals(inserted.parent.parent.left)) {
                Node temp = inserted.parent.parent.right;
                if (temp.color.equals(RED)) {
                    inserted.parent.color = BLACK;
                    temp.color = BLACK;
                    inserted.parent.parent.color = RED;
                    inserted = inserted.parent.parent;
                }
                else if (inserted.equals(inserted.parent.right)) {
                    inserted = inserted.parent;
                    leftRotation(inserted);
                }
                inserted.parent.color = BLACK;
                inserted.parent.parent.color = RED;
                rightRotation(inserted.parent.parent);
            }
            else {
                Node temp = inserted.parent.parent.right;
                if (temp.color.equals(RED)) {
                    inserted.parent.color = BLACK;
                    temp.color = BLACK;
                    inserted.parent.parent.color = RED;
                    inserted = inserted.parent.parent;
                }
                else if (inserted.equals(inserted.parent.right)) {
                    inserted = inserted.parent;
                    leftRotation(inserted);
                }
                inserted.parent.color = BLACK;
                inserted.parent.parent.color = RED;
                rightRotation(inserted.parent.parent);
            }
            }
            root.color = BLACK;
        }
    }




    protected class Node extends BstSet.BstNode<E> {
        //protected E value;
        protected Node left;
        protected Node right;
        protected Color color;
        protected Node parent;

        public Node() {}

        public Node(E data) {
            super(data);
        }

        public Node(E data, Color color) {
            super(data);
            this.color = color;
        }

        public Node(E data, Color color, Node parent) {
            super(data);
            this.color = color;
            this.parent = parent;
        }

    }

    protected class Color {
        protected String color;

        public Color(String colorName) {
            this.color = colorName;
        }
    }

    private static final String[] term = {"\u2500", "\u2534", "\u252C", "\u253C"};
    private static final String rightEdge = "\u250C";
    private static final String leftEdge = "\u2514";
    private static final String endEdge = "\u25CF";
    private static final String vertical = "\u2502  ";
    private String horizontal;

    /* Papildomas metodas, išvedantis aibės elementus į vieną String eilutę.
     * String eilutė formuojama atliekant elementų postūmį nuo krašto,
     * priklausomai nuo elemento lygio medyje. Galima panaudoti spausdinimui į
     * ekraną ar failą tyrinėjant medžio algoritmų veikimą.
     *
     * @author E. Karčiauskas
     */
    @Override
    public String toVisualizedString(String dataCodeDelimiter) {
        horizontal = term[0] + term[0];
        return root == null ? ">" + horizontal
                : toTreeDraw(root, ">", "", dataCodeDelimiter);
    }

    private String toTreeDraw(Node node, String edge, String indent, String dataCodeDelimiter) {
        if (node == null) {
            return "";
        }
        String step = (edge.equals(leftEdge)) ? vertical : "   ";
        StringBuilder sb = new StringBuilder();
        sb.append(toTreeDraw(node.right, rightEdge, indent + step, dataCodeDelimiter));
        int t = (node.right != null) ? 1 : 0;
        t = (node.left != null) ? t + 2 : t;
        sb.append(indent).append(edge).append(horizontal).append(term[t]).append(endEdge).append(
                split(node.element.toString(), dataCodeDelimiter)).append(System.lineSeparator());
        step = (edge.equals(rightEdge)) ? vertical : "   ";
        sb.append(toTreeDraw(node.left, leftEdge, indent + step, dataCodeDelimiter));
        return sb.toString();
    }

    private String split(String s, String dataCodeDelimiter) {
        int k = s.indexOf(dataCodeDelimiter);
        if (k <= 0) {
            return s;
        }
        return s.substring(0, k);
    }


}
