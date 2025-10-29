package at.hochschule.burgenland.bswe.algo.traversal;

import at.hochschule.burgenland.bswe.algo.display.TreePrinter;
import at.hochschule.burgenland.bswe.algo.model.AVLTree;

public class AVLTreeTraversal {

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

    // Perform a traversal and return the result
    int[] result = TreeTraverser.traverse(tree.getRoot(), order);
    TreeTraverser.printTraversal(result, order);

    return result;
  }
}
