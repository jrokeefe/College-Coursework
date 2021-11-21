
//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           P10 Search Engine
// Files:           SearchEngine.java
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

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class allows for containment and BST-based sorting of multiple web pages. Web
 * pages are inserted into the engine and can then be printed out in
 * alphabetical order, counted, and called back.
 * 
 * @param root
 * @author Jack O'Keefe
 *
 */

public class SearchEngine {

	private WebPageNode root; // root of the BST-based search engine

	/**
	 * Creates an empty search engine by setting root to null.
	 * 
	 */

	public SearchEngine() {
		root = null;
	}

	/**
	 * Returns true if the search engine is empty; false otherwise
	 * 
	 */

	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Inserts an instance of WebPageNode with the given id and weblink into the
	 * search engine conforming to the search order property of a BST. This method
	 * throws an exception if the user tries to insert an entry with a duplicate id.
	 * 
	 * @param id
	 * @param webLink
	 * @throws if
	 *             a web page with the same id already exists in the engine
	 */

	public void insert(String id, String webLink) throws Exception { //

		WebPageNode webPage = new WebPageNode(id, webLink);
		if (findSame(root, id) != null) { // checks if web page with same id exists in the engine
			throw new Exception();
		}
		if (root == null) { // sets input web page to the root if the engine is empty
			root = webPage;
		} else {
			findSpot(root, webPage); // inserts the input web page into the engine keeping with BST form
		}
	}

	/**
	 * A look-up method that searches for a webPageNode with the given id in the
	 * current search engine and returns the related weblink if that webPageNode is
	 * present. Otherwise, a Warning message starting with "No web link found"
	 * should be returned back.
	 * 
	 * @param id
	 * @return weblink for the id
	 */

	public String searchWebPage(String id) {
		WebPageNode webPage = findSame(root, id); // searches for web page with specified id
		if (webPage == null) { // if none exists, ouptut error
			return "No web link found for the web page <" + id + ">.";
		} else { // return web page's link
			return webPage.getWebLink();
		}
	}

	/**
	 * Returns the number of webPageNodes stored in the search engine
	 * 
	 * @return number of webPageNodes
	 */

	public int getWebPageCount() {
		return getNum(root); // begins recursive method at the top of pyramid
	}

	/**
	 * Returns an ArrayList of String that stores all the id fields of the
	 * webPageNodes present in the current search engine, sorted in alphabetical
	 * order from left to right.
	 * 
	 * @return ArrayList of webPage ids
	 */

	public ArrayList<String> getAllWebPages() {

		ArrayList<String> ret = new ArrayList<String>(); // create array
		if (root == null) { // if engine is empty
			return ret;
		} else {
			getLeftMost(ret, root); // begin series of recursive methods at the top of the pyramid
			return ret;
		}
	}

	/**
	 * A recursive method that compares the id of the input webPage to the input id
	 * and returns the webPage that is a match. If no webPage with the specified id
	 * exists, returns null
	 * 
	 * @return ArrayList of webPage ids
	 */

	private WebPageNode findSame(WebPageNode webPage, String id) {
		if (webPage == null) { // base case to return null if the id doesn't exist in the engine
			return null;
		}
		int check = webPage.getId().compareTo(id); // compare ids
		if (check < 0) { // calls the method again with the right child if the id is lower in the
							// alphabet than the webPage's id
			return findSame(webPage.getRightChild(), id);
		}
		if (check > 0) { // calls the method again with the left child if the id is higher in the
							// alphabet than the webPage's id
			return findSame(webPage.getLeftChild(), id);
		}
		return webPage; // if the ids are the same, returns input webPage
	}

	/**
	 * A recursive method used to place a webPage in proper order to keep BST form
	 * 
	 * @param check
	 *            - the webPageNode that the search stands at
	 * @param webPage
	 *            - the webPageNode that is desired to be input
	 */

	private void findSpot(WebPageNode check, WebPageNode webPage) {
		int num = check.getId().compareTo(webPage.getId()); // compare webPage's ids
		if (num > 0) { // if higher in the alphabet
			if (check.getLeftChild() == null) { // if check's left child is currently empty
				check.setLeftChild(webPage); // set left child to the webPage
			} else {
				findSpot(check.getLeftChild(), webPage); // call the method again with check's left child
			}
		} else {
			if (check.getRightChild() == null) { // if check's right child is currently empty
				check.setRightChild(webPage); // set right child to the webPage
			} else {
				findSpot(check.getRightChild(), webPage); // call the method again with check's right child
			}
		}
	}

	/**
	 * A recursive method used to move through and add up the amount of nodes in the
	 * engine.
	 * 
	 * @param webPage
	 *            - the webPageNode that the movement stands on
	 * @return the number of nodes connected with the webPage, including itself
	 */

	private int getNum(WebPageNode webPage) {
		if (webPage == null) { // base case to return 0 when input webPage is empty
			return 0;
		} else {
			return 1 + getNum(webPage.getLeftChild()) + getNum(webPage.getRightChild()); // add to count for both itself
																							// as well as its left and
																							// right children
		}
	}

