//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           P07 Exploring a Maze
// Files:           MazeRunnerStack.java
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
 * This class creates a Position object, which contains two integers relating to
 * column and row.
 */

class Position {

	int col; // column
	int row; // row

	/**
	 * Constructor takes input row and column parameters and assigns them to row and
	 * column for the object
	 * 
	 * @param row
	 * @param col
	 */

	Position(int row, int col) {
		this.col = col;
		this.row = row;
	}

	/**
	 * Determines if the row and column of this Position object matches that of the
	 * input Position object
	 * 
	 * @param other
	 *            input Position object
	 * @return whether or not the Position objects have the same values
	 */

	boolean equals(Position other) {
		return this.col == other.col && this.row == other.row;
	}

}

/**
 * This class creates a stack of Position objects. Each new object is put at the
 * front of the stack.
 * 
 * @param stack
 * @param size
 * @author Jack O'Keefe
 */

public class MazeRunnerStack implements StackADT<Position> {

	private Position[] stack = new Position[1000]; // over-sized array where each object is placed
	private int size = 0; // number of objects in stack

	/**
	 * Adds new Position object to stack
	 * 
	 * @param item
	 *            new Position object
	 */

	public void push(Position item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		else {
			for (int i = size - 1; i >= 0; --i) { // shifts all objects currently in stack back
				stack[i + 1] = stack[i];
			}
			stack[0] = item; // beginning of stack is new object
			++size; // update size
		}
	}

	/**
	 * Takes top object out of the stack
	 * 
	 * @throws EmptyStackException
	 * @return the top object in the stack
	 */

	public Position pop() throws EmptyStackException {
		if (size == 0) { // if nothing is in the stack, throw exception
			throw new EmptyStackException();
		}
		Position ret = stack[0]; // take top object
		for (int i = 1; i < size; ++i) { // shift all other objects up
			stack[i - 1] = stack[i];
		}
		--size; // update size
		return ret;
	}

	/**
	 * Returns top object from the stack
	 * 
	 * @throws EmptyStackException
	 * @return the top object in the stack
	 */

	public Position peek() throws EmptyStackException {
		if (size == 0) { // if nothing is in the stack, throw exception
			throw new EmptyStackException();
		}
		return stack[0]; // top object
	}

	/**
	 * Checks if there are no objects in the stack
	 * 
	 * @return whether or not the stack is empty
	 */

	public boolean isEmpty() {
		if (size == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if there exists an object in the stack with the same values as the
	 * input object
	 * 
	 * @param p
	 *            input object
	 * @return whether or not the stack contains a Position with the same values
	 */

	public boolean contains(Position p) {
		for (int i = 0; i < size; ++i) { // for each object, checks if it has same values as p
			if (p.equals(stack[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Getter for stack size
	 * 
	 * @return size of stack
	 */

	public int getSize() {
		return size;
	}

}
