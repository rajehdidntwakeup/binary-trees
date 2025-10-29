package at.hochschule.burgenland.bswe.algo.traversal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import at.hochschule.burgenland.bswe.algo.model.Node;

public class TreeTraverser {
  /**
   * Traverses a binary tree starting from the given root node and
   * returns the traversal result as an array of integers. The order
   * of traversal is determined by the specified traversal type.
   *
   * @param root  the root node of the binary tree to be traversed
   * @param order a string specifying the traversal order. Valid options
   *              are "preorder", "inorder", "postorder", and "levelorder"
   * @return an array of integers representing the values
   */
  public static int[] traverse(Node root, String order) {
    List<Integer> result = new ArrayList<>();

    switch (order.toLowerCase()) {
      case "preorder":
        preOrder(root, result);
        break;
      case "inorder":
        inOrder(root, result);
        break;
      case "postorder":
        postOrder(root, result);
        break;
      case "levelorder":
        levelOrder(root, result);
        break;
      default:
        throw new IllegalArgumentException("Invalid traversal type: " + order +
            ". Valid options: preorder, inorder, postorder, levelorder");
    }

    return result.stream().mapToInt(i -> i).toArray();
  }

  /**
   * Prints the elements of a tree traversal in the specified order.
   *
   * @param traversal an array of integers representing the traversal result
   * @param order     a string specifying the traversal order (e.g., "preorder", "inorder", "postorder", "levelorder")
   */
  public static void printTraversal(int[] traversal, String order) {
    System.out.print(order + " Traversal: ");
    for (int i = 0; i < traversal.length; i++) {
      System.out.print(traversal[i]);
      if (i < traversal.length - 1) {
        System.out.print(", ");
      }
    }
    System.out.println();
  }

  /**
   * Performs a pre-order traversal of a binary tree and stores the values of the visited nodes
   * in the provided list.
   *
   * @param node   the current node being visited during the traversal, starting with the root of the tree
   * @param result the list where the values of the visited nodes are stored in traversal order
   */
  private static void preOrder(Node node, List<Integer> result) {
    if (node != null) {
      result.add(node.value);
      preOrder(node.left, result);
      preOrder(node.right, result);
    }
  }

  /**
   * Performs an in-order traversal of a binary tree and stores the values of the visited nodes
   * in the provided list.
   *
   * @param node   the current node being visited during the traversal, starting with the root of the tree
   * @param result the list where the values of the visited nodes are stored in traversal order
   */
  private static void inOrder(Node node, List<Integer> result) {
    if (node != null) {
      inOrder(node.left, result);
      result.add(node.value);
      inOrder(node.right, result);
    }
  }

  /**
   * Performs a post-order traversal of a binary tree and stores the values of the visited nodes
   * in the provided list.
   *
   * @param node   the current node being visited during the traversal, starting with the root of the tree
   * @param result the list where the values of the visited nodes are stored in traversal order
   */
  private static void postOrder(Node node, List<Integer> result) {
    if (node != null) {
      postOrder(node.left, result);
      postOrder(node.right, result);
      result.add(node.value);
    }
  }

  /**
   * Performs a level-order traversal (breadth-first search) of a binary tree
   * and stores the values of the visited nodes in the provided list.
   *
   * @param root   the root node of the binary tree to be traversed
   * @param result the list where the values of the visited nodes are stored
   */
  private static void levelOrder(Node root, List<Integer> result) {
    if (root == null) {
      return;
    }

    Queue<Node> queue = new LinkedList<>();
    queue.add(root);

    while (!queue.isEmpty()) {
      Node node = queue.poll();
      result.add(node.value);

      if (node.left != null) {
        queue.add(node.left);
      }
      if (node.right != null) {
        queue.add(node.right);
      }
    }
  }
}
