package contentFiltering.observers;

import java.util.TreeMap;

import contentFiltering.observers.filters.Filter;

public class Observer {

	private TreeMap<String, FeedEntry> feed; // complete feed status at the moment of creation / at last print
	private TreeMap<String, FeedEntry> filteredFeed; // feed that is to be printed
	private TreeMap<String, FeedEntry> tempRemoved; // items that shouldn't be printed at a certain point
	private Filter filter;

	public Observer(String operatorString, TreeMap<String, FeedEntry> feed) {
		filter = new Filter(operatorString);
		filteredFeed = new TreeMap<String, FeedEntry>();
		tempRemoved = new TreeMap<String, FeedEntry>();
		
		this.feed = new TreeMap<String, FeedEntry>();
		FeedEntry value;
		for (String name : feed.keySet()) {
			value = feed.get(name);
			this.feed.put(name, value.copy());
		}
	}

	public TreeMap<String, FeedEntry> getFeed() {
		return feed;
	}

	/**
	 * @param feed
	 * @return feed that should be printed (according to the filter)
	 */
	public TreeMap<String, FeedEntry> getFilteredFeed(TreeMap<String, FeedEntry> feed) {
		// Puts back the items that were removed at the last print (if any)
		for (String key : tempRemoved.keySet()) {
			filteredFeed.put(key, tempRemoved.get(key));
		}
		tempRemoved.clear();

		FeedEntry entry;
		for (String name : feed.keySet()) {
			entry = feed.get(name).copy();
			if (filter.check(name, entry.getValue())) {
				// The number of changes since the last print will be the total number of
				// changes (from the base feed) minus the number of changes at the moment of the
				// last print (from this.feed)
				int oldNrOfChanges = 0;
				if (this.feed.get(name) != null)
					oldNrOfChanges = this.feed.get(name).getNrOfChanges();
				this.feed.put(name, entry.copy()); // reset NrOfChanges
				entry.setNrOfChanges(entry.getNrOfChanges() - oldNrOfChanges);

				// The increase since the last print will be the percentage difference between
				// the current value and the value at the last print
				if (filteredFeed.get(name) != null)
					entry.setIncrease(
							(entry.getValue() - filteredFeed.get(name).getValue()) / filteredFeed.get(name).getValue());
				else
					entry.setIncrease(0);

				filteredFeed.put(name, entry);
			} else {
				if (filteredFeed.get(name) != null)
					// This item should not be printed, but should be remembered
					tempRemoved.put(name, filteredFeed.get(name));
				filteredFeed.remove(name);

			}
		}
		return filteredFeed;
	}

	public Filter getFilter() {
		return filter;
	}

}
