package at.hochschule.burgenland.bswe.algo.model;

public class Node {
  public int value;
  public int height;
  public Node left;
  public Node right;

  public Node(int value) {
    this.value = value;
    this.height = 1;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
