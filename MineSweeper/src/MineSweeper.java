
//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           Mine Sweeper
// Files:           MineSweeper.java
// Course:          CS 200, Fall 2017
//
// Author:          Tyler Bambrough
// Email:           Tbambrough@wisc.edu
// Lecturer's Name: Jim
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    Jack O'Keefe
// Partner Email:   jokeefe2@wisc.edu
// Lecturer's Name: Jim
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   _X__ Write-up states that pair programming is allowed for this assignment.
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
// Persons:         N/A
// Online Sources:  N/A
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.Random;
import java.util.Scanner;

/**
 * This class runs a simulated minesweeper game through java. The game consists
 * of
 *
 *
 * @author Tyler Bambrough
 * @author Jack O'Keefe
 *
 */

public class MineSweeper {

	/**
	 * This is the main method for Mine Sweeper game! This method contains the
	 * within game and play again loops and calls the various supporting methods.
	 *
	 * @param args
	 *            (unused)
	 */
	public static void main(String[] args) {

		Scanner scnr = new Scanner(System.in); // initialize scnr as Scanner input
		Random randGen = new Random();
		randGen.setSeed(Config.SEED);

		System.out.println("Welcome to Mine Sweeper!"); // introduction

		String userInput = "yes"; // userInput is game play decision from user //initialization of game play
		// decision
		boolean gamePlay = true;

		while ((userInput.charAt(0) == 'Y') || (userInput.charAt(0) == 'y')) { // reads first letter only of userInput

			gamePlay = true;
			int width = promptUser(scnr,
					"What width of map would you like (" + Config.MIN_SIZE + " - " + Config.MAX_SIZE + "): ",
					Config.MIN_SIZE, Config.MAX_SIZE); // determines valid width of map
			int height = promptUser(scnr,
					"What height of map would you like (" + Config.MIN_SIZE + " - " + Config.MAX_SIZE + "): ",
					Config.MIN_SIZE, Config.MAX_SIZE); // determines valid height of map
			System.out.println("");

			boolean[][] mines = new boolean[height][width];// initializes mine array, will not be visible to users
			int numMines = placeMines(mines, randGen);// randomly places mines onto boolean map
			System.out.println("Mines: " + numMines);

			char[][] mineField = new char[height][width]; // initialization of main game play array
			eraseMap(mineField); // clears all mines and picked spots from map
			printMap(mineField); // prints out newly erased map

			while (gamePlay == true) {
				int userRow = promptUser(scnr, "row: ", 1, height) - 1; // determines valid row input
				int userCol = promptUser(scnr, "column: ", 1, width) - 1; // determines valid column input
				int nearbyMines = sweepLocation(mineField, mines, userRow, userCol); // user input is array location

				if (nearbyMines == -1) { // if user value is a mine
					mineField[userRow][userCol] = Config.SWEPT_MINE;
					showMines(mineField, mines);
					printMap(mineField);
					System.out.println("Sorry, you lost.");
					gamePlay = false;
				} else if (nearbyMines == 0) { // no mines, no nearby neighbors
					sweepAllNeighbors(mineField, mines, userRow, userCol);
					System.out.println("");
					System.out.println("Mines: " + numMines);
					printMap(mineField);
				} else if (nearbyMines > 0) { // more than one neighbor
					if (allSafeLocationsSwept(mineField, mines) == true) { // if locations completely swept - game won
						showMines(mineField, mines);
						printMap(mineField);
						System.out.println("You Win!");
						gamePlay = false;
					} else { // if locations not swept, continue game and print out updated map
						System.out.println("");
						System.out.println("Mines: " + numMines);
						printMap(mineField);
					}
				} else { // if userinput is outside bounds or an already swept location, print map and
					// continue game
					System.out.println("Mines: " + numMines);
					printMap(mineField);
				}
			}

			System.out.print("Would you like to play again (y/n)? ");

			String userInputRaw = scnr.nextLine(); // reads user input for game play decision
			userInput = userInputRaw.trim(); // cuts off extra white space of input

		}

		System.out.println("Thank you for playing Mine Sweeper!"); // ends program

	}

