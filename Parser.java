package contentFiltering;

import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

import contentFiltering.observers.FeedEntry;
import contentFiltering.observers.Observer;

public class Parser {

	// Will save the observers and their data in a HashMap.
	private HashMap<Integer, Observer> observers = new HashMap<Integer, Observer>();
	// Will save every feed entry in a base feed.
	private TreeMap<String, FeedEntry> feed = new TreeMap<String, FeedEntry>();

	/**
	 * Parses and interprets the commands in input.
	 * 
	 * @param input
	 * @throws InvalidFormatException
	 */
	public void parse(String input) {
		StringTokenizer tok = new StringTokenizer(input);

		String command = tok.nextToken();

		switch (command) {
		case "feed": // FORMAT: feed name value
			String name;
			float value;

			if (tok.countTokens() == 2) {
				name = tok.nextToken();

				try {
					value = Float.parseFloat(tok.nextToken());
				} catch (NumberFormatException e) {
					throw new InvalidFormatException("Invalid value!");
				}
				
				FeedEntry feedEntry = feed.get(name);
				
				if (feedEntry == null)
					feedEntry = new FeedEntry(value);
				else {
					feedEntry.setValue(value);
					feedEntry.incNrOfChanges();
				}
				
				feed.put(name, feedEntry);
			} else
				throw new InvalidFormatException("Invalid arguments for command 'feed'.");

			break;
		case "create_obs": // FORMAT: create_obs id filter_expression
			int id;

			if (tok.countTokens() >= 2) {
				try {
					id = Integer.parseInt(tok.nextToken());
				} catch (NumberFormatException e) {
					throw new InvalidFormatException("Invalid id!");
				}

				// The rest of the string will be the filter expression.
				Observer observer = new Observer(tok.nextToken(""), feed);
				observers.put(id, observer);
			} else
				throw new InvalidFormatException("Invalid arguments for command 'create_obs'.");

			break;
		case "delete_obs": // FORMAT: delete_obs id
			if (tok.countTokens() == 1) {
				try {
					id = Integer.parseInt(tok.nextToken());
				} catch (NumberFormatException e) {
					throw new InvalidFormatException("Invalid value!");
				}

				// Removing Observer from HashMap (if found).
				observers.remove(id);
			} else
				throw new InvalidFormatException("Invalid arguments for command 'delete_obs'.");

			break;
		case "print": // FORMAT: print id
			if (tok.countTokens() == 1) {
				try {
					id = Integer.parseInt(tok.nextToken());
				} catch (NumberFormatException e) {
					throw new InvalidFormatException("Invalid id!");
				}

				if (observers.get(id) == null)
					throw new InvalidFormatException("Observer not found!");

				// Printing the id and feed of the right Observer from the HashMap.
				TreeMap<String, FeedEntry> filteredFeed = observers.get(id).getFilteredFeed(feed);

				for (String name1 : filteredFeed.keySet())
					System.out.println("obs " + id + ": " + name1 + " " + filteredFeed.get(name1));
			} else if (tok.countTokens() == 0) { // no arguments => print all observers
				TreeMap<String, FeedEntry> filteredFeed;

				for (int obsId : observers.keySet()) {
					filteredFeed = observers.get(obsId).getFilteredFeed(feed);

					for (String name2 : filteredFeed.keySet())
						System.out.println("obs " + obsId + ": " + name2 + " " + filteredFeed.get(name2));
				}
			} else
				throw new InvalidFormatException("Too many arguments for command 'print'.");

			break;
		case "begin":
			break;
		default:
			throw new InvalidFormatException("Invalid command!");
		}
	}

}
