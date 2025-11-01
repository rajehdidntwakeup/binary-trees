package at.hochschule.burgenland.bswe.algo.traversal;

import at.hochschule.burgenland.bswe.algo.display.TreePrinter;
import at.hochschule.burgenland.bswe.algo.model.AVLTree;

/**
 * Represents a utility class for AVL Tree traversal operations.
 * This class provides a method to construct an AVL Tree from an array of integers
 * and perform a traversal based on the specified order.
 */
public class AVLTreeTraversal {

  /**
   * Traverses an AVL tree constructed from the given array of integers in the specified order.
   * The input integers are inserted into the AVL tree, ensuring it remains balanced,
   * and then a traversal is performed to produce the result.
   *
   * @param numbers an array of integers to construct the AVL tree; cannot be null
   * @param order   the traversal order to use ("inorder", "preorder", "postorder"); cannot be null
   * @return an array of integers resulting from the traversal of the AVL tree
   * @throws IllegalArgumentException if {@code numbers} is null
   * @throws IllegalArgumentException if {@code order} is null
   */
  public static int[] traverse(int[] numbers, String order) {
    // Validate input
    if (numbers == null) {
      throw new IllegalArgumentException("Numbers array cannot be null");
    }
    if (order == null) {
      throw new IllegalArgumentException("Order cannot be null");
    }

    // Create AVL tree and insert numbers
    AVLTree tree = new AVLTree();

    System.out.println("=== AVL Tree Construction ===");
    for (int number : numbers) {
      System.out.println("\n--- Inserting " + number + " ---");
      tree.insert(number);
      TreePrinter.printTree(tree.getRoot());
    }

    System.out.println("\n=== Final AVL Tree ===");
    TreePrinter.printTree(tree.getRoot());

    System.out.println("\n=== Traversal (" + order + ") ===");

    int[] result = TreeTraverser.traverse(tree.getRoot(), order);
    TreeTraverser.printTraversal(result, order);

    return result;
  }
}
