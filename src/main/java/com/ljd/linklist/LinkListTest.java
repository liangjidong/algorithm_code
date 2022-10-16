package com.ljd.linklist;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 题目数量：3
public class LinkListTest {
    public static void main(String[] args) {
        ListNode listNode = createListNode(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        printListNode(listNode);
        printListNode(reverseMN(listNode, 4, 7));

        System.out.println("sort");
        listNode = createListNode(Arrays.asList(2, 1, 5, 4, 8, 7, 1, 10, 9, 100, 20));
        printListNode(sortLinkList(listNode));

        System.out.println("random list node");
        RandomListNode rln = createRandomListNode(Arrays.asList(2, 1, 5, 4, 8, 7, 10, 9), new HashMap<Integer, Integer>() {
            {
                put(1, 2);
                put(5, 8);
                put(9, 10);
                put(7, 1);
            }
        });
        // 2->1->5->4->8->7->10->9
        // 2->2->1->1->5->5->4->4->8->8->7->7->10->10->9->9
        printRandomListNode(copyRandomLinkList(rln));
        printRandomListNode(rln);
    }

    // 给定一个链表，每个节点包含一个额外增加的随机指针，该指针可以指向链表中的任何节点或空节点。 要求返回这个链表的 深拷贝。
    public static RandomListNode copyRandomLinkList(RandomListNode head) {
        // 1，当前链表中，每个节点copy一份放到节点后面
        // 1->2->3 => 1->1->2->2->3->3
        // 2，然后将copy节点的rand指针连上（参考原节点）a, a_copy
        // a_copy.random = a.random.next
        // 3，分离新老节点。
        RandomListNode cur = head;
        while (cur != null) {
            RandomListNode n = new RandomListNode();
            n.val = cur.val;
            n.next = cur.next;
            cur.next = n;

            cur = n.next;
        }

        printRandomListNode(head);

        RandomListNode dummyHead = new RandomListNode();
        RandomListNode rc = dummyHead;
        cur = head;
        while (cur != null) {
            RandomListNode rn = cur.next;
            if (cur.rand != null) {
                rn.rand = cur.rand.next;
            }
            cur = cur.next.next;
        }
        printRandomListNode(head);

        cur = head;
        while (cur != null) {
            RandomListNode rn = cur.next;
            cur.next = cur.next.next;
            cur = cur.next;

            rc.next = rn;
            rc = rc.next;
        }

        return dummyHead.next;
    }

    //在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序。
    private static ListNode sortLinkList(ListNode start) {
        if (start == null || start.next == null) {
            return start;
        }
        // 找中间节点
        ListNode fast = start, slow = start, prev = null;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            prev = slow;
            slow = slow.next;
        }
        ListNode first = sortLinkList(prev.next);
        prev.next = null;
        ListNode second = sortLinkList(start);

        // 将first和second合并
        ListNode dummy = new ListNode();
        ListNode p = dummy;
        while (first != null || second != null) {
            if (second == null || first != null && first.val < second.val) {
                p.next = first;
                first = first.next;
            } else {
                p.next = second;
                second = second.next;
            }
            p = p.next;
        }
        p.next = null;
        printListNode(dummy.next);
        return dummy.next;
    }


    // 反转链表的m->n位置
    public static ListNode reverseMN(ListNode head, int m, int n) {
        if (m <= 0 || n <= 0 || m > n) {
            return head;
        }
        ListNode dummy = new ListNode();
        dummy.next = head;
        ListNode cur = head, prev = dummy;
        int idx = 0;
        while (cur != null) {
            idx++;
            if (idx == m) {
                break;
            }
            prev = cur;
            cur = cur.next;
        }

        idx = 1;
        ListNode last = cur;
        ListNode headP = prev;
        ListNode next = null;
        cur = cur.next;
        while (cur != null && idx < n - m + 1) {
            next = cur.next;
            // 头插法
            ListNode tmp = headP.next;
            headP.next = cur;
            cur.next = tmp;
            idx++;
            cur = next;
        }
        last.next = cur;

        return dummy.next;
    }

    public static ListNode createListNode(List<Integer> list) {
        ListNode dummy = new ListNode();
        ListNode cur = dummy;
        for (Integer i : list) {
            ListNode t = new ListNode();
            t.val = i;
            cur.next = t;
            cur = t;
        }

        return dummy.next;
    }

    public static RandomListNode createRandomListNode(List<Integer> list, Map<Integer, Integer> randomFrom/*randomNext->from*/) {
        RandomListNode dummy = new RandomListNode();
        Map<Integer, RandomListNode> fromNodeMap = new HashMap<>();
        RandomListNode cur = dummy;
        for (Integer i : list) {
            RandomListNode t = new RandomListNode();
            t.val = i;
            cur.next = t;
            cur = t;
            fromNodeMap.put(i, t);
        }

        for (Integer i : list) {
            Integer from = randomFrom.get(i);
            if (from != null) {
                fromNodeMap.get(from).rand = fromNodeMap.get(i);
            }
        }

        return dummy.next;
    }

    public static void printListNode(ListNode head) {
        while (head != null) {
            System.out.print(head.val);
            if (head.next != null) {
                System.out.print("->");
            }
            head = head.next;
        }
        System.out.println();
    }

    public static void printRandomListNode(RandomListNode head) {
        while (head != null) {
            System.out.print(head.val);
            if (head.rand != null) {
                System.out.print("-" + head.rand.val);
            } else {
                System.out.print("-null");
            }
            if (head.next != null) {
                System.out.print("->");
            }
            head = head.next;
        }
        System.out.println();
    }


    private static class ListNode {
        public int val;
        public ListNode next;
    }

    private static class RandomListNode {
        public RandomListNode rand;
        public int val;
        public RandomListNode next;
    }
}
