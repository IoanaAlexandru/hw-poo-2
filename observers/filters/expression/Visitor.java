package contentFiltering.observers.filters.expression;

import contentFiltering.observers.filters.expression.nodes.AndNode;
import contentFiltering.observers.filters.expression.nodes.OperandNode;
import contentFiltering.observers.filters.expression.nodes.OrNode;

public interface Visitor {

	boolean visit(AndNode node);

	boolean visit(OrNode node);

	boolean visit(OperandNode operandNode);
}
