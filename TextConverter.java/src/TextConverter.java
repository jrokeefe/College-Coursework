//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           Text Converter
// Files:           TextConverter.java
// Course:          CS 200, Fall 2017
//
// Author:          Jack O'Keefe
// Email:           jokeefe2@wisc.edu
// Lecturer's Name: Jim
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
import java.util.Scanner;

public class TextConverter { 

	public static String action1337(String current) {
            current = current.replace('l','1');
            current = current.replace('L','1');
            current = current.replace('e','3');
            current = current.replace('E','3');
            current = current.replace('t','7');
            current = current.replace('T','7');
            current = current.replace('o','0');
            current = current.replace('O','0');
            current = current.replace('s','$');
            current = current.replace('S','$');
    	
    	
    	return current;  //FIX ME
    }

    public static String actionReverse(String current) {
    		if (current.length() > 0) {
    			int j = current.length() - 1;
    			char a = current.charAt(j);
    			String currentTrim = "" + a;
    			currentTrim = currentTrim.trim();
    			j = j-1;
    			for (int i = j; i >= 0; i--) {
    			
    				currentTrim = currentTrim + current.charAt(i);
    			
    			
    					
    			}
    			current = currentTrim;
    		}
    			
    	
    	return current;  //FIX ME
    }

    public static void main(String[] args) {
    		Scanner scnr = new Scanner(System.in);
    		System.out.println("Welcome to the Text Converter.");
    		System.out.println("Available Actions:");
    		System.out.println("  1337) convert to 1337-speak");
    		System.out.println("  rev) reverse the string");
    		System.out.println("  quit) exit the program");
    		System.out.println("");
    		System.out.print("Please enter a string: ");
    		String userString = scnr.nextLine();
    		String userAction = "LOL";
    		
    		

    			do {
        			System.out.print("Action (1337, rev, quit): ");
        			userAction = scnr.nextLine();
        			
				if (userAction.equals("1337")) {
					userString = action1337(userString);
					System.out.println(userString);
				}
			
				else if (userAction.equals("rev")) {
					userString = actionReverse(userString);
					System.out.println(userString);
				}
				
				else if (userAction.equals("quit")) {
				}
				
				else {
					System.out.println("Unrecognized action.");
					System.out.println(userString);
					}

    			}
    			while (userAction.equals("quit") == false);


    	    		System.out.println("See you next time!");
    	    		scnr.close();
    			}
 


    			
    		}
    		