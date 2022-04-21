package edu.ktu.ds.lab2.utils;

import java.util.Comparator;

public class RBTree<E extends Comparable<E>> extends BstSet<E> implements SortedSet<E> {
    protected Node root;
    private final Color RED = new Color("RED");
    private final Color BLACK = new Color("BLACK");

    public RBTree() {
        super(Comparator.naturalOrder());
    }


    public void insert(E key) {
        Node parent = null;
        Node x = root;
        int compareResult = 0;
        while (x != null) {
            parent = x;
            compareResult = c.compare(x.element, key);
            if (compareResult < 0) {
                x = x.right;
            }
            else if (compareResult > 0) {
                x = x.left;
            }
            else return;
        }
        Node insert = new Node(key, RED, parent);
        if (parent == null) {
            root = insert;
        }
        else if (compareResult < 0) {
            parent.right = insert;
        }
        else {
            parent.left = insert;
        }
        size++;
        fixBalanceAfterInsert(insert);

    }

    @Override
    public E get(E key) {
        Node current = root;
        while (current != null) {
            int compareResult = c.compare(current.element, key);
            if (compareResult < 0) {
                current = current.right;
            } else if (compareResult > 0) current = current.left;
            else return current.element;
        }
        return null;
    }

    public void leftRotation(Node toRotate) {
        if (toRotate == null) return;
        Node parent = toRotate.right;
        if (parent != null) {
            toRotate.right = parent.left;
            if (parent.left != null) {
                parent.left.parent = toRotate;
            }
            parent.parent = toRotate.parent;
            if (toRotate.parent == null) {
                root = parent;
            } else if (toRotate == toRotate.parent.left) {
                toRotate.parent.left = parent;
            } else {
                toRotate.parent.right = parent;
            }
            parent.left = toRotate;
            toRotate.parent = parent;
        }
    }

    public void rightRotation(Node toRotate) {
        if (toRotate == null) return;
        Node parent = toRotate.left;
        if (parent != null) {
            toRotate.left = parent.right;
            if (parent.right != null) {
                parent.right.parent = toRotate;
            }
            parent.parent = toRotate.parent;
            if (toRotate.parent == null) {
                root = parent;
            } else if (toRotate == toRotate.parent.right) {
                toRotate.parent.right = parent;
            } else {
                toRotate.parent.left = parent;
            }
            parent.right = toRotate;
            toRotate.parent = parent;
        }
    }


