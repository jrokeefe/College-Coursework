import java.util.ArrayList;

//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           P03 Dorm Designer 5000
// Files:           N/A
// Course:          CS 300 Spring 2018
//
// Author:          Jack O'Keefe
// Email:           jokeefe2@wisc.edu
// Lecturer's Name: Dahl
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    Hank Freyberg
// Partner Email:   hfreyberg@wisc.edu
// Lecturer's Name: Alexi
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   __X_ Write-up states that pair programming is allowed for this assignment.
//   __X_ We have both read and understand the course Pair Programming Policy.
//   __X_ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates 
// strangers, etc do.  If you received no outside help from either type of 
// source, then please explicitly indicate NONE.
//
// Persons:         NONE
// Online Sources:  NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

/**
 * This class runs a simulation of a typical dorm room and allows users to
 * create their own unique layout using different types of furniture. Users can
 * save their progress and load from that save at any time.
 *
 * @author Hank Freyberg
 * @author Jack O'Keefe
 */

public class Main {
	private PApplet processing; // the processing instance field
	private PImage backgroundImage;
	private ArrayList<DormGUI> guiObjects;
	private final static int MAX_LOAD_FURNITURE = 100; 	// Max number of furniture that LoadButton will be allowed to load at once.    


	/**
	 * This is the main method that runs the startApplication method defined in
	 * Utility. This drives the entire program.
	 *
	 * @param args
	 *
	 */

	public static void main(String[] args) {
		Utility.startApplication(); // starts the application that runs the entire program
	}

	/**
	 * The main constructor class that initializes the objects from the Main class.
	 *
	 * @param processing
	 *            the processing instance field
	 *
	 */

	public Main(PApplet processing) {
		this.processing = processing;
		backgroundImage = processing.loadImage("images/background.png"); // backgroundImage object designated for the
																			// image of the room
		guiObjects = new ArrayList<DormGUI>();
		guiObjects.add(new CreateFurnitureButton("bed", 50, 24, processing)); // create a button that creates a furniture object when pressed
		guiObjects.add(new CreateFurnitureButton("sofa", 150, 24, processing)); // create a button that creates a furniture object when
															// pressed
		guiObjects.add(new CreateFurnitureButton("dresser", 250, 24, processing));
		guiObjects.add(new CreateFurnitureButton("desk", 350, 24, processing));
		guiObjects.add(new CreateFurnitureButton("sink", 450, 24, processing));
		guiObjects.add(new ClearButton(550, 24, processing));
		guiObjects.add(new SaveButton(650, 24, processing)); // create a button that saves the information of the current dorm
													// configuration when pressed
		guiObjects.add( new LoadButton(750, 24, processing)); // create a button that loads the information of the last saved dorm
													// configuration when pressed
	}

	/**
	 * This method is called to update the visual the user is seeing.
	 *
	 */

	public void update() {
		processing.background(100, 150, 250);
		processing.image(backgroundImage, processing.width / 2, processing.height / 2); // display the background image
		for (int i = 0; i < guiObjects.size(); ++i) {
			guiObjects.get(i).update(); // calls the update method for all created DormDUI objects
		}
	}

	/**
	 * This method is called when the mouse has been pressed. It cycles through
	 * non-null furniture objects and calls the furniture mouseDown method. It also
	 * calls the different buttons mouseDown methods. If the clicked mouse indicates
	 * to create a new furniture object, the method does so.
	 */

	public void mouseDown() {
		Furniture[] furniture = extractFurnitureFromGUIObjects();
		for (int i = 0; i < guiObjects.size(); ++i) {
			guiObjects.get(i).mouseDown(furniture); // calls the mouseDown method for all created DormDUI objects
		}
		replaceFurnitureInGUIObjects(furniture);

	}

	/**
	 * This method is called when the mouse is not pressed, and calls the mouseUp
	 * method for each non-null furniture object
	 *
	 *
	 */

	public void mouseUp() {
		for (int i = 0; i < guiObjects.size(); ++i) {
			guiObjects.get(i).mouseUp(); // calls the mouseUp method for all created DormDUI objects
		}
	}

	/**
	 * This method is called when a key has been pressed. Depending on the key
	 * pressed, it will call a method in class Furniture, or delete the furniture
	 * object.
	 *
	 *
	 */

	public void keyPressed() {
		if (processing.key == 'r' || processing.key == 'R') { // when the key pressed is the 'r' key
			for (int i = 0; i < guiObjects.size(); ++i) {
				if (guiObjects.get(i) instanceof Furniture) { // when furniture object
					((Furniture) guiObjects.get(i)).rotate(); // call the rotate method
				}
			}
		} else if ((processing.key == 'd' || processing.key == 'D')) { // when the key pressed is the 'r' key
			for (int i = 0; i < guiObjects.size(); ++i) { // cycle through the GUI objects
				if (guiObjects.get(i) instanceof Furniture && guiObjects.get(i).isMouseOver()) {
					guiObjects.remove(i); // delete the Furniture object that is in the same position as the mouse
					break; // Furniture object deleted, break the loop
				}
			}
		}
	}

	/**
	 * This method creates a new Furniture[] for the old mouseDown() methods
	 * to make use of.  It does so by copying all Furniture references from
	 * this.guiObjects into a temporary array of size MAX_LOAD_FURNITURE.
	 * @return that array of Furniture references.
	 */
	
	private Furniture[] extractFurnitureFromGUIObjects() {
	    Furniture[] furniture = new Furniture[MAX_LOAD_FURNITURE];
	    int nextFreeIndex = 0;
	    for(int i=0;i<guiObjects.size() && nextFreeIndex < furniture.length;i++)
	        if(guiObjects.get(i) instanceof Furniture)
	            furniture[nextFreeIndex++] = (Furniture)guiObjects.get(i);        
	    return furniture;        
	}   
	
	/**
	 * This method first removes all Furniture references from this.guiObjects,
	 * and then adds back in all of the non-null references from it's parameter.
	 * @param furniture contains the only furniture that will be left in 
	 *   this.guiObjects after this method is invoked (null references ignored).
	 */
	
	private void replaceFurnitureInGUIObjects(Furniture[] furniture) {
	    for(int i=0;i<guiObjects.size();i++)
	        if(guiObjects.get(i) instanceof Furniture)
	            guiObjects.remove(i--);
	    for(int i=0;i<furniture.length;i++)
	        if(furniture[i] != null)
	            guiObjects.add(furniture[i]);
	}
}
