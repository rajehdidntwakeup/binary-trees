package at.hochschule.burgenland.bswe.algo;

import java.util.Arrays;

import at.hochschule.burgenland.bswe.algo.model.Node;
import at.hochschule.burgenland.bswe.algo.traversal.TreeTraverser;


/**
 * This class is only used for testing purposes.
 * Hopefully it's this time :)
 * I went through all the algorithms that I used in my project.
 */
public class TestMain {

  private static int tests = 0;
  private static int passed = 0;

  public static void main(String[] args) {
    System.out.println("Running TreeTraverser tests...\n");

    // 1) Empty tree
    Node empty = null;
    check("empty-preorder", TreeTraverser.traverse(empty, "preorder"), new int[] {});
    check("empty-inorder", TreeTraverser.traverse(empty, "inorder"), new int[] {});
    check("empty-postorder", TreeTraverser.traverse(empty, "postorder"), new int[] {});
    check("empty-levelorder", TreeTraverser.traverse(empty, "levelorder"), new int[] {});

    // 2) Single node tree
    Node single = new Node(10);
    check("single-preorder", TreeTraverser.traverse(single, "preorder"), new int[] {10});
    check("single-inorder", TreeTraverser.traverse(single, "inorder"), new int[] {10});
    check("single-postorder", TreeTraverser.traverse(single, "postorder"), new int[] {10});
    check("single-levelorder", TreeTraverser.traverse(single, "levelorder"), new int[] {10});

    // 3) Perfect balanced tree
    Node balanced = perfectBalancedTree();
    check("balanced-preorder", TreeTraverser.traverse(balanced, "preorder"), new int[] {4, 2, 1, 3, 6, 5, 7});
    check("balanced-inorder", TreeTraverser.traverse(balanced, "inorder"), new int[] {1, 2, 3, 4, 5, 6, 7});
    check("balanced-postorder", TreeTraverser.traverse(balanced, "postorder"), new int[] {1, 3, 2, 5, 7, 6, 4});
    check("balanced-levelorder", TreeTraverser.traverse(balanced, "levelorder"), new int[] {4, 2, 6, 1, 3, 5, 7});

    // 4) Left-skewed tree
    Node leftSkew = leftSkewed(5);
    check("left-skew-preorder", TreeTraverser.traverse(leftSkew, "preorder"), new int[] {5, 4, 3, 2, 1});
    check("left-skew-inorder", TreeTraverser.traverse(leftSkew, "inorder"), new int[] {1, 2, 3, 4, 5});
    check("left-skew-postorder", TreeTraverser.traverse(leftSkew, "postorder"), new int[] {1, 2, 3, 4, 5});
    check("left-skew-levelorder", TreeTraverser.traverse(leftSkew, "levelorder"), new int[] {5, 4, 3, 2, 1});

    // 5) Right-skewed tree
    Node rightSkew = rightSkewed(5);
    check("right-skew-preorder", TreeTraverser.traverse(rightSkew, "preorder"), new int[] {1, 2, 3, 4, 5});
    check("right-skew-inorder", TreeTraverser.traverse(rightSkew, "inorder"), new int[] {1, 2, 3, 4, 5});
    check("right-skew-postorder", TreeTraverser.traverse(rightSkew, "postorder"), new int[] {5, 4, 3, 2, 1});
    check("right-skew-levelorder", TreeTraverser.traverse(rightSkew, "levelorder"), new int[] {1, 2, 3, 4, 5});

    // 6) Case-insensitivity of order string
    check("case-insensitive-InOrDeR", TreeTraverser.traverse(balanced, "InOrDeR"), new int[] {1, 2, 3, 4, 5, 6, 7});

    // 7) Invalid traversal type should throw
    expectIllegalArgument("invalid-order-exception", () -> TreeTraverser.traverse(balanced, "zigzag"));

    // 8) printTraversal sanity (no assertion, just ensure no crash/output looks right)
    int[] inorder = TreeTraverser.traverse(balanced, "inorder");
    System.out.print("[INFO] printTraversal demo -> ");
    TreeTraverser.printTraversal(inorder, "inorder");

    // 9) Sparse/non-BST-shaped tree (missing children at multiple levels)
    Node sparse = buildSparseTree();
    check("sparse-preorder", TreeTraverser.traverse(sparse, "preorder"), new int[] {10, 5, 7, 20, 30, 25});
    check("sparse-inorder", TreeTraverser.traverse(sparse, "inorder"), new int[] {5, 7, 10, 20, 25, 30});
    check("sparse-postorder", TreeTraverser.traverse(sparse, "postorder"), new int[] {7, 5, 25, 30, 20, 10});
    check("sparse-levelorder", TreeTraverser.traverse(sparse, "levelorder"), new int[] {10, 5, 20, 7, 30, 25});

    // 10) Tree with duplicate values across branches
    Node dups = buildDuplicatesTree();
    check("dups-preorder", TreeTraverser.traverse(dups, "preorder"), new int[] {2, 1, 2, 1});
    check("dups-inorder", TreeTraverser.traverse(dups, "inorder"), new int[] {1, 2, 1, 2});
    check("dups-postorder", TreeTraverser.traverse(dups, "postorder"), new int[] {1, 1, 2, 2});
    check("dups-levelorder", TreeTraverser.traverse(dups, "levelorder"), new int[] {2, 1, 2, 1});

    // 11) Extreme integer values mixed with negatives and zero
    Node extremes = buildExtremeValuesTree();
    check("extreme-preorder", TreeTraverser.traverse(extremes, "preorder"),
        new int[] {0, Integer.MIN_VALUE, Integer.MAX_VALUE, -1});
    check("extreme-inorder", TreeTraverser.traverse(extremes, "inorder"),
        new int[] {Integer.MIN_VALUE, 0, -1, Integer.MAX_VALUE});
    check("extreme-postorder", TreeTraverser.traverse(extremes, "postorder"),
        new int[] {Integer.MIN_VALUE, -1, Integer.MAX_VALUE, 0});
    check("extreme-levelorder", TreeTraverser.traverse(extremes, "levelorder"),
        new int[] {0, Integer.MIN_VALUE, Integer.MAX_VALUE, -1});

    // 12) Zigzag path (alternating left-right), depth 8
    Node zigzag = buildZigZag(8);
    check("zigzag-preorder", TreeTraverser.traverse(zigzag, "preorder"), new int[] {1, 2, 3, 4, 5, 6, 7, 8});
    check("zigzag-postorder", TreeTraverser.traverse(zigzag, "postorder"), new int[] {8, 7, 6, 5, 4, 3, 2, 1});
    check("zigzag-levelorder", TreeTraverser.traverse(zigzag, "levelorder"), new int[] {1, 2, 3, 4, 5, 6, 7, 8});

    // 13) Deep left-skewed (depth 300) — verify length and boundary elements only
    Node deepLeft = leftSkewed(300);
    int[] preDeep = TreeTraverser.traverse(deepLeft, "preorder");
    int[] inDeep = TreeTraverser.traverse(deepLeft, "inorder");
    int[] postDeep = TreeTraverser.traverse(deepLeft, "postorder");
    int[] levelDeep = TreeTraverser.traverse(deepLeft, "levelorder");
    checkLen("deep-left-preorder-len", preDeep, 300);
    checkLen("deep-left-inorder-len", inDeep, 300);
    checkLen("deep-left-postorder-len", postDeep, 300);
    checkLen("deep-left-levelorder-len", levelDeep, 300);
    // Spot-check boundaries
    check("deep-left-preorder-first-last", new int[] {preDeep[0], preDeep[preDeep.length - 1]}, new int[] {300, 1});
    check("deep-left-inorder-first-last", new int[] {inDeep[0], inDeep[inDeep.length - 1]}, new int[] {1, 300});
    check("deep-left-postorder-first-last", new int[] {postDeep[0], postDeep[postDeep.length - 1]}, new int[] {1, 300});
    check("deep-left-levelorder-first-last", new int[] {levelDeep[0], levelDeep[levelDeep.length - 1]},
        new int[] {300, 1});

    // 14) Perfect tree with 15 nodes (1..15)
    Node perfect15 = buildPerfect15();
    check("perfect15-preorder", TreeTraverser.traverse(perfect15, "preorder"),
        new int[] {8, 4, 2, 1, 3, 6, 5, 7, 12, 10, 9, 11, 14, 13, 15});
    check("perfect15-inorder", TreeTraverser.traverse(perfect15, "inorder"),
        new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
    check("perfect15-postorder", TreeTraverser.traverse(perfect15, "postorder"),
        new int[] {1, 3, 2, 5, 7, 6, 4, 9, 11, 10, 13, 15, 14, 12, 8});
    check("perfect15-levelorder", TreeTraverser.traverse(perfect15, "levelorder"),
        new int[] {8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15});

    System.out.printf("\nSummary: %d/%d tests passed.\n", passed, tests);
    if (passed != tests) {
      System.exit(1);
    }
  }

  private static void check(String name, int[] actual, int[] expected) {
    tests++;
    if (Arrays.equals(actual, expected)) {
      passed++;
      System.out.println("[PASS] " + name + " -> " + Arrays.toString(actual));
    } else {
      System.out.println("[FAIL] " + name);
      System.out.println("       expected: " + Arrays.toString(expected));
      System.out.println("       actual  : " + Arrays.toString(actual));
    }
  }

  private static void checkLen(String name, int[] actual, int expectedLen) {
    tests++;
    if (actual.length == expectedLen) {
      passed++;
      System.out.println("[PASS] " + name + " -> len=" + actual.length);
    } else {
      System.out.println("[FAIL] " + name);
      System.out.println("       expected len: " + expectedLen);
      System.out.println("       actual   len: " + actual.length);
    }
  }

  private static void expectIllegalArgument(String name, Runnable r) {
    tests++;
    try {
      r.run();
      System.out.println("[FAIL] " + name + " -> expected IllegalArgumentException, but none thrown");
    } catch (IllegalArgumentException ex) {
      passed++;
      System.out.println("[PASS] " + name + " -> caught: " + ex.getMessage());
    } catch (Throwable t) {
      System.out.println("[FAIL] " + name + " -> expected IllegalArgumentException, but got: " + t);
    }
  }

  private static Node perfectBalancedTree() {
    //          4
    //        /   \
    //       2     6
    //      / \   / \
    //     1   3 5   7
    Node n1 = new Node(1);
    Node n3 = new Node(3);
    Node n5 = new Node(5);
    Node n7 = new Node(7);
    Node n2 = new Node(2);
    n2.left = n1;
    n2.right = n3;
    Node n6 = new Node(6);
    n6.left = n5;
    n6.right = n7;
    Node n4 = new Node(4);
    n4.left = n2;
    n4.right = n6;
    return n4;
  }

  private static Node leftSkewed(int n) {
    if (n <= 0) {
      return null;
    }
    Node root = new Node(n);
    Node curr = root;
    for (int v = n - 1; v >= 1; v--) {
      Node node = new Node(v);
      curr.left = node;
      curr = node;
    }
    return root;
  }

  private static Node rightSkewed(int n) {
    Node root = null;
    Node curr = null;
    for (int v = 1; v <= n; v++) {
      Node node = new Node(v);
      if (root == null) {
        root = node;
        curr = node;
      } else {
        curr.right = node;
        curr = node;
      }
    }
    return root;
  }

  private static Node buildSparseTree() {
    //          10
    //         /  \
    //        5    20
    //         \     \
    //          7     30
    //               /
    //              25
    Node n10 = new Node(10);
    Node n5 = new Node(5);
    Node n20 = new Node(20);
    Node n7 = new Node(7);
    Node n30 = new Node(30);
    Node n25 = new Node(25);
    n10.left = n5;
    n10.right = n20;
    n5.right = n7;
    n20.right = n30;
    n30.left = n25;
    return n10;
  }

  private static Node buildDuplicatesTree() {
    //       2
    //      / \
    //     1   2
    //        /
    //       1
    Node a = new Node(2);
    Node b = new Node(1);
    Node c = new Node(2);
    Node d = new Node(1);
    a.left = b;
    a.right = c;
    c.left = d;
    return a;
  }

  private static Node buildExtremeValuesTree() {
    //         0
    //       /   \
    //  MIN_INT  MAX_INT
    //           /
    //         -1
    Node zero = new Node(0);
    Node min = new Node(Integer.MIN_VALUE);
    Node max = new Node(Integer.MAX_VALUE);
    Node neg1 = new Node(-1);
    zero.left = min;
    zero.right = max;
    max.left = neg1;
    return zero;
  }

  private static Node buildZigZag(int n) {
    // 1 left-> 2 right-> 3 left-> 4 ... alternating
    if (n <= 0) {
      return null;
    }
    Node root = new Node(1);
    Node curr = root;
    boolean nextLeft = true;
    for (int v = 2; v <= n; v++) {
      Node node = new Node(v);
      if (nextLeft) {
        curr.left = node;
      } else {
        curr.right = node;
      }
      curr = node;
      nextLeft = !nextLeft;
    }
    return root;
  }

  private static Node buildPerfect15() {
    //              8
    //          /       \
    //         4         12
    //       /   \     /   \
    //      2     6   10    14
    //     / \   / \  / \  / \
    //    1  3  5  7 9  11 13 15
    Node n1 = new Node(1);
    Node n2 = new Node(2);
    Node n3 = new Node(3);
    Node n4 = new Node(4);
    Node n5 = new Node(5);
    Node n6 = new Node(6);
    Node n7 = new Node(7);
    Node n8 = new Node(8);
    Node n9 = new Node(9);
    Node n10 = new Node(10);
    Node n11 = new Node(11);
    Node n12 = new Node(12);
    Node n13 = new Node(13);
    Node n14 = new Node(14);
    Node n15 = new Node(15);

    n4.left = n2;
    n4.right = n6;
    n2.left = n1;
    n2.right = n3;
    n6.left = n5;
    n6.right = n7;

    n12.left = n10;
    n12.right = n14;
    n10.left = n9;
    n10.right = n11;
    n14.left = n13;
    n14.right = n15;

    n8.left = n4;
    n8.right = n12;
    return n8;
  }
}
