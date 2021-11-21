
public class Button implements DormGUI {

	private static final int WIDTH = 96;
	private static final int HEIGHT = 32;
	protected PApplet processing;
	private float[] position;
	protected String label;
	 
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
	
	public Button(float x, float y, PApplet processing) {
		label = "Button";
		this.processing = processing;
		position = new float[6]; // an array that stores different floats corresponding to pertinent positions of
									// the button
		position[0] = x - WIDTH / 2; // the left most position of the button
		position[1] = y + HEIGHT / 2; // the top most position of the button
		position[2] = x + WIDTH / 2; // the right most position of the button
		position[3] = y - HEIGHT / 2; // the bottom most position of the button
		position[4] = x; // the center x position of the button
		position[5] = y; // the center y position of the button
	}
	 
	public void update() {
		if (this.isMouseOver()) { // if the mouse is over the button
			processing.fill(100); // fill the button with dark grey if the mouse is over the button
		} else {
			processing.fill(200); // fill the button with light grey if the mouse is over the button
		}
		processing.rect(position[0], position[1], position[2], position[3]); // make the rectangle
		processing.fill(0); // set the writing to black
		processing.text(label, position[4], position[5]); // write inside the rectangle
	}
	
	/**
	 * This method will create a new Furniture object of type "sofa" under the right
	 * conditions
	 *
	 * @return the new furniture object if the mouse is over the button, or null if
	 *         it is not
	 */

	public void mouseDown(Furniture[] furniture) {
			if (this.isMouseOver()) { //
				System.out.println("A button was pressed."); // create a new Furniture object of type sofa at
			}
	}
	
	public void mouseUp() {
		
	}
	
	/**
	 * This method checks if the mouse is over the space that the button takes up
	 *
	 * @return boolean, whether or not the mouse is over the button
	 */

	public boolean isMouseOver() {
		if (processing.mouseX > position[0] && processing.mouseX < position[2] && processing.mouseY > position[3]
				&& processing.mouseY < position[1]) { // if the mouse is within the bounds of the rectangle
			return true;
		} else {
			return false;
		}
	}
}
