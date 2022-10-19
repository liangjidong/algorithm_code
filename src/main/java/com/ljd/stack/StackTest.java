package com.ljd.stack;

import java.util.*;

// 栈：3题
public class StackTest {
    public static void main(String[] args) {
        int[] a = new int[10];
        operateMinStack();

        System.out.println("decode");
        System.out.println(decode("2[abc]3[cd]ef"));

        System.out.println("max area");
        System.out.println(maxArea(new int[]{1, 2, 3, 4, 5, 6, 7, 8}));
        System.out.println(maxAreaWithStack(new int[]{1, 2, 3, 4, 5, 6, 7, 8}));
    }

    // 设计一个支持 push，pop，top 操作，并能在常数时间内检索到最小元素的栈。
    public static void operateMinStack() {
        MinStack ms = new MinStack();
        ms.push(2);
        ms.push(5);
        ms.push(10);
        ms.push(7);
        ms.push(7);
        ms.push(9);
        ms.push(6);
        System.out.println(ms.getMin());
        ms.pop();
        System.out.println(ms.getMin());
        ms.pop();
        System.out.println(ms.getMin());
        ms.pop();
        System.out.println(ms.getMin());
        ms.pop();
        System.out.println(ms.getMin());
    }

    static class MinStack {
        // 两个栈实现最小栈
        // 第一个栈存入栈的数据
        // 第二个栈存最小值，如果存在比栈顶元素更小的数，则入栈
        private Deque<Integer> stack1, stack2;

        public MinStack() {
            stack1 = new LinkedList<>();
            stack2 = new LinkedList<>();
        }

        public Integer getMin() {
            return stack2.peek();
        }

        public void push(Integer val) {
            if (stack1.isEmpty() || stack1.peek().compareTo(val) >= 0) {
                stack2.push(val);
            }
            stack1.push(val);
        }

        public boolean isEmpty() {
            return stack1.isEmpty();
        }

        public Integer pop() {
            if (isEmpty()) {
                return null;
            }
            if (stack2.peek().compareTo(stack1.peek()) >= 0) {
                stack2.pop();
            }
            return stack1.pop();
        }

        public Integer top() {
            if (isEmpty()) {
                return null;
            }
            return stack1.peek();
        }
    }

    // 给定一个经过编码的字符串，返回它解码后的字符串。
    // s = "3[a]2[bc]", 返回 "aaabcbc".
    // s = "3[a2[c]]", 返回 "accaccacc".
    // s = "2[abc]3[cd]ef", 返回 "abcabccdcdcdef".

    public static String decode(String source) {
        if (source == null || source.isEmpty()) {
            return source;
        }
        // 2[x]32[d]asdf3[r]
        Deque<Character> stack = new LinkedList<>();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            if (c != ']') {
                stack.push(c);
            } else {
                List<Character> temp = new ArrayList<>();
                while (!stack.isEmpty()) {
                    Character c1 = stack.pop();
                    if (c1 == '[') {
                        break;
                    }
                    temp.add(c1);
                }
                int num = 0;
                int t = 0;
                while (!stack.isEmpty()) {
                    Character c1 = stack.peek();
                    if (c1 >= '0' && c1 <= '9') {
                        if (t == 0) {
                            num += (c1 - '0');
                        } else {
                            num += (c1 - '0') * t * 10;
                        }
                        t++;
                        stack.pop();
                    } else {
                        break;
                    }
                }

                for (int i1 = 0; i1 < num; i1++) {
                    for (int j = temp.size() - 1; j >= 0; j--) {
                        stack.push(temp.get(j));
                    }
                }

            }
        }
        char[] cs = new char[stack.size()];
        int i = 0;
        while (!stack.isEmpty()) {
            cs[i] = stack.removeLast();
            i++;
        }
        return new String(cs);
    }

    // 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。 求在该柱状图中，能够勾勒出来的矩形的最大面积
    // 找当前值左右两边小于该值的点，
    public static int maxArea(int[] arr) {
        int max = 0;
        // 1,2,3,0,2,1,4,9
        for (int i = 0; i < arr.length; i++) {
            int width = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] >= arr[i]) {
                    width++;
                } else {
                    break;
                }
            }
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] >= arr[i]) {
                    width++;
                } else {
                    break;
                }
            }
            max = Math.max(width * arr[i], max);
        }
        return max;
    }

    // todo 不能理解为什么这样 -- 为什么?
    public static int maxAreaWithStack(int[] arr) {
        int max = 0;
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i <= arr.length; i++) {
            int cur;
            if (i == arr.length) {
                cur = 0;
            } else {
                cur = arr[i];
            }
            while (!stack.isEmpty() && cur <= arr[stack.peek()]) {
                int top = stack.pop();
                int h = arr[top];
                int w = i;
                if (!stack.isEmpty()) {
                    int peek = stack.peek();
                    w = i - peek - 1;
                }
                max = Math.max(max, h * w);
            }
            stack.push(i);
        }
        return max;
    }
}
