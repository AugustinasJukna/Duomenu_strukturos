package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[] numbers = {2, 7, 3, 1, 5, 2, 6, 2};
        ArrayList<Integer> maximumValues = new ArrayList();
        int k = 4, m = numbers.length;
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        int i;
        for (i = 0; i < k; i++) {
            while (!deque.isEmpty() && numbers[i] >= numbers[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.addLast(i);
        }
        for (; i < m; i++) {
            maximumValues.add(deque.peekFirst());
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pop();
            }
            while (!deque.isEmpty() && numbers[i] >= numbers[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.addLast(i);
        }
        maximumValues.add(deque.peekFirst());

        for (int number : maximumValues) {
            System.out.println(numbers[number]);
        }
    }
}
