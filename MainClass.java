package contentFiltering;

import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {

		String input;

		Scanner s = new Scanner(System.in);
		Parser p = new Parser();

		// Ignores any input before the keyword "begin".
		do {
			input = s.nextLine();
		} while (!input.equals("begin"));

		input = s.nextLine();

		while (!input.equals("end")) {
			try {
				p.parse(input); // Executes command
			} catch (InvalidFormatException e) {
				// Just ignores the invalid command and prints the error message.
			}
			input = s.nextLine();
		}
		// Terminates the program after the keyword "end".

		s.close();
	}

}
