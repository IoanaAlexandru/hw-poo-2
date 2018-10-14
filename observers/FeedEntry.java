package contentFiltering.observers;

import java.text.DecimalFormat;

public class FeedEntry {

	private float value;
	private double increase;
	private int nrOfChanges;

	public FeedEntry(float value) {
		this.value = value;
		nrOfChanges = 1;
	}

	public float getValue() {
		return value;
	}

	public int getNrOfChanges() {
		return nrOfChanges;
	}

	public double getIncrease() {
		return increase;
	}

	public void setIncrease(double increase) {
		this.increase = increase;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public void incNrOfChanges() {
		nrOfChanges++;
	}

	public void setNrOfChanges(int nrOfChanges) {
		this.nrOfChanges = nrOfChanges;
	}

	public FeedEntry copy() {
		FeedEntry copy = new FeedEntry(value);
		copy.setIncrease(increase);
		copy.setNrOfChanges(nrOfChanges);
		return copy;
	}

	public void reset() {
		increase = 0;
		nrOfChanges = 0;
	}

	@Override
	public String toString() {
		DecimalFormat valueFormat = new DecimalFormat("0.00"), increaseFormat = new DecimalFormat("0.00%");
		return valueFormat.format(value) + " " + increaseFormat.format(increase) + " " + Integer.toString(nrOfChanges);
	}

}
