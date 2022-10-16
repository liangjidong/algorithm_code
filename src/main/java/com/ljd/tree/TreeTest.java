package com.ljd.tree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

// 题目数量：5
public class TreeTest {
    public static void main(String[] args) {
        List<Integer> tree = new ArrayList<>();
        tree.add(1);
        tree.add(5);
        tree.add(2);
        tree.add(null);
        tree.add(null);
        tree.add(30);
        tree.add(4);
        System.out.println("preorder");
        preorderTraversal(createTree(tree, 0));
        preorderTraversalNotRecursion(createTree(tree, 0));
        System.out.println("inorder");
        inorderTraversal(createTree(tree, 0));
        inorderTraversalNotRecursion(createTree(tree, 0));
        System.out.println("postorder");
        postorderTraversal(createTree(tree, 0));
        postorderTraversalNotRecursive(createTree(tree, 0));
        System.out.println("bfs");
        bfs(createTree(tree, 0));

        System.out.println("bst");
        System.out.println(validBinarySearchTree(createTree(tree, 0)));
        List<Integer> bstTree = new ArrayList<>();
        bstTree.add(5);
        bstTree.add(3);
        bstTree.add(7);
        bstTree.add(1);
        bstTree.add(null);
        bstTree.add(6);
        bstTree.add(20);
        System.out.println(validBinarySearchTree(createTree(bstTree, 0)));

        // 中序遍历 -- 记录prev
        prev = null;
        System.out.println(isValidBST(createTree(tree, 0)));
        prev = null;
        System.out.println(isValidBST(createTree(bstTree, 0)));
    }

    public static void preorderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.println(root.val);
        preorderTraversal(root.left);
        preorderTraversal(root.right);
    }

    public static void preorderTraversalNotRecursion(TreeNode root) {
        if (root == null) {
            return;
        }

        // 使用栈
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode h = stack.pop();
            System.out.println(h.val);
            if (h.right != null) {
                stack.push(h.right);
            }
            if (h.left != null) {
                stack.push(h.left);
            }
        }
    }

    public static void inorderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }

        inorderTraversal(root.left);
        System.out.println(root.val);
        inorderTraversal(root.right);
    }

    public static void inorderTraversalNotRecursion(TreeNode root) {
        if (root == null) {
            return;
        }

        Deque<TreeNode> stack = new LinkedList<>();

        TreeNode cur = root;
        while (!stack.isEmpty() || cur != null) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            System.out.println(cur.val);
            cur = cur.right;
        }
    }

    public static void postorderTraversal(TreeNode root) {
        if (root == null) {
            return;
        }

        postorderTraversal(root.left);
        postorderTraversal(root.right);
        System.out.println(root.val);
    }

    public static void postorderTraversalNotRecursive(TreeNode root) {
        if (root == null) {
            return;
        }

        Deque<TreeNode> stack = new LinkedList<>();
        // 首先将左节点入栈
        // 判断栈顶节点是否需要出栈：右子节点已经出栈
        TreeNode cur = null;
        TreeNode prev = null;
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            cur = stack.peek();
            if (prev == cur.right || cur.right == null) { // todo 背掉，每次这里都有问题
                stack.pop();
                prev = cur;
                System.out.println(cur.val);
            } else {
                root = cur.right;
            }
        }

    }

    public static void bfs(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode dummy = new TreeNode();

        Deque<TreeNode> queue = new LinkedList<>();
        queue.addLast(root);
        queue.addLast(dummy);

        while (!queue.isEmpty()) {
            TreeNode node = queue.removeFirst();
            if (node == dummy) {
                if (queue.isEmpty()) {
                    return;
                }
                System.out.println("-----");
                queue.addLast(dummy);
            } else {
                System.out.print(node.val + ",");
                if (node.left != null) {
                    queue.addLast(node.left);
                }
                if (node.right != null) {
                    queue.addLast(node.right);
                }
            }
        }

    }

    // 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
    // 左<中<右
    public static boolean validBinarySearchTree(TreeNode root) {
        CheckValidBSTTmp tmp = checkValidBSTTmp(root);
        return tmp == null || tmp.valid;
    }

    private static CheckValidBSTTmp checkValidBSTTmp(TreeNode root) {
        if (root == null) {
            return null;
        }
        CheckValidBSTTmp tmp = new CheckValidBSTTmp();
        CheckValidBSTTmp leftTmp = checkValidBSTTmp(root.left);
        CheckValidBSTTmp rightTmp = checkValidBSTTmp(root.right);
        if (leftTmp != null) {
            if (!leftTmp.valid || leftTmp.max >= root.val) {
                tmp.valid = false;
                return tmp;
            }
        }
        if (rightTmp != null) {
            if (!rightTmp.valid || rightTmp.min <= root.val) {
                tmp.valid = false;
                return tmp;
            }
        }
        tmp.min = leftTmp == null ? root.val : leftTmp.min;
        tmp.max = rightTmp == null ? root.val : rightTmp.max;
        tmp.valid = true;
        return tmp;
    }

    static class CheckValidBSTTmp {
        int max, min;
        boolean valid;
    }

    private static TreeNode prev = null;

    public static boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        // 中序遍历
        if (!isValidBST(root.left)) {
            return false;
        }
        System.out.println(root.val);
        if (prev != null && prev.val >= root.val) {
            return false;
        }
        prev = root;
        return isValidBST(root.right);
    }


    public static TreeNode createTree(List<Integer> list, int idx) {
        if (list == null || list.size() == 0) {
            return null;
        }

        if (idx >= list.size() || list.get(idx) == null) {
            return null;
        }
        TreeNode root = new TreeNode();
        root.val = list.get(idx);
        root.left = createTree(list, idx * 2 + 1);
        root.right = createTree(list, idx * 2 + 2);
        return root;
    }

}

class TreeNode {
    int val;
    TreeNode left, right;
}
