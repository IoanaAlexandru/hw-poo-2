package contentFiltering.observers.filters.operators;

public class Le extends Operator {

	public Le(Object nameValue) {
		super(nameValue);
	}

	@Override
	public boolean check(Object nameValue) {
		if (nameValue instanceof Float)
			if ((float) nameValue <= this.value)
				return true;
		return false;
	}

}
