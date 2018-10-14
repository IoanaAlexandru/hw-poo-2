package contentFiltering.observers.filters.operators;

public class Nil extends Operator {

	public Nil(Object nameValue) {
		super(nameValue);
	}

	@Override
	public boolean check(Object nameValue) {
		return true;
	}

}
