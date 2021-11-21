//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           P07 Exploring a Maze
// Files:           Maze.java
// Course:          CS 300 Spring 2018
//
// Author:          Jack O'Keefe
// Email:           jokeefe2@wisc.edu
// Lecturer's Name: Dahl
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    N/A
// Partner Email:   N/A
// Lecturer's Name: N/A
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
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
 * This class creates a maze from given information about the maze. Users can
 * print out the maze and print out the solution to the maze. If no solution can
 * be found, the program will let the user know.
 *
 * @author Jack O'Keefe
 */

public class Maze {

	public MazeRunnerStack path; // stack used to store positions of solution
	private Boolean solved; // whether or not the maze has been solved
	private char[][] mazeInfo; // contains parameters that define the maze
	private int[] start; // contain position of the start of the maze
	private int[] finish; // contain position of the end of the maze
	private int[] tracker; // keeps track of cursor as it finds a solution to maze
	private int mazeWidth; // width of maze in characters
	private boolean north; // whether or not solver is looking up
	private boolean west; // whether or not solver is looking to the left
	private boolean east; // whether or not solver is looking to the right
	private boolean south; // whether or not solver is looking down

	/**
	 * Constructor stores input maze information, then initializes various aspects
	 * of the maze. Constructor always sets the direction the solver is looking at
	 * to the right.
	 * 
	 * @param mazeInfo
	 */

	public Maze(char[][] mazeInfo) {
		this.mazeInfo = mazeInfo;
		start = new int[2];
		finish = new int[2];
		tracker = new int[2];
		path = new MazeRunnerStack();
		mazeWidth = mazeInfo[0].length;
		mazeWidth = (mazeWidth * 3) + (1 + mazeWidth);
		solved = null;
		north = false;
		west = false;
		south = false;
		east = true;
	}

	/**
	 * Stores the position of the start of the maze.
	 * 
	 * @param row
	 * @param col
	 */

	public void setStart(int row, int col) { // stores input row and column into the start array
		start[0] = row;
		start[1] = col;
	}

	/**
	 * Stores the position of the end of the maze.
	 * 
	 * @param row
	 * @param col
	 */

	public void setFinish(int row, int col) { // stores input row and column into the finish array
		finish[0] = row;
		finish[1] = col;
	}

	/**
	 * Takes the information in the mazeInfo array and uses it to print out a maze.
	 * If the maze has been solved, prints out the maze with a visual pathway as
	 * well as the pathway's coordinates. If the maze can't be solved, prints out
	 * the maze stating so. Else, simply prints out maze.
	 */

	public void displayMaze() {

		if (solved == null) { // if solveMaze has not been called, simply print out maze
			makeTop(mazeWidth);
			for (int i = 0; i < mazeInfo.length; ++i) {
				makeInside(i);
				makeBorder(i);
			}
		} else if (solved == false) { // if solveMaze has been called and no solution was found, states so and prints
										// out maze
			System.out.println("No solution could be found.");
			makeTop(mazeWidth);
			for (int i = 0; i < mazeInfo.length; ++i) {
				makeInside(i);
				makeBorder(i);
			}
		} else { // if maze has been solved, print solved maze and coordinate pathway
			System.out.println("Solution is:");
			makeTop(mazeWidth);
			for (int i = 0; i < mazeInfo.length; ++i) {
				makeInside(i);
				makeBorder(i);
			}
			String conclusion = ""; // initialize coordinate pathway
			while (path.getSize() > 0) {
				Position x = path.pop(); // take top object
				if (path.getSize() == 0) {
					conclusion = "Path is: [" + x.row + "," + x.col + "]" + conclusion; // last object is first
																						// coordinate
				} else {
					conclusion = " --> [" + x.row + "," + x.col + "]" + conclusion; // objects are stored in reverse
																					// order
				}
			}
			System.out.print(conclusion); // print coordinate pathway
		}
	}

	/**
	 * Attempts to find a way through the maze from start to finish using the right
	 * hand rule. If the number of steps taken exceeds four times the product of the
	 * mazeInfo's width and height, or if the cursor can't move, determines that the
	 * maze cannot be solved
	 */

