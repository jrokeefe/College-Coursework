
/**
 * This file contains testing methods for the MineSweeper project.
 * These methods are intended to serve several objectives:
 * 1) provide an example of a way to incrementally test your code
 * 2) provide example method calls for the MineSweeper methods
 * 3) provide examples of creating, accessing and modifying arrays
 *   
 * Toward these objectives, the expectation is that part of the
 * grade for the MineSweeper project is to write some tests and
 * write header comments summarizing the tests that have been written.
 * Specific places are noted with FIXME but add any other comments
 * you feel would be useful.
 *
 * Some of the provided comments within this file explain
 * Java code as they are intended to help you learn Java.  However,
 * your comments and comments in professional code, should
 * summarize the purpose of the code, not explain the meaning
 * of the specific Java constructs.
 *   
 */

import java.util.Random;
import java.util.Scanner;

/**
 * This class contains a few methods for testing methods in the MineSweeper
 * class as they are developed. These methods are all private as they are only
 * intended for use within this class.
 *
 * @author Jim Williams
 * @author Tyler Bambrough
 * @author Jack O'Keefe
 *
 */
public class TestMineSweeper {

	/**
	 * This is the main method that runs the various tests. Uncomment the tests when
	 * you are ready for them to run.
	 *
	 * @param args
	 *            (unused)
	 */
	public static void main(String[] args) {
		// Milestone 1
		// testing the main loop, promptUser and simplePrintMap, since they have
		// a variety of output, is probably easiest using a tool such as diffchecker.com
		// and comparing to the examples provided.
		testEraseMap();

		// Milestone 2
		testPlaceMines();
		testNumNearbyMines();
		testShowMines();
		testAllSafeLocationsSwept();

		// Milestone 3
		testSweepLocation();
		testSweepAllNeighbors();
		// testing printMap, due to printed output is probably easiest using a
		// tool such as diffchecker.com and comparing to the examples provided.
	}

	/**
	 * This is intended to run some tests on the eraseMap method. 1. The test is to
	 * determine whether or not the eraseMap method if functioning correctly. An
	 * array of several different characters is created and passed to the eraseMap
	 * method in MineSweeper. If the array returned has all of its characters
	 * returned as an UNSWEPT character, then the test passed, else, the the test
	 * fails.
	 *
	 */
	private static void testEraseMap() {
		char[][] testArray = new char[5][6];
		boolean pass = true;

		for (int i = 0; i < testArray.length; ++i) { // rows
			for (int j = 0; j < testArray[0].length; ++j) { // columns
				testArray[i][j] = (char) (i + j); // random character from ASCII list of value i + j
			}
		}
		MineSweeper.eraseMap(testArray);
		for (int i = 0; i < testArray.length; ++i) { // rows
			for (int j = 0; j < testArray[0].length; ++j) { // columns
				if (testArray[i][j] != Config.UNSWEPT) {
					pass = false; // test failed
				}
			}
		}

		if (pass) {
			System.out.print("Test eraseMap Passed");
		} else {
			System.out.print("Test eraseMap Failed");
		}

		return;
	}

	/**
	 * This method is used to determine the number of each elements that are
	 * different between two cells. This is used in the testPlaceMines method below.
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */

	private static int getDiffCells(boolean[][] array1, boolean[][] array2) {
		int counter = 0;
		for (int i = 0; i < array1.length; i++) { // rows
			for (int j = 0; j < array1[i].length; j++) { // columns
				if (array1[i][j] != array2[i][j]) // if they're not equal, add to counter
					counter++;
			}
		}
		return counter;
	}

