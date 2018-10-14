package contentFiltering;

public class InvalidFormatException extends IllegalArgumentException {

	/**
	 * DEFAULT
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Something is wrong with the command.
	 * 
	 * @param message
	 */
	public InvalidFormatException(String message) {
		System.err.println(message);
	}

}
