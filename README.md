# Binary-Trees

### by `Rajéh Abdulhadi`

## Algorithm Complexity (Best vs. Worst Cases)

Below is an overview of the time and space complexity for the key algorithms implemented in my project.
`n` will be the number of nodes in the tree, `h` the tree height, and `w` the maximum width (max nodes at any level).

Notes

- In my solution, I tried to balance the tree using the `Left-Right` rotation, despite what we learned in our class.
- For an AVL tree, `h = O(log n)` by definition (balanced height).
- For a general binary tree, `h` can be as large as `n` in the worst case (a skewed tree).

### AVL Tree Operations (current code implements insertion)

- Insert (`AVLTree.insert`)
    - Time
        - Best: `O(log n)` descend one path from the root to a leaf; may require no rotations.
        - Worst: `O(log n)` height is bounded by `O(log n)`, still a single root-to-leaf path at most a constant
          number of rotations (0–2 single rotations or 1 double rotation).
    - Space
        - Best/Worst: `O(log n)` — due to recursion stack along one path of height `h`.

### Tree Traversals (`TreeTraverser`)

All traversals visit each node exactly once, so their time is `O(n)`. Space differs by traversal order and tree shape.

- Pre-order, In-order, Post-order (recursive)
    - Time
        - Best/Worst: `O(n)` visit each node once.
    - Space (call stack of recursion)
        - Best: `O(log n)` if the tree is balanced.
        - Worst: `O(n)` for a skewed tree.

- Level-order
    - Time
        - Best/Worst: `O(n)` each node is enqueued and dequeued once.
    - Space (queue holding nodes of a level)
        - Best: `O(1)` to `O(log n)` depending on shape; minimal when very narrow.
        - Worst: `O(w)` which is `O(n)` in the densest levels (e.g., near-complete tree where a level can hold ~n/2
          nodes).

### Printing Traversal Output (`TreeTraverser.printTraversal`)

- Time: `O(n)` — prints each element of the given traversal array.
- Space: `O(1)` — aside from input array and loop variables.

### Summary Cheat Sheet

- AVL Insert: Time `O(log n)` best/worst; Space `O(log n)`.
- DFS Traversals (pre/in/post): Time `O(n)`; Space `O(h)` ⇒ `O(log n)` balanced, `O(n)` worst.
- BFS Level-order: Time `O(n)`; Space `O(w)` ⇒ up to `O(n)` worst.
