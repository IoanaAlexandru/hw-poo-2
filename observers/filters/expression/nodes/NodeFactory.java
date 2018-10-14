package contentFiltering.observers.filters.expression.nodes;

public class NodeFactory {

	private static NodeFactory instance;

	private NodeFactory() {
	}

	public static NodeFactory getInstance() {
		if (instance == null)
			instance = new NodeFactory();
		return instance;
	}

	/**
	 * Converts c from '1' or '0' to the corresponding boolean value.
	 * 
	 * @param c
	 * @return true if c is '1', false otherwise.
	 */
	private boolean toBoolean(char c) {
		return (c == '1') ? true : false;
	}

	public Node getNode(char c) {
		switch (c) {
		case '&':
			return new AndNode(null, null);
		case '|':
			return new OrNode(null, null);
		case '1':
		case '0':
			return new OperandNode(toBoolean(c));
		default:
			return null;
		}
	}

}