    public void fixBalanceAfterInsert(Node inserted) {
        while (inserted != null && inserted.parent != null && inserted.parent.color == RED) {
            if (inserted.parent.parent != null && inserted.parent == inserted.parent.parent.left) {
                Node y = inserted.parent.parent.right;
                if (y != null && y.color == RED) {
                    inserted.parent.color = BLACK;
                    y.color = BLACK;
                    inserted.parent.parent.color = RED;
                    inserted = inserted.parent.parent;
                }
                else if (inserted == inserted.parent.right) {
                    inserted = inserted.parent;
                    leftRotation(inserted);
                }
                if (inserted.parent != null && inserted.parent.parent != null) {
                    inserted.parent.color = BLACK;
                    inserted.parent.parent.color = RED;
                    rightRotation(inserted.parent.parent);
                }
            }
            else  {
                    Node y = inserted.parent.parent.left;
                if (y != null && y.color == RED) {
                    inserted.parent.color = BLACK;
                    y.color = BLACK;
                    inserted.parent.parent.color = RED;
                    inserted = inserted.parent.parent;
                }
                else if (inserted == inserted.parent.left) {
                    inserted = inserted.parent;
                    rightRotation(inserted);
                }
                if (inserted.parent != null && inserted.parent.parent != null) {
                    inserted.parent.color = BLACK;
                    inserted.parent.parent.color = RED;
                    leftRotation(inserted.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    public void rewireNodes(Node x, Node y) {
        if (x.parent == null) {
            root = y;
        } else if (x.equals(x.parent.left)) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        if (y != null) {
            y.parent = x.parent;
        }
    }

    public void remove(E value) {
        Node current = root;
        while (current != null) {
            int compareResult = c.compare(value, current.element);
            if (compareResult > 0) {
                current = current.right;
            } else if (compareResult < 0) current = current.left;
            else {
                removeNode(current);
                size--;
                break;
            }
        }
    }

    protected void removeNode(Node toRemove) {
        Node y = toRemove;
        Node x = null;
        Color YOriginalColor = y.color;
        if (toRemove.left == null) {
            x = toRemove.right;
            rewireNodes(toRemove, toRemove.right);
        } else if (toRemove.right == null) {
            x = toRemove.left;
            rewireNodes(toRemove, toRemove.left);
        } else {
            y = minimumNode(toRemove.right);
            YOriginalColor = y.color;
            x = y.right;
            if (x != null && y.parent != null && y.parent.equals(toRemove)) {
                x.parent = y;
            } else {
                rewireNodes(y, y.right);
                y.right = toRemove.right;
                if (y.right != null)
                    y.right.parent = y;
            }
            rewireNodes(toRemove, y);
            y.left = toRemove.left;
            y.left.parent = y;
            y.color = toRemove.color;
        }
        if (YOriginalColor.equals(BLACK) && x != null) {
            fixBalanceAfterRemove(x);
        }
    }

    protected void fixBalanceAfterRemove(Node toBalance) {
        while (toBalance != root && toBalance.color == BLACK) {
            if (toBalance == toBalance.parent.left) {
                Node temp = toBalance.parent.right;
                if (temp != null && temp.color == RED) {
                    temp.color = BLACK;
                    toBalance.parent.color = RED;
                    leftRotation(toBalance.parent);
                    temp = toBalance.parent.right;
                }
                if (temp != null) {
                    if (temp.left != null && temp.right != null && temp.left.color == BLACK && temp.right.color == BLACK) {
                        temp.color = RED;
                        toBalance = toBalance.parent;
                    } else if (temp.left != null && temp.right != null && temp.right.color == BLACK) {
                        temp.left.color = BLACK;
                        temp.color = RED;
                        rightRotation(temp);
                        temp = toBalance.parent.right;
                    }
                }
                if (toBalance.parent != null)
                    toBalance.parent.color = BLACK;
                if (temp != null && temp.right != null && toBalance.parent != null) {
                    temp.color = toBalance.parent.color;
                    temp.right.color = BLACK;
                }
                leftRotation(toBalance.parent);
                toBalance = root;
            }
            else {
                Node temp = toBalance.parent.left;
                if (temp != null && temp.color == RED) {
                    temp.color = BLACK;
                    toBalance.parent.color = RED;
                    rightRotation(toBalance.parent);
                    temp = toBalance.parent.left;
                }
                if (temp != null && temp.right != null && temp.left != null && temp.right.color == BLACK && temp.left.color == BLACK) {
                    temp.color = RED;
                    toBalance = toBalance.parent;
                }
                else if (temp != null && temp.left != null && temp.right != null && temp.left.color == BLACK) {
                    temp.right.color = BLACK;
                    temp.color = RED;
                    leftRotation(temp);
                    temp = toBalance.parent.left;
                    temp.left.color = BLACK;
                }
                if (temp != null && toBalance.parent != null)
                    temp.color = toBalance.parent.color;
                if (toBalance.parent != null)
                    toBalance.parent.color = BLACK;
                rightRotation(toBalance.parent);
                toBalance = root;
            }
        }
        toBalance.color = BLACK;
    }


    public Node minimumNode(Node x) {
        Node y = x;
        while (y != null && y.left != null) {
            y = y.left;
        }
        return y;
    }

        protected class Node extends BstNode<E> {
            protected Node left;
            protected Node right;
            protected Color color;
            protected Node parent;

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

            public boolean equals(Node b) {
                if (b == null) {
                    return false;
                }
                if (this.element.equals(b.element)) {
                    return true;
                } else return false;
            }

        }

        protected class Color {
            protected String color;

            public Color(String colorName) {
                this.color = colorName;
            }

            @Override
            public String toString() {
                return this.color;
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
        public String toVisualizedString (String dataCodeDelimiter){
            horizontal = term[0] + term[0];
            return root == null ? ">" + horizontal
                    : toTreeDraw(root, ">", "", dataCodeDelimiter);
        }

        private String toTreeDraw (Node node, String edge, String indent, String dataCodeDelimiter){
            if (node == null) {
                return "";
            }
            String step = (edge.equals(leftEdge)) ? vertical : "   ";
            StringBuilder sb = new StringBuilder();
            sb.append(toTreeDraw(node.right, rightEdge, indent + step, dataCodeDelimiter));
            int t = (node.right != null) ? 1 : 0;
            t = (node.left != null) ? t + 2 : t;
            sb.append(indent).append(edge).append(horizontal).append(term[t]).append(endEdge).append(
                    split(node.element.toString(), dataCodeDelimiter)).append(" " + node.color.toString()).append(System.lineSeparator());
            step = (edge.equals(rightEdge)) ? vertical : "   ";
            sb.append(toTreeDraw(node.left, leftEdge, indent + step, dataCodeDelimiter));
            return sb.toString();
        }

        private String split (String s, String dataCodeDelimiter){
            int k = s.indexOf(dataCodeDelimiter);
            if (k <= 0) {
                return s;
            }
            return s.substring(0, k);
        }
}
