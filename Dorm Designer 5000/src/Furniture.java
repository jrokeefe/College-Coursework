/**
 * This class creates a square image with the string "Load" inside of it which,
 * when pressed, will load information from the file "RoomData.ddd" to create
 * new Furniture objects
 *
 * @author Hank Freyberg
 * @author Jack O'Keefe
 *
 */

public class Furniture implements DormGUI{

	private PApplet processing;
	private PImage image;
	private float[] position;
	private boolean isDragging;
	private int rotations;
	private String type;

	/**
	 * This constructor initializes the class' objects and sets the specific
	 * coordinates for Furniture object
	 *
	 * @param type
	 *            the type of Furniture object
	 * @param xPosition
	 *            the variable corresponding with the x position of the center of
	 *            the Furniture object
	 * @param yPosition
	 *            the variable corresponding with the y position of the center of
	 *            the Furniture object
	 * @param proccesing
	 *            the processing instance field
	 * @param rotations
	 *            the number of rotations the image has undergone
	 */

	public Furniture(String type, PApplet processing, float xPosition, float yPosition, int rotations) {
		this.processing = processing; // the processing instance field
		this.type = type; // the type of Furniture object

		image = processing.loadImage("images/" + type + ".png"); // set the object's image
		position = new float[] { xPosition, yPosition }; // set the object's position to the specified location
		this.rotations = rotations;
		isDragging = false;
	}

	/**
	 * This constructor initializes the class' objects and places it in the middle
	 * of the room
	 *
	 * @param type
	 *            the type of Furniture object
	 * @param proccesing
	 *            the processing instance field
	 */

	public Furniture(String type, PApplet processing) {
		this.processing = processing; // the processing instance field
		this.type = type; // the type of Furniture object

		image = processing.loadImage("images/" + type + ".png"); // set the object's image
		position = new float[] { processing.width / 2, processing.height / 2 }; // set the object's position to the
																				// middle of the room
		isDragging = false;
	}

	/**
	 * This method draws this bed at its current position
	 *
	 */

	public void update() {
		if (this.isDragging && rotations > 0) { // if the object is being dragged and has been rotated
			position[0] = processing.mouseX; // set the object's position to the position of the mouse
			position[1] = processing.mouseY;
			processing.image(image, position[0], position[1], rotations * PApplet.PI / 2); // print the image
		} else if (this.isDragging) { // if the object is being dragged
			position[0] = processing.mouseX; // set the object's position to the position of the mouse
			position[1] = processing.mouseY;
			processing.image(image, position[0], position[1]); // print the image
		} else if (rotations > 0) { // if the object has been rotated
			processing.image(image, position[0], position[1], rotations * PApplet.PI / 2); // print the image
		} else {
			processing.image(image, position[0], position[1]); // print the image
		}
	}

	/**
	 * This method is used to start dragging the bed, when the mouse is over this
	 * bed when it is pressed
	 *
	 */

	public void mouseDown(Furniture[] furniture) {
		if (isMouseOver()) { // if the mouse is over the object
			isDragging = true; // the object is being dragged
		}
	}

	/**
	 * This method is used to indicate that the bed is no longer being dragged
	 *
	 */

	public void mouseUp() {
		isDragging = false;
	}

	/**
	 * This method determines whether the mouse is currently over this bed
	 *
	 */

	public boolean isMouseOver() {
		if (rotations % 2 == 0 && position[0] < (processing.mouseX + image.width / 2)
				&& position[0] > (processing.mouseX - image.width / 2) // check if the mouse is in object's position on
																		// even rotations
				&& position[1] < (processing.mouseY + image.height / 2)
				&& position[1] > (processing.mouseY - image.height / 2)) {
			return true;
		} else if (rotations % 2 != 0 && position[0] < processing.mouseX + image.height / 2
				&& position[0] > processing.mouseX - image.height / 2
				&& position[1] < processing.mouseY + image.width / 2 // check if the mouse is in object's position on
																		// odd rotations
				&& position[1] > processing.mouseY - image.width / 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method is used to indicate that bed has been rotated
	 *
	 */

	public void rotate() {
		if (this.isMouseOver()) { // if the mouse is over the object's location
			++rotations; // increment the number of rotations
		}
	}

	/**
	 * Helper method to get the Furniture object's x position
	 *
	 * @return the Furniture object's x position
	 */

	public float getXPosition() {
		return position[0];
	}

	/**
	 * Helper method to get the Furniture object's y position
	 *
	 * @return the Furniture object's y position
	 */

	public float getYPosition() {
		return position[1];
	}

	/**
	 * Helper method to get the number of rotations undergone by the Furniture
	 * object
	 *
	 * @return the number of rotations
	 */

	public int getRotations() {
		int rotate = rotations % 4;
		return rotate;
	}

	/**
	 * Helper method to get the Furniture object's type
	 *
	 * @return the Furniture object's type
	 */

	public String getType() {
		return type;
	}
}
