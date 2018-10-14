package contentFiltering.observers.filters.expression.nodes;

import contentFiltering.observers.filters.expression.Visitable;
import contentFiltering.observers.filters.expression.Visitor;

public class Node implements Visitable {

	private Node left;
	private Node right;

	public Node(Node left, Node right) {
		this.setLeft(left);
		this.setRight(right);
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	@Override
	public boolean accept(Visitor visitor) {
		return false;
	}
	
}