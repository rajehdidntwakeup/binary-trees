package at.hochschule.burgenland.bswe.algo.model;

import lombok.Getter;

@Getter
public class AVLTree {
  private Node root;

  /**
   * Inserts a value into the AVL tree. Adjusts the tree structure to maintain
   * AVL balancing property after the insertion.
   *
   * @param value the integer value to be inserted into the AVL tree
   */
  public void insert(int value) {
    root = insertRec(root, value);
  }

  /**
   * Computes the height of a node in the AVL tree.
   *
   * @param node the node whose height is requested; may be null
   * @return 0 if the node is null; otherwise the stored height of the node
   */
  private int height(Node node) {
    return node == null ? 0 : node.height;
  }

  /**
   * Calculates the balance factor of a node.
   * The balance factor is defined as height(left subtree) - height(right subtree).
   *
   * @param node the node whose balance factor is calculated; may be null
   * @return the balance factor, or 0 if the node is null
   */
  private int getBalance(Node node) {
    return node == null ? 0 : height(node.left) - height(node.right);
  }

  /**
   * Performs a right rotation on the given subtree root to restore AVL balance.
   *
   * @param y the root of the subtree to rotate
   * @return the new root of the rotated subtree
   */
  private Node rightRotate(Node y) {
    System.out.println("Right rotation around " + y.value);
    Node x = y.left;
    Node T2 = x.right;

    x.right = y;
    y.left = T2;

    y.height = Math.max(height(y.left), height(y.right)) + 1;
    x.height = Math.max(height(x.left), height(x.right)) + 1;

    return x;
  }

  /**
   * Performs a left rotation on the given subtree root to restore AVL balance.
   *
   * @param x the root of the subtree to rotate
   * @return the new root of the rotated subtree
   */
  private Node leftRotate(Node x) {
    System.out.println("Left rotation around " + x.value);
    Node y = x.right;
    Node T2 = y.left;

    y.left = x;
    x.right = T2;

    x.height = Math.max(height(x.left), height(x.right)) + 1;
    y.height = Math.max(height(y.left), height(y.right)) + 1;

    return y;
  }

  /**
   * Recursively inserts a value into the subtree rooted at the given node and
   * rebalances the subtree according to AVL rules.
   *
   * @param node  the current subtree root; may be null
   * @param value the value to insert
   * @return the potentially new root of the subtree after insertion and rebalancing
   */
  private Node insertRec(Node node, int value) {
    if (node == null) {
      return new Node(value);
    }

    if (value < node.value) {
      node.left = insertRec(node.left, value);
    } else if (value > node.value) {
      node.right = insertRec(node.right, value);
    } else {
      return node; // Duplicates not allowed
    }

    node.height = 1 + Math.max(height(node.left), height(node.right));
    int balance = getBalance(node);

    // Rotations
    if (balance > 1 && value < node.left.value) {
      System.out.println("AVL Check: Left-Left rotation needed for " + node.value);
      return rightRotate(node);
    }

    if (balance < -1 && value > node.right.value) {
      System.out.println("AVL Check: Right-Right rotation needed for " + node.value);
      return leftRotate(node);
    }

    if (balance > 1 && value > node.left.value) {
      System.out.println("AVL Check: Left-Right rotation needed for " + node.value);
      node.left = leftRotate(node.left);
      return rightRotate(node);
    }

    if (balance < -1 && value < node.right.value) {
      System.out.println("AVL Check: Right-Left rotation needed for " + node.value);
      node.right = rightRotate(node.right);
      return leftRotate(node);
    }

    return node;
  }
}
