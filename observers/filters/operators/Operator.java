package contentFiltering.observers.filters.operators;

public abstract class Operator {
	String name;
	float value;

	public Operator(Object nameValue) {
		if (nameValue instanceof String)
			this.name = (String) nameValue;
		else if (nameValue instanceof Float)
			this.value = (float) nameValue;
	}

	/**
	 * @param name
	 * @param value
	 * @return true if the feed entry (name + data) respects the operator, false
	 *         otherwise.
	 */
	public abstract boolean check(Object nameValue);
}
