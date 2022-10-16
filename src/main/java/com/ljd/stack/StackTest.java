package com.ljd.stack;

import java.util.Deque;
import java.util.LinkedList;

// 栈：1题
public class StackTest {
    public static void main(String[] args) {
        operateMinStack();
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
}
