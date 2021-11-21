//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           P07 Exploring a Maze
// Files:           TestStack.java
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

import java.util.EmptyStackException;

/**
 * This class contains a series of different tests used to test the methods
 * implemented in MazeRunnerStack
 *
 * @author Jack O'Keefe
 */

public class TestStack {

	/**
	 * Main method that calls all tests and keeps track if the the MazeRunnerStack
	 * was able to pass TestStack
	 * 
	 * @return whether or not the tests found an error
	 */

	public static boolean runTests() { // calls a series of test methods. If these methods return true, an error has
										// occurred
		boolean error = false; // initializes the presence of an error as false
		if (testPushFromEmpty()) { // doesn't test other methods that test method push() until it is known that
									// push() can handle empty stacks
			error = true;
		} else {
			error = testPushSize();
		}
		if (testPopFromEmpty()) { // doesn't test other methods that test method pop() until it is known that
									// pop() can handle EmptyStackException
			error = true;
		} else {
			if (error) { // counducts tests if error is already known to exist
				testPopSize();
				testPopReturn();
			} else { // error will be true if either methods find error
				error = testPopSize() || testPopReturn();
			}
		}
		if (testPeekFromEmpty()) { // doesn't test other methods that test method peek() until it is known that
									// peek() can handle EmptyStackException
			error = true;
		} else {
			if (error) {
				testPeekSize();
				testPeekReturn();
			} else {
				error = testPeekSize() || testPeekReturn();
			}
		}
		if (error) {
			testContainsTrue();
			testContainsFalse();
		} else {
			error = testContainsTrue() || testContainsFalse();
		}
		return error;
	}

	/**
	 * Tests if stack is initialized upon creation
	 * 
	 * @return whether or not the test found an error
	 */

	public static boolean testPushFromEmpty() {
		MazeRunnerStack test = new MazeRunnerStack(); // creates new stack
		try {
			test.push(new Position(1, 1)); // possible throw of NullPointerException
			return false;
		} catch (NullPointerException e) {
			System.out.println("Failure: push null stack. Failed to initialize internal storage in stack.");
			return true;
		}
	}

	/**
	 * Tests if stack size is updated after push
	 * 
	 * @return whether or not the test found an error
	 */

	public static boolean testPushSize() {
		MazeRunnerStack test = new MazeRunnerStack();
		test.push(new Position(1, 1)); // add a new position to stack
		if (test.getSize() == 1) { // stack size should now be 1
			return false;
		} else {
			System.out.println("Failure: push stack size. Expected size 1, got " + test.getSize() + ".");
			return true;
		}
	}

	/**
	 * Tests if stack throws EmptyStackException if pop is called on a stack of size
	 * 0
	 * 
	 * @return whether or not the test found an error
	 */

	public static boolean testPopFromEmpty() {
		MazeRunnerStack test = new MazeRunnerStack(); // stack is size 0
		try {
			Position x = test.pop(); // EmptyStackException error should be thrown here
			System.out.println("Failure: pop from an empty stack. Expected EmptyStackException. Got (" + x.row + ", "
					+ x.col + ").");
			return true;
		} catch (EmptyStackException e) {
			return false;
		}
	}

	/**
	 * Tests if stack size is updated after pop() is called
	 * 
	 * @return whether or not the test found an error
	 */

	public static boolean testPopSize() {
		MazeRunnerStack test = new MazeRunnerStack();
		test.push(new Position(1, 1));
		test.pop(); // stack size should be 0 now
		if (test.getSize() == 0) {
			return false;
		} else {
			System.out.println("Failure: pop size. Expected stack size of 0. Got " + test.getSize() + ".");
			return true;
		}
	}

	/**
	 * Tests if pop() returns top object from stack
	 * 
	 * @return whether or not the test found an error
	 */

