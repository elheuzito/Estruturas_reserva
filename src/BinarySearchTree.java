import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class TreeNode {
    String word;
    List<Integer> indices;
    TreeNode left, right;

    public TreeNode(String word, int index) {
        this.word = word;
        this.indices = new ArrayList<>();
        this.indices.add(index);
        this.left = this.right = null;
    }
}

class BinarySearchTree {
    private TreeNode root;

    public BinarySearchTree() {
        this.root = null;
    }

    public void insert(String word, int index) {
        root = insertRec(root, word, index);
    }

    private TreeNode insertRec(TreeNode root, String word, int index) {
        if (root == null) {
            root = new TreeNode(word, index);
            return root;
        }
        if (word.compareTo(root.word) < 0) {
            root.left = insertRec(root.left, word, index);
        } else if (word.compareTo(root.word) > 0) {
            root.right = insertRec(root.right, word, index);
        } else {
            root.indices.add(index); // Se a palavra já existe, apenas atualize os índices
        }
        return root;
    }

    public List<Integer> search(String word) {
        TreeNode node = searchRec(root, word);
        return node != null ? node.indices : null;
    }

    private TreeNode searchRec(TreeNode root, String word) {
        if (root == null) {
            return null;
        }

        if (root.word.startsWith(word)) {
            return root;
        }

        int cmp = word.compareTo(root.word);

        if (cmp < 0) {
            return searchRec(root.left, word);
        } else {
            return searchRec(root.right, word);
        }
    }

    public void delete(String word) {
        root = deleteRec(root, word);
    }

    private TreeNode deleteRec(TreeNode root, String word) {
        if (root == null) {
            return root;
        }
        if (word.compareTo(root.word) < 0) {
            root.left = deleteRec(root.left, word);
        } else if (word.compareTo(root.word) > 0) {
            root.right = deleteRec(root.right, word);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            TreeNode minLargerNode = getMin(root.right);
            root.word = minLargerNode.word;
            root.indices = minLargerNode.indices;
            root.right = deleteRec(root.right, minLargerNode.word);
        }
        return root;
    }

    private TreeNode getMin(TreeNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    public void printInOrder() {
        printInOrderRec(root);
    }

    private void printInOrderRec(TreeNode root) {
        if (root != null) {
            printInOrderRec(root.left);
            System.out.print(root.word + " ");
            boolean first = true;
            for (int index : root.indices) {
                if (index != -1) {
                    if (!first) {
                        System.out.print(" ");
                    }
                    System.out.print(index);
                    first = false;
                }
            }
            System.out.println();
            printInOrderRec(root.right);
        }
    }

    public boolean isEmpty() {
        return root == null;
    }
    public List<Integer> searchPrefix(String prefix) {
        List<Integer> indices = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            if (node.word.startsWith(prefix)) {
                indices.addAll(node.indices);
            }

            if (node.left != null && node.word.compareTo(prefix) > 0) {
                queue.offer(node.left);
            }
            if (node.right != null && node.word.compareTo(prefix) < 0) {
                queue.offer(node.right);
            }
        }

        return indices;
    }
}