	public void solveMaze() {
		int steps = 0; // steps that the cursor has taken
		tracker[0] = start[0]; // initialize the cursor's position to the starting position
		tracker[1] = start[1];
		path.push(new Position(tracker[0], tracker[1])); // add starting position to path stack
		while (steps < 4 * mazeInfo.length * mazeInfo[0].length) { // sets the maximum number of steps
			if (tracker[0] == finish[0] && tracker[1] == finish[1]) { // if the cursor's position matches the end's, the
																		// maze has been solved
				solved = true;
				break;
			} else {
				if (turnRight()) { // tries to turn right, increments steps if possible
					checkForDuplicate(); // stores coordinate or erases backtrack
					++steps;
				} else if (goStraight()) { // tries to move straight, increments steps if possible
					checkForDuplicate();
					++steps;
				} else if (turnLeft()) { // tries to turn left, increments steps if possible
					checkForDuplicate();
					++steps;
				} else if (goBack()) { // tries to turn around, increments steps if possible
					checkForDuplicate();
					++steps;
				} else { // if cursor can't move, it is stuck and the maze cannot be solved
					solved = false;
					break;
				}
			}
		}
		if (solved == null) { // if the cursor has taken too many steps and no solution to the maze has been
								// found, it is assumed that the maze cannot be solved
			solved = false;
		}
	}

	/**
	 * Creates the top border of the maze based on the integer passed to it
	 * 
	 * @param param
	 *            the maze's width in number of characters
	 */

	private void makeTop(int param) {
		while (param > 0) { // while there are characters remaining
			if (param == 1) { // last character of top is '+'
				System.out.println("+");
				--param;
			} else { // iterates pattern of four characters until the width is used up
				System.out.print("+---");
				param = param - 4;
			}
		}
	}

	/**
	 * Makes a row of the maze at the row input to the method. If start and finish
	 * are set, prints out 'S' and 'F' are printed to represent them respectively.
	 * If the maze has been solved, prints out '*'s to show the pathway
	 * 
	 * @param param
	 *            the row of mazeInfo in focus
	 */

	private void makeInside(int param) {
		for (int i = 0; i < mazeInfo[param].length; ++i) {

			if (start[0] == param && start[1] == i) { // if coordinates match the start position, mark section with 'S'
				if (mazeInfo[param][i] == '|' || mazeInfo[param][i] == 'L') { // if the section is marked with 'L' or
																				// '|', it has a wall on its left side
					System.out.print("| S ");
				} else { // otherwise, there is no wall
					System.out.print("  S ");
				}
			} else if (finish[0] == param && finish[1] == i) { // if coordinates match the end position, mark section
																// with 'F'
				if (mazeInfo[param][i] == '|' || mazeInfo[param][i] == 'L') {
					System.out.print("| F ");
				} else {
					System.out.print("  F ");
				}
			} else if (solved != null && solved == true) { // if the maze has been solved
				Position check = new Position(param, i); // make a 'check' Position of the loops current coordinates
				if (path.contains(check)) { // if the path stack has this position, mark the section with an '*'
					if (mazeInfo[param][i] == '|' || mazeInfo[param][i] == 'L') {
						System.out.print("| * ");
					} else {
						System.out.print("  * ");
					}
				} else {// otherwise print out normally
					if (mazeInfo[param][i] == '|' || mazeInfo[param][i] == 'L') {
						System.out.print("|   ");
					} else {
						System.out.print("    ");
					}
				}
			} else { // with no other special conditions, print out normally
				if (mazeInfo[param][i] == '|' || mazeInfo[param][i] == 'L') {
					System.out.print("|   ");
				} else {
					System.out.print("    ");
				}
			}
		}
		System.out.println("|"); // a section row ends with '|' and a new line
	}

	/**
	 * Creates the row-separating border based on the mazeInfo row specified by the
	 * parameter. If mazeInfo contains 'L' or '_' at a certain coordinate, then
	 * there is a row separating border at that section.
	 * 
	 * @param param
	 *            the row of mazeInfo in focus
	 */