	public static boolean testPopReturn() {
		MazeRunnerStack test = new MazeRunnerStack();
		Position a = new Position(1, 1);
		Position b = new Position(2, 2);
		Position c = new Position(3, 3);
		test.push(c);
		test.push(b);
		test.push(a); // order of stack is now a, b, c
		Position x = test.pop(); // x should be a
		if (x.equals(a)) {
			return false;
		} else {
			System.out.println("Failure: pop return. Method pop did not return the correct Position.");
			return true;
		}
	}

	/**
	 * Tests if stack throws EmptyStackException if peek is called on a stack of
	 * size 0
	 * 
	 * @return whether or not the test found an error
	 */

	public static boolean testPeekFromEmpty() {
		MazeRunnerStack test = new MazeRunnerStack(); // stack size is 0
		try {
			Position x = test.peek(); // EmptyStackException should be thrown here
			System.out.println("Failure: peek from an empty stack. Expected EmptyStackException. Got (" + x.row + ", "
					+ x.col + ").");
			return true;
		} catch (EmptyStackException e) {
			return false;
		}
	}

	/**
	 * Tests if peek() does not change the size of the stack
	 * 
	 * @return whether or not the test found an error
	 */

	public static boolean testPeekSize() {
		MazeRunnerStack test = new MazeRunnerStack();
		test.push(new Position(1, 1));
		test.peek(); // method should not change size of stack
		if (test.getSize() == 1) {
			return false;
		} else {
			System.out.println("Failure: peek stack size. After peek was called, expected stack size 1. Got: "
					+ test.getSize() + ".");
			return true;
		}

	}

	/**
	 * Tests if peek() returns top object from stack
	 * 
	 * @return whether or not the test found an error
	 */

	public static boolean testPeekReturn() {
		MazeRunnerStack test = new MazeRunnerStack();
		Position a = new Position(1, 1);
		Position b = new Position(2, 2);
		Position c = new Position(3, 3);
		test.push(c);
		test.push(b);
		test.push(a); // stack order is now a, b, c
		Position x = test.peek(); // x should be equal to a
		if (x.equals(a)) {
			return false;
		} else {
			System.out.println("Failure: peek return. Method peek did not return the correct Position.");
			return true;
		}
	}

	/**
	 * Tests if contains() method returns true when stack contains object of same
	 * values as entered object
	 * 
	 * @return whether or not the test found an error
	 */

	public static boolean testContainsTrue() {
		MazeRunnerStack test = new MazeRunnerStack();
		Position yes = new Position(3, 3); // position that is the same
		Position No = new Position(2, 2); // position that is not the same
		Position no = new Position(1, 1); // position that is not the same
		Position same = new Position(3, 3); // same position used to check
		test.push(yes);
		test.push(No);
		test.push(no); // order in stack is no, No, yes
		if (test.contains(same)) {
			return false;
		} else {
			System.out.println("Failure: Method contains returned false when the position did exist in the stack.");
			return true;
		}
	}

	/**
	 * Tests if contains() method returns false when stack does not contain object
	 * of same values as entered object
	 * 
	 * @return whether or not the test found an error
	 */

	public static boolean testContainsFalse() {
		MazeRunnerStack test = new MazeRunnerStack();
		Position NO = new Position(3, 3); // position that is not the same
		Position No = new Position(2, 2); // position that is not the same
		Position no = new Position(1, 1); // position that is not the same
		Position yes = new Position(0, 0); // position that is not the same as others, used to check
		test.push(NO);
		test.push(No);
		test.push(no);
		if (test.contains(yes)) {
			System.out.println("Failure: Method contains returned true when the position did not exist in the stack.");
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println("~~~~~Start of Maze Runner Stack Test~~~~~");
		if (runTests()) { // if runTests() reports at least one error, test has failed
			System.out.println("***Maze Runner Stack Test Failed***");
		} else {
			System.out.println("***Maze Runner Stack Test Passed***");
		}
		System.out.println("~~~~~~End of Maze Runner Stack Test~~~~~~");
	}

}
