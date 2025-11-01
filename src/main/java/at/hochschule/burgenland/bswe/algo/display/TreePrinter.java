package at.hochschule.burgenland.bswe.algo.display;

import java.util.ArrayList;
import java.util.List;

import at.hochschule.burgenland.bswe.algo.model.Node;

/**
 * Utility class for creating visual representations of binary trees in ASCII format.
 * This class provides methods to print a tree with hierarchical visual connectors
 * and calculates the balance factor of nodes within the tree.
 */
public class TreePrinter {

  /**
   * Prints the structure of a binary tree in a human-readable format using ASCII characters.
   *
   * @param root the root node of the tree to be printed; may be null
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


  /**
   * Recursively prints nodes of a binary tree at a given level in an easily readable
   * structure. It includes node values, connecting lines,
   * and spacing for visualization.
   *
   * @param nodes    the list of nodes to print at the current level; may contain nulls
   * @param level    the current depth level being printed, starting from 1
   * @param maxLevel the maximum depth of the tree to determine spacing and structuring
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

    // print the connecting '/' and '\' lines
    for (int i = 1; i <= edgeLines; i++) {
      for (Node node : nodes) {
        printWhitespaces(firstSpaces - i);

        if (node == null) {
          // print blanks for both sides when the node is null
          printWhitespaces(edgeLines + edgeLines + i + 1);
          continue;
        }

        // left side '/'
        if (node.left != null) {
          System.out.print("/");
        } else {
          System.out.print(" ");
        }

        // the space between '/' and '\' grows as i increases
        printWhitespaces(i + i - 1);

        // right side '\'
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


  /**
   * Prints the balance information of a binary tree rooted at the provided node.
   * Outputs the balance factor and balance status (e.g., BALANCED or UNBALANCED)
   * for each node in the tree.
   *
   * @param root the root node of the tree for which balance information is printed; may be null
   */
  public static void printBalanceInfo(Node root) {
    System.out.println("\n=== Balance Information ===");
    printBalanceRec(root);
  }


  /**
   * Recursively prints the balance information for each node in a binary tree.
   * For each node, it computes and displays its balance factor and balance status
   * (BALANCED if the absolute balance factor is less than or equal to 1, otherwise UNBALANCED).
   * Traverses the tree in a pre-order manner.
   *
   * @param node the current node for which balance information is printed; may be null
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
   * Computes the balance factor of a given binary tree node.
   * The balance factor is calculated as the difference in height
   * between the left and right subtrees of the node.
   *
   * @param node the binary tree node for which to calculate the balance factor; may be null
   * @return the balance factor of the node; 0 if the node is null
   */
  private static int getBalance(Node node) {
    if (node == null) {
      return 0;
    }
    return maxLevel(node.left) - maxLevel(node.right);
  }
}