	/**
	 * This runs some tests on the placeMines method. 1. The first test chooses an
	 * randomly generated boolean map in which the expected number of mines is known
	 * Once sent to the method in MineSweeper, if the number of mines in the map is
	 * not the expected mines, test failed. It additionally determines the number of
	 * different elements between the expected map and the students.
	 */
	private static void testPlaceMines() {
		boolean error = false;

		boolean[][] expectedMap = new boolean[][] { { false, false, false, false, false }, // These expected values were
			// generated with a Random
			// instance set with
			{ false, false, false, false, false }, { false, false, false, true, true }, // seed of 123 and
			// MINE_PROBABILITY is 0.2.
			{ false, false, false, false, false }, { false, false, true, false, false } };
			int expectedNumMines = 3;

			Random studentRandGen = new Random(123);
			boolean[][] studentMap = new boolean[5][5];
			int studentNumMines = MineSweeper.placeMines(studentMap, studentRandGen); // sends new clear boolean map to
			// place mines with the random
			// generator created

			if (studentNumMines != expectedNumMines) { // if the two numbers do not match
				error = true;
				System.out.println(
						"testPlaceMines 1: expectedNumMines=" + expectedNumMines + " studentNumMines=" + studentNumMines);
			}
			int diffCells = getDiffCells(expectedMap, studentMap); // calculate the number of different cells
			if (diffCells != 0) { // if there are differences, tests failed
				error = true;
				System.out.println("testPlaceMines 2: mine map differs.");
			}
			if (error) {
				System.out.println("testPlaceMines: failed");
			} else {
				System.out.println("testPlaceMines: passed");
			}

	}

	/**
	 * This runs some tests on the numNearbyMines method. 1. This test determines
	 * whether or not the number of nearby mines returned is correct for two
	 * different locations on a pre-determined boolean array.
	 */
	private static void testNumNearbyMines() {
		boolean error = false;

		boolean[][] mines = new boolean[][] { { false, false, true, false, false },
			{ false, false, false, false, false }, { false, true, false, true, true },
			{ false, false, false, false, false }, { false, false, true, false, false } };
			int numNearbyMines = MineSweeper.numNearbyMines(mines, 1, 1); // tests location [1][1] of new boolean map

			if (numNearbyMines != 2) { // if not what is expected, test failed
				error = true;
				System.out.println("testNumNearbyMines 1: numNearbyMines=" + numNearbyMines + " expected mines=2");
			}
			numNearbyMines = MineSweeper.numNearbyMines(mines, 0, 0); // tests location [0][0]

			if (numNearbyMines != 0) { // if not what is expected (0), test failed
				error = true;
				System.out.println("testNumNearbyMines 2: numNearbyMines=" + numNearbyMines + " expected mines=0");
			}

			if (error) {
				System.out.println("testNumNearbyMines: failed");
			} else {
				System.out.println("testNumNearbyMines: passed");
			}
	}

