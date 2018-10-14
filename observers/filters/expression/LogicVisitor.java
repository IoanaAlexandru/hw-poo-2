package contentFiltering.observers.filters.expression;

import contentFiltering.observers.filters.expression.nodes.AndNode;
import contentFiltering.observers.filters.expression.nodes.OperandNode;
import contentFiltering.observers.filters.expression.nodes.OrNode;

public class LogicVisitor implements Visitor {

	@Override
	public boolean visit(AndNode node) {
		return node.getLeft().accept(this) && node.getRight().accept(this);
	}

	@Override
	public boolean visit(OrNode node) {
		return node.getLeft().accept(this) || node.getRight().accept(this);
	}

	@Override
	public boolean visit(OperandNode node) {
		return node.getValue();
	}

}
