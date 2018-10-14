package contentFiltering.observers.filters;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import contentFiltering.InvalidFormatException;
import contentFiltering.observers.filters.expression.nodes.AndNode;
import contentFiltering.observers.filters.expression.nodes.Node;
import contentFiltering.observers.filters.expression.nodes.NodeFactory;
import contentFiltering.observers.filters.expression.nodes.OperandNode;
import contentFiltering.observers.filters.expression.nodes.OrNode;
import contentFiltering.observers.filters.operators.Operator;
import contentFiltering.observers.filters.operators.OperatorsFactory;
import contentFiltering.observers.filters.expression.*;

public class Filter {

	String filterString;

	public Filter(String filterString) {
		this.filterString = filterString;
	}

	/**
	 * @param c
	 * @return true if c is a bracket ('(' or ')'), false otherwise.
	 */
	private boolean isBracket(char c) {
		if (c == '(' || c == ')')
			return true;
		return false;
	}

	/**
	 * @param c
	 * @return true if c is an operator ('&' or '|'), false otherwise.
	 */
	private boolean isOperator(char c) {
		if (c == '&' || c == '|')
			return true;
		return false;
	}

	/**
	 * Extracts the operator string (format: op name/value filter_value) from a
	 * longer string.
	 * 
	 * @param filterString
	 * @return operator string that can be used to create an Operator.
	 */
	private String getOperatorString(String filterString) {
		String operatorString = "";
		char c = filterString.charAt(0);
		int i = 0, n = filterString.length();

		while (c != ')' && i < n - 1) {
			operatorString = operatorString.concat(Character.toString(c));
			i++;
			c = filterString.charAt(i);
		}
		if (c != ')')
			throw new InvalidFormatException("Mismatched parantheses!");

		return operatorString;
	}

	/**
	 * Applies the operator obtained from operatorString.
	 * 
	 * @param name
	 * @param value
	 * @param operatorString
	 * @return '1' or '0'.
	 */
	private char applyOperator(String name, float value, String operatorString) {
		OperatorsFactory opFactory = OperatorsFactory.getInstance();
		Operator operator = opFactory.getOperator(operatorString);

		Object nameValue = null;
		if (operatorString.contains("name"))
			nameValue = name;
		else if (operatorString.contains("value"))
			nameValue = value;

		if (operator.check(nameValue))
			return '1';
		else
			return '0';
	}

	/**
	 * Apply the shunting yard algorithm on the filter expression.
	 * 
	 * @param name
	 * @param value
	 * @return postfix expression as a character queue containing '0', '1', '|',
	 *         '&'.
	 * @throws InvalidFormatException
	 */
	private Queue<Character> shuntingYard(String name, float value) {
		int i;
		char c, op = 0;
		String operatorString;

		Queue<Character> output = new LinkedList<Character>();
		Stack<Character> operators = new Stack<Character>();

		for (i = 0; i < filterString.length(); i++) { // Iterating through string char by char
			c = filterString.charAt(i);

			switch (c) {
			case ' ':
				continue;
			case '(':
				operators.push(c);
				break;
			case ')':
				while (!operators.isEmpty()) {
					op = operators.pop();

					if (op == '(')
						break;
					else
						output.add(op);
				}
				if (op != '(') {
					throw new InvalidFormatException("Mismatched paranteses!");
				}
				break;
			case '&':
			case '|':
				i++;
				while (!operators.isEmpty()) {
					op = operators.peek();

					if (isOperator(op)) {
						operators.pop();
						output.add(op);
					} else
						break;
				}
				operators.push(c);
				break;
			default:
				operatorString = getOperatorString(filterString.substring(i));
				i += operatorString.length() - 1;
				// Skips the operatorString in filterString: next character will be ')'
				output.add(applyOperator(name, value, operatorString));
				// Added '0' or '1' to the output queue
				break;
			}
		}

		while (!operators.isEmpty()) {
			op = operators.pop();

			if (isBracket(op))
				throw new InvalidFormatException("Mismatched parantheses!");

			output.add(op);
		}

		return output;
	}

	/**
	 * Interprets the postfix expression obtained using the shunting yard algorithm.
	 * 
	 * @param name
	 * @param value
	 * @return boolean result of expression
	 */
	public Node createTree(Queue<Character> postfixExpression) {
		Stack<Node> stack = new Stack<Node>();
		NodeFactory nodeFactory = NodeFactory.getInstance();

		for (char c : postfixExpression) {
			Node node = nodeFactory.getNode(c);
			if (isOperator(c)) {
				node.setRight(stack.pop());
				node.setLeft(stack.pop());
			}
			stack.push(node);
		}

		return stack.pop();
	}

	/**
	 * Checks if an entry passes through the filter or not by visiting the root of
	 * the filter binary tree.
	 * 
	 * @param name
	 * @param value
	 * @return true if the entry respects the filter, false otherwise
	 */
	public boolean check(String name, float value) {
		if (filterString.contains("nil"))
			return true;

		LogicVisitor visitor = new LogicVisitor();

		Node root = createTree(shuntingYard(name, value));

		if (root instanceof AndNode)
			return visitor.visit((AndNode) root);
		else if (root instanceof OrNode)
			return visitor.visit((OrNode) root);
		else
			return ((OperandNode) root).getValue();
	}

}