	/**
	 * This runs some tests on the showMines method. 1. Tests if method succesfully
	 * reveals the mines on the map 2. Tests if method only reveals a mine on the
	 * map where there is a mine 3. Tests if swept mine is not changed into "hidden
	 * mine" character
	 */
	private static void testShowMines() {
		boolean error = false; // error is defualted as false
		boolean[][] mines = new boolean[][] { { false, false, true, false, false }, // creates a minefield where a true
				// element signifies a mine
				{ false, false, false, false, false }, { false, true, false, false, false },
				{ false, false, false, false, false }, { false, false, true, false, false } };
		char[][] map = new char[mines.length][mines[0].length];
		map[0][2] = Config.UNSWEPT; // locations containing a mine are fitted with an unswept character
		map[2][1] = Config.UNSWEPT;
		map[4][2] = Config.UNSWEPT;
		map[1][1] = Config.SWEPT_MINE; // sets an element in the character array to "swept mine". This should not be
		// changed to the "hidden mine" character
		MineSweeper.showMines(map, mines);
		if (!(map[0][2] == Config.HIDDEN_MINE && map[2][1] == Config.HIDDEN_MINE && map[4][2] == Config.HIDDEN_MINE)) { // if
			// any
			// of
			// the
			// mine
			// locations
			// are
			// not
			// marked
			// with
			// the
			// hidden
			// mine
			// character,
			// error
			// is
			// true
				error = true;
				System.out.println("testShowMines 1: a mine not mapped");
			}
			if (map[0][0] == Config.HIDDEN_MINE || map[0][4] == Config.HIDDEN_MINE || map[4][4] == Config.HIDDEN_MINE) {
				error = true;
				System.out.println("testShowMines 2: unexpected showing of mine."); // if any location of these mine-less
				// locations are marked with the hidden
				// mine character, error is true
			}

			if (map[1][1] == Config.HIDDEN_MINE) { // if swept mine location is changed to "hidden mine" character, an error
				// has occured
				error = true;
				System.out.println("testShowMines 3: swept mine incorrectly changed to hidden mine");
			}

			if (error) {
				System.out.println("testShowMines: failed");
			} else {
				System.out.println("testShowMines: passed");
			}
	}
	/**
	 * This is intended to run some tests on the allSafeLocationsSwept method. 1.
	 * Tests if method returns true when all safe locations have been swept 2. Tests
	 * if method returns false when unswept safe locations remain
	 */
	private static void testAllSafeLocationsSwept() {
		boolean error = false; // error defaulted to false
		boolean[][] mines = new boolean[][] { { true, true, true, true, true }, // creates minefield where all elements
			// are mines
			{ true, true, true, true, true }, { true, true, true, true, true }, { true, true, true, true, true },
			{ true, true, true, true, true } };
			char[][] Array = new char[5][5]; // assigns unswept to all locations in the minefield
			for (int i = 0; i < Array.length; i++) { // rows
				for (int j = 0; j < Array[0].length; j++) { // cols
					Array[i][j] = Config.UNSWEPT;
				}
			}
			Boolean test = MineSweeper.allSafeLocationsSwept(Array, mines); // no unswept safe locations, should return true

			if (!test) { // if the called method returns false, error has occurred
				error = true;
				System.out.println("testAllSafeLocationsSwept 1: unable to recognize all safe locations swept");
			}
			mines[0][0] = false; // minefield now contains safe location
			test = MineSweeper.allSafeLocationsSwept(Array, mines); // contains unswept safe location, should return false
			if (test) { // if called method returns true, error has occurred
				error = true;
				System.out.println("testAllSafeLocationsSwept 2: unable to recognize unswept safe locations");
			}
			if (error) {
				System.out.println("testAllSafeLocationsSwept: failed");
			} else {
				System.out.println("testAllSafeLocationsSwept: passed");
			}
	}