	/**
	 * A recursive method used to move through and find the highest alphabetical id
	 * in reference to the input webPage. Adds this webPage to the array, then moves
	 * back to systematically add webPages to the array in alphabetical order.
	 * 
	 * @param webPage
	 *            - the webPageNode that the movement stands on
	 * @param ArrayList
	 *            - the storage system that contains the webPages
	 */

	private void getLeftMost(ArrayList<String> list, WebPageNode webPage) {
		if (webPage.getLeftChild() == null) { // left most node
			list.add(webPage.getId()); // add to list
			getOtherChild(list, webPage); // add its rightChild node and branches
		} else if (webPage.getLeftChild().getLeftChild() != null) {
			getLeftMost(list, webPage.getLeftChild()); // calls next left node recursively
			list.add(webPage.getId()); // add to list
			getOtherChild(list, webPage); // add its rightChild node and branches
		} else { // at the left most triangle
			list.add(webPage.getLeftChild().getId()); // add left most node
			getOtherChild(list, webPage.getLeftChild()); // add left most node's right child and branches
			list.add(webPage.getId()); // add to list
			getOtherChild(list, webPage); // add its rightChild node and branches
		}
	}

	/**
	 * A recursive method used to move through and capture all nodes to the right of
	 * the reference node. Adds this webPage to the array, then moves back to
	 * systematically add webPages to the array in alphabetical order.
	 * 
	 * @param webPage
	 *            - the webPageNode that the movement stands on
	 * @param ArrayList
	 *            - the storage system that contains the webPages
	 */

	private void getOtherChild(ArrayList<String> list, WebPageNode webPage) {
		if (webPage.getRightChild() != null) { // if a right child exists
			if (webPage.getRightChild().getLeftChild() != null) { // first check to add the extensions left most node
				getLeftMost(list, webPage.getRightChild());
			} else {
				list.add(webPage.getRightChild().getId()); // add to list
				getOtherChild(list, webPage.getRightChild()); // add its rightChild node and branches
			}
		}
	}

	/**
	 * Runs a user-based system where users can add webPages to a search engine,
	 * search for ids in the search engine, and print out all the ids in the search
	 * engine
	 * 
	 * @param webPage
	 *            - the webPageNode that the movement stands on
	 * @return the number of nodes connected with the webPage, including itself
	 */

	public static void main(String[] args) throws Exception {
		SearchEngine se = new SearchEngine();
		Scanner scnr = new Scanner(System.in);
		boolean quit = false;
		String input;
		while (!quit) {
			System.out.println("");
			System.out.println("=========================== Search Engine ============================");
			System.out.println("1. Enter 'i <id> <webLink>' to insert a web page in the search engine");
			System.out.println("2. Enter 's <id>' to search a web link in the search engine");
			System.out.println("3. Enter 'p' to print all the web page ids in the search engine");
			System.out.println("4. Enter 'c' to get the count of all web pages in the search engine");
			System.out.println("5. Enter 'q' to quit the program");
			System.out.println("======================================================================\n");
			System.out.print("Please enter your command:");
			input = scnr.next();
			if (input.isEmpty()) {
				System.out.println("WARNING: Unrecognized command.");
			}
			if (input.charAt(0) == 'i') { // i inputs a new web page
				if (scnr.hasNext()) {
					String id = scnr.next(); // next String is the id
					if (scnr.hasNext()) {
						String webLink = scnr.next(); // next String is the link
						se.insert(id, webLink); // create web page and insert into search engine
					} else {
						System.out.println("WARNING: Unrecognized command."); // input not properly entered, announce
																				// error
					}
				} else {
					System.out.println("WARNING: Unrecognized command."); // input not properly entered, announce error
				}
			} else if (input.charAt(0) == 's') { // s searches for a web page in the engine
				if (scnr.hasNext()) {
					String id = scnr.next(); // next String is the id
					String response = se.searchWebPage(id); // search for id
					System.out.println(id + " - " + response); // output result of search
				} else {
					System.out.println("WARNING: Unrecognized command."); // input not properly entered, announce error
				}
			} else if (input.charAt(0) == 'p') { // p calls to print out all web pages
				ArrayList<String> list = se.getAllWebPages(); // create array of sorted web pages
				if (list.isEmpty()) {
					System.out.println("Search Engine is empty.");
				} else {
					for (int i = 0; i < list.size(); ++i) { // print out contents of array
						if (i == list.size() - 1) {
							System.out.println(list.get(i)); // last web page ends with new line
						} else {
							System.out.print(list.get(i) + ", "); // printed pages separated by a space and comma
						}
					}
				}
			} else if (input.charAt(0) == 'c') { // c calls to count the number of web pages in the engine
				System.out.println(se.getWebPageCount()); // print out search engine size
			} else if (input.charAt(0) == 'q') { // q calls to end the program
				quit = true; // breaks the while loop
			} else {
				System.out.println("WARNING: Unrecognized command."); // input not properly entered, announce error
			}
		}
		System.out.print("============================== END ===================================");
		scnr.close();
	}
}
