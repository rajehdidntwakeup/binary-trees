package at.hochschule.burgenland.bswe.algo.display;

import java.util.ArrayList;
import java.util.List;

import at.hochschule.burgenland.bswe.algo.model.Node;

public class TreePrinter {

  // Pretty-print the tree using levels and ASCII connectors like in the example.

  /**
   * Prints a visual ASCII representation of the binary tree to standard output.
   *
   * @param root the root node of the tree; if null, prints a message that the tree is empty
   */
  public static void printTree(Node root) {
    if (root == null) {
      System.out.println("Tree is empty");
      return;
    }

    int maxLevel = maxLevel(root);
    List<Node> nodes = new ArrayList<>();
    nodes.add(root);
    printNodeInternal(nodes, 1, maxLevel);
  }

  // --- Pretty printer helpers ---

  /**
   * Recursively prints each level of the tree with ASCII connectors.
   *
   * @param nodes    the list of nodes at the current level (may contain nulls)
   * @param level    the current level index starting at 1 for the root level
   * @param maxLevel the maximum depth of the tree
   */
  private static void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
    if (nodes.isEmpty() || isAllElementsNull(nodes)) {
      return;
    }

    int floor = maxLevel - level;
    int edgeLines = (int) Math.pow(2, Math.max(floor - 1, 0));
    int firstSpaces = (int) Math.pow(2, floor) - 1;
    int betweenSpaces = (int) Math.pow(2, floor + 1) - 1;

    printWhitespaces(firstSpaces);

    List<Node> newNodes = new ArrayList<>();
    for (Node node : nodes) {
      if (node != null) {
        System.out.print(node.value);
        newNodes.add(node.left);
        newNodes.add(node.right);
      } else {
        System.out.print(" ");
        newNodes.add(null);
        newNodes.add(null);
      }
      printWhitespaces(betweenSpaces);
    }
    System.out.println();

    // print the connecting '/' and '\\' lines
    for (int i = 1; i <= edgeLines; i++) {
      for (Node node : nodes) {
        printWhitespaces(firstSpaces - i);

        if (node == null) {
          // print blanks for both sides when the node is null
          printWhitespaces(edgeLines + edgeLines + i + 1);
          continue;
        }

        // left branch
        if (node.left != null) {
          System.out.print("/");
        } else {
          System.out.print(" ");
        }

        // the space between '/' and '\\' grows as i increases
        printWhitespaces(i + i - 1);

        // right branch
        if (node.right != null) {
          System.out.print("\\");
        } else {
          System.out.print(" ");
        }
        printWhitespaces(edgeLines + edgeLines - i + 1);
      }
      System.out.println();
    }

    printNodeInternal(newNodes, level + 1, maxLevel);
  }

  /**
   * Prints a number of whitespace characters to standard output.
   *
   * @param count the number of spaces to print; non-negative
   */
  private static void printWhitespaces(int count) {
    for (int i = 0; i < count; i++) {
      System.out.print(" ");
    }
  }

  /**
   * Computes the maximum depth (height) of the tree rooted at the given node.
   *
   * @param node the root node of the subtree; may be null
   * @return the number of levels in the subtree; 0 for a null node
   */
  private static int maxLevel(Node node) {
    if (node == null) {
      return 0;
    }
    return 1 + Math.max(maxLevel(node.left), maxLevel(node.right));
  }

  /**
   * Checks whether all elements of the provided list are null.
   *
   * @param list the list to check
   * @return true if every element is null; false otherwise
   */
  private static boolean isAllElementsNull(List<Node> list) {
    for (Object object : list) {
      if (object != null) {
        return false;
      }
    }
    return true;
  }

  // --- Balance utilities kept as-is ---

  /**
   * Prints balance factor information for every node in the tree.
   * The balance factor is computed as height(left) - height(right).
   *
   * @param root the root of the tree for which to print balance information
   */
  public static void printBalanceInfo(Node root) {
    System.out.println("\n=== Balance Information ===");
    printBalanceRec(root);
  }

  /**
   * Recursively prints the balance factor and status for each node in the tree.
   *
   * @param node the current node being processed; may be null
   */
  private static void printBalanceRec(Node node) {
    if (node != null) {
      int balance = getBalance(node);
      String balanceStatus = Math.abs(balance) <= 1 ? "BALANCED" : "UNBALANCED";
      System.out.println("Node " + node.value + ": Balance = " + balance + " (" + balanceStatus + ")");
      printBalanceRec(node.left);
      printBalanceRec(node.right);
    }
  }

  /**
   * Computes a simple balance metric for the given node based on subtree depths.
   * The metric equals maxLevel(left) - maxLevel(right).
   * Note: This uses depth, not the stored AVL height.
   *
   * @param node the node for which to compute the balance metric; may be null
   * @return the balance metric; 0 if the node is null
   */
  private static int getBalance(Node node) {
    if (node == null) {
      return 0;
    }
    return maxLevel(node.left) - maxLevel(node.right);
  }
}
