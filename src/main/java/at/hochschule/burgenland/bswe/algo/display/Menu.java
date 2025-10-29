package at.hochschule.burgenland.bswe.algo.display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import at.hochschule.burgenland.bswe.algo.model.AVLTree;
import at.hochschule.burgenland.bswe.algo.model.Node;
import at.hochschule.burgenland.bswe.algo.traversal.AVLTreeTraversal;
import at.hochschule.burgenland.bswe.algo.traversal.TreeTraverser;

public class Menu {

  private final Scanner scanner;
  private AVLTree tree;

  /**
   * Creates a new menu with a fresh AVL tree and an interactive scanner.
   */
  public Menu() {
    this.tree = new AVLTree();
    this.scanner = new Scanner(System.in);
  }

  /**
   * Starts the interactive CLI menu loop; blocks until the user chooses to exit.
   */
  public void start() {
    displayWelcomeMessage();

    while (true) {
      displayMainMenu();
      int choice = getMenuChoice();

      switch (choice) {
        case 1:
          addNumbersManually();
          break;
        case 2:
          callTraverseMethod();
          break;
        case 3:
          printAVLTree();
          break;
        case 4:
          performTraversal();
          break;
        case 5:
          displayTreeInfo();
          break;
        case 6:
          clearTree();
          break;
        case 7:
          displayExitMessage();
          return;
        default:
          System.out.println("Invalid choice! Please try again.");
      }
    }
  }

  /**
   * Prints an introduction banner and a brief feature overview to the console.
   */
  private void displayWelcomeMessage() {
    System.out.println("=========================================");
    System.out.println("      WELCOME TO AVL TREE PROGRAM");
    System.out.println("=========================================");
    System.out.println("This program allows you to:");
    System.out.println("• Build and maintain an AVL Tree");
    System.out.println("• Add numbers manually");
    System.out.println("• Call traverse method with numbers and order");
    System.out.println("• View tree structure and balance info");
    System.out.println("• Perform various tree traversals");
    System.out.println("• Ensure all numbers are unique and non-negative");
    System.out.println("=========================================\n");
  }

  /**
   * Shows the main menu options and prompts the user for a choice.
   */
  private void displayMainMenu() {
    System.out.println("\n=== MAIN MENU ===");
    System.out.println("1. Add Numbers Manually");
    System.out.println("2. Call Traverse Method (Input Numbers + Order)");
    System.out.println("3. Print AVL Tree Structure");
    System.out.println("4. Perform Tree Traversal");
    System.out.println("5. Display Tree Information");
    System.out.println("6. Clear Tree");
    System.out.println("7. Exit Program");
    System.out.print("Please enter your choice (1-7): ");
  }

  /**
   * Reads and validates a main menu choice from standard input.
   *
   * @return a number between 1 and 7 representing the selected action
   */
  private int getMenuChoice() {
    while (true) {
      try {
        String input = scanner.nextLine().trim();
        int choice = Integer.parseInt(input);
        if (choice >= 1 && choice <= 7) {
          return choice;
        } else {
          System.out.print("Please enter a number between 1 and 7: ");
        }
      } catch (NumberFormatException e) {
        System.out.print("Invalid input! Please enter a number (1-7): ");
      }
    }
  }