	/**
	 * This method prompts the user for a number, verifies that it is between min
	 * and max, inclusive, before returning the number.
	 *
	 * If the number entered is not between min and max then the user is shown an
	 * error message and given another opportunity to enter a number. If min is 1
	 * and max is 5 the error message is: Expected a number from 1 to 5.
	 *
	 * If the user enters characters, words or anything other than a valid int then
	 * the user is shown the same message. The entering of characters other than a
	 * valid int is detected using Scanner's methods (hasNextInt) and does not use
	 * exception handling.
	 *
	 * Do not use constants in this method, only use the min and max passed in
	 * parameters for all comparisons and messages. Do not create an instance of
	 * Scanner in this method, pass the reference to the Scanner in main, to this
	 * method. The entire prompt should be passed in and printed out.
	 *
	 * @param in
	 *            The reference to the instance of Scanner created in main.
	 * @param prompt
	 *            The text prompt that is shown once to the user.
	 * @param min
	 *            The minimum value that the user must enter.
	 * @param max
	 *            The maximum value that the user must enter.
	 * @param userInput
	 *            The input from scanner that is to be verified.
	 * @return The integer that the user entered that is between min and max,
	 *         inclusive.
	 */
	public static int promptUser(Scanner in, String prompt, int min, int max) {
		int userInput = 0;

		System.out.print(prompt);

		do {
			if (in.hasNextInt()) { // if number
				userInput = in.nextInt();
				if (userInput < min || userInput > max) { // if out of bounds
					System.out.println("Expected a number from " + min + " to " + max + ".");
				}
			}

			else { // if not a number
				System.out.println("Expected a number from " + min + " to " + max + ".");
			}

			in.nextLine();

		} while (userInput < min || userInput > max); // out of bounds

		return userInput; // if not an error, return input and continue
	}

	/**
	 * This initializes the map char array passed in such that all elements have the
	 * Config.UNSWEPT character. Within this method only use the actual size of the
	 * array. Don't assume the size of the array. This method does not print out
	 * anything. This method does not return anything.
	 *
	 * @param map
	 *            An allocated array. After this method call all elements in the
	 *            array have the same character, Config.UNSWEPT.
	 */
	public static void eraseMap(char[][] map) {
		for (int i = 0; i < map.length; ++i) { // rows

			for (int j = 0; j < map[0].length; ++j) { // columns
				map[i][j] = Config.UNSWEPT; // clear with unswept mine character
			}
		}
		return;
	}

	/**
	 * This prints out a version of the map without the row and column numbers. A
	 * map with width 4 and height 6 would look like the following: . . . . . . . .
	 * . . . . . . . . . . . . . . . . For each location in the map a space is
	 * printed followed by the character in the map location.
	 *
	 * @param map
	 *            The map to print out.
	 */
	public static void simplePrintMap(char[][] map) {
		for (int i = 0; i < map.length; ++i) { // rows
			for (int j = 0; j < map[0].length; ++j) { // columns
				System.out.print(" " + map[i][j]);
			}
			System.out.println("");
		}

		return;
	}

	/**
	 * This prints out the map. This shows numbers of the columns and rows on the
	 * top and left side, respectively. map[0][0] is row 1, column 1 when shown to
	 * the user. The first column, last column and every multiple of 5 are shown.
	 *
	 * To print out a 2 digit number with a leading space if the number is less than
	 * 10, you may use: System.out.printf("%2d", 1);
	 *
	 * @param map
	 *            The map to print out.
	 */
	public static void printMap(char[][] map) {
		System.out.print("  ");
		for (int i = 1; i <= map[0].length; ++i) { // columns
			if (i == 1 || i % 5 == 0 || i == map[0].length) { // if i is 1, a factor of 5, or last column
				System.out.printf("%2d", i); // prints out two digits
			} else {
				System.out.print("--");
				;
			}
		}
		System.out.println("");

		for (int k = 1; k <= map.length; ++k) { // rows
			if (k == 1 || k % 5 == 0 || k == map.length) { // if k is 1, a factor of 5, or last row
				System.out.printf("%2d", k);
			} else {
				System.out.print(" |");
			}
			for (int l = 1; l <= map[0].length; ++l) { // for each row

				System.out.print(" " + map[k - 1][l - 1]); // print out corresponding row
			}

			System.out.println(""); // start a new line for the next row
		}

		return;
	}