	private void makeBorder(int param) {

		System.out.print("+"); // border always begins with a '+'
		boolean wallBefore = true; // initializes that there was a border in the section before (for details in
									// border-character length)
		for (int i = 0; i < mazeInfo[param].length; ++i) {
			if (mazeInfo[param][i] == '_' || mazeInfo[param][i] == 'L') { // if coordinate has a bottom border
				if (wallBefore) { // if there was a wall before, only print four characters
					System.out.print("---+");
				} else { // if there wasn't a wall before, print five characters
					System.out.print("+---+");
					wallBefore = true; // now the previous section did have a border
				}
			} else if (mazeInfo[param][i] == '|') { // if section simply made a wall, print out its border connector
													// only if there wasn't a border before
				if (wallBefore) {
					System.out.print("   ");
					wallBefore = false; // now there wasn't a border before
				} else {
					System.out.print("+   ");
				}
			} else {
				if (wallBefore) { // if wall before, print three characters
					System.out.print("   ");
					wallBefore = false; // now no border before
				} else { // otherwise, print four characters
					System.out.print("    ");
				}
			}
		}
		if (!wallBefore) { // if row did not finish with a border, print out a '+' to finsh the border
			System.out.print('+');
		}
		System.out.println("");
	}

	/**
	 * Checks if the cursors position is one that has already been stored in the
	 * stack. If it has, removes from the stack the series of coordinates that led
	 * to this backtrack. Otherwise, simply adds new position to the stack
	 */

	private void checkForDuplicate() {
		Position check = new Position(tracker[0], tracker[1]); // creates position based on cursor's current coordinates
		if (path.contains(check)) { // checks if position already exists in stack
			while (path.getSize() > 0 && !path.peek().equals(check)) { // while top position doesn't equal the first
																		// iteration of check position
				path.pop(); // get rid of top position
			}
		} else { // add new position to stack
			path.push(check);
		}
	}

	/**
	 * Attempts to turn the cursor to the right (based on its orientation) by one
	 * position. Cursor cannot move through walls or out of the maze
	 * 
	 * @return whether or not cursor was able to turn right
	 */

