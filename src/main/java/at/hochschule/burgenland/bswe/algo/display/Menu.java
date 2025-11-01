package at.hochschule.burgenland.bswe.algo.display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import at.hochschule.burgenland.bswe.algo.model.AVLTree;
import at.hochschule.burgenland.bswe.algo.model.Node;
import at.hochschule.burgenland.bswe.algo.traversal.AVLTreeTraversal;
import at.hochschule.burgenland.bswe.algo.traversal.TreeTraverser;

/**
 * The Menu class provides an interactive command-line interface (CLI) for performing
 * various operations on an AVL tree. It allows users to manually insert numbers,
 * traverse the tree in different orders, and display tree properties, among other features.
 * The class encapsulates user interactions and input validation, maintaining tight
 * integration with the AVLTree structure.
 */
public class Menu {

  private final Scanner scanner;
  private AVLTree tree;

  /**
   * Constructs a Menu instance, initializing the required components for the menu system.
   * This includes:
   * - Creating a new instance of AVLTree to manage tree operations.
   * - Initializing a Scanner for input handling from standard input.
   * This constructor sets up the fundamental environment for the interactive menu.
   */
  public Menu() {
    this.tree = new AVLTree();
    this.scanner = new Scanner(System.in);
  }


  /**
   * Starts the interactive menu-driven program for managing an AVL tree.
   * This method provides a continuous loop to allow users to interact with the application
   * through various options such as adding numbers, traversing the tree, displaying the tree structure,
   * fetching tree information, clearing the tree, and exiting the program.
   * The method performs the following:
   * - Displays a welcome message to the user.
   * - Continuously displays the main menu until the user chooses to exit.
   * - Prompts the user for a menu choice and processes the selected option.
   * - Executes corresponding actions for each menu option, including managing the AVL tree
   * and invoking helper methods for specific operations.
   * Menu choices include:
   * 1. Adding numbers manually to the AVL tree.
   * 2. Calling the traverse method with user input.
   * 3. Printing the AVL tree structure.
   * 4. Performing a traversal of the tree.
   * 5. Displaying detailed tree information.
   * 6. Clearing the AVL tree.
   * 7. Exiting the program.
   * Handles invalid menu choices by prompting the user to enter a valid selection.
   * Exits the program gracefully when the user selects the appropriate option.
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
   * Displays the welcome message for the AVL Tree Program.
   * This method outputs an introductory message explaining the
   * functionality and purpose of the program to the console.
   * Key details included in the welcome message:
   * - Introduction to program capabilities, such as building and maintaining an AVL Tree.
   * - Instructions for adding unique, non-negative numbers.
   * - Overview of tree traversal options (e.g., pre-order, in-order, post-order).
   * - Assurance of maintaining AVL tree balance and unique values.
   * - General expectations for tree information and visualization.
   * The displayed message provides users with essential information to begin interacting
   * with the menu-driven AVL tree application.
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
   * Displays the main menu of the AVL Tree application.
   * This method outputs the available menu options to the console for user selection.
   * The menu includes the following choices:
   * 1. Add numbers manually to the AVL tree.
   * 2. Call the traverse method with inputted numbers and a specified traversal order.
   * 3. Print the structure of the current AVL tree.
   * 4. Perform a traversal operation on the tree and display the result.
   * 5. Show detailed information about the AVL tree, including its properties.
   * 6. Clear all nodes in the AVL tree and reset it.
   * 7. Exit the application.
   * After displaying the options, the user is prompted to enter their choice
   * by inputting a number between 1 and 7.
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
   * Prompts the user to input a menu choice for the AVL Tree application and validates the input.
   * The method ensures that the input is a valid integer within the acceptable range of 1 to 7
   * (inclusive). It loops until valid input is provided, displaying appropriate error messages for
   * invalid entries.
   *
   * @return the validated menu choice as an integer in the range 1 to 7
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
   * Manually adds unique numbers to the AVL tree through user input.
   * This method prompts the user to input numbers one by one and inserts them
   * into the AVL tree while ensuring only unique values are added. The user
   * can terminate the input process by entering the word "done". For duplicate
   * numbers or invalid inputs, proper messages are displayed.
   * Key details of the process:
   * - The user is prompted to input a number or 'done' to finish.
   * - If the input is a valid integer and not already present in the tree,
   * it is inserted into the tree.
   * - If the number already exists in the tree, it is skipped as a duplicate.
   * - Invalid inputs (non-integer and not 'done') are rejected with a message.
   * Upon completion, a summary is displayed, including:
   * - The total count of successfully added numbers.
   * - The total count of rejected numbers due to duplicates.
   * - The resulting AVL tree structure is displayed after each insertion.
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
   * Executes the process of calling the `traverse` method from the `AVLTreeTraversal` class.
   * This involves validating input numbers, collecting traversal order input, and invoking the method with the
   * provided parameters.
   * The method performs the following steps:
   * - Prompts the user to input an array of integers. Only positive integers are permitted; negative inputs are rejected.
   * - Prompts the user to specify the traversal order as a string.
   * - Calls the `traverse` method from the `AVLTreeTraversal` class, passing the collected inputs.
   * - Measures and logs the execution time and results of the method call.
   * - Handles and logs any exceptions that occur during the execution of the `traverse` method.
   * If valid input numbers are not provided, the operation is canceled, and the method terminates without performing
   * further actions.
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

    String order = getTraversalOrder();

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
   * Prompts the user to input a series of positive integers in either comma-separated
   * or space-separated formats. The input is validated to ensure only positive integers
   * are accepted, while duplicates and invalid entries are handled appropriately.
   * The method performs the following:
   * - Accepts input from the user.
   * - Validates integers are positive; negative numbers and invalid inputs are rejected.
   * - Removes duplicate numbers while preserving the order.
   * - Displays warnings for invalid or negative inputs and informs about removed duplicates.
   *
   * @return An array of unique, valid
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
   * Prompts the user to select a tree traversal order using a numerical or textual input.
   * The options for traversal order are:
   * 1. Preorder (Root → Left → Right)
   * 2. Inorder (Left → Root → Right) - Sorted order
   * 3. Postorder (Left → Right → Root)
   * 4. Level order (Breadth-first, level by level)
   * The method continuously prompts the user until a valid input is provided.
   *
   * @return A string representing the selected traversal order.
   * It could be one of the following values: "preorder", "inorder", "postorder
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
   * Prints the structure and balance information of the AVL tree.
   * This method displays a visual representation of the AVL tree, including
   * balance information for each node. If the tree is empty, a message is printed
   * indicating that the tree is empty and prompting the user to add elements.
   * Precondition: The tree object must be initialized before calling this method.
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
   * Performs a tree traversal operation based on the user's choice of traversal type.
   * This method prompts the user to input their desired traversal type for the tree
   * and then prints the traversal result. The available traversal options are:
   * Preorder (Root-Left-Right), Inorder (Left-Root-Right), Postorder (Left-Right-Root),
   * or Level
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
   * Reads and validates user input to select a traversal choice between 1 and 4.
   * Continuously prompts the user until a valid input is received.
   *
   * @return the user's chosen traversal option as an integer between 1 and 4
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
   * Displays detailed information about the tree structure.
   * Prints a summary of the tree's key attributes including its root value, total number of nodes,
   * height, minimum and maximum values, and traversals (inorder and preorder).
   * If the tree is empty, a message indicating this will be displayed instead.
   * The displayed information includes:
   * - The root node's value.
   * - Total number of nodes in the tree.
   * - Height of the tree.
   * - The minimum value present in the tree (based on inorder traversal).
   * - The maximum value present in the tree (based on inorder traversal).
   * - Inorder traversal list (sorted order).
   * - Preorder traversal list.
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
   * Clears the current AVL tree after confirming with the user.
   * This method prompts the user for confirmation before resetting the tree
   * to a new, empty instance of an AVLTree. If the user confirms by entering
   * "yes" or "y" (case-insensitively), the tree is cleared. Otherwise, the
   * operation is canceled, and the tree remains unchanged.
   * Preconditions:
   * - A Scanner instance must be defined and initialized before calling this method.
   * - The 'tree' instance variable must be an instance of AVLTree.
   * Postconditions:
   * - If confirmed, the current tree is reset to an empty AVLTree instance.
   * - If not confirmed, the tree remains unchanged.
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
   * Displays a formatted exit message to the console.
   * This message indicates the termination of the program
   * and includes a thank-you note for the user.
   */
  private void displayExitMessage() {
    System.out.println("\n============================================");
    System.out.println("    THANK YOU FOR USING AVL TREE PROGRAM");
    System.out.println("                  RAJÉH <3                    7");
    System.out.println("============================================");
  }

  /**
   * Checks if the specified number exists within the binary tree.
   *
   * @param number the number to search for within the tree
   * @return {@code true} if the tree contains the specified number, otherwise {@code false}
   */
  private boolean treeContainsNumber(int number) {
    return checkIfNumberExists(tree.getRoot(), number);
  }

  /**
   * Checks if a specific number exists in the binary tree starting from the given node.
   *
   * @param node   the root node of the binary tree to be searched
   * @param number the number to search for in the binary tree
   * @return true if the number exists in the binary tree, false otherwise
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
   * Checks if the tree is empty.
   *
   * @return true if the tree has no elements, false otherwise.
   */
  private boolean treeIsEmpty() {
    return tree.getRoot() == null;
  }

  /**
   * Calculates the height of a binary tree starting from the given node.
   * The height is determined as the number of edges on the longest path
   * from the node to a leaf. If the tree is empty, the height is 0.
   *
   * @param node the root node of the binary tree for which the height is to be calculated
   * @return the height of the binary tree, or 0 if the node is null
   */
  private int getTreeHeight(Node node) {
    if (node == null) {
      return 0;
    }
    return 1 + Math.max(getTreeHeight(node.left), getTreeHeight(node.right));
  }
}