	/**
	 * This method initializes the boolean mines array passed in. A true value for
	 * an element in the mines array means that location has a mine, false means the
	 * location does not have a mine. The MINE_PROBABILITY is used to determine
	 * whether a particular location has a mine. The randGen parameter contains the
	 * reference to the instance of Random created in the main method.
	 *
	 * Access the elements in the mines array with row then column (e.g.,
	 * mines[row][col]).
	 *
	 * Access the elements in the array solely using the actual size of the mines
	 * array passed in, do not use constants.
	 *
	 * A MINE_PROBABILITY of 0.3 indicates that a particular location has a 30%
	 * chance of having a mine. For each location the result of randGen.nextFloat()
	 * < Config.MINE_PROBABILITY determines whether that location has a mine.
	 *
	 * This method does not print out anything.
	 *
	 * @param mines
	 *            The array of boolean that tracks the locations of the mines.
	 * @param randGen
	 *            The reference to the instance of the Random number generator
	 *            created in the main method.
	 * @return The number of mines in the mines array.
	 */
	public static int placeMines(boolean[][] mines, Random randGen) {
		int minesCounter = 0; // default number of mines is 0
		float a;
		for (int i = 0; i < mines.length; ++i) { // rows
			for (int j = 0; j < mines[0].length; ++j) { // columns

				a = randGen.nextFloat(); // randomly generate a float
				if (a < Config.MINE_PROBABILITY) { // if the float is below the set probability, make the element true,
					// which signifies the presence of a mine
					mines[i][j] = true;
					minesCounter += 1; // updates the number of mines in the map
				}
			}
		}
		return minesCounter; // returns the total number of mines in the map
	}

	/**
	 * This method returns the number of mines in the 8 neighboring locations. For
	 * locations along an edge of the array, neighboring locations outside of the
	 * mines array do not contain mines. This method does not print out anything.
	 *
	 * If the row or col arguments are outside the mines array, then return -1. This
	 * method (or any part of this program) should not use exception handling.
	 *
	 * @param mines
	 *            The array showing where the mines are located.
	 * @param row
	 *            The row, 0-based, of a location.
	 * @param col
	 *            The col, 0-based, of a location.
	 * @return The number of mines in the 8 surrounding locations or -1 if row or
	 *         col are invalid.
	 */
	public static int numNearbyMines(boolean[][] mines, int row, int col) {

		int numMines = 0; // number of surrounding mines is defaulted to 0
		if (row >= mines.length || col >= mines[0].length || row < 0 || col < 0) { // if the entered element is out of
			// range, returns -1

			numMines = -1;
		} else {

			for (int i = -1; i < 2; ++i) { // rows
				for (int j = -1; j < 2; ++j) {// columns
					if (row + i > -1 && row + i < mines.length && col + j > -1 && col + j < mines[0].length // ensures
					// the
					// presence
					// of a
					// surrounding
					// element
					// and
					// prevents
					// out of
					// bounds
					// exception
							&& mines[row + i][col + j] == true) { // if the surrounding element contains a mine (is
						// true), updates the number of surrounding mines
						// counter

						numMines = numMines + 1; //

					}

				}

			}
		}

		return numMines; // returns the total number of surrounding mines
	}

	/**
	 * This updates the map with each unswept mine shown with the Config.HIDDEN_MINE
	 * character. Swept mines will already be mapped and so should not be changed.
	 * This method does not print out anything.
	 *
	 * @param map
	 *            An array containing the map. On return the map shows unswept
	 *            mines.
	 * @param mines
	 *            An array indicating which locations have mines. No changes are
	 *            made to the mines array.
	 */
	public static void showMines(char[][] map, boolean[][] mines) {
		for (int i = 0; i < map.length; ++i) { // rows
			for (int j = 0; j < map[0].length; ++j) { // columns
				if (mines[i][j] == true && map[i][j] != Config.SWEPT_MINE) { // if the element in the boolean array is
					// true and is not the called location,
					// changes the character in the char
					// array to the hidden mine character
					map[i][j] = Config.HIDDEN_MINE;
				}
			}
		}
		return;
	}

