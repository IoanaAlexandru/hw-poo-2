package contentFiltering.observers.filters.expression.nodes;

import contentFiltering.observers.filters.expression.Visitor;

public class OperandNode extends Node {

	private boolean value;

	public OperandNode(boolean value) {
		super(null, null);
		this.setValue(value);
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	@Override
	public boolean accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