	private boolean turnRight() {
		if (east) { // looking right
			if (tracker[0] + 1 < mazeInfo.length && mazeInfo[tracker[0]][tracker[1]] != 'L'
					&& mazeInfo[tracker[0]][tracker[1]] != '_') { // checks if move runs into wall or out of the map
				east = false; // update orientation
				south = true;
				tracker[0] = tracker[0] + 1; // update position
				return true;
			} else {
				return false;
			}
		} else if (north) { // looking up
			if (tracker[1] + 1 < mazeInfo[0].length && mazeInfo[tracker[0]][tracker[1] + 1] != 'L'
					&& mazeInfo[tracker[0]][tracker[1] + 1] != '|') {
				north = false;
				east = true;
				tracker[1] = tracker[1] + 1;
				return true;
			} else {
				return false;
			}
		} else if (west) { // looking left
			if (tracker[1] > 0 && mazeInfo[tracker[0] - 1][tracker[1]] != 'L'
					&& mazeInfo[tracker[0] - 1][tracker[1]] != '_') {
				west = false;
				north = true;
				tracker[0] = tracker[0] - 1;
				return true;
			} else {
				return false;
			}
		} else if (south) { // looking down
			if (tracker[1] > 0 && mazeInfo[tracker[0]][tracker[1]] != 'L' && mazeInfo[tracker[0]][tracker[1]] != '|') {
				south = false;
				west = true;
				tracker[1] = tracker[1] - 1;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Attempts to move the cursor forward (based on its orientation) by one
	 * position. Cursor cannot move through walls or out of the maze
	 * 
	 * @return whether or not cursor was able to move forward
	 */

	private boolean goStraight() {
		if (south) { // looking down
			if (tracker[0] + 1 < mazeInfo.length && mazeInfo[tracker[0]][tracker[1]] != 'L'
					&& mazeInfo[tracker[0]][tracker[1]] != '_') { // checks if move runs into wall or out of the map
				tracker[0] = tracker[0] + 1; // update position
				return true;
			} else {
				return false;
			}
		} else if (east) { // looking right
			if (tracker[1] + 1 < mazeInfo[0].length && mazeInfo[tracker[0]][tracker[1] + 1] != 'L'
					&& mazeInfo[tracker[0]][tracker[1] + 1] != '|') {
				tracker[1] = tracker[1] + 1;
				return true;
			} else {
				return false;
			}
		} else if (north) { // looking up
			if (tracker[0] > 0 && mazeInfo[tracker[0] - 1][tracker[1]] != 'L'
					&& mazeInfo[tracker[0] - 1][tracker[1]] != '_') {
				tracker[0] = tracker[0] - 1;
				return true;
			} else {
				return false;
			}
		} else if (west) { // looking left
			if (tracker[1] > 0 && mazeInfo[tracker[0]][tracker[1]] != 'L' && mazeInfo[tracker[0]][tracker[1]] != '|') {
				tracker[1] = tracker[1] - 1;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Attempts to turn the cursor to the left (based on its orientation) by one
	 * position. Cursor cannot move through walls or out of the maze
	 * 
	 * @return whether or not cursor was able to turn left
	 */

	private boolean turnLeft() {
		if (west) { // looking left
			if (tracker[0] + 1 < mazeInfo.length && mazeInfo[tracker[0]][tracker[1]] != 'L'
					&& mazeInfo[tracker[0]][tracker[1]] != '_') { // checks if move runs into wall or out of the map
				west = false;
				south = true;
				tracker[0] = tracker[0] + 1;
				return true;
			} else {
				return false;
			}
		} else if (south) { // looking down
			if (tracker[1] + 1 < mazeInfo[0].length && mazeInfo[tracker[0]][tracker[1] + 1] != 'L'
					&& mazeInfo[tracker[0]][tracker[1] + 1] != '|') {
				south = false;
				east = true;
				tracker[1] = tracker[1] + 1;
				return true;
			} else {
				return false;
			}
		} else if (east) { // looking right
			if (tracker[0] > 0 && mazeInfo[tracker[0] - 1][tracker[1]] != 'L'
					&& mazeInfo[tracker[0] - 1][tracker[1]] != '_') {
				east = false;
				north = true;
				tracker[0] = tracker[0] - 1;
				return true;
			} else {
				return false;
			}
		} else if (north) { // looking up
			if (tracker[1] > 0 && mazeInfo[tracker[0]][tracker[1]] != 'L' && mazeInfo[tracker[0]][tracker[1]] != '|') {
				north = false;
				west = true;
				tracker[1] = tracker[1] - 1;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Attempts to move the cursor backwards (based on its orientation) by one
	 * position. Cursor cannot move through walls or out of the maze
	 * 
	 * @return whether or not cursor was able to move backwards
	 */

	private boolean goBack() {
		if (north) { // looking up
			if (tracker[0] + 1 < mazeInfo.length && mazeInfo[tracker[0]][tracker[1]] != 'L'
					&& mazeInfo[tracker[0]][tracker[1]] != '_') { // checks if move runs into wall or out of the map
				north = false;
				south = true;
				tracker[0] = tracker[0] + 1;
				return true;
			} else {
				return false;
			}
		} else if (west) { // looking left
			if (tracker[1] + 1 < mazeInfo[0].length && mazeInfo[tracker[0]][tracker[1] + 1] != 'L'
					&& mazeInfo[tracker[0]][tracker[1] + 1] != '|') {
				west = false;
				east = true;
				tracker[1] = tracker[1] + 1;
				return true;
			} else {
				return false;
			}
		} else if (south) { // looking down
			if (tracker[0] > 0 && mazeInfo[tracker[0] - 1][tracker[1]] != 'L'
					&& mazeInfo[tracker[0] - 1][tracker[1]] != '_') {
				south = false;
				north = true;
				tracker[0] = tracker[0] - 1;
				return true;
			} else {
				return false;
			}
		} else if (east) { // looking right
			if (tracker[1] > 0 && mazeInfo[tracker[0]][tracker[1]] != 'L' && mazeInfo[tracker[0]][tracker[1]] != '|') {
				east = false;
				west = true;
				tracker[1] = tracker[1] - 1;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