	/**
	 * Returns whether all the safe (non-mine) locations have been swept. In other
	 * words, whether all unswept locations have mines. This method does not print
	 * out anything.
	 *
	 * @param map
	 *            The map showing touched locations that is unchanged by this
	 *            method.
	 * @param mines
	 *            The mines array that is unchanged by this method.
	 * @return whether all non-mine locations are swept.
	 */
	public static boolean allSafeLocationsSwept(char[][] map, boolean[][] mines) {
		char a;
		boolean b;
		boolean c = true;
		for (int i = 0; i < map.length; ++i) { // rows
			for (int j = 0; j < map[0].length; ++j) { // columns
				a = map[i][j];
				b = mines[i][j];
				if (a == Config.UNSWEPT && b == false) { // if an element in the boolean array is false and the
					// character in the same location for the char array is the
					// unswept character, then makes c false
					c = false;
				}
			}
		}
		return c; // if the game is over, returns true, otherwise returns false

	}

	/**
	 * This method sweeps the specified row and col. - If the row and col specify a
	 * location outside the map array then return -3 without changing the map. - If
	 * the location has already been swept then return -2 without changing the map.
	 * - If there is a mine in the location then the map for the corresponding
	 * location is updated with Config.SWEPT_MINE and return -1. - If there is not a
	 * mine then the number of nearby mines is determined by calling the
	 * numNearbyMines method. - If there are 1 to 8 nearby mines then the map is
	 * updated with the characters '1'..'8' indicating the number of nearby mines. -
	 * If the location has 0 nearby mines then the map is updated with the
	 * Config.NO_NEARBY_MINE character. - Return the number of nearbyMines.
	 *
	 * @param map
	 *            The map showing swept locations.
	 * @param mines
	 *            The array showing where the mines are located.
	 * @param row
	 *            The row, 0-based, of a location.
	 * @param col
	 *            The col, 0-based, of a location.
	 * @return the number of nearby mines, -1 if the location is a mine, -2 if the
	 *         location has already been swept, -3 if the location is off the map.
	 */
	public static int sweepLocation(char[][] map, boolean[][] mines, int row, int col) {
		int i;
		if (row < 0 || row >= map.length || col < 0 || col >= map[0].length) { // if location in out of bounds, returns
			// -3
			i = -3;
		} else if (map[row][col] != Config.UNSWEPT) { // if the location has already been swept, returns -2
			i = -2;
		} else if (mines[row][col] == true) { // if the entered location is a mine, returns -1
			map[row][col] = Config.SWEPT_MINE;
			i = -1;
		} else {
			i = numNearbyMines(mines, row, col); // if the entered location is not a mine, nor out of bounds, calls
			// method to determine number of nearby mines
			if (i == 0) { // if there are no nearby mines, the element in the char array is changed to the
				// no nearby mine character
				map[row][col] = Config.NO_NEARBY_MINE;
			} else { // otherwise, the number of nearby mines is entered into the location of the
				// char array in char form
				map[row][col] = (char) (i + 48);
			}
		}
		return i; // return the number for each situation
	}

	/**
	 * This method iterates through all 8 neighboring locations and calls
	 * sweepLocation for each. It does not call sweepLocation for its own location,
	 * just the neighboring locations.
	 *
	 * @param map
	 *            The map showing touched locations.
	 * @param mines
	 *            The array showing where the mines are located.
	 * @param row
	 *            The row, 0-based, of a location.
	 * @param col
	 *            The col, 0-based, of a location.
	 */
	public static void sweepAllNeighbors(char[][] map, boolean[][] mines, int row, int col) {
		for (int i = -1; i < 2; ++i) { // rows
			for (int j = -1; j < 2; ++j) { // columns
				if (!(i == 0 && j == 0)) { // if not the called location
					sweepLocation(map, mines, row + i, col + j); // calls the sweepLocation method for neighbor
				}
			}
		}
		return;
	}
}