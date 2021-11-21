import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class creates a square image with the string "Save" inside of it which,
 * when pressed, will store every Furniture object's positional information into
 * a file.
 *
 * @author Hank Freyberg
 * @author Jack O'Keefe
 */

public class SaveButton extends Button implements DormGUI{

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

	public SaveButton(float x, float y, PApplet processing) {
		super(x,y,processing);
		label = "Save Room";
	}

	/**
	 * This method writes the pertinent information of each furniture object onto a
	 * file named "RoomData.ddd" under the right conditions
	 *
	 * @param furniture[]
	 *            the array of furniture objects which the method will parse through
	 *
	 */
	@Override
	public void mouseDown(Furniture furniture[]) {
		if (isMouseOver()) { // if the mouse is over the button
			FileOutputStream fileByteStream = null; // initialize file stream
			PrintWriter outFS = null; // initialize file writer
			try { // try to open file
				fileByteStream = new FileOutputStream("RoomData.ddd"); // designate to a file named "RoomData.ddd"
				outFS = new PrintWriter(fileByteStream);
			} catch (IOException e) { // catch FileNotFound exception
				System.out.println("WARNING: Could not save room contents to file RoomData.ddd.");
				return; // exit method
			} catch (NullPointerException e) { // catch nullPointer exception
				System.out.println("WARNING: Could not save room contents to file RoomData.ddd.");
				return; // exit method
			}
			for (int i = 0; i < furniture.length; ++i) { // for size of array
				if (furniture[i] != null) { // for size of subArray
					outFS.print(furniture[i].getType() + ":"); // write the Furniture object's type
					outFS.print(furniture[i].getXPosition() + ","); // separate the coordinates by commas
					outFS.print(furniture[i].getYPosition() + ",");
					outFS.print(furniture[i].getRotations());
					outFS.println("");
				}
			}
			outFS.close(); // close scanner
			try { // try to close scanner
				fileByteStream.close();
			} catch (IOException e) { // catch exception
				System.out.println("WARNING: Could not save room contents to file RoomData.ddd.");
				return; // exit method
			}
		}
	}
}
