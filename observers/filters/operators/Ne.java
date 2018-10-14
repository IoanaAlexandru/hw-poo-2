package contentFiltering.observers.filters.operators;

public class Ne extends Operator {
	public Ne(Object nameValue) {
		super(nameValue);
	}

	@Override
	public boolean check(Object nameValue) {
		if (nameValue instanceof String)
			if (!((String) nameValue).equals(this.name))
				return true;
		if (nameValue instanceof Float)
			if ((float) nameValue != this.value)
				return true;
		return false;
	}
}