  /**
   * Interactive flow to insert numbers one-by-one entered by the user.
   * Ensures only unique integers are added to the tree.
   */
  private void addNumbersManually() {
    System.out.println("\n=== ADD NUMBERS MANUALLY ===");
    System.out.println("Enter numbers one by one. Type 'done' to finish.");
    System.out.println("Note: Only unique numbers will be accepted.");

    int numbersAdded = 0;
    int numbersRejected = 0;

    while (true) {
      System.out.print("Enter a number (or 'done' to finish): ");
      String input = scanner.nextLine().trim();

      if (input.equalsIgnoreCase("done")) {
        break;
      }

      try {
        int number = Integer.parseInt(input);

        // Check if number already exists
        if (treeContainsNumber(number)) {
          System.out.println("Number " + number + " already exists in the tree. Skipping...");
          numbersRejected++;
        } else {
          System.out.println("--- Inserting " + number + " ---");
          tree.insert(number);
          numbersAdded++;
          TreePrinter.printTree(tree.getRoot());
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid input! Please enter a valid integer or 'done'.");
      }
    }

    System.out.println("\n Operation completed!");
    System.out.println("Numbers successfully added: " + numbersAdded);
    System.out.println("Numbers rejected (duplicates): " + numbersRejected);
  }

  /**
   * Calls the traverse method with user-provided numbers and order.
   * Reads numbers from console input and calls AVLTreeTraversal.traverse()
   */
  private void callTraverseMethod() {
    System.out.println("\n=== CALL TRAVERSE METHOD ===");
    System.out.println("This will call: public static int[] traverse(int[] numbers, String order)");
    System.out.println("Rules: Only positive integers are allowed. Negative numbers will be rejected.");

    // Get numbers input
    int[] numbers = getNumbersInput();
    if (numbers == null || numbers.length == 0) {
      System.out.println("No valid numbers provided. Operation cancelled.");
      return;
    }

    // Get traversal order
    String order = getTraversalOrder();

    // Call the traverse method
    System.out.println("\n=== EXECUTING TRAVERSE METHOD ===");
    System.out.println("Parameters:");
    System.out.println("  numbers: " + Arrays.toString(numbers));
    System.out.println("  order: " + order);
    System.out.println();

    try {
      long startTime = System.currentTimeMillis();
      int[] result = AVLTreeTraversal.traverse(numbers, order);
      long endTime = System.currentTimeMillis();

      System.out.println("\n=== METHOD EXECUTION COMPLETE ===");
      System.out.println("Execution time: " + (endTime - startTime) + "ms");
      System.out.println("Return value: " + Arrays.toString(result));
      System.out.println("Return array length: " + result.length);

    } catch (Exception e) {
      System.out.println("Error during traversal: " + e.getMessage());
    }
  }

  /**
   * Reads numbers from console input with validation
   *
   * @return array of valid positive integers
   */
  private int[] getNumbersInput() {
    System.out.println("\nEnter numbers in one of these formats:");
    System.out.println("1. Comma-separated: 8,4,9,7,2,13,11,46");
    System.out.println("2. Space-separated: 8 4 9 7 2 13 11 46");
    System.out.println("Rules: Only positive integers allowed. Negative numbers will be rejected.");
    System.out.print("Enter numbers: ");

    String input = scanner.nextLine().trim();
    if (input.isEmpty()) {
      return new int[0];
    }

    // Try comma-separated first
    String[] numberStrings;
    if (input.contains(",")) {
      numberStrings = input.split(",");
    } else {
      numberStrings = input.split("\\s+");
    }

    List<Integer> numbersList = new ArrayList<>();
    List<String> invalidInputs = new ArrayList<>();
    List<String> negativeNumbers = new ArrayList<>();

    for (String numStr : numberStrings) {
      try {
        int number = Integer.parseInt(numStr.trim());

        // Check if number is negative
        if (number < 0) {
          negativeNumbers.add(numStr.trim());
          continue;
        }

        numbersList.add(number);
      } catch (NumberFormatException e) {
        invalidInputs.add(numStr.trim());
      }
    }

    // Display warnings for invalid inputs
    if (!invalidInputs.isEmpty()) {
      System.out.println("⚠The following inputs were ignored (not numbers): " + invalidInputs);
    }

    if (!negativeNumbers.isEmpty()) {
      System.out.println("The following inputs were rejected (negative numbers): " + negativeNumbers);
    }

    if (numbersList.isEmpty()) {
      System.out.println("No valid positive numbers provided.");
      return new int[0];
    }

    // Remove duplicates while preserving order
    List<Integer> uniqueNumbers = new ArrayList<>();
    List<Integer> duplicateNumbers = new ArrayList<>();

    for (Integer num : numbersList) {
      if (!uniqueNumbers.contains(num)) {
        uniqueNumbers.add(num);
      } else {
        duplicateNumbers.add(num);
      }
    }

    if (!duplicateNumbers.isEmpty()) {
      System.out.println("Duplicate numbers removed: " + duplicateNumbers);
    }

    System.out.println("Valid numbers accepted: " + uniqueNumbers);

    return uniqueNumbers.stream().mapToInt(i -> i).toArray();
  }

  /**
   * Prompts the user to choose a traversal order for the traverse method call.
   */
  private String getTraversalOrder() {
    System.out.println("\n=== SELECT TRAVERSAL ORDER ===");
    System.out.println("1. preorder  (Root → Left → Right)");
    System.out.println("2. inorder   (Left → Root → Right) - Sorted order");
    System.out.println("3. postorder (Left → Right → Root)");
    System.out.println("4. levelorder (Breadth-first, level by level)");
    System.out.print("Enter your choice (1-4 or the order name): ");

    while (true) {
      String input = scanner.nextLine().trim().toLowerCase();

      switch (input) {
        case "1":
        case "preorder":
          return "preorder";
        case "2":
        case "inorder":
          return "inorder";
        case "3":
        case "postorder":
          return "postorder";
        case "4":
        case "levelorder":
          return "levelorder";
        default:
          System.out.print("Invalid choice! Please enter 1-4 or the order name: ");
      }
    }
  }

  /**
   * Prints the current AVL tree structure and per-node balance information.
   * If the tree is empty, informs the user and returns.
   */
  private void printAVLTree() {
    System.out.println("\n=== AVL TREE STRUCTURE ===");
    if (treeIsEmpty()) {
      System.out.println("The tree is empty. Please add some numbers first.");
      return;
    }

    TreePrinter.printTree(tree.getRoot());
    TreePrinter.printBalanceInfo(tree.getRoot());
  }

  /**
   * Prompts the user to choose a traversal order, performs the traversal,
   * and prints the result to the console.
   */
  private void performTraversal() {
    if (treeIsEmpty()) {
      System.out.println("The tree is empty. Please add some numbers first.");
      return;
    }

    System.out.println("\n=== TREE TRAVERSAL ===");
    System.out.println("Select traversal type:");
    System.out.println("1. Preorder (Root-Left-Right)");
    System.out.println("2. Inorder (Left-Root-Right) - Sorted order");
    System.out.println("3. Postorder (Left-Right-Root)");
    System.out.println("4. Levelorder (Breadth-first)");
    System.out.print("Enter your choice (1-4): ");

    int choice = getTraversalChoice();
    String[] traversalTypes = {"preorder", "inorder", "postorder", "levelorder"};
    String[] displayNames = {"Preorder", "Inorder", "Postorder", "Levelorder"};

    String traversalType = traversalTypes[choice - 1];
    String displayName = displayNames[choice - 1];

    System.out.println("\n=== " + displayName.toUpperCase() + " TRAVERSAL ===");
    int[] result = TreeTraverser.traverse(tree.getRoot(), traversalType);
    TreeTraverser.printTraversal(result, displayName);
  }

  /**
   * Reads and validates a traversal menu choice from standard input.
   *
   * @return a number between 1 and 4 representing the traversal type
   */
  private int getTraversalChoice() {
    while (true) {
      try {
        String input = scanner.nextLine().trim();
        int choice = Integer.parseInt(input);
        if (choice >= 1 && choice <= 4) {
          return choice;
        } else {
          System.out.print("Please enter a number between 1 and 4: ");
        }
      } catch (NumberFormatException e) {
        System.out.print("Invalid input! Please enter a number (1-4): ");
      }
    }
  }

  /**
   * Displays general information about the current tree, including root value,
   * node count, height, min/max values, and prints inorder and preorder traversals.
   */
  private void displayTreeInfo() {
    System.out.println("\n=== TREE INFORMATION ===");
    if (treeIsEmpty()) {
      System.out.println("The tree is empty.");
      return;
    }

    Node root = tree.getRoot();
    int[] inorder = TreeTraverser.traverse(root, "inorder");
    int[] preorder = TreeTraverser.traverse(root, "preorder");

    System.out.println("Root value: " + root.value);
    System.out.println("Total nodes: " + inorder.length);
    System.out.println("Tree height: " + getTreeHeight(root));
    System.out.println("Minimum value: " + inorder[0]);
    System.out.println("Maximum value: " + inorder[inorder.length - 1]);
    System.out.println("\nInorder traversal (sorted): ");
    TreeTraverser.printTraversal(inorder, "Sorted");

    System.out.println("\nPreorder traversal: ");
    TreeTraverser.printTraversal(preorder, "Preorder");
  }

  /**
   * Clears the current tree after a yes/no confirmation.
   * Creates a new empty AVLTree instance when confirmed.
   */
  private void clearTree() {
    System.out.print("\nAre you sure you want to clear the tree? (yes/no): ");
    String confirmation = scanner.nextLine().trim();

    if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
      this.tree = new AVLTree();
      System.out.println("Tree cleared successfully!");
    } else {
      System.out.println("Clear operation cancelled.");
    }
  }

