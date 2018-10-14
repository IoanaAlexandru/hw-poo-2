package contentFiltering.observers.filters.operators;

public class Lt extends Operator {

	public Lt(Object nameValue) {
		super(nameValue);
	}

	@Override
	public boolean check(Object nameValue) {
		if (nameValue instanceof Float)
			if ((float) nameValue < this.value)
				return true;
		return false;
	}

}
