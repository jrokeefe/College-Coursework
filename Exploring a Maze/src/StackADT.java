
//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           P07 Exploring a Maze
// Files:           StackADT.java
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
 * Generic interface used to create generic data type StackADT
 */

public interface StackADT<E> {

	public void push(E item); // adds a new item to the top of the stack

	public E pop() throws EmptyStackException; // removes the top item from the stack and returns it

	public E peek() throws EmptyStackException; // returns the top item from the stack without removing it

	public boolean isEmpty(); // returns true if the stack is empty, otherwise returns false
}
