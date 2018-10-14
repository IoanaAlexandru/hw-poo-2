package contentFiltering.observers.filters.expression.nodes;

import contentFiltering.observers.filters.expression.Visitor;

public class AndNode extends Node {

	public AndNode(Node left, Node right) {
		super(left, right);
	}

	@Override
	public boolean accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
