package com.company;

import java.util.ArrayList;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        testMethod();
    }

    static void testMethod() {
        String[] testLines = {"}", "{()}{[]}", "[{}}", "{()[{}]}{}", "{(})", "([(]{)})", "[{]}()", "([{}])({[]})"};
        boolean flag;
        for (String string : testLines) {
            flag = findsIfStringIsBalanced(string);
            if (flag) {
                System.out.println(string + " " + true);
            }
            else {
                System.out.println(string + " " + false);
            }
        }
    }

     static boolean findsIfStringIsBalanced(String line) {
        char[] array = line.toCharArray();
        Stack<Character> symbols = new Stack<>();
        boolean flag = false;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == '{' || array[i] ==  '[' || array[i] ==  '(') {
                symbols.push(array[i]);
            }
            else if (!symbols.empty()) {
                if (array[i] == '}') {
                    if (symbols.peek() != '{') {
                        flag = false;
                        break;
                    }
                    else {
                        symbols.pop();
                        flag = true;
                    }
                }
                else if (array[i] == ']') {
                    if (symbols.peek() != '[') {
                        flag = false;
                        break;
                    }
                    else {
                        symbols.pop();
                        flag = true;
                    }
                }
                else if (array[i] == ')') {
                    if (symbols.peek() != '(') {
                        flag = false;
                        break;
                    }
                    else {
                        symbols.pop();
                        flag = true;
                    }
                }
            }
        }

        return flag;
    }

}
