//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           P10 Search Engine
// Files:           WebPageNode.java
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
// Persons:         N/A
// Online Sources:  N/A
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

/**
 * An object web page is constructed here. The web page has an id, link, and a
 * left and right child that allows for sorting of a group of web pages.
 * 
 * @param id
 * @param webLink
 * @param leftChild
 * @param rightChild
 * @author Jack O'Keefe
 *
 */

public class WebPageNode {

	private final String id; // The id of the web page
	private final String webLink; // The web link of the web page
	private WebPageNode leftChild; // The leftChild of the the current WebPageNode
	private WebPageNode rightChild; // The rightChild of the the current WebPageNode

	/**
	 * Creates a web page object with set id and link. Left and right children are
	 * set to null
	 * 
	 * @param id
	 * @param webLink
	 */

	public WebPageNode(String id, String webLink) {
		this.id = id; // set web id
		this.webLink = webLink; // set web link
		leftChild = null;
		rightChild = null;
	}

	/**
	 * Returns the web page that is the left child of this web page
	 * 
	 * @return leftChild
	 */

	public WebPageNode getLeftChild() {
		return leftChild;
	}

	/**
	 * Sets the left child of this web page to the input left child
	 * 
	 * @param leftChild
	 */

	public void setLeftChild(WebPageNode leftChild) {
		this.leftChild = leftChild;
	}

	/**
	 * Returns the web page that is the right child of this web page
	 * 
	 * @return rightChild
	 */

	public WebPageNode getRightChild() {
		return rightChild;
	}

	/**
	 * Sets the left child of this web page to the input right child
	 * 
	 * @param rightChild
	 */

	public void setRightChild(WebPageNode rightChild) {
		this.rightChild = rightChild;
	}

	/**
	 * Returns the web page's id
	 * 
	 * @return id
	 */

	public String getId() {
		return id;
	}

	/**
	 * Returns the web page's link
	 * 
	 * @return webLink
	 */

	public String getWebLink() {
		return webLink;
	}
}