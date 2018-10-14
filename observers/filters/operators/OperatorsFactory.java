package contentFiltering.observers.filters.operators;

import java.util.StringTokenizer;

import contentFiltering.InvalidFormatException;

public class OperatorsFactory { // Singleton
	
	private static OperatorsFactory instance;

	private OperatorsFactory() {
	}

	public static OperatorsFactory getInstance() {
		if (instance == null)
			instance = new OperatorsFactory();
		return instance;
	}
	
	/**
	 * @param operatorString
	 * @return an instance of an Operator that matches the operatorString.
	 */
	public Operator getOperator(String operatorString) {
		// FORMAT: eq/ne/le/lt/ge/gt value/name filter_expression
		//               OP1            OP2           OP3
		
		StringTokenizer tok = new StringTokenizer(operatorString);
		String op1 = tok.nextToken();

		if (op1.equals("nil")) {
			return new Nil(null);
		}

		if (tok.countTokens() < 2)
			throw new InvalidFormatException("Invalid operator!");

		String op2 = tok.nextToken();
		Object op3; // will be float or String

		if (op2.equals("value")) {
			op3 = 0;
			try {
				op3 = Float.parseFloat(tok.nextToken());
			} catch (NumberFormatException en) {
				throw new InvalidFormatException("Invalid value!");
			}
		} else if (op2.equals("name")) {
			op3 = tok.nextToken();
		} else
			throw new InvalidFormatException("Invalid operation!");

		switch (op1) {
		case "eq":
			return new Eq(op3);
		case "ne":
			return new Ne(op3);
		case "gt":
			return new Gt(op3);
		case "ge":
			return new Ge(op3);
		case "lt":
			return new Lt(op3);
		case "le":
			return new Le(op3);
		default:
			throw new InvalidFormatException("Invalid operator!");
		}
	}
	
}
