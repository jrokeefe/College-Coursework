import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class creates a square image with the string "Load" inside of it which,
 * when pressed, will load information from the file "RoomData.ddd" to create
 * new Furniture objects
 *
 * @author Hank Freyberg
 * @author Jack O'Keefe
 */

public class LoadButton extends Button implements DormGUI{

	/**
	 * This constructor initializes the class' objects and sets the coordinates for
	 * the button's image
	 *
	 * @param x
	 *            the variable corresponding with the x position of the center of
	 *            the button
	 * @param y
	 *            the variable corresponding with the y position of the center of
	 *            the button
	 * @param proccesing
	 *            the processing instance field
	 *
	 */

	public LoadButton(float x, float y, PApplet processing) {
		super(x,y,processing);
		label = "Load Room";
	}

	/**
	 * This method checks if there's room left in the Furniture array to store a new
	 * Furniture object
	 *
	 * @param numFurniture
	 *            the loop counter
	 * @param room
	 *            the length of the Furniture array
	 * @return boolean, whether or not there's room left in the Furniture array to
	 *         store a new Furniture object
	 */

	private boolean stillRoom(int numFurniture, int room) {
		if (numFurniture < room) {
			return true;
		} else { // when the array can't take on any more objects
			System.out.println("WARNING: Unable to load more furniture."); // display error
			return false;
		}
	}

	/**
	 * This method checks if the line printed in the file contains any amount of
	 * string
	 *
	 * @param text
	 *            the line of text from the file
	 * @return boolean, whether or not the line contains text
	 */

	private boolean containText(String text) {
		if (text.length() > 0) {
			return true;
		} else { // when string is empty
			System.out.println("WARNING: Found incorrectly formatted line in file: " + text); // display error
			return false;
		}
	}

	/**
	 * This method checks if the line in the file is split into two strings after it
	 * is split by a semicolon
	 *
	 * @param colon
	 *            the array that is made after the line of text is split by
	 *            semicolons
	 * @param text
	 *            the line of text from the file
	 * @return boolean, whether or not the line is formatted correctly
	 */

	private boolean semiColon(String[] colon, String text) {
		if (colon.length == 2) {
			return true;
		} else { // when semicolon split does not product the expected result
			System.out.println("WARNING: Found incorrectly formatted line in file: " + text); // display the error
			return false;
		}
	}

	/**
	 * This method checks if the line in the file is split into two strings after it
	 * is split by commas
	 *
	 * @param colon
	 *            the array that is made after the line of text is split by commas
	 * @param text
	 *            the line of text from the file
	 * @return boolean, whether or not the line is formatted correctly
	 */

	private boolean commas(String[] commas, String text) {
		if (commas.length == 3) {
			return true;
		} else { // when commas split does not product the expected result
			System.out.println("WARNING: Found incorrectly formatted line in file: " + text); // display the error
			return false;
		}
	}

	/**
	 * This method checks if strings stored in the numbers array can be converted
	 * into floats
	 *
	 * @param colon
	 *            the array that is made after the line of text is split by commas
	 * @param text
	 *            the line of text from the file
	 * @return boolean, whether or not the line is formatted correctly
	 */

	private boolean floats(String numbers[], String text) {
		try {
			Float.parseFloat(numbers[0]);
			Float.parseFloat(numbers[1]);
			Float.parseFloat(numbers[2]);
			return true;
		} catch (Exception e) { // when compiler can't convert the string to a float
			System.out.println("WARNING: Found incorrectly formatted line in file: " + text); // display the error
			return false;
		}
	}

	/**
	 * This method checks if the line in the file is split into two strings after it
	 * is split by commas
	 *
	 * @param type
	 *            the part of the line on the file that contains what type of
	 *            Furniture object it is
	 * @return boolean, whether or not the image corresponds with an existing image
	 *         in the file
	 */

	private boolean imageExists(String type) {
		try {
			processing.loadImage("images/" + type + ".png");
			return true;
		} catch (Exception e) { // when loading the image displays an error
			System.out.println("WARNING: Could not find an image for a furniture object of type: " + type); // display
																											// the error
			return false;
		}
	}

	/**
	 * This method checks if the line in the file is split into two strings after it
	 * is split by commas
	 *
	 * @param furniture[]
	 *            the processing field's array of Furniture objects
	 */
	@Override
	public void mouseDown(Furniture furniture[]) {
		if (isMouseOver()) {
			String text = null; // initialize text
			FileInputStream fileByteStream = null; // initialize file stream
			Scanner inFS = null; // initialize scanner
			try { // try to open file
				fileByteStream = new FileInputStream("RoomData.ddd");
				inFS = new Scanner(fileByteStream);
			} catch (IOException e) { // catch FileNotFound exception
				System.out.println("WARNING: Could not load room contents from file RoomData.ddd.");
				return; // exit method
			} catch (NullPointerException e) { // catch nullPointer exception
				System.out.println("WARNING: Could not load room contents from file RoomData.ddd.");
				return; // exit method
			}
			int numFurniture = 0; // initialize variables
			String[] type;
			String[] numbers;
			float[] Numbers = new float[3];
			int room = furniture.length; // record length of array
			while (inFS.hasNextLine()) { // while scanner isn't empty
				boolean running = true;
				if (!stillRoom(numFurniture, room)) {
					break; // break the filereader when there isn't enough room in the array
				}
				while (running) {
					text = inFS.nextLine(); // text is next line of string
					text.trim();
					if (!containText(text)) {
						break; // break from this line when the line contains no text
					}
					type = text.split(":"); // split text by the semicolon
					if (!semiColon(type, text)) {
						break; // error in format, break from this line
					}
					type[0] = type[0].trim();
					type[1] = type[1].trim();
					numbers = type[1].split(","); // split text by commas
					if (!commas(numbers, text)) {
						break; // error in format, break from this line
					}
					numbers[0] = numbers[0].trim();
					numbers[1] = numbers[1].trim();
					numbers[2] = numbers[2].trim();
					if (!floats(numbers, text)) {
						break; // error in format, break from this line
					}
					Numbers[0] = Float.parseFloat(numbers[0]); // change the strings into floats
					Numbers[1] = Float.parseFloat(numbers[1]);
					Numbers[2] = Float.parseFloat(numbers[2]);
					int rotations = (int) Numbers[2]; // change the rotations into an int
					if (!imageExists(type[0])) {
						break; // error in data, break from this line
					}
					furniture[numFurniture] = new Furniture(type[0], processing, Numbers[0], Numbers[1], rotations); // create
																														// a
																														// new
																														// Furniture
																														// object
					++numFurniture; // move up the furniture counter
					running = false; // break the loop
				}
			}
			inFS.close(); // close scanner
			try {
				fileByteStream.close(); // done with file, so try to close it
			} catch (IOException e) { // catch exception
				System.out.println("WARNING: Could not load room contents from file RoomData.ddd.");
				return; // exit method
			}
			return; // exit method
		} else {
			return;
		}
	}
}