	/**
	 * This is intended to run some tests on the sweepLocation method. 1. Tests if
	 * method returns -3 when location is out of bounds 2. Tests if method returns
	 * -1 when mine is swept 3. Tests if method returns 0 and changes character
	 * array to "no nearby mines" character 4. Tests if method returns -2 and map is
	 * not changed when swept location is swept again 5. Tests if method returns 1
	 * and changes the element in the character array to 1 when a location
	 * neighboring 1 mine is swept
	 */
	private static void testSweepLocation() {
		boolean error = false; // defaults error to false
		char[][] map = new char[3][10]; // makes character array and assigns the unswept character to each element
		for (int i = 0; i < map.length; i++) { // rows
			for (int j = 0; j < map[0].length; j++) { // cols
				map[i][j] = Config.UNSWEPT;
			}
		}
		boolean[][] mines = new boolean[][] { { false, false, false, false, false, false, false, false, false, true }, // creates
			// minefield
			// where
			// true
			// signifies
			// a
			// mine
			{ false, false, false, false, false, true, false, false, false, false },
			{ false, false, false, false, false, false, false, false, false, true } };

			int returnedTest = MineSweeper.sweepLocation(map, mines, 11, 4); // location out of bounds, should return -3
			if (returnedTest != -3) { // if method does not return -3, error has occurred
				error = true;
				System.out.println("testSweepLocation 1: does not recognize location is out of bounds");
			}

			returnedTest = MineSweeper.sweepLocation(map, mines, 2, 9); // method should return -1
			if (returnedTest != -1) { // if called method does not return -1, error has occurred
				error = true;
				System.out.println("testSweepLocation 2: failed to recongize mine hit");
			}

			returnedTest = MineSweeper.sweepLocation(map, mines, 1, 1); // method should set the location in the character
			// array to the "no nearby mine" character and
			// return 0
			if (map[1][1] != Config.NO_NEARBY_MINE || returnedTest != 0) { // if called method does not change locations
				// character to the "no nearby mine" character
				// or does not return 0, error has occurred
				error = true;
				System.out.println("testSweepLocation 3: failed to recongize no nearby mines");
			}

			char[][] oldMap = new char[3][10];
			oldMap = map; // stores map before method is called
			returnedTest = MineSweeper.sweepLocation(map, mines, 1, 1); // selecting a swept location, method should return
			// -2 without changing the map
			if (returnedTest != -2 || oldMap != map) { // if called method does not return -2, or if map is changed, error
				// has occurred
				error = true;
				System.out.println("TestSweepLocation 4: unable to recognize swept location selected");
			}

			returnedTest = MineSweeper.sweepLocation(map, mines, 2, 8); // method should set the location in the character
			// array to and return 1
			if (map[2][8] != (char) 49 || returnedTest != 1) { // if called method does not change locations character to 1
				// or does not return 1, error has occurred
				error = true;
				System.out.println("testSweepLocation 5: failed to recognize nearby mine");
			}
			if (error) {
				System.out.println("testSweepLocation: failed");
			} else {
				System.out.println("testSweepLocation: passed");
			}
	}

	/**
	 * This is intended to run some tests on the sweepAllNeighbors method. 1. Tests
	 * if all neighboring locations of a called location are swept 2. Tests if
	 * called location is not swept
	 */
	private static void testSweepAllNeighbors() {
		boolean error = false; // defaults error to false
		char[][] map = new char[3][10]; // creates minefield where all elements are set to the "unswept" character
		for (int i = 0; i < map.length; i++) { // rows
			for (int j = 0; j < map[0].length; j++) { // cols
				map[i][j] = Config.UNSWEPT;
			}
		}
		boolean[][] mines = new boolean[][] { { false, false, false, false, false, false, false, false, false, true }, // sets
																														// the
																														// mines
																														// in
																														// the
																														// minefield
																														// where
																														// true
																														// signifies
																														// a
																														// mine
				{ false, true, false, false, false, true, false, false, false, true },
				{ false, false, false, false, false, false, false, false, false, true } };
		MineSweeper.sweepAllNeighbors(map, mines, 1, 3); // all neigboring elements should be swept but not the called
		// location
		for (int i = -1; i < 2; i++) { // parses through rows
			for (int j = -1; j < 2; j++) { // parses through cols
				if (1 + i > 0 && 3 + j > 0 && 1 + i < map.length && 3 + j < map[0].length && (i != 0 || j != 0)) { // if
																													// neighboring
																													// location
																													// is
																													// not
																													// out
																													// of
																													// bounds
					if (map[1 + i][3 + j] == Config.UNSWEPT) { // if a neighboring element is set at the "unswept"
						// character, an error has occurred
						error = true;
						System.out.println("testSweepAllNeighbors 1: unable to sweep all neighbors");
					}
				}

			}
		}
		if (map[1][3] != Config.UNSWEPT) { // if the called location is not the "unswept" character, an error has
			// occurred
			error = true;
			System.out.println("testSweepAllNeighbors 2: incorrectly sweeps called location");
		}
		if (error) {
			System.out.println("testSweepAllNeighbors: failed");
		} else {
			System.out.println("testSweepAllNeighbors: passed");
		}

	}
}