  /**
   * Prints a closing banner when the program is about to exit.
   */
  private void displayExitMessage() {
    System.out.println("\n============================================");
    System.out.println("    THANK YOU FOR USING AVL TREE PROGRAM");
    System.out.println("                  RAJÉH <3                    7");
    System.out.println("============================================");
  }

  // Helper methods

  /**
   * Checks whether the current tree already contains the specified number.
   *
   * @param number the value to look for
   * @return true if the number exists in the tree; false otherwise
   */
  private boolean treeContainsNumber(int number) {
    return checkIfNumberExists(tree.getRoot(), number);
  }

  /**
   * Recursively checks whether a value exists in the subtree rooted at the given node.
   *
   * @param node   the subtree root to search (may be null)
   * @param number the value to search for
   * @return true if found; false otherwise
   */
  private boolean checkIfNumberExists(Node node, int number) {
    if (node == null) {
      return false;
    }
    if (node.value == number) {
      return true;
    }
    return checkIfNumberExists(node.left, number) || checkIfNumberExists(node.right, number);
  }

  /**
   * Indicates whether the current AVL tree has no nodes.
   *
   * @return true if the tree root is null; false otherwise
   */
  private boolean treeIsEmpty() {
    return tree.getRoot() == null;
  }

  /**
   * Computes the height of a binary tree rooted at the given node.
   * Height is the number of nodes on the longest path from this node to a leaf.
   *
   * @param node the root of the subtree
   * @return the height (0 for null)
   */
  private int getTreeHeight(Node node) {
    if (node == null) {
      return 0;
    }
    return 1 + Math.max(getTreeHeight(node.left), getTreeHeight(node.right));
  }